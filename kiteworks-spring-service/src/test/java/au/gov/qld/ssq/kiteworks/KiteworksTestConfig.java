package au.gov.qld.ssq.kiteworks;

import com.kiteworks.client.api.ActivitiesApi;
import com.kiteworks.client.api.FilesApi;
import com.kiteworks.client.api.FoldersApi;
import com.kiteworks.client.api.UploadsApi;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.when;

@TestConfiguration
public class KiteworksTestConfig {

    UploadsApi uploadsApi;
    FoldersApi foldersApi;
    FilesApi filesApi;
    ActivitiesApi activitiesApi;

    @Primary
    @Bean
    public KiteworksService kiteworksService() {
        uploadsApi = Mockito.mock(UploadsApi.class);
        foldersApi = Mockito.mock(FoldersApi.class);
        filesApi = Mockito.mock(FilesApi.class);
        activitiesApi = Mockito.mock(ActivitiesApi.class);

        KiteworksService kiteworksService = Mockito.mock(KiteworksService.class);

        when(kiteworksService.uploadsApi()).thenReturn(uploadsApi);
        when(kiteworksService.foldersApi()).thenReturn(foldersApi);
        when(kiteworksService.filesApi()).thenReturn(filesApi);
        when(kiteworksService.activitiesApi()).thenReturn(activitiesApi);
        return kiteworksService;
    }
}
