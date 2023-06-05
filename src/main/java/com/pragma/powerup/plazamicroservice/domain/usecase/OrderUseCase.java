package com.pragma.powerup.plazamicroservice.domain.usecase;


import com.pragma.powerup.plazamicroservice.configuration.Constants;
import com.pragma.powerup.plazamicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.plazamicroservice.domain.exceptions.OrderInProcessException;
import com.pragma.powerup.plazamicroservice.domain.model.Employee;
import com.pragma.powerup.plazamicroservice.domain.model.Order;
import com.pragma.powerup.plazamicroservice.domain.model.OrderDish;
import com.pragma.powerup.plazamicroservice.domain.model.Restaurant;
import com.pragma.powerup.plazamicroservice.domain.spi.*;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IOrderDishPersistencePort orderDishPersistencePort;
    private final IEmployeePersistencePort employeePersistencePort;
    private final IJwtProviderConfigurationPort jwtProviderConfigurationPort;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort,
                        IOrderDishPersistencePort orderDishPersistencePort,
                        IEmployeePersistencePort employeePersistencePort,
                        IJwtProviderConfigurationPort jwtProviderConfigurationPort) {
        this.orderPersistencePort = orderPersistencePort;
        this.orderDishPersistencePort = orderDishPersistencePort;
        this.employeePersistencePort = employeePersistencePort;
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

    public List<Order> getOrdersOfRestaurantByStatus(String token, String status, Pageable pageable){

        String idEmployee = jwtProviderConfigurationPort.getIdFromToken(token);
        Employee employee = employeePersistencePort.findByIdEmployee( Long.valueOf(idEmployee) );
        Long idRestaurant = employee.getRestaurant().getId();

        return orderPersistencePort.findOrderOfRestaurantByStatus(idRestaurant, status, pageable);
    }

}
