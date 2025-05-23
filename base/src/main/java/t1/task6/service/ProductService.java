package t1.task6.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import t1.task6.projection.ProductProjection;
import t1.task6.repository.ProductRepository;

import java.math.BigDecimal;
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

    @Transactional
    public void decreaseProductBalance(Long productId, BigDecimal amount) {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("ID не может быть null");
        }

        if (amount == null) {
            throw new IllegalArgumentException("Не указана сумма списания");
        }
        ProductProjection productProjection = getProductById(productId);
        BigDecimal newBalance = productProjection.getBalance().subtract(amount);

        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Недостаточно средств на счете");
        }

        updateProductBalance(productId, newBalance);
    }

    @Transactional
    public void updateProductBalance(Long productId, BigDecimal productBalance) {
        int updated = productRepository.updateProductBalance(productId, productBalance);

        if (updated == 0) {
            throw new EntityNotFoundException("Продукт не найден");
        }
    }

}
