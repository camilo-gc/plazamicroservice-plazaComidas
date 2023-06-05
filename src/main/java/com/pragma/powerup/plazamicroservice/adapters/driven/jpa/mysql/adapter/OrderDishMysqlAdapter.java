package com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.exceptions.DishIsNotInRestaurantException;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.exceptions.DishNotFoundException;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.mappers.IOrderDishEntityMapper;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.repositories.IDishRepository;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.repositories.IOrderDishRepository;
import com.pragma.powerup.plazamicroservice.domain.model.OrderDish;
import com.pragma.powerup.plazamicroservice.domain.spi.IOrderDishPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Transactional
public class OrderDishMysqlAdapter implements IOrderDishPersistencePort {

    private final IOrderDishRepository orderDishRepository;
    private final IOrderDishEntityMapper orderDishEntityMapper;
    private final IDishRepository dishRepository;

    @Override
    public void saveOrderDish(OrderDish orderDish, Long idRestaurant) {

        DishEntity dish = dishRepository.findById( orderDish.getDish().getId() ).orElseThrow(DishNotFoundException::new);

        if (!dish.getRestaurantEntity().getId().equals(idRestaurant)){
            throw new DishIsNotInRestaurantException();
        }

        orderDishRepository.save(orderDishEntityMapper.toEntity(orderDish));
    }

}
