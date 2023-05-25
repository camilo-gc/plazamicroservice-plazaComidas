package com.pragma.powerup.plazamicroservice.configuration;

import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.adapter.DishMysqlAdapter;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.adapter.RestaurantMysqlAdapter;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.adapter.UserApiAdapter;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.mappers.IDishEntityMapper;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.mappers.IRestaurantEntityMapper;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.repositories.ICategoryRepository;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.repositories.IDishRepository;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.repositories.IUserApiRepository;
import com.pragma.powerup.plazamicroservice.domain.api.IDishServicePort;
import com.pragma.powerup.plazamicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.plazamicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup.plazamicroservice.domain.spi.IJwtProviderConfigurationPort;
import com.pragma.powerup.plazamicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.plazamicroservice.domain.spi.IUserApiPersistencePort;
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
    private final IUserApiRepository userApiRepository;
    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;
    private final ICategoryRepository categoryRepository;
    private final IJwtProviderConfigurationPort jwtProviderPort;


    @Bean
    public IRestaurantServicePort restaurantServicePort() {
        return new RestaurantUseCase(restaurantPersistencePort(), userApiPersistencePort());
    }

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort() {
        return new RestaurantMysqlAdapter(restaurantRepository, restaurantEntityMapper);
    }

    @Bean
    public IUserApiPersistencePort userApiPersistencePort(){
        return new UserApiAdapter(userApiRepository);
    }


    @Bean
    public IDishServicePort dishServicePort() {
        return new DishUseCase( dishPersistencePort(), restaurantPersistencePort(), jwtProviderPort );
    }

    @Bean
    public IDishPersistencePort dishPersistencePort() {
        return new DishMysqlAdapter(dishRepository, categoryRepository, dishEntityMapper);
    }



}
