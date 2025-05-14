package au.gov.qld.ssq.kiteworks;

import com.kiteworks.client.ApiClient;
import com.kiteworks.client.api.ActivitiesApi;
import com.kiteworks.client.api.FilesApi;
import com.kiteworks.client.api.FoldersApi;
import com.kiteworks.client.api.UploadsApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


public class KiteworksServiceTest {

    @Mock
    private KiteworksConfig kiteworksConfig;

    @Mock
    private ApiClient apiClient;

    @InjectMocks
    private KiteworksService kiteworksService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Mocking ApiClient creation to avoid actual instantiation
        when(kiteworksConfig.getSignatureKey()).thenReturn("abc123");
        when(kiteworksConfig.getAuthorizationGrantType()).thenReturn("user-credentials");
        when(kiteworksConfig.getBaseUri()).thenReturn("https://kiteworks.dcj.nsw.gov.au/");
        when(kiteworksConfig.getClientId()).thenReturn("client-id");
        when(kiteworksConfig.getClientSecret()).thenReturn("client-secret");
        when(kiteworksConfig.getUsername()).thenReturn("username");
        when(kiteworksConfig.getPassword()).thenReturn("password");
        when(kiteworksConfig.getScope()).thenReturn("*/*/*");
        when(apiClient.getBaseUri()).thenReturn("https://kiteworks.dcj.nsw.gov.au/");
    }

    @Test
    void testKiteworksDefaultApiClient() {
        ApiClient apiClient = kiteworksService.kiteworksDefaultApiClient();
        assertThat(apiClient).isNotNull();
    }

    @Test
    void testActivitiesApi() {
        ActivitiesApi activitiesApi = kiteworksService.activitiesApi();
        assertThat(activitiesApi).isNotNull();
    }

    @Test
    void testFoldersApi() {
        FoldersApi foldersApi = kiteworksService.foldersApi();
        assertThat(foldersApi).isNotNull();
    }

    @Test
    void testFilesApi() {
        FilesApi filesApi = kiteworksService.filesApi();
        assertThat(filesApi).isNotNull();
    }

    @Test
    void testUploadsApi() {
        UploadsApi uploadsApi = kiteworksService.uploadsApi();
        assertThat(uploadsApi).isNotNull();
    }
}
