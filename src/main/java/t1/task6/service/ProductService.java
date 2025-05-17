package t1.task6.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import t1.task6.projection.ProductProjection;
import t1.task6.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductProjection> findProductsByUserId(Long userId) {
        return productRepository.findProductsByUser_Id(userId, ProductProjection.class);
    }

    public ProductProjection getProductById(Long id) {
        return productRepository.findProductById(id, ProductProjection.class)
                .orElseThrow(() -> new EntityNotFoundException("Продукт не найден"));
    }
}
