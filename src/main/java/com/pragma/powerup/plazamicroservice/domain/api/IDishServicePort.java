package com.pragma.powerup.plazamicroservice.domain.api;

import com.pragma.powerup.plazamicroservice.domain.model.Dish;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDishServicePort {

    Dish saveDish( Dish dish, String token );

    Dish updateDish( Dish dish, String token );

    Dish activeDish( Dish dish, String token );

    List<Dish> getDishesByRestaurantAndCategory( Long idRestaurant, Long idCategory, Pageable pageable );

}
