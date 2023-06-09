package com.pragma.powerup.plazamicroservice.domain.usecase;


import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.exceptions.DishIsNotInRestaurantException;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.exceptions.DishNotFoundException;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.exceptions.NoDataFoundException;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.exceptions.OrderNotFoundException;
import com.pragma.powerup.plazamicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.plazamicroservice.domain.exceptions.OrderInProcessException;
import com.pragma.powerup.plazamicroservice.domain.exceptions.OrderListEmptyException;
import com.pragma.powerup.plazamicroservice.domain.model.*;
import com.pragma.powerup.plazamicroservice.domain.spi.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class OrderUseCaseTest {

    private IOrderPersistencePort orderPersistencePort;
    private IOrderDishPersistencePort orderDishPersistencePort;
    private IEmployeePersistencePort employeePersistencePort;
    private IUserApiFeignPort userApiFeignPort;
    private ITwilioApiFeignPort twilioApiFeignPort;
    private IJwtProviderConfigurationPort jwtProviderConfigurationPort;
    private IOrderServicePort orderServicePort;

    @BeforeEach
    void setUp() {
        this.orderPersistencePort = mock(IOrderPersistencePort.class);
        this.orderDishPersistencePort = mock(IOrderDishPersistencePort.class);
        this.employeePersistencePort = mock(IEmployeePersistencePort.class);
        this.userApiFeignPort = mock(IUserApiFeignPort.class);
        this.twilioApiFeignPort = mock(ITwilioApiFeignPort.class);
        this.jwtProviderConfigurationPort = mock(IJwtProviderConfigurationPort.class);
        this.orderServicePort = new OrderUseCase(
                orderPersistencePort,
                orderDishPersistencePort,
                employeePersistencePort,
                userApiFeignPort,
                twilioApiFeignPort,
                jwtProviderConfigurationPort);
    }


    @Test
    void saveOrderInProcessException() {

        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjbGllbnRvbmVAZ21haWwuY29tIiwicm9sZXMiOlsiUk9MRV9DTElFTlQiXSwiaWQiOjQsImlhdCI6MTY4NTkyODY1MCwiZXhwIjoxNjg2NTc2NjUwfQ.WwreJGgc_vvqg9-Dtlnfp18E_xFhWiN3gPSsNDu7BH0";

        Restaurant restaurant = new Restaurant();
        restaurant.setId(4L);
        Order order = new Order();
        order.setRestaurant(restaurant);

        OrderDish orderDish = new OrderDish();
        Dish dish = new Dish();
        dish.setId(4L);
        orderDish.setDish(dish);
        orderDish.setQuantity(2);

        List<OrderDish> dishList = new ArrayList<>();
        dishList.add(orderDish);

        Long idClient = 2L;
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);

        when(jwtProviderConfigurationPort.getIdFromToken(token)).thenReturn("2");
        when(orderPersistencePort.findOrderInProcessByClient(idClient)).thenReturn(orderList);
        assertThrows(OrderInProcessException.class, () -> orderServicePort.saveOrder(order, dishList, token));
    }

    @Test
    void saveOrderDishNotFoundException() {

        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjbGllbnRvbmVAZ21haWwuY29tIiwicm9sZXMiOlsiUk9MRV9DTElFTlQiXSwiaWQiOjQsImlhdCI6MTY4NTkyODY1MCwiZXhwIjoxNjg2NTc2NjUwfQ.WwreJGgc_vvqg9-Dtlnfp18E_xFhWiN3gPSsNDu7BH0";

        Restaurant restaurant = new Restaurant();
        restaurant.setId(4L);
        Order order = new Order();
        order.setRestaurant(restaurant);

        OrderDish orderDish = new OrderDish();
        Dish dish = new Dish();
        dish.setId(999L);
        orderDish.setDish(dish);
        orderDish.setQuantity(2);

        List<OrderDish> dishList = new ArrayList<>();
        dishList.add(orderDish);

        Long idClient = 4L;

        when(jwtProviderConfigurationPort.getIdFromToken(token)).thenReturn(String.valueOf(idClient));
        when(orderPersistencePort.findOrderInProcessByClient(idClient)).thenReturn(Collections.emptyList());
        when(orderPersistencePort.saveOrder(order)).thenReturn(order);

        doThrow(DishNotFoundException.class).when(orderDishPersistencePort).saveOrderDish(orderDish, order.getRestaurant().getId());
        assertThrows(DishNotFoundException.class, () -> orderServicePort.saveOrder(order, dishList, token));

    }

    @Test
    void saveOrderDishIsNotInRestaurantException() {

        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjbGllbnRvbmVAZ21haWwuY29tIiwicm9sZXMiOlsiUk9MRV9DTElFTlQiXSwiaWQiOjQsImlhdCI6MTY4NTkyODY1MCwiZXhwIjoxNjg2NTc2NjUwfQ.WwreJGgc_vvqg9-Dtlnfp18E_xFhWiN3gPSsNDu7BH0";

        Restaurant restaurant = new Restaurant();
        restaurant.setId(4L);
        Order order = new Order();
        order.setId(5L);
        order.setRestaurant(restaurant);

        OrderDish orderDish = new OrderDish();
        Dish dish = new Dish();
        dish.setId(999L);
        orderDish.setDish(dish);
        orderDish.setQuantity(2);

        List<OrderDish> dishList = new ArrayList<>();
        dishList.add(orderDish);

        Long idClient = 4L;

        when(jwtProviderConfigurationPort.getIdFromToken(token)).thenReturn(String.valueOf(idClient));
        when(orderPersistencePort.findOrderInProcessByClient(idClient)).thenReturn(Collections.emptyList());
        when(orderPersistencePort.saveOrder(order)).thenReturn(order);

        doThrow(DishIsNotInRestaurantException.class).when(orderDishPersistencePort).saveOrderDish(orderDish, order.getRestaurant().getId());
        assertThrows(DishIsNotInRestaurantException.class, () -> orderServicePort.saveOrder(order, dishList, token));

    }

    @Test
    void saveOrderSuccessful() {

        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjbGllbnRvbmVAZ21haWwuY29tIiwicm9sZXMiOlsiUk9MRV9DTElFTlQiXSwiaWQiOjQsImlhdCI6MTY4NTkyODY1MCwiZXhwIjoxNjg2NTc2NjUwfQ.WwreJGgc_vvqg9-Dtlnfp18E_xFhWiN3gPSsNDu7BH0";

        Restaurant restaurant = new Restaurant();
        restaurant.setId(4L);
        Order order = new Order();
        order.setId(5L);
        order.setRestaurant(restaurant);

        OrderDish orderDish = new OrderDish();
        Dish dish = new Dish();
        dish.setId(999L);
        orderDish.setDish(dish);
        orderDish.setQuantity(2);

        List<OrderDish> dishList = new ArrayList<>();
        dishList.add(orderDish);

        Long idClient = 4L;

        when(jwtProviderConfigurationPort.getIdFromToken(token)).thenReturn(String.valueOf(idClient));
        when(orderPersistencePort.findOrderInProcessByClient(idClient)).thenReturn(Collections.emptyList());
        when(orderPersistencePort.saveOrder(order)).thenReturn(order);

        assertNotNull(orderServicePort.saveOrder(order, dishList, token));

    }


    @Test
    void getOrderOfRestaurantByStatusNoDataFoundException(){

        String idEmployee = "4";
        String status = "Pending";
        Pageable pageable = PageRequest.of( 0, 10 );
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjbGllbnRvbmVAZ21haWwuY29tIiwicm9sZXMiOlsiUk9MRV9DTElFTlQiXSwiaWQiOjQsImlhdCI6MTY4NTkyODY1MCwiZXhwIjoxNjg2NTc2NjUwfQ.WwreJGgc_vvqg9-Dtlnfp18E_xFhWiN3gPSsNDu7BH0";
        Restaurant restaurant = new Restaurant();
        restaurant.setId(4L);

        Employee employee = new Employee(3L, 4L, restaurant);

        when(jwtProviderConfigurationPort.getIdFromToken(token)).thenReturn(idEmployee);
        when(employeePersistencePort.findByIdEmployee(Long.valueOf(idEmployee))).thenReturn(employee);

        doThrow(NoDataFoundException.class).when(orderPersistencePort).findOrderOfRestaurantByStatus(restaurant.getId(), status, pageable);
        assertThrows(NoDataFoundException.class, ()-> orderServicePort.getOrdersOfRestaurantByStatus(token, status, pageable));

    }

    @Test
    void getOrderOfRestaurantByStatusSuccessful(){

        String idEmployee = "4";
        String status = "Pending";
        Pageable pageable = PageRequest.of( 0, 10 );
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjbGllbnRvbmVAZ21haWwuY29tIiwicm9sZXMiOlsiUk9MRV9DTElFTlQiXSwiaWQiOjQsImlhdCI6MTY4NTkyODY1MCwiZXhwIjoxNjg2NTc2NjUwfQ.WwreJGgc_vvqg9-Dtlnfp18E_xFhWiN3gPSsNDu7BH0";
        Restaurant restaurant = new Restaurant();
        restaurant.setId(4L);
        Employee employee = new Employee(3L, 4L, restaurant);
        List<Order> orderList = new ArrayList<>();
        Order order = new Order();
        orderList.add(order);

        when(jwtProviderConfigurationPort.getIdFromToken(token)).thenReturn(idEmployee);
        when(employeePersistencePort.findByIdEmployee(Long.valueOf(idEmployee))).thenReturn(employee);
        when(orderPersistencePort.findOrderOfRestaurantByStatus(restaurant.getId(), status, pageable)).thenReturn(orderList);

        assertNotNull( orderServicePort.getOrdersOfRestaurantByStatus(token, status, pageable) );
        assertFalse(orderServicePort.getOrdersOfRestaurantByStatus(token, status, pageable).isEmpty());

    }

    @Test
    void assignToOrderListEmptyException(){

        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjbGllbnRvbmVAZ21haWwuY29tIiwicm9sZXMiOlsiUk9MRV9DTElFTlQiXSwiaWQiOjQsImlhdCI6MTY4NTkyODY1MCwiZXhwIjoxNjg2NTc2NjUwfQ.WwreJGgc_vvqg9-Dtlnfp18E_xFhWiN3gPSsNDu7BH0";
        List<Order> orderList = new ArrayList<>();

        assertThrows(OrderListEmptyException.class, () -> orderServicePort.assignToOrder(orderList, token));
    }

    @Test
    void assignToOrderNotFoundException(){

        Long idEmployee = 3L;
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjbGllbnRvbmVAZ21haWwuY29tIiwicm9sZXMiOlsiUk9MRV9DTElFTlQiXSwiaWQiOjQsImlhdCI6MTY4NTkyODY1MCwiZXhwIjoxNjg2NTc2NjUwfQ.WwreJGgc_vvqg9-Dtlnfp18E_xFhWiN3gPSsNDu7BH0";
        Restaurant restaurant = new Restaurant();
        restaurant.setId(4L);
        Employee employee = new Employee( 3L, 3L, restaurant );
        List<Order> orderList = new ArrayList<>();
        Order order = new Order();
        order.setId(3L);
        orderList.add(order);


        when(jwtProviderConfigurationPort.getIdFromToken(token)).thenReturn(String.valueOf(idEmployee));
        when(employeePersistencePort.findByIdEmployee(idEmployee)).thenReturn(employee);
        doThrow(OrderNotFoundException.class).when(orderPersistencePort).findById(order.getId());
        assertThrows(OrderNotFoundException.class, () -> orderServicePort.assignToOrder(orderList, token));
    }

    @Test
    void assignToOrderSuccessful(){

        Long idEmployee = 3L;
        Long idClient = 3L;
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjbGllbnRvbmVAZ21haWwuY29tIiwicm9sZXMiOlsiUk9MRV9DTElFTlQiXSwiaWQiOjQsImlhdCI6MTY4NTkyODY1MCwiZXhwIjoxNjg2NTc2NjUwfQ.WwreJGgc_vvqg9-Dtlnfp18E_xFhWiN3gPSsNDu7BH0";
        Restaurant restaurant = new Restaurant();
        restaurant.setId(4L);
        Employee employee = new Employee( 3L, 3L, restaurant );
        List<Order> orderList = new ArrayList<>();
        Order order = new Order(3L, idClient, new Date(), "Pending", employee, restaurant);
        orderList.add(order);


        when(jwtProviderConfigurationPort.getIdFromToken(token)).thenReturn(String.valueOf(idEmployee));
        when(employeePersistencePort.findByIdEmployee(idEmployee)).thenReturn(employee);
        when(orderPersistencePort.findById(order.getId())).thenReturn(order);
        when(orderPersistencePort.saveOrder(order)).thenReturn(order);

        assertNotNull(orderServicePort.assignToOrder(orderList, token));
        assertFalse(orderServicePort.assignToOrder(orderList, token).isEmpty());

    }

}