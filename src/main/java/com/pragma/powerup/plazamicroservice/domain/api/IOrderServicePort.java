package com.pragma.powerup.plazamicroservice.domain.api;

import com.pragma.powerup.plazamicroservice.domain.model.Order;
import com.pragma.powerup.plazamicroservice.domain.model.OrderDish;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IOrderServicePort {

    Order saveOrder( Order order, List<OrderDish> dishList, String token );

    List<Order> getOrdersOfRestaurantByStatus(String token, String status, Pageable pageable);

    List<Order> assignToOrder(List<Order> idOrders, String token);

    boolean orderReady(Long idOrder, String token);

    String deliverOrder(Long idOrder, String code, String token);

    void orderCanceled(Long idOrder, String token);


}