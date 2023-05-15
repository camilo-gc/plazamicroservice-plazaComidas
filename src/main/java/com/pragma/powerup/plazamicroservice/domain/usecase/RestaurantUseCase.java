package com.pragma.powerup.plazamicroservice.domain.usecase;


import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.exceptions.OwnerNotFoundException;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.exceptions.RoleNotAllowedForCreationException;
import com.pragma.powerup.plazamicroservice.configuration.Constants;
import com.pragma.powerup.plazamicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.plazamicroservice.domain.model.Restaurant;
import com.pragma.powerup.plazamicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.plazamicroservice.domain.spi.IUserApiPersistencePort;
import com.pragma.powerup.plazamicroservice.domain.utils.FieldValidation;

import java.util.Map;

public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserApiPersistencePort userApiPersistencePort;


    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort, IUserApiPersistencePort userApiPersistencePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userApiPersistencePort = userApiPersistencePort;
    }


    public void saveRestaurant(Restaurant restaurant) {

        Map user = userApiPersistencePort.findOwnerById(restaurant.getIdOwner());

        if ( user.isEmpty() ) {
            throw new OwnerNotFoundException();
        }

        if ( !user.get("id_role").equals(Constants.OWNER_ROLE_ID)) {
            throw new RoleNotAllowedForCreationException();
        }

        FieldValidation.restaurantValidate(restaurant);
        restaurantPersistencePort.saveRestaurant(restaurant);

    }

}
