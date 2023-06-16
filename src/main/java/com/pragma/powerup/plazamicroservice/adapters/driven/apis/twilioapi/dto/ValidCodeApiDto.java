package com.pragma.powerup.plazamicroservice.adapters.driven.apis.twilioapi.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ValidCodeApiDto {

    @NotBlank
    private String code;

    @NotBlank
    @Pattern(regexp = "\\+[0-9]{12}", message = "invalid phone number")
    private String phone;

}
