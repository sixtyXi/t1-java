package t1.task8.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import t1.task8.config.LimitsProperties;
import t1.task8.dto.PaymentRequest;
import t1.task8.entity.Payment;
import t1.task8.exception.BadRequestException;
import t1.task8.repository.PaymentsRepository;

import java.math.BigDecimal;

@Service
@Validated
@RequiredArgsConstructor
public class PaymentsService {

    private final LimitsProperties limitsProperties;
    private final PaymentsRepository paymentsRepository;

    @Transactional
    public void increaseAmount(@Valid PaymentRequest paymentRequest) {
        paymentsRepository.findPaymentByUserId(paymentRequest.userId()).ifPresentOrElse(
                payment ->
                {
                    BigDecimal newAmount = payment.getAmount().add(paymentRequest.amount());

                    if (isOverMaxLimit(newAmount)) {
                        throw new BadRequestException("Платеж превышает допустимый лимит");
                    }

                    int updatedRows = paymentsRepository.updatePaymentAmount(paymentRequest.userId(), newAmount);

                    if (updatedRows == 0) {
                        throw new IllegalStateException("Не удалось сохранить платеж");
                    }
                },
                () -> createPayment(paymentRequest)
        );
    }

    @Transactional
    public void decreaseAmount(@Valid PaymentRequest paymentRequest) {
        Payment payment = paymentsRepository.findPaymentByUserId(paymentRequest.userId()).orElseThrow(() -> new EntityNotFoundException("пользователь не найден"));

        BigDecimal newAmount = payment.getAmount().subtract(paymentRequest.amount());

        if (newAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("Некорректная сумма платежа");
        }

        int updatedRows = paymentsRepository.updatePaymentAmount(paymentRequest.userId(), newAmount);

        if (updatedRows == 0) {
            throw new IllegalStateException("Не удалось сохранить платеж");
        }
    }

    public void createPayment(@Valid PaymentRequest paymentRequest) {
        if (isOverMaxLimit(paymentRequest.amount())) {
            throw new BadRequestException("Платеж превышает допустимый лимит");
        }

        Payment payment = Payment.builder()
                .userId(paymentRequest.userId())
                .amount(paymentRequest.amount())
                .build();

        paymentsRepository.save(payment);
    }

    private boolean isOverMaxLimit(BigDecimal amount) {
        return amount.compareTo(limitsProperties.getMaxAmount()) > 0;
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void resetAllPayments() {
        int updatedRows = paymentsRepository.resetAllPaymentsAmount(BigDecimal.ZERO);

        if (updatedRows == 0) {
            throw new IllegalStateException("Не удалось сбросить платежи");
        }
    }

}
