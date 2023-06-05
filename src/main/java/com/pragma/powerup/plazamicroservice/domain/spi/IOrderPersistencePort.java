package com.pragma.powerup.plazamicroservice.domain.spi;

import com.pragma.powerup.plazamicroservice.domain.model.Order;

import java.util.List;

public interface IOrderPersistencePort {

    List<Order> findOrderInProcessByClient(Long idClient);

    Order saveOrder(Order order);


}
