package t1.task6.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import t1.task6.dto.DebitRequest;
import t1.task6.dto.ListResponse;
import t1.task6.projection.ProductProjection;
import t1.task6.service.ProductService;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping(value = "/{id}")
    public ProductProjection getProductById(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "/debit")
    public void decreaseProductBalance(@Valid @RequestBody DebitRequest debitRequest) {
        productService.decreaseProductBalance(debitRequest);
    }

    @GetMapping(value = "/user/{userId}")
    public ListResponse<ProductProjection> getProductsByUserId(@PathVariable("userId") Long userId) {
        return new ListResponse<>(productService.findProductsByUserId(userId));
    }

}
