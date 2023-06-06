package com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class OrderResponseDto {

    private Long id;
    @JsonProperty(value = "id_client")
    private String idClient;
    private String date;
    private String status;
    @JsonProperty(value = "id_chef")
    private String idChef;
    @JsonProperty(value = "id_restaurant")
    private String idRestaurant;

}
