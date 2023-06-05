package com.pragma.powerup.plazamicroservice.domain.api;

import com.pragma.powerup.plazamicroservice.domain.model.Order;
import com.pragma.powerup.plazamicroservice.domain.model.OrderDish;

import java.util.List;


public interface IOrderServicePort {

    Order saveOrder( Order order, List<OrderDish> dishList, String token );

}