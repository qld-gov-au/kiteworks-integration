package au.gov.qld.ssq.kiteworks;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

/**
 * Interceptor for Kiteworks API requests that uses OAuth 2.0 with a signature-based authorization code.
 *
 * Ensure "Signature Authorization" is enabled
 *
 * Only use this class for high trust applications that need to access the Kiteworks API as different users. Use with caution.
 *
 * Please use {@link KiteworksUserCredentialOAuthInterceptor} for low trust applications that need to access the Kiteworks API as a single user.
 *
 *
 *
 * Please see the Kiteworks API documentation for more information on OAuth 2.0 authorization.
 * <a href="https://developer.kiteworks.com/api-guides/authentication.htm">Signature Authorization flow</a>
 *
 * Kiteworks offers Signature Authorization flow for trusted apps where user interaction is impossible or undesirable.
 * This is mostly applicable when some backend servers in your corporate network need to communicate with Kiteworks or
 * when your app handles user authentication on its own.
 *
 * Note: Apps using Signature Authorization flow can access any user's content, simply by specifying their email address.
 * As such, these apps should only be accessible by trusted employees with high security clearance.
 */
public class KiteworksSignatureOAuthInterceptor implements Consumer<HttpRequest.Builder> {

    private static final Logger LOG = LoggerFactory.getLogger(KiteworksSignatureOAuthInterceptor.class);
    private final KiteworksConfig config;
    private String accessToken;
    private Instant tokenExpiry = Instant.now().minusSeconds(120); //start expired
    private final ReentrantLock lock = new ReentrantLock();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String HMAC_ALGORITHM = "HmacSHA1";
    private final Mac mac;

    public KiteworksSignatureOAuthInterceptor(KiteworksConfig config) {
        if (isEmpty(config.getClientId()) || isEmpty(config.getClientSecret())) {
            throw new IllegalArgumentException("Client ID and secret must be provided");
        }
        if (isEmpty(config.getRedirectUri()) || isEmpty(config.getSignatureKey())|| isEmpty(config.getUserId())) {
            throw new IllegalArgumentException("Redirect URI, signature key, UserId must be provided");
        }
        if (isEmpty(config.getScope())) {
            throw new IllegalArgumentException("Scopes must be provided");
        }

        this.config = config;

        final SecretKeySpec keySpec = new SecretKeySpec(config.getSignatureKey().getBytes(StandardCharsets.UTF_8), HMAC_ALGORITHM);
        try {
            mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new IllegalArgumentException("Unable to initialise token generator from supplied key", e);
        }
    }

    @Override
    public void accept(HttpRequest.Builder builder) {
        if (isTokenExpired()) {
            fetchAccessToken();
        }
        builder.header("Authorization", "Bearer " + accessToken);
        builder.header("X-Accellion-Version", config.getKiteworksApiVersion());
        builder.header("User-Agent", config.getUserAgent());
    }

    private boolean isTokenExpired() {
        // Subtract a minute to account for network delays and recollection
        return Instant.now().isAfter(tokenExpiry.minusSeconds(60));
    }

