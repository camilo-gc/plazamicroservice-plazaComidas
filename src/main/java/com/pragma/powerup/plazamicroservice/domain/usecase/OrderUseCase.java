package com.pragma.powerup.plazamicroservice.domain.usecase;


import com.pragma.powerup.plazamicroservice.configuration.Constants;
import com.pragma.powerup.plazamicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.plazamicroservice.domain.exceptions.OrderInProcessException;
import com.pragma.powerup.plazamicroservice.domain.model.Order;
import com.pragma.powerup.plazamicroservice.domain.model.OrderDish;
import com.pragma.powerup.plazamicroservice.domain.spi.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IJwtProviderConfigurationPort jwtProviderConfigurationPort;
    private final IOrderDishPersistencePort orderDishPersistencePort;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort,
                        IOrderDishPersistencePort orderDishPersistencePort,
                        IJwtProviderConfigurationPort jwtProviderConfigurationPort) {
        this.orderPersistencePort = orderPersistencePort;
        this.orderDishPersistencePort = orderDishPersistencePort;
        this.jwtProviderConfigurationPort = jwtProviderConfigurationPort;
    }

    @Transactional
    public Order saveOrder(Order order, List<OrderDish> dishList, String token){

        Long idClient = Long.valueOf( jwtProviderConfigurationPort.getIdFromToken(token) );
        List<Order> orderList = orderPersistencePort.findOrderInProcessByClient(  idClient ) ;

        if (!orderList.isEmpty()) {
            throw new OrderInProcessException();
        }

        order.setIdClient( idClient );
        order.setDate(new Date());
        order.setStatus(Constants.ORDER_STATUS_PENDING);

        order = orderPersistencePort.saveOrder( order );

        for ( OrderDish orderDish: dishList ) {
            orderDish.setOrder( order );
            orderDishPersistencePort.saveOrderDish(orderDish, order.getRestaurant().getId());
        }

        return order;

    }

}
