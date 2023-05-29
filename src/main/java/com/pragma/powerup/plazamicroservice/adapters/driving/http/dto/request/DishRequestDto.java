package com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DishRequestDto {

    @NotBlank
    private String name;
    @NotBlank
    @Pattern(regexp ="^[1-9]\\d*$")
    private String id_category;
    @NotBlank
    private String description;
    @NotBlank
    @Pattern(regexp ="^[1-9]\\d*$")
    private String price;
    @NotBlank
    @Pattern(regexp ="^[1-9]\\d*$")
    private String id_restaurant;
    @NotBlank
    private String url_image;

}
