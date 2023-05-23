package com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.exceptions.CategoryNotFoundException;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.exceptions.DishNotFoundException;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.exceptions.RestaurantNotFoundException;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.mappers.IDishEntityMapper;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.repositories.ICategoryRepository;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.repositories.IDishRepository;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
import com.pragma.powerup.plazamicroservice.domain.model.Dish;
import com.pragma.powerup.plazamicroservice.domain.model.Restaurant;
import com.pragma.powerup.plazamicroservice.domain.spi.IDishPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
public class DishMysqlAdapter implements IDishPersistencePort {

    private final IDishRepository dishRepository;
    private final ICategoryRepository categoryRepository;
    private final IRestaurantRepository restaurantRepository;
    private final IDishEntityMapper dishEntityMapper;

    @Override
    public Dish saveDish(Dish dish) {

        categoryRepository.findById(dish.getCategory().getId()).orElseThrow(CategoryNotFoundException::new);
        restaurantRepository.findById(dish.getRestaurant().getId()).orElseThrow(RestaurantNotFoundException::new);

        return dishEntityMapper.toDish(
                dishRepository.save(dishEntityMapper.toEntity(dish))
        );
    }

    @Override
    public Dish updateDish(Dish dishReq) {


        Dish dish = dishEntityMapper.toDish(dishRepository.findById(dishReq.getId()).orElseThrow(DishNotFoundException::new));
        dish.setDescription(dishReq.getDescription());
        dish.setPrice(dishReq.getPrice());

        return dishEntityMapper.toDish(
                dishRepository.save(dishEntityMapper.toEntity(dish))
        );
    }

}
