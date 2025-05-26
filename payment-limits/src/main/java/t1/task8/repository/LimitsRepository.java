package t1.task8.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import t1.task8.entity.Limit;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface LimitsRepository extends JpaRepository<Limit, Long> {

    Optional<Limit> findLimitByUserId(Long userId);

    @Modifying
    @Query("UPDATE Limit p SET p.userLimit = :userLimit WHERE p.userId = :userId")
    int updateUserLimit(@Param("userId") Long userId, @Param("userLimit") BigDecimal userLimit);

    @Modifying
    @Query("UPDATE Limit p SET p.userLimit = :userLimit")
    int resetAllLimits(@Param("userLimit") BigDecimal userLimit);

}
