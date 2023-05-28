package com.pragma.powerup.plazamicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.response.RestaurantResponseDto;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.handlers.IRestaurantHandler;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.mapper.IRestaurantRequestMapper;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.mapper.IRestaurantResponseMapper;
import com.pragma.powerup.plazamicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.plazamicroservice.domain.model.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantHandlerImpl implements IRestaurantHandler {

    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantRequestMapper restaurantRequestMapper;
    private final IRestaurantResponseMapper restaurantResponseMapper;

    public void saveRestaurant(RestaurantRequestDto restaurantRequestDto, String authorizationHeader) {
        restaurantServicePort.saveRestaurant( restaurantRequestMapper.toRestaurant( restaurantRequestDto ), authorizationHeader );
    }

    public RestaurantResponseDto getRestaurantById(Long id){
        return restaurantResponseMapper.toResponse( restaurantServicePort.getRestaurantById( id ) );
    }

}
