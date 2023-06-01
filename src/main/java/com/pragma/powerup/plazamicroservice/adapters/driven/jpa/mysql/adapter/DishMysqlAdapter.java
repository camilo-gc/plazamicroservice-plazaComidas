package com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.exceptions.CategoryNotFoundException;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.exceptions.DishNotFoundException;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.exceptions.NoDataFoundException;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.mappers.IDishEntityMapper;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.repositories.ICategoryRepository;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.repositories.IDishRepository;
import com.pragma.powerup.plazamicroservice.domain.model.Dish;
import com.pragma.powerup.plazamicroservice.domain.spi.IDishPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
public class DishMysqlAdapter implements IDishPersistencePort {

    private final IDishRepository dishRepository;
    private final ICategoryRepository categoryRepository;
    private final IDishEntityMapper dishEntityMapper;

    @Override
    public Dish saveDish(Dish dish) {

        categoryRepository.findById(dish.getCategory().getId()).orElseThrow(CategoryNotFoundException::new);

        return dishEntityMapper.toDish(
                dishRepository.save(dishEntityMapper.toEntity(dish))
        );
    }

    @Override
    public Dish updateDish(Dish dish) {

        return dishEntityMapper.toDish(
                dishRepository.save(dishEntityMapper.toEntity(dish))
        );

    }

    @Override
    public Dish findDishById(Long id){
        return dishEntityMapper.toDish(
                dishRepository.findById(id).orElseThrow(DishNotFoundException::new)
        );
    }

    @Override
    public List<Dish> findDishesByRestaurantAndCategory(Long idRestaurant, Long idCategory, Pageable pageable){

        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setId( idRestaurant );

        CategoryEntity category = new CategoryEntity();
        category.setId( idCategory );

        List<DishEntity> dishEntityList = dishRepository.findByRestaurantEntityAndCategoryEntity( idRestaurant, idCategory, pageable );

        if (dishEntityList.isEmpty()) {
            throw new NoDataFoundException();
        }

        return dishEntityMapper.toDishList( dishEntityList );
    }

}
