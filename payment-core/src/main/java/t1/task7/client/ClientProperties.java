package t1.task7.client;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "integration.clients")
public class ClientProperties {

    private final RestTemplateProperties productsClient;

}
