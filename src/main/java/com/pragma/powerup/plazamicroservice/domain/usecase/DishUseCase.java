package com.pragma.powerup.plazamicroservice.domain.usecase;


import com.pragma.powerup.plazamicroservice.domain.api.IDishServicePort;
import com.pragma.powerup.plazamicroservice.domain.model.Dish;
import com.pragma.powerup.plazamicroservice.domain.spi.IDishPersistencePort;


public class DishUseCase implements IDishServicePort {

    private final IDishPersistencePort dishPersistencePort;



    public DishUseCase(IDishPersistencePort dishPersistencePort) {

        this.dishPersistencePort = dishPersistencePort;

    }

    @Override
    public Dish saveDish(Dish dish) {

        dish.setActive(true);
        return dishPersistencePort.saveDish(dish);

    }

}
