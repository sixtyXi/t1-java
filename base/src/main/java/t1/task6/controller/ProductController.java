package t1.task6.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import t1.task6.dto.DebitRequest;
import t1.task6.dto.ListResponse;
import t1.task6.exception.BadRequestException;
import t1.task6.projection.ProductProjection;
import t1.task6.service.ProductService;

import java.math.BigDecimal;

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
    public void decreaseProductBalance(@RequestBody DebitRequest debitRequest) throws BadRequestException {
        Long productId = debitRequest.productId();
        if (productId == null || productId <= 0) {
            throw new BadRequestException("ID не может быть null или меньше 0");
        }

        String amount = debitRequest.amount();
        if (amount == null || amount.isEmpty()) {
            throw new BadRequestException("Amount не может быть пустым");
        }

        try {
            productService.decreaseProductBalance(productId, new BigDecimal(amount));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @GetMapping(value = "/user/{userId}")
    public ListResponse<ProductProjection> getProductsByUserId(@PathVariable("userId") Long userId) {
        return new ListResponse<>(productService.findProductsByUserId(userId));
    }

}
