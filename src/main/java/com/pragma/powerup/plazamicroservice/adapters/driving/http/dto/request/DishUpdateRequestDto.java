package com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DishUpdateRequestDto {

    @NotBlank(message = "must not be empty")
    @Pattern(regexp ="^[1-9]\\d*$", message = "an integer greater than 0 was expected")
    private String id;
    @NotBlank(message = "must not be empty")
    private String description;
    @NotBlank(message = "must not be empty")
    @Pattern(regexp ="^[1-9]\\d*$", message = "an integer greater than 0 was expected")
    private String price;

}
