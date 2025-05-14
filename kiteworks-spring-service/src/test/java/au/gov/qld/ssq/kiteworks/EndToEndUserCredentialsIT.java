package au.gov.qld.ssq.kiteworks;


import com.kiteworks.client.ApiException;
import com.kiteworks.client.api.FilesApi;
import com.kiteworks.client.api.FoldersApi;
import com.kiteworks.client.api.UploadsApi;
import com.kiteworks.client.model.RestFoldersSharedGet200Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@SpringBootTest( classes = {KiteworksSpringService.class, ProxyConfig.class})
@EnableConfigurationProperties
@ActiveProfiles("cicd-user-credentials")
public class EndToEndUserCredentialsIT {

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

        RestFoldersSharedGet200Response foldersSharedGet = foldersApi.restFoldersSharedGet(null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null);
        assertThat(foldersSharedGet.getMetadata().getTotal()).isGreaterThan(1);
    }
}
