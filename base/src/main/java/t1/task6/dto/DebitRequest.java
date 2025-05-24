package t1.task6.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DebitRequest(
        @NotNull(message = "ID не может быть null")
        @Min(value = 1, message = "ID не может быть меньше 0")
        Long productId,
        @NotBlank(message = "Amount не может быть пустым")
        String amount
) {}
