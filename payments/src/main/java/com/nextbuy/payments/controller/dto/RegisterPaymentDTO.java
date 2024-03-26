package com.nextbuy.payments.controller.dto;

import com.nextbuy.payments.domain.TypePayment;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

public record RegisterPaymentDTO(

        @NotNull
        Long idCart,

        @Enumerated(EnumType.STRING)
        @NotNull
        TypePayment typePayment

) {
}
