package t1.task7.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import t1.task7.dto.DebitRequest;
import t1.task7.dto.ListResponse;
import t1.task7.dto.ProductResponse;
import t1.task7.service.PaymentProductService;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class PaymentProductController {

    private final PaymentProductService service;

    @GetMapping(value = "/user/{userId}")
    public ListResponse<ProductResponse> getProductsByUserId(@PathVariable("userId") Long userId) {
        return service.getProductsByUserId(userId);
    }

    @PostMapping(value = "/debit")
    public void decreaseProductBalance(@RequestBody DebitRequest request) {
        service.decreaseProductBalance(request);
    }

}
