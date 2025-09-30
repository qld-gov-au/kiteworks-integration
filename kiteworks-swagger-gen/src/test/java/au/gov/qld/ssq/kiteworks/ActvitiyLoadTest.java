package au.gov.qld.ssq.kiteworks;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiteworks.client.model.ActivityList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.io.IOUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class ActvitiyLoadTest {


    private static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public static void setupClass() {
        objectMapper.configure(JsonParser.Feature.INCLUDE_SOURCE_IN_LOCATION, true);
    }

    @Test
    public void deserialise_runsWithoutErrors() throws IOException {
        FileInputStream fis = new FileInputStream("src/test/resources/Kiteworks API Activities Response-20250930.json");
        String sampleEvent = IOUtils.toString(fis, "UTF-8");


        // given
        ActivityList dataWrapper = null;

        // when
        dataWrapper = objectMapper.readValue(sampleEvent, ActivityList.class);

        // then
        assertThat(dataWrapper).isNotNull();
        assertThat(dataWrapper.getData().size()).isEqualTo(30);
        assertThat(dataWrapper.getData().stream().filter(a -> a.getId().equals(17087471)).toList()
                .getFirst().getData().getFile()).isNotNull();
        assertThat(dataWrapper.getData().stream().filter(a -> a.getId().equals(17087397)).toList()
                .getFirst().getData().getFolder()).isNotNull();
    }


    @Test
    public void deserialise_runsWithoutErrors2() throws IOException {
        FileInputStream fis = new FileInputStream("src/test/resources/activites.example.json");
        String sampleEvent = IOUtils.toString(fis, "UTF-8");

        // given
        ActivityList dataWrapper = null;

        // when
        dataWrapper = objectMapper.readValue(sampleEvent, ActivityList.class);

        // then
        assertThat(dataWrapper).isNotNull();
        assertThat(dataWrapper.getData().size()).isGreaterThan(50);
        assertThat(dataWrapper.getData().stream().filter(a -> a.getId().equals(16956458)).toList()
                .getFirst().getData().getFile()).isNotNull();
        assertThat(dataWrapper.getData().stream().filter(a -> a.getId().equals(16956371)).toList()
                .getFirst().getData().getFiles()).isNotEmpty();
    }
}
