package com.pragma.powerup.plazamicroservice.configuration;

import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.adapter.DishMysqlAdapter;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.adapter.EmployeeRestaurantMysqlAdapter;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.adapter.RestaurantMysqlAdapter;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.mappers.IEmployeeRestaurantEntityMapper;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.repositories.IEmployeeRestaurantRepository;
import com.pragma.powerup.plazamicroservice.adapters.driven.userapi.adapter.UserApiAdapter;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.mappers.IDishEntityMapper;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.mappers.IRestaurantEntityMapper;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.repositories.ICategoryRepository;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.repositories.IDishRepository;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
import com.pragma.powerup.plazamicroservice.adapters.driven.userapi.mappers.IUserApiMapper;
import com.pragma.powerup.plazamicroservice.adapters.driven.userapi.repositories.IUserApiRepository;
import com.pragma.powerup.plazamicroservice.domain.api.IDishServicePort;
import com.pragma.powerup.plazamicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.plazamicroservice.domain.spi.*;
import com.pragma.powerup.plazamicroservice.domain.usecase.DishUseCase;
import com.pragma.powerup.plazamicroservice.domain.usecase.RestaurantUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final IEmployeeRestaurantRepository employeeRestaurantRepository;
    private final IEmployeeRestaurantEntityMapper employeeRestaurantEntityMapper;
    private final IUserApiRepository userApiRepository;
    private final IUserApiMapper userApiMapper;
    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;
    private final ICategoryRepository categoryRepository;
    private final IJwtProviderConfigurationPort jwtProviderConfigurationPort;


    @Bean
    public IRestaurantServicePort restaurantServicePort() {
        return new RestaurantUseCase(restaurantPersistencePort(), userApiPersistencePort(), employeeRestaurantPersistencePort(), jwtProviderConfigurationPort);
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
    public IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort() {
        return new EmployeeRestaurantMysqlAdapter(employeeRestaurantRepository, employeeRestaurantEntityMapper);
    }

    @Bean
    public IDishServicePort dishServicePort() {
        return new DishUseCase( dishPersistencePort(), restaurantPersistencePort(), jwtProviderConfigurationPort );
    }

    @Bean
    public IDishPersistencePort dishPersistencePort() {
        return new DishMysqlAdapter(dishRepository, categoryRepository, dishEntityMapper);
    }


}
