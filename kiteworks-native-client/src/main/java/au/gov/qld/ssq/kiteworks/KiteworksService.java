package au.gov.qld.ssq.kiteworks;

import com.kiteworks.client.ApiClient;
import com.kiteworks.client.api.ActivitiesApi;
import com.kiteworks.client.api.FilesApi;
import com.kiteworks.client.api.FoldersApi;
import com.kiteworks.client.api.UploadsApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Consumer;

import static au.gov.qld.ssq.kiteworks.KiteworksConfig.Mode.*;

public class KiteworksService {

    private static final Logger LOG = LoggerFactory.getLogger(KiteworksService.class);

    KiteworksConfig config;

    ApiClient apiClient;

    public KiteworksService(KiteworksConfig config) {
        this.config = config;
    }

    public ApiClient kiteworksDefaultApiClient() {
        if (Objects.isNull(apiClient)) {

            if (Objects.isNull(config.getAuthorizationGrantType()) || config.getAuthorizationGrantType().isEmpty()) {
                LOG.error("Kiteworks mode is not set, defaulting to User Credentials");
                config.setAuthorizationGrantType(USER_CREDENTIALS.getValue());
            }

            Consumer<HttpRequest.Builder> kiteworksOAuthInterceptor;
            if (SIGNATURE.equals(KiteworksConfig.Mode.fromValue(config.getAuthorizationGrantType()))) {
                kiteworksOAuthInterceptor = new KiteworksSignatureOAuthInterceptor(config);
            } else if (USER_CREDENTIALS.equals(KiteworksConfig.Mode.fromValue(config.getAuthorizationGrantType()))) {
                kiteworksOAuthInterceptor = new KiteworksUserCredentialOAuthInterceptor(config);
            } else {
                throw new IllegalArgumentException("Invalid mode: " + config.getAuthorizationGrantType());
            }

            apiClient = new ApiClient(ApiClient.createDefaultHttpClientBuilder(), ApiClient.createDefaultObjectMapper(), config.getBaseUri());
            apiClient.setRequestInterceptor(kiteworksOAuthInterceptor);
        }
        return apiClient;
    }

    public ActivitiesApi activitiesApi() {
        return new ActivitiesApi(kiteworksDefaultApiClient());
    }
    public FoldersApi foldersApi() {
        return new FoldersApi(kiteworksDefaultApiClient());
    }
    public FilesApi filesApi() {
        return new FilesApi(kiteworksDefaultApiClient());
    }
    public UploadsApi uploadsApi() {
        return new UploadsApi(kiteworksDefaultApiClient());
    }
}
