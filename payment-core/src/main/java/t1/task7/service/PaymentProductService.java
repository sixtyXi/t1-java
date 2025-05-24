package t1.task7.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import t1.task7.dto.DebitRequest;
import t1.task7.dto.ListResponse;
import t1.task7.dto.ProductResponse;

@Service
public class PaymentProductService {

    private final RestTemplate productsClient;

    public PaymentProductService(@Qualifier("productsClient") RestTemplate restTemplate) {
        this.productsClient = restTemplate;
    }

    public ListResponse<ProductResponse> getProductsByUserId(Long userId) {
        return productsClient.exchange(
                "/user/{userId}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ListResponse<ProductResponse>>() {},
                userId
        ).getBody();
    }

    public void decreaseProductBalance(DebitRequest debitRequest) {
        productsClient.postForEntity(
                "/debit",
                debitRequest,
                Void.class
        );
    }

}
