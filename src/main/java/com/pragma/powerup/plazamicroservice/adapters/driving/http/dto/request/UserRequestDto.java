package com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserRequestDto {


    @NotBlank(message = "must not be empty")
    @Pattern(regexp = "^(?!0)[1-9][0-9]{0,18}$", message = "an integer greater than 0 was expected")
    private String id_restaurant;

    @NotBlank(message = "must not be empty")
    private String name;

    @NotBlank(message = "must not be empty")
    private String surname;

    @NotBlank(message = "must not be empty")
    @Digits(integer = 11, fraction = 0)
    private String dni;

    @NotBlank(message = "must not be empty")
    @Pattern(regexp = "\\+[0-9]{12}")
    private String phone;

    @NotBlank(message = "must not be empty")
    @Email
    private String email;

    @NotBlank(message = "must not be empty")
    private String password;



}


