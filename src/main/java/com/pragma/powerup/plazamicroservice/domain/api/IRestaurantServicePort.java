package com.pragma.powerup.plazamicroservice.domain.api;

import com.pragma.powerup.plazamicroservice.domain.dto.User;
import com.pragma.powerup.plazamicroservice.domain.model.Restaurant;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IRestaurantServicePort {

    Restaurant saveRestaurant(Restaurant restaurant, String authorizationHeader);

    User addEmployeeToRestaurant(Long idRestaurant, User employee, String token);

    List<Restaurant> getAllRestaurants(Pageable pageable);

}
