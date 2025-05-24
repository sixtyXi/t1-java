package t1.task6.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import t1.task6.dto.DebitRequest;
import t1.task6.projection.ProductProjection;
import t1.task6.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
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
    public void decreaseProductBalance(@Valid DebitRequest debitRequest) {
        ProductProjection productProjection = getProductById(debitRequest.productId());

        BigDecimal newBalance = productProjection.
                getBalance()
                .subtract(new BigDecimal(debitRequest.amount()));

        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Недостаточно средств на счете");
        }

        updateProductBalance(new DebitRequest(debitRequest.productId(), newBalance.toString()));
    }

    @Transactional
    public void updateProductBalance(@Valid DebitRequest debitRequest) {
        int updated = productRepository.updateProductBalance(debitRequest.productId(), new BigDecimal(debitRequest.amount()));

        if (updated == 0) {
            throw new EntityNotFoundException("Продукт не найден");
        }
    }

}
