package com.nextbuy.user.dto.user;

import com.nextbuy.user.domain.user.Gender;

import java.time.LocalDate;

public record UserUpdateDTO(
        String name,
        String cpf,
        String email,
        String password,
        LocalDate birthday,
        Gender gender,
        String address,
        Integer addressNumber
) {
}
