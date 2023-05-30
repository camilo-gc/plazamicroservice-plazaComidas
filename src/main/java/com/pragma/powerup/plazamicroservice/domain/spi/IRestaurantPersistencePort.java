package com.pragma.powerup.plazamicroservice.domain.spi;

import com.pragma.powerup.plazamicroservice.domain.model.Dish;
import com.pragma.powerup.plazamicroservice.domain.model.Restaurant;

import java.util.List;

public interface IRestaurantPersistencePort {

    Restaurant saveRestaurant(Restaurant restaurant);

    Restaurant findRestaurantById(Long id);

    List<Restaurant> getAllRestaurants();

}
