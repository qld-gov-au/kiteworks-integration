package au.gov.qld.ssq.kiteworks;

import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.kiteworks.client.ApiClient;
import com.kiteworks.client.api.ActivitiesApi;
import com.kiteworks.client.api.FilesApi;
import com.kiteworks.client.api.FoldersApi;
import com.kiteworks.client.api.UploadsApi;

import java.net.http.HttpClient;
import java.text.SimpleDateFormat;
import java.util.Locale;
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
            objectMapper.enable(StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION.mappedFeature());
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US));
            objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
//            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
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
