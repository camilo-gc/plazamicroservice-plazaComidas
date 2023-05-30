package com.pragma.powerup.plazamicroservice.domain.spi;

import com.pragma.powerup.plazamicroservice.domain.model.Restaurant;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IRestaurantPersistencePort {

    Restaurant saveRestaurant(Restaurant restaurant);

    Restaurant findRestaurantById(Long id);

    List<Restaurant> findAllRestaurants(Pageable pageable);

}
