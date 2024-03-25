package com.nextbuy.cart.dto;

import com.nextbuy.cart.domain.Items;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CartDTO(
        @NotNull(message = FIELD_CANNOT_BE_NULL)
        Long userId,
        @Valid
        List<Items> itemsIds
) {
  private static final String FIELD_CANNOT_BE_NULL = "Field cannot be null";

}
