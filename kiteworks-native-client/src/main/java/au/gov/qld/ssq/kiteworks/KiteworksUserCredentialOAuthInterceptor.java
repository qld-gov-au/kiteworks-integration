package au.gov.qld.ssq.kiteworks;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

/**
 * Interceptor for Kiteworks API requests that uses OAuth 2.0 with a Username Password authorization.
 *
 * Ensure "User Credential" is enabled
 *
 * Please ensure that 2FA/OTP/MFA is disabled for the user account used to authenticate. (Custom profile set)
 *
 * You may also want to set password expiry to infinite for server 2 server user accounts.
 *
 * It is unknown how long this interface will work, but based on the fact the kiteworks API is Python based, it is likely
 * to continue working for many years to come.
 *
 * https://oauth.net/2/grant-types/password/
 * The Password grant type is a legacy way to exchange a user's credentials for an access token. Because the client application has to collect the user's password and send it to the authorization server, it is not recommended that this grant be used at all anymore.
 *
 * This flow provides no mechanism for things like multifactor authentication or delegated accounts, so is quite limiting in practice.
 *
 * The latest OAuth 2.0 Security Best Current Practice disallows the password grant entirely, and the grant is not defined in OAuth 2.1.
 *
 **/
public class KiteworksUserCredentialOAuthInterceptor implements Consumer<HttpRequest.Builder> {

    private static final Logger LOG = LoggerFactory.getLogger(KiteworksUserCredentialOAuthInterceptor.class);
    private final KiteworksConfig config;
    private String accessToken;
    private Instant tokenExpiry = Instant.now().minusSeconds(120); //start expired
    private final ReentrantLock lock = new ReentrantLock();
    private static final ObjectMapper objectMapper = new ObjectMapper();



    public KiteworksUserCredentialOAuthInterceptor(KiteworksConfig config) {
        if (isEmpty(config.getClientId()) || isEmpty(config.getClientSecret())) {
            throw new IllegalArgumentException("Client ID and secret must be provided");
        }
        if (isEmpty(config.getUsername()) || isEmpty(config.getPassword())) {
            throw new IllegalArgumentException("Username and password must be provided");
        }
        if (isEmpty(config.getScope())) {
            throw new IllegalArgumentException("Client app scopes must be provided");
        }
        this.config = config;
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
                formData.put("grant_type", "password");
                formData.put("scope", config.getScope());
                formData.put("username", config.getUsername());
                formData.put("password", config.getPassword());

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(config.getAccessTokenUri()))
                    .header("User-Agent", config.getUserAgent())
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(BodyPublishers.ofString(getFormDataAsString(formData)))
                    .build();

                // Parse the response to extract the access token and its expiry time
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() >= 400 && response.statusCode() <= 499) {
                    LOG.error("Failed to fetch access token, incorrect username/password or client configuration?, username: {}..., client: {}... status code: {} body: {}",
                            config.getUsername().substring(0, config.getClientId().length() / 2),
                            config.getClientId().substring(0, config.getClientId().length() / 2),
                            response.statusCode(), response.body());
                    throw new RuntimeException("Failed to fetch access token, status code: " + response.statusCode() + " body: " + response.body());
                }
                if (response.statusCode() != 200) {
                    LOG.error("Failed to fetch access token, status code: {}", response.statusCode());
                    throw new RuntimeException("Failed to fetch access token, status code: " + response.statusCode());
                }
                JsonNode jsonNode = objectMapper.readTree(response.body());
                accessToken = jsonNode.get("access_token").asText();
                int expiresIn = jsonNode.get("expires_in").asInt();
                tokenExpiry = Instant.now().plusSeconds(expiresIn);

            } catch (InterruptedException e) {
                LOG.trace("Was interrupted, moving on");
                //no need
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                LOG.error("Failed to fetch access token", e);
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

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.isEmpty();
    }
}
