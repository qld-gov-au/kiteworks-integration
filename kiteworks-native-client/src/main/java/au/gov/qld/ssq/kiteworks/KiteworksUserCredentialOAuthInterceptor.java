package au.gov.qld.ssq.kiteworks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

/**
 * Interceptor for Kiteworks API requests that uses OAuth 2.0 with a Username Password authorization.
 *
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
 * User Credential are expected to be deprecated in a future version preference now is JWT.
 **/
@Deprecated( since = "Kiteworks Version 9.3.1-ng30 - 18 Mar 2026")
public class KiteworksUserCredentialOAuthInterceptor extends AbstractOAuthInterceptor implements Consumer<HttpRequest.Builder> {

    private static final Logger LOG = LoggerFactory.getLogger(KiteworksUserCredentialOAuthInterceptor.class);
    private final KiteworksConfig config;

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
    protected KiteworksConfig getConfig() {
        return config;
    }

    protected HttpResponse<String> fetchAccessTokenInternal() throws IOException, InterruptedException {
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
                    (Objects.isNull(config.getUsername())) ? "Is Null" : config.getUsername().substring(0, config.getUsername().length() / 2),
                    (Objects.isNull(config.getClientId())) ? "Is Null" : config.getClientId().substring(0, config.getClientId().length() / 2),
                    response.statusCode(), response.body());
            throw new RuntimeException("Failed to fetch access token, status code: " + response.statusCode() + " body: " + response.body());
        }
        return response;
    }
}
