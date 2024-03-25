package com.nextbuy.payments.controller.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Date;

public record CardDTO(

        @NotNull
        Long idUser,

        @NotBlank
        String nameOnCard,

        @NotNull
        @Positive
        Long numberCard,

        @NotNull
        @Positive
        Integer cvc,

        @Future
        Date dueDate

) {
}
