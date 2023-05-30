package com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DishResponseDto {

    private Long id;
    private String name;
    private Long id_category;
    private String description;
    private Long price;
    private String id_restaurant;
    private String url_image;
    private String active;

}
