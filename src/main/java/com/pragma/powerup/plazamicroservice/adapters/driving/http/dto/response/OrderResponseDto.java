package com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class OrderResponseDto {

    private Long id;
    private String id_client;
    private String date;
    private String status;
    private String id_chef;
    private String id_restaurant;

}
