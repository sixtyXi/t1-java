package t1.task7.dto;

import t1.task7.enums.ProductType;

public record ProductResponse(
        Long id,
        String accountNumber,
        String balance,
        ProductType type,
        Long userId
) {}