    private void fetchAccessToken() {
        // Get a new token if first. If another process is getting a token, skip unless token is fully expired
        if (lock.tryLock() || Instant.now().isAfter(tokenExpiry) ) {
            try {
                lock.lock();
                if (!isTokenExpired()) {
                    return; // Token was refreshed by another thread
                }


                Map<String, String> formData = new HashMap<>();
                formData.put("client_id", config.getClientId());
                formData.put("client_secret", config.getClientSecret());
                formData.put("grant_type", "authorization_code");
                formData.put("code", generateSignatureAuthorizationCode());
                formData.put("scope", config.getScope());
                formData.put("redirect_uri", config.getRedirectUri());

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(config.getAccessTokenUri()))
                    .header("User-Agent", config.getUserAgent())
                    .header("accept", "*/*")
                    .header("cache-control", "no-cache")
                    .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                    .POST(BodyPublishers.ofString(getFormDataAsString(formData)))
                    .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() >= 400 && response.statusCode() <= 499) {
                    LOG.error("Failed to fetch access token, incorrect username/password or client configuration?, username: {}..., client: {}... status code: {} body: {}",
                        (Objects.isNull(config.getUserId()))  ? "Is Null" : config.getUserId().substring(0, config.getUserId().length() / 2),
                        (Objects.isNull(config.getClientId()))  ? "Is Null" : config.getClientId().substring(0, config.getClientId().length() / 2),
                            response.statusCode(), response.body());
                    throw new RuntimeException("Failed to fetch access token, status code: " + response.statusCode() + " body: " + response.body());
                }
                if (response.statusCode() != 200) {
                    LOG.error("Failed to fetch access token, status code: {}", response.statusCode());
                    throw new RuntimeException("Failed to fetch access token, status code: " + response.statusCode());
                }
                // Parse the response to extract the access token and its expiry time
                JsonNode jsonNode = objectMapper.readTree(response.body());
                accessToken = jsonNode.get("access_token").asText();
                int expiresIn = jsonNode.get("expires_in").asInt();
                tokenExpiry = Instant.now().plusSeconds(expiresIn);

            } catch (InterruptedException e) {
                //no need
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to fetch access token", e);
            } finally {
                lock.unlock();
            }
        }
    }

    private static String getFormDataAsString(Map<String, String> formData) {
        StringBuilder formBodyBuilder = new StringBuilder();
        for (Map.Entry<String, String> singleEntry : formData.entrySet()) {
            if (!formBodyBuilder.isEmpty()) {
                formBodyBuilder.append("&");
            }
            formBodyBuilder.append(URLEncoder.encode(singleEntry.getKey(), StandardCharsets.UTF_8));
            formBodyBuilder.append("=");
            formBodyBuilder.append(URLEncoder.encode(singleEntry.getValue(), StandardCharsets.UTF_8));
        }
        return formBodyBuilder.toString();
    }


    private static final SecureRandom NONCES = new SecureRandom();
    private static final String AUTH_CODE_DELIMITER = "|@@|";
    private static final String SIGNATURE_BASE_FORMAT = "%s" + AUTH_CODE_DELIMITER + "%s" + AUTH_CODE_DELIMITER + "%s" + AUTH_CODE_DELIMITER + "%s";
    private static final String AUTH_CODE_FORMAT = SIGNATURE_BASE_FORMAT + AUTH_CODE_DELIMITER + "%s";
    public String generateSignatureAuthorizationCode() {
        final long timestamp = System.currentTimeMillis();
        final int nonce = NONCES.nextInt(999999);
        final byte[] signatureBase = String.format(SIGNATURE_BASE_FORMAT,
            config.getClientId(), config.getUserId(), timestamp, nonce
        ).getBytes(StandardCharsets.UTF_8);

        final byte[] result;
        synchronized (mac) {
            result = mac.doFinal(signatureBase);
            mac.reset();
        }

        return String.format(AUTH_CODE_FORMAT,
            Base64.getEncoder().encodeToString(config.getClientId().getBytes(StandardCharsets.UTF_8)),
            Base64.getEncoder().encodeToString(config.getUserId().getBytes(StandardCharsets.UTF_8)),
            timestamp,
            nonce,
            new String(Hex.encode(result))
        );
    }


    public final class Hex {
        private static final char[] HEX = "0123456789abcdef".toCharArray();

        private Hex() {
        }

        public static char[] encode(byte[] bytes) {
            int nBytes = bytes.length;
            char[] result = new char[2 * nBytes];
            int j = 0;
            byte[] var4 = bytes;
            int var5 = bytes.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                byte aByte = var4[var6];
                result[j++] = HEX[(240 & aByte) >>> 4];
                result[j++] = HEX[15 & aByte];
            }

            return result;
        }
    }

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.isEmpty();
    }
}
