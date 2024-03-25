package com.nextbuy.payments.controller.dto;

import com.nextbuy.payments.domain.TypePayment;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public record PaymentDTO(

        @CPF
        @NotNull
        String cpf,

        @Enumerated(EnumType.STRING)
        @NotNull
        TypePayment typePayment

) {
}
