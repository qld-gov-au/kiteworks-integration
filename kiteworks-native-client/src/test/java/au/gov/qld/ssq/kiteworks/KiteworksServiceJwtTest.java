package au.gov.qld.ssq.kiteworks;

import com.kiteworks.client.ApiClient;
import com.kiteworks.client.api.ActivitiesApi;
import com.kiteworks.client.api.FilesApi;
import com.kiteworks.client.api.FoldersApi;
import com.kiteworks.client.api.UploadsApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


public class KiteworksServiceJwtTest {

    private KiteworksConfig kiteworksConfig;

    @Mock
    private ApiClient apiClient;

    private KiteworksService kiteworksService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        kiteworksConfig = new KiteworksConfig();

        // Mocking ApiClient creation to avoid actual instantiation
        kiteworksConfig.withSignatureKey("abc123")
                .withAuthorizationGrantType("jwt")
                .withBaseUri("https://kiteworks.dcj.nsw.gov.au/")
                .withClientId("client-id")
                .withClientSecret("client-secret")
                .withUsername("username@example.com") //The identity, if you are oidc'ing your own jwt broker, then this would be email if sub is not email
                .withAudience("https://kiteworks.dcj.nsw.gov.au") //This is the kiteworks site in question is the jwt audience restriction
                .withIssuer("https://myclientsite.com/") //Issuer when configuring $site/cfadmin/customClients API. (a pre-shared url usually)
                .withPrivateKey("-----BEGIN PRIVATE KEY----- MIIJQg== -----END PRIVATE KEY-----")
                .withScope("*/*/*");
        when(apiClient.getBaseUri()).thenReturn("https://kiteworks.dcj.nsw.gov.au/");
        kiteworksService = new KiteworksService(kiteworksConfig);
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
