package com.nextbuy.user.dto.user;

import com.nextbuy.user.domain.user.Gender;
import com.nextbuy.user.domain.user.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

import static com.nextbuy.user.config.WebSecurityConfig.passwordEncoder;

public record UserDTO(

        @NotBlank(message = FIELD_CANNOT_BE_NULL_EMPTY_BLANK_MESSAGE)
        @Pattern(regexp = NUMBER_REGEX, message = FIELD_ONLY_NUMBER_MESSAGE)
        @PositiveOrZero(message = FIELD_ONLY_NUMBER_MESSAGE)
        @CPF(message = CPF_INVALID_MESSAGE)
        String cpf,

        @Pattern(regexp = NO_NUMERIC_REGEX, message = NAME_NON_NUMERIC_MESSAGE)
        @NotBlank(message = FIELD_CANNOT_BE_NULL_EMPTY_BLANK_MESSAGE)
        String name,

        @PastOrPresent(message = BIRTHDAY_PAST_MESSAGE)
        @NotNull(message = FIELD_CANNOT_BE_NULL_EMPTY_BLANK_MESSAGE)
        LocalDate birthday,

        @NotNull(message = FIELD_CANNOT_BE_NULL_EMPTY_BLANK_MESSAGE)
        Gender gender,

        Role role,

        @Email(message = EMAIL_MESSAGE)
        @NotBlank(message = FIELD_CANNOT_BE_NULL_EMPTY_BLANK_MESSAGE)
        String email,

        @Size(min = PASSWORD_MIN_SIZE, message = PASSWORD_SIZE_MESSAGE)
        @Pattern(regexp = PASSWORD_REGEX, message = PASSWORD_MESSAGE)
        String password,

        @NotBlank(message = FIELD_CANNOT_BE_NULL_EMPTY_BLANK_MESSAGE)
        @NotNull(message = FIELD_CANNOT_BE_NULL_EMPTY_BLANK_MESSAGE)
        String address,

        @NotNull(message = FIELD_CANNOT_BE_NULL_EMPTY_BLANK_MESSAGE)
        Integer addressNumber

) {
  private static final String FIELD_ONLY_NUMBER_MESSAGE = "Invalid field, enter only positive numbers";
  private static final String FIELD_CANNOT_BE_NULL_EMPTY_BLANK_MESSAGE = "Field cannot be null, empty or blank";
  private static final String CPF_INVALID_MESSAGE = "CPF number is invalid, Please use a valid cpf";
  private static final String BIRTHDAY_PAST_MESSAGE = "Must be a past date";
  private static final String PASSWORD_MESSAGE = "Password must contain letters and numbers";
  private static final String PASSWORD_SIZE_MESSAGE = "The password must be in the minimum 6 characters";
  private static final String NUMBER_REGEX = "^[0-9]*$";
  private static final String NAME_NON_NUMERIC_MESSAGE = "Name must not contain number and cannot be empty";
  private static final String EMAIL_MESSAGE = "Email is invalid, Please use a valid email";
  private static final String PASSWORD_REGEX = "[A-Za-z0-9]+";
  private static final String NO_NUMERIC_REGEX = "[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ\\s]+$";
  private static final int PASSWORD_MIN_SIZE = 6;

  @Override
  public String password() {
    return passwordEncoder().encode(this.password);
  }

}
