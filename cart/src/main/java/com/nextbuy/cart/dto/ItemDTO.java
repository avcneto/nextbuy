package com.nextbuy.cart.dto;

import java.math.BigDecimal;

public record ItemDTO(
        String id,
        String name,
        BigDecimal price,
        Integer quantity
) {
}
