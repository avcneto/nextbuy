package com.nextbuy.items.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateItemDTO(
        @NotBlank
        String name,
        @NotNull
        BigDecimal price
) {
    @JsonCreator
    public UpdateItemDTO(
            @JsonProperty("name") String name,
            @JsonProperty("price") BigDecimal price
    ) {
        this.name = name;
        this.price = price;
    }
}