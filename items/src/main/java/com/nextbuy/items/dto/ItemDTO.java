package com.nextbuy.items.dto;

import com.nextbuy.items.exception.BadRequestException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import static com.nextbuy.items.util.Validators.isNullOrEmptyOrBlank;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public record ItemDTO(
    @NotBlank(message = NOT_EMPTY_OR_BLANK_MESSAGE)
    @NotNull(message = NOT_EMPTY_OR_BLANK_MESSAGE)
    String name,
    BigDecimal price
) {
    private static final String NOT_EMPTY_OR_BLANK_MESSAGE = "The field cannot be empty or blank";
    private static final String PRICE_REQUIRED_MESSAGE = "The field cannot be empty or blank";
    private static final String PRICE_REGEX = "^\\d{1,3}(\\.\\d{3})*(,\\d{2})?$";

    public void validPrice() {
        if (isNullOrEmptyOrBlank(price) || isPriceValid(price)) {
            throw new BadRequestException(PRICE_REQUIRED_MESSAGE);
        }
    }
    private boolean isPriceValid(BigDecimal price) {
        Pattern pattern = Pattern.compile(PRICE_REGEX);
        return pattern.matcher(price.toString()).matches();
    }
}
