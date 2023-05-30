package com.pragma.powerup.plazamicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.response.RestaurantResponseDto;

import java.util.List;

public interface IRestaurantHandler {

    void saveRestaurant(RestaurantRequestDto restaurantRequestDto, String authorizationHeader);

    RestaurantResponseDto getRestaurantById(Long id);

    List<RestaurantResponseDto> getAllRestaurants();
}
