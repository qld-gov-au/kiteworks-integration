package au.gov.qld.ssq.kiteworks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public abstract class AbstractOAuthInterceptor  implements Consumer<HttpRequest.Builder> {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final ReentrantLock lock = new ReentrantLock();
    private String accessToken;
    private Instant tokenExpiry = Instant.now().minusSeconds(120); //start expired

    abstract KiteworksConfig getConfig();
    abstract HttpResponse<String> fetchAccessTokenInternal() throws IOException, InterruptedException, GeneralSecurityException;

    @Override
    public void accept(HttpRequest.Builder builder) {
        if (isTokenExpired()) {
            fetchAccessToken();
        }
        builder.header("Authorization", "Bearer " + accessToken);
        builder.header("X-Accellion-Version", getConfig().getKiteworksApiVersion());
        builder.header("User-Agent", getConfig().getUserAgent());
    }

    protected boolean isTokenExpired() {
        // Subtract a minute to account for network delays and recollection
        return Instant.now().isAfter(tokenExpiry.minusSeconds(60));
    }

    protected void fetchAccessToken() {
        // Get a new token if first. If another process is getting a token, skip unless token is fully expired
        if (lock.tryLock() || Instant.now().isAfter(tokenExpiry) ) {
            try {
                lock.lock();
                if (!isTokenExpired()) {
                    return; // Token was refreshed by another thread
                }
                HttpResponse<String> response = fetchAccessTokenInternal();

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

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.isEmpty();
    }

    protected static String getFormDataAsString(Map<String, String> formData) {
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
}
