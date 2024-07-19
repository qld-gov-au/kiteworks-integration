package au.gov.qld.ssq.kiteworks;

import au.gov.au.ssq.kiteworks.KiteworksConfig;
import au.gov.au.ssq.kiteworks.KiteworksService;
import com.kiteworks.client.api.FilesApi;
import com.kiteworks.client.api.FoldersApi;
import com.kiteworks.client.api.UploadsApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //Set as configuration but must have kiteworks.enabled: true to enable service
@ConditionalOnProperty(prefix = "kiteworks", name = "enabled", havingValue = "true", matchIfMissing = false)
@EnableConfigurationProperties
public class KiteworksSpringService {

    @Bean
    @ConfigurationProperties(prefix = "kiteworks")
    public KiteworksConfig kiteworksConfig() {
        return new KiteworksConfig();
    }

    @Bean
    public KiteworksService kiteworksService(KiteworksConfig kiteworksConfig) {
        return new KiteworksService(kiteworksConfig);
    }

    @Bean
    public UploadsApi uploadsApi(KiteworksService kiteworksService) {
        return kiteworksService.uploadsApi();
    }
    @Bean
    public FoldersApi foldersApi(KiteworksService kiteworksService) {
        return kiteworksService.foldersApi();
    }
    @Bean
    public FilesApi filesApi(KiteworksService kiteworksService) {
        return kiteworksService.filesApi();
    }
}
