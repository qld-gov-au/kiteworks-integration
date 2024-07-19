package au.gov.au.ssq.kiteworks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiteworks.client.ApiClient;
import com.kiteworks.client.api.ActivitiesApi;
import com.kiteworks.client.api.FilesApi;
import com.kiteworks.client.api.FoldersApi;
import com.kiteworks.client.api.UploadsApi;

import java.net.http.HttpClient;
import java.util.Objects;

public class KiteworksService {

    KiteworksConfig config;

    ApiClient apiClient;

    public KiteworksService(KiteworksConfig config) {
        this.config = config;
    }

    public ApiClient kiteworksDefaultApiClient() {
        if (Objects.isNull(apiClient)) {
            HttpClient.Builder httpClientBuilder = HttpClient.newBuilder();
            ObjectMapper objectMapper = new ObjectMapper();
            KiteworksSignatureOAuthInterceptor kiteworksSignatureOAuthInterceptor = new KiteworksSignatureOAuthInterceptor(config);
            apiClient = new ApiClient(httpClientBuilder, objectMapper, config.getBaseUri());
            apiClient.setRequestInterceptor(kiteworksSignatureOAuthInterceptor);
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
