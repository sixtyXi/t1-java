package t1.task8.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import t1.task8.config.LimitsProperties;
import t1.task8.dto.LimitChangeRequest;
import t1.task8.entity.Limit;
import t1.task8.exception.BadRequestException;
import t1.task8.repository.LimitsRepository;

import java.math.BigDecimal;

@Service
@Validated
@RequiredArgsConstructor
public class LimitsService {

    private final LimitsProperties limitsProperties;
    private final LimitsRepository limitsRepository;

    @Transactional
    public void increaseLimit(@Valid LimitChangeRequest limitChangeRequest) {
        Limit limit = limitsRepository.findLimitByUserId(limitChangeRequest.userId()).orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));

        BigDecimal newAmount = limit.getUserLimit().add(limitChangeRequest.amount());

        if (isOverMaxLimit(newAmount)) {
            throw new BadRequestException("Лимит не может быть больше " + limitsProperties.getMaxAmount());
        }

        int updatedRows = limitsRepository.updateUserLimit(limitChangeRequest.userId(), newAmount);

        if (updatedRows == 0) {
            throw new IllegalStateException("Не удалось увеличить лимит");
        }
    }

    @Transactional
    public void decreaseLimit(@Valid LimitChangeRequest limitChangeRequest) {
        limitsRepository.findLimitByUserId(limitChangeRequest.userId()).ifPresentOrElse(
                limit ->
                {
                    BigDecimal newUserLimit = limit.getUserLimit().subtract(limitChangeRequest.amount());

                    if (isUnderMinLimit(newUserLimit)) {
                        throw new BadRequestException("Запрошенная сумма превышает допустимый лимит");
                    }

                    int updatedRows = limitsRepository.updateUserLimit(limitChangeRequest.userId(), newUserLimit);

                    if (updatedRows == 0) {
                        throw new IllegalStateException("Не удалось уменьшить лимит");
                    }
                },
                () -> createLimit(limitChangeRequest)
        );
    }

    public void createLimit(@Valid LimitChangeRequest limitChangeRequest) {
        BigDecimal newUserLimit = limitsProperties.getMaxAmount().subtract(limitChangeRequest.amount());

        if (isUnderMinLimit(newUserLimit)) {
            throw new BadRequestException("Запрошенная сумма превышает допустимый лимит");
        }

        Limit limit = Limit.builder()
                .userId(limitChangeRequest.userId())
                .userLimit(newUserLimit)
                .build();

        limitsRepository.save(limit);
    }

    private boolean isOverMaxLimit(BigDecimal amount) {
        return amount.compareTo(limitsProperties.getMaxAmount()) > 0;
    }

    private boolean isUnderMinLimit(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) < 0;
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void resetAllLimits() {
        int updatedRows = limitsRepository.resetAllLimits(limitsProperties.getMaxAmount());

        if (updatedRows == 0) {
            throw new IllegalStateException("Не удалось сбросить лимиты");
        }
    }

}
