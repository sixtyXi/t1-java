package t1.task6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import t1.task6.entity.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    <T> List<T> findProductsByUser_Id(Long userId, Class<T> type);

    <T> Optional<T> findProductById(Long id, Class<T> type);
}
