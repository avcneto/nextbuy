package com.nextbuy.cart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddItemDTO(
        @NotNull(message = FIELD_CANNOT_BE_NULL)
        Long userId,

        @NotBlank(message = FIELD_CANNOT_BE_BLANK)
        @NotNull(message = FIELD_CANNOT_BE_NULL)
        String id,

        @NotNull(message = FIELD_CANNOT_BE_NULL)
        Integer quantity
) {
  private static final String FIELD_CANNOT_BE_NULL = "Field cannot be null";
  private static final String FIELD_CANNOT_BE_BLANK = "Field cannot be blank";
}
