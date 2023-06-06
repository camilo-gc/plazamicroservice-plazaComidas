package com.pragma.powerup.plazamicroservice.domain.usecase;

import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.exceptions.DishIsNotInRestaurantException;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.exceptions.DishNotFoundException;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.exceptions.NoDataFoundException;
import com.pragma.powerup.plazamicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.plazamicroservice.domain.exceptions.OrderInProcessException;
import com.pragma.powerup.plazamicroservice.domain.model.*;
import com.pragma.powerup.plazamicroservice.domain.spi.IEmployeePersistencePort;
import com.pragma.powerup.plazamicroservice.domain.spi.IJwtProviderConfigurationPort;
import com.pragma.powerup.plazamicroservice.domain.spi.IOrderDishPersistencePort;
import com.pragma.powerup.plazamicroservice.domain.spi.IOrderPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderUseCaseTest {

    private IOrderPersistencePort orderPersistencePort;
    private IOrderDishPersistencePort orderDishPersistencePort;
    private IEmployeePersistencePort employeePersistencePort;
    private IJwtProviderConfigurationPort jwtProviderConfigurationPort;
    private IOrderServicePort orderServicePort;

    @BeforeEach
    void setUp() {
        this.orderPersistencePort = mock(IOrderPersistencePort.class);
        this.orderDishPersistencePort = mock(IOrderDishPersistencePort.class);
        this.employeePersistencePort = mock(IEmployeePersistencePort.class);
        this.jwtProviderConfigurationPort = mock(IJwtProviderConfigurationPort.class);
        this.orderServicePort = new OrderUseCase(orderPersistencePort, orderDishPersistencePort, employeePersistencePort, jwtProviderConfigurationPort);
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

}