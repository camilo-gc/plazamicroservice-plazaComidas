package com.pragma.powerup.plazamicroservice.domain.api;

import com.pragma.powerup.plazamicroservice.domain.model.Restaurant;

public interface IRestaurantServicePort {
    Restaurant saveRestaurant(Restaurant restaurant, String authorizationHeader);

}
