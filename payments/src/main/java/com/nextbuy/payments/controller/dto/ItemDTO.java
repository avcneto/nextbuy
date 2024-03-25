package com.nextbuy.payments.controller.dto;

import java.math.BigDecimal;

public record ItemDTO(
        String id,
        BigDecimal price,
        Integer quantity
) {

}