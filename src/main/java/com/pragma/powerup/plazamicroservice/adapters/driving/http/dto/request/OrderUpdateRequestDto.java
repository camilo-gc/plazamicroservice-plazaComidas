package com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderUpdateRequestDto {

    @NotBlank(message = "must not be empty")
    @Pattern(regexp = "^(?!0)[1-9][0-9]{0,18}$", message = "an integer greater than 0 was expected")
    private String id;

}