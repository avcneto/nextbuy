package com.nextbuy.cart.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Item {
  private static final String FIELD_CANNOT_BE_NULL = "Field cannot be null";
  private static final String FIELD_CANNOT_BE_BLANK = "Field cannot be blank";

  @NotBlank(message = FIELD_CANNOT_BE_BLANK)
  @NotNull(message = FIELD_CANNOT_BE_NULL)
  private String id;

  @NotNull(message = FIELD_CANNOT_BE_NULL)
  private Integer quantity;
}
