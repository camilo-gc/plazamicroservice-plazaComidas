package com.pragma.powerup.plazamicroservice.domain.usecase;


import com.pragma.powerup.plazamicroservice.domain.exceptions.RoleNotAllowedForCreationException;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.response.UserResponseDto;
import com.pragma.powerup.plazamicroservice.configuration.Constants;
import com.pragma.powerup.plazamicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.plazamicroservice.domain.model.Restaurant;
import com.pragma.powerup.plazamicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.plazamicroservice.domain.spi.IUserApiPersistencePort;
import com.pragma.powerup.plazamicroservice.domain.utils.FieldValidation;


public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserApiPersistencePort userApiPersistencePort;


    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort, IUserApiPersistencePort userApiPersistencePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userApiPersistencePort = userApiPersistencePort;
    }


    public Restaurant saveRestaurant(Restaurant restaurant, String authorizationHeader) {

        FieldValidation.restaurantValidate(restaurant);

        UserResponseDto owner = userApiPersistencePort.findOwnerById( restaurant.getIdOwner(), authorizationHeader );

        if ( !owner.getId_role().equals(Constants.OWNER_ROLE_ID) ) {
            throw new RoleNotAllowedForCreationException();
        }

        return restaurantPersistencePort.saveRestaurant(restaurant);

    }

}
