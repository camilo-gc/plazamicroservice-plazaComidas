package com.pragma.powerup.plazamicroservice.domain.usecase;

import com.pragma.powerup.plazamicroservice.domain.exceptions.RoleNotAllowedForCreationException;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.response.UserResponseDto;
import com.pragma.powerup.plazamicroservice.configuration.Constants;
import com.pragma.powerup.plazamicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.plazamicroservice.domain.model.Restaurant;
import com.pragma.powerup.plazamicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.plazamicroservice.domain.spi.IUserApiFeignPort;
import com.pragma.powerup.plazamicroservice.domain.utils.FieldValidation;
import org.springframework.data.domain.Pageable;

import java.util.List;


public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserApiFeignPort userApiFeignPort;


    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort, IUserApiFeignPort userApiFeignPort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userApiFeignPort = userApiFeignPort;
    }

    public Restaurant saveRestaurant(Restaurant restaurant, String authorizationHeader) {

        FieldValidation.restaurantValidate(restaurant);

        UserResponseDto owner = userApiFeignPort.findOwnerById( restaurant.getIdOwner(), authorizationHeader );

        if ( !owner.getId_role().equals(Constants.OWNER_ROLE_ID) ) {
            throw new RoleNotAllowedForCreationException();
        }

        return restaurantPersistencePort.saveRestaurant(restaurant);

    }

    public Restaurant getRestaurantById(Long id) {
        return restaurantPersistencePort.findRestaurantById( id );
    }

    @Override
    public List<Restaurant> getAllRestaurants(Pageable pageable) {
        return restaurantPersistencePort.findAllRestaurants(pageable);
    }

}
