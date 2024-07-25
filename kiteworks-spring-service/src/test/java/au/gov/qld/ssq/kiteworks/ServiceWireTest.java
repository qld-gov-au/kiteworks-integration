package au.gov.qld.ssq.kiteworks;


import com.kiteworks.client.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@SpringBootTest( classes = {KiteworksTestConfig.class, KiteworksSpringService.class})
@Import({KiteworksTestConfig.class})
@EnableConfigurationProperties
@ActiveProfiles("test")
public class ServiceWireTest {

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
    public void testBeansAreWiredCorrectly() {
        try {
            for (String profileName : env.getActiveProfiles()) {
                System.out.println("Currently active profile - " + profileName);
            }
        } catch (Exception e) {
            System.out.println("could not get active profile");
        }

        // Act and Assert
        assertThat(kiteworksConfig).isNotNull();
        assertThat(uploadsApi).isNotNull();
        assertThat(foldersApi).isNotNull();
        assertThat(filesApi).isNotNull();

    }
}
