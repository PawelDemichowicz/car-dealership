package pl.zajavka.integration;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import pl.zajavka.integration.configuration.AbstractIT;

@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class MechanicControllerIT extends AbstractIT {

    private final TestRestTemplate testRestTemplate;

    @Test
    void homePageWorksCorrectly() {
        String url = String.format("http://localhost:%s%s/mechanic", port, basePath);

        String page = this.testRestTemplate.getForObject(url, String.class);
        Assertions.assertThat(page).contains("Check the following service requests and work on them!");
    }
}
