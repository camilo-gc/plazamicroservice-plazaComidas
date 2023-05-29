package com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DishActiveRequestDto {

    @NotBlank(message = "must not be empty")
    private String active;

}
