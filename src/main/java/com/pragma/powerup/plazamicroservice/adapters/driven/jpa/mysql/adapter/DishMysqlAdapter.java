package com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.mappers.IDishEntityMapper;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.repositories.IDishRepository;
import com.pragma.powerup.plazamicroservice.domain.model.Dish;
import com.pragma.powerup.plazamicroservice.domain.model.Restaurant;
import com.pragma.powerup.plazamicroservice.domain.spi.IDishPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
public class DishMysqlAdapter implements IDishPersistencePort {

    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;

    @Override
    public Dish saveDish(Dish dish) {
        return dishEntityMapper.toDish(
                dishRepository.save(dishEntityMapper.toEntity(dish))
        );
    }


}
