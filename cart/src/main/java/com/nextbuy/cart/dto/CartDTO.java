package com.nextbuy.cart.dto;

import java.math.BigDecimal;

public record CartDTO (
        Long itemId,
        Integer itemQuantity,
        BigDecimal total,
        String paymentMethod
){
}
