package com.pragma.powerup.plazamicroservice.domain.api;

import com.pragma.powerup.plazamicroservice.domain.model.Dish;

public interface IDishServicePort {

    Dish saveDish( Dish dish, String token );

    Dish updateDish( Dish dish, String token );
}
