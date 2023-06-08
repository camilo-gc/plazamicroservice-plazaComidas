package com.pragma.powerup.plazamicroservice.configuration;

import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.adapter.*;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.mappers.*;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.repositories.*;
import com.pragma.powerup.plazamicroservice.adapters.driven.apis.userapi.adapter.UserApiAdapter;
import com.pragma.powerup.plazamicroservice.adapters.driven.apis.userapi.mappers.IUserApiMapper;
import com.pragma.powerup.plazamicroservice.adapters.driven.apis.userapi.repositories.IUserApiRepository;
import com.pragma.powerup.plazamicroservice.domain.api.IDishServicePort;
import com.pragma.powerup.plazamicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.plazamicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.plazamicroservice.domain.spi.*;
import com.pragma.powerup.plazamicroservice.domain.usecase.DishUseCase;
import com.pragma.powerup.plazamicroservice.domain.usecase.OrderUseCase;
import com.pragma.powerup.plazamicroservice.domain.usecase.RestaurantUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final IEmployeeRepository employeeRestaurantRepository;
    private final IEmployeeEntityMapper employeeRestaurantEntityMapper;
    private final IUserApiRepository userApiRepository;
    private final IUserApiMapper userApiMapper;
    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;
    private final ICategoryRepository categoryRepository;
    private final IJwtProviderConfigurationPort jwtProviderConfigurationPort;
    private final IOrderRepository orderRepository;
    private final IOrderDishRepository orderDishRepository;
    private final IOrderEntityMapper orderEntityMapper;
    private final IOrderDishEntityMapper orderDishEntityMapper;


    @Bean
    public IRestaurantServicePort restaurantServicePort() {
        return new RestaurantUseCase(restaurantPersistencePort(), userApiPersistencePort(), employeePersistencePort(), jwtProviderConfigurationPort);
    }

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort() {
        return new RestaurantMysqlAdapter(restaurantRepository, restaurantEntityMapper);
    }

    @Bean
    public IUserApiFeignPort userApiPersistencePort(){
        return new UserApiAdapter(userApiRepository, userApiMapper);
    }

    @Bean
    public IEmployeePersistencePort employeePersistencePort() {
        return new EmployeeMysqlAdapter(employeeRestaurantRepository, employeeRestaurantEntityMapper);
    }

    @Bean
    public IDishServicePort dishServicePort() {
        return new DishUseCase( dishPersistencePort(), restaurantPersistencePort(), jwtProviderConfigurationPort );
    }

    @Bean
    public IDishPersistencePort dishPersistencePort() {
        return new DishMysqlAdapter(dishRepository, categoryRepository, dishEntityMapper);
    }

    @Bean
    public IOrderServicePort orderServicePort() {
        return new OrderUseCase(orderPersistencePort(), orderDishPersistencePort(), employeePersistencePort(), jwtProviderConfigurationPort);
    }

    @Bean
    public IOrderPersistencePort orderPersistencePort() {
        return new OrderMysqlAdapter(orderRepository, orderEntityMapper);
    }

    @Bean
    public IOrderDishPersistencePort orderDishPersistencePort() {
        return new OrderDishMysqlAdapter(orderDishRepository, orderDishEntityMapper, dishRepository);
    }

}