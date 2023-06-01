package com.pragma.powerup.plazamicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.request.UserRequestDto;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.response.RestaurantNewResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IRestaurantHandler {

    void saveRestaurant(RestaurantRequestDto restaurantRequestDto, String authorizationHeader);

    void addEmployeeToRestaurant(UserRequestDto userRequestDto, String token);

    List<RestaurantNewResponseDto> getAllRestaurants(Pageable pageable);
}
