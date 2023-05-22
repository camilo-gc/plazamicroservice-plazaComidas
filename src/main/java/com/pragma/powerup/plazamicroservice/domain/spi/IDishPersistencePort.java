package com.pragma.powerup.plazamicroservice.domain.spi;

import com.pragma.powerup.plazamicroservice.domain.model.Dish;
import com.pragma.powerup.plazamicroservice.domain.model.Restaurant;

public interface IDishPersistencePort {

    Dish saveDish(Dish dish);

}
