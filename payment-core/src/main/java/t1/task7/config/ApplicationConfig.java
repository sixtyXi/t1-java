package t1.task7.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import t1.task7.client.ClientProperties;
import t1.task7.client.RestTemplateProperties;
import t1.task7.exception.RestTemplateResponseErrorHandler;

@Configuration
@EnableConfigurationProperties(ClientProperties.class)
@RequiredArgsConstructor
public class ApplicationConfig {

    private final ClientProperties clientProperties;

    @Bean
    public RestTemplate productsClient(RestTemplateResponseErrorHandler errorHandler) {
        RestTemplateProperties productsClient = clientProperties.getProductsClient();
        return new RestTemplateBuilder()
                .rootUri(productsClient.getUrl())
                .readTimeout(productsClient.getReadTimeout())
                .connectTimeout(productsClient.getConnectTimeout())
                .errorHandler(errorHandler)
                .build();
    }

}
