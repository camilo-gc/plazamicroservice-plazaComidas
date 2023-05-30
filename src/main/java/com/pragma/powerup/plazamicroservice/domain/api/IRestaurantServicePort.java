package com.pragma.powerup.plazamicroservice.domain.api;

import com.pragma.powerup.plazamicroservice.domain.model.Restaurant;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IRestaurantServicePort {

    Restaurant saveRestaurant(Restaurant restaurant, String authorizationHeader);

    Restaurant getRestaurantById( Long id);

    List<Restaurant> getAllRestaurants(Pageable pageable);

}
