package com.pragma.powerup.plazamicroservice.domain.spi;

import com.pragma.powerup.plazamicroservice.domain.model.Dish;

public interface IDishPersistencePort {

    Dish saveDish(Dish dish);

}
