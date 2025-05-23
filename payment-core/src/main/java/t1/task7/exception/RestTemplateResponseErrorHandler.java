package t1.task7.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import t1.task7.dto.ProductsApiError;

import java.io.IOException;
import java.net.URI;

@Component
@RequiredArgsConstructor
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    private final ObjectMapper mapper;

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        HttpStatusCode statusCode = response.getStatusCode();
        return statusCode.is4xxClientError() || statusCode.is5xxServerError();
    }

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
            ProductsApiError apiError = mapper.readValue(response.getBody(), ProductsApiError.class);
            throw new IntegrationException(apiError.message(), response.getStatusCode().value());
        }
    }

}
