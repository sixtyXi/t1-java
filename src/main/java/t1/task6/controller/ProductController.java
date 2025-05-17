package t1.task6.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import t1.task6.projection.ProductProjection;
import t1.task6.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping(value = "/{id}")
    public ProductProjection getProductById(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }

    @GetMapping(value = "/user/{userId}")
    public List<ProductProjection> getProductsByUserId(@PathVariable("userId") Long userId) {
        return productService.findProductsByUserId(userId);
    }
}
