package com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RestaurantRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotBlank
    @Pattern(regexp = "^[1-9]\\d*$")
    private String id_owner;

    @NotBlank
    @Pattern(regexp = "\\+[0-9]{12}")
    private String phone;

    @NotBlank
    private String url_logo;

    @NotBlank
    @Digits(integer = 11, fraction = 0)
    private String nit;

}
