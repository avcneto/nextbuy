package com.nextbuy.payments.controller;

import jakarta.validation.constraints.NotNull;

public record CartIdDTO(

        @NotNull
        Long idCart

) {
}
