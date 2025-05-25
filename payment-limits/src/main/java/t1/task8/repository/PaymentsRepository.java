package t1.task8.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import t1.task8.entity.Payment;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface PaymentsRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findPaymentByUserId(Long userId);

    @Modifying
    @Query("UPDATE Payment p SET p.amount = :amount WHERE p.userId = :userId")
    int updatePaymentAmount(@Param("userId") Long userId, @Param("amount") BigDecimal amount);

    @Modifying
    @Query("UPDATE Payment p SET p.amount = :amount")
    int resetAllPaymentsAmount(@Param("amount") BigDecimal amount);

}
