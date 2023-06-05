package com.pragma.powerup.plazamicroservice.domain.spi;

import com.pragma.powerup.plazamicroservice.domain.model.OrderDish;


public interface IOrderDishPersistencePort {

    void saveOrderDish(OrderDish orderDish, Long idRestaurant);

}