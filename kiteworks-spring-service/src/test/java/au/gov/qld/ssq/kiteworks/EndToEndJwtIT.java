package au.gov.qld.ssq.kiteworks;

import au.gov.qld.ssq.kiteworks.helpers.KiteworksApiHelper;
import au.gov.qld.ssq.kiteworks.helpers.TempFileResource;
import au.gov.qld.ssq.kiteworks.model.RestFoldersIdActionsFilePostRequestBuilder;
import au.gov.qld.ssq.kiteworks.model.RestFoldersIdActivitiesGetRequestBuilder;
import com.kiteworks.client.ApiException;
import com.kiteworks.client.ApiResponse;
import com.kiteworks.client.api.FilesApi;
import com.kiteworks.client.api.FoldersApi;
import com.kiteworks.client.api.UploadsApi;
import com.kiteworks.client.model.ActivityList;
import com.kiteworks.client.model.ModelFile;
import com.kiteworks.client.model.RestFoldersActionsRecoverPatch200Response;
import org.apache.commons.lang3.Strings;
import org.assertj.core.api.Fail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest( classes = {KiteworksSpringService.class, ProxyConfig.class})
@EnableConfigurationProperties
@ActiveProfiles("cicd-jwt")
public class EndToEndJwtIT {

    @Autowired
    private KiteworksService kiteworksService;

    @Autowired
    private KiteworksConfig kiteworksConfig;

    @Autowired
    private UploadsApi uploadsApi;

    @Autowired
    private FoldersApi foldersApi;

    @Autowired
    private FilesApi filesApi;

    @Autowired
    private Environment env;

    @Test
    public void testBeansAreWiredCorrectly() throws ApiException {

        try {
            for (String profileName : env.getActiveProfiles()) {
                System.out.println("Currently active profile - " + profileName);
            }
        } catch (Exception e) {
            System.out.println("could not get active profile");
        }

        RestFoldersActionsRecoverPatch200Response foldersSharedGet = foldersApi.restFoldersSharedGet(null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null);
        assertThat(foldersSharedGet.getMetadata().getTotal()).isGreaterThan(1);

        UUID uuid = UUID.fromString(env.getProperty("KITEWORKS_ACTIVITY_LIST_UUID"));
        ActivityList activityList = foldersApi.restFoldersIdActivitiesGet(uuid, null
                , null, null, null, null, null, null, null, null
                , null, null, null, 1000, null, null, null);
        assertThat(activityList.getData().size()).isGreaterThanOrEqualTo(0);
    }

    @Test
    public void testActivityCollectionWorksCorrectly() throws ApiException {
        try {
            for (String profileName : env.getActiveProfiles()) {
                System.out.println("Currently active profile - " + profileName);
            }
        } catch (Exception e) {
            System.out.println("could not get active profile");
        }

        UUID uuid = UUID.fromString(env.getProperty("KITEWORKS_ACTIVITY_LIST2_UUID"));
        ActivityList activityList = foldersApi.restFoldersIdActivitiesGet(uuid, null
                , null, null, null, null, null, null, null, null
                , null, null, null, 1000, null, null, null);
        assertThat(activityList.getData().size()).isGreaterThanOrEqualTo(0);
    }

    @Test
    public void shouldUploadFileAndThenShowUpOnActivityList() throws IOException, ApiException {
        String tempFolder = "3decab93-a7cf-4e0a-b622-eb9a201498b4";
        KiteworksApiHelper kiteworksApiHelper = new KiteworksApiHelper(foldersApi, filesApi);
        try (TempFileResource tempFile = new TempFileResource("testFile.txt", "a,b,c\r\nd,e,f".getBytes(StandardCharsets.UTF_8))) {
            ApiResponse<ModelFile> response = kiteworksApiHelper.doUpload(new RestFoldersIdActionsFilePostRequestBuilder()
                    .withId("3decab93-a7cf-4e0a-b622-eb9a201498b4")
                    .withBody(tempFile.getPath().toFile())
                    .withReturnEntity(true).build());

            RestFoldersIdActivitiesGetRequestBuilder builder = new RestFoldersIdActivitiesGetRequestBuilder()
                    .withId(UUID.fromString(tempFolder))
                    .withLimit(1000);
            ApiResponse<ActivityList> responseActivity = kiteworksApiHelper.restFoldersIdActivitiesGetWithHttpInfo(builder);

            AtomicBoolean foundUploadedFile = new AtomicBoolean(false);
            responseActivity.getData().getData().forEach(activity -> {
                if (activity.getData().getFile() != null
                        && Strings.CI.equals(activity.getData().getFile().getId().toString(), response.getData().getId().toString())) {
                    foundUploadedFile.set(true);
                }
            });

            if (!foundUploadedFile.get()) {
                Fail.fail("did not find uploaded filed");
            }
        }
    }
}
