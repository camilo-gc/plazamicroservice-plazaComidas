package com.pragma.powerup.plazamicroservice.domain.usecase;


import com.pragma.powerup.plazamicroservice.configuration.Constants;
import com.pragma.powerup.plazamicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.plazamicroservice.domain.dto.User;
import com.pragma.powerup.plazamicroservice.domain.exceptions.*;
import com.pragma.powerup.plazamicroservice.domain.model.Employee;
import com.pragma.powerup.plazamicroservice.domain.model.Order;
import com.pragma.powerup.plazamicroservice.domain.model.OrderDish;
import com.pragma.powerup.plazamicroservice.domain.spi.*;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IOrderDishPersistencePort orderDishPersistencePort;
    private final IEmployeePersistencePort employeePersistencePort;
    private final IUserApiFeignPort userApiFeignPort;
    private final ITwilioApiFeignPort twilioPersistencePort;
    private final IJwtProviderConfigurationPort jwtProviderConfigurationPort;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort,
                        IOrderDishPersistencePort orderDishPersistencePort,
                        IEmployeePersistencePort employeePersistencePort,
                        IUserApiFeignPort userApiFeignPort,
                        ITwilioApiFeignPort twilioPersistencePort,
                        IJwtProviderConfigurationPort jwtProviderConfigurationPort) {
        this.orderPersistencePort = orderPersistencePort;
        this.orderDishPersistencePort = orderDishPersistencePort;
        this.employeePersistencePort = employeePersistencePort;
        this.userApiFeignPort = userApiFeignPort;
        this.twilioPersistencePort = twilioPersistencePort;
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

    public List<Order> assignToOrder(List<Order> orderList, String token) {

        if (orderList.isEmpty()) {
            throw new OrderListEmptyException();
        }

        String idEmployee = jwtProviderConfigurationPort.getIdFromToken(token);
        Employee chef = employeePersistencePort.findByIdEmployee(Long.valueOf(idEmployee));
        List<Order> orderListUpdated = new ArrayList<>();

        for ( Order order: orderList ) {

            order = orderPersistencePort.findById(order.getId());

            order.setChef(chef);
            order.setStatus(Constants.ORDER_STATUS_PREPARATION);
            order = orderPersistencePort.saveOrder(order);

            orderListUpdated.add(order);

        }

        return orderListUpdated;

    }

    public String orderReady(Long idOrder, String token){

        Order order = orderPersistencePort.findById(idOrder);

        if (!order.getStatus().equals(Constants.ORDER_STATUS_PREPARATION)) {
            throw new OrderIsNotInPreparationException();
        }

        User client = userApiFeignPort.findUserById(order.getIdClient(), token);

        order.setStatus(Constants.ORDER_STATUS_READY);
        orderPersistencePort.saveOrder(order);
        String status = twilioPersistencePort.sendCodeVerification(
                        Constants.ORDER_READY_MESSAGE ,
                        client.getPhone(),
                        token
                ).get(Constants.SENT_CODE_STATUS_KEY);

        if (!status.equals(Constants.APPROVED_STATUS)) {
            throw new SentCodeNotApprovedException();
        }

        return status;

    }

    public String deliverOrder(Long idOrder, String code, String token) {

        Order order = orderPersistencePort.findById(idOrder);

        if (!order.getStatus().equals(Constants.ORDER_STATUS_READY)) {
            throw new OrderIsNotReadyException();
        }

        User client = userApiFeignPort.findUserById(order.getIdClient(), token);

        String status = twilioPersistencePort.validateCodeVerification( code , client.getPhone(), token )
                .get(Constants.SENT_CODE_STATUS_KEY);

        if (!status.equals(Constants.APPROVED_STATUS)) {
            throw new SentCodeNotApprovedException();
        }

        order.setStatus(Constants.ORDER_STATUS_DELIVERED);
        orderPersistencePort.saveOrder(order);

        return status;
    }

    public void orderCanceled(Long idOrder, String token){

        Order order = orderPersistencePort.findById(idOrder);

        if (!order.getStatus().equals(Constants.ORDER_STATUS_PENDING)) {
            throw new OrderIsNotInPreparationException();
        }

        User client = userApiFeignPort.findUserById(order.getIdClient(), token);

        order.setStatus(Constants.ORDER_STATUS_CANCELED);
        orderPersistencePort.saveOrder(order);
        twilioPersistencePort.notifyOrderStatus( Constants.ORDER_CANCEL_MESSAGE, client.getPhone(), token );

    }

}