package com.nextbuy.payments.controller.dto;

import java.math.BigDecimal;
import java.util.List;


public record CartDTO(

        Long id,

        Long userId,

        List<ItemDTO> itemsIds,

        String status,

        BigDecimal total

) {
}
