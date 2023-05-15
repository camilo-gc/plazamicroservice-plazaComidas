package com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.mappers.IRestaurantEntityMapper;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
import com.pragma.powerup.plazamicroservice.domain.model.Restaurant;
import com.pragma.powerup.plazamicroservice.domain.spi.IRestaurantPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
//@Transactional
public class RestaurantMysqlAdapter implements IRestaurantPersistencePort {
    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        /*
        if (userRepository.findByDni(restaurant.getDni()).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        roleRepository.findById(restaurant.getRole().getId()).orElseThrow(RoleNotFoundException::new);
        restaurant.setPassword(passwordEncoder.encode(restaurant.getPassword()));

         */
        restaurantRepository.save(restaurantEntityMapper.toEntity(restaurant));
    }


}
