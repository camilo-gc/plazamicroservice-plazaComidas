package com.pragma.powerup.plazamicroservice.domain.spi;

import com.pragma.powerup.plazamicroservice.domain.model.Dish;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDishPersistencePort {

    Dish saveDish(Dish dish);

    Dish updateDish(Dish dish);

    Dish findDishById(Long id);

    List<Dish> findDishesByRestaurantAndCategory(Long idRestaurant, Long idCategory, Pageable pageable);

}
