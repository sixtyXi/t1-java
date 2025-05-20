package t1.task7.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class PaymentProductController {
    @GetMapping("")
    public String getProducts() {
        return "Hello";
    }
}
