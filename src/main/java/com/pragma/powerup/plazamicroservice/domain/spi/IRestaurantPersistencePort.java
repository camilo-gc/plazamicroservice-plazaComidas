package com.pragma.powerup.plazamicroservice.domain.spi;

import com.pragma.powerup.plazamicroservice.domain.model.Restaurant;

public interface IRestaurantPersistencePort {

    Restaurant saveRestaurant(Restaurant restaurant);

    Restaurant findRestaurantById(Long id);

}
