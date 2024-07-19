package au.gov.au.ssq.kiteworks;

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
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

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
                formData.put("scope", config.getClientAppScopes());
                formData.put("redirect_uri", config.getRedirectUri());

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(config.getAccessTokenUri()))
                    .header("User-Agent", config.getUserAgent())
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(BodyPublishers.ofString(getFormDataAsString(formData)))
                    .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

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
}
