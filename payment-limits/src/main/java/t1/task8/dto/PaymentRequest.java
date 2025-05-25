package t1.task8.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PaymentRequest(
        @NotNull(message = "userId не может быть null")
        @Min(value = 1, message = "userId не может быть меньше 1")
        Long userId,
        @NotNull(message = "amount не может быть null или пустым")
        BigDecimal amount
) {}
