package com.pragma.powerup.plazamicroservice.domain.usecase;


import com.pragma.powerup.plazamicroservice.domain.api.IDishServicePort;
import com.pragma.powerup.plazamicroservice.domain.exceptions.OwnerNotAuthorizedException;
import com.pragma.powerup.plazamicroservice.domain.model.Dish;
import com.pragma.powerup.plazamicroservice.domain.model.Restaurant;
import com.pragma.powerup.plazamicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup.plazamicroservice.domain.spi.IJwtProviderConfigurationPort;
import com.pragma.powerup.plazamicroservice.domain.spi.IRestaurantPersistencePort;

import static java.lang.String.valueOf;


public class DishUseCase implements IDishServicePort {

    private final IDishPersistencePort dishPersistencePort;
    private final IJwtProviderConfigurationPort jwtProviderConfigurationPort;

    private final IRestaurantPersistencePort restaurantPersistencePort;


    public DishUseCase(IDishPersistencePort dishPersistencePort, IRestaurantPersistencePort restaurantPersistencePort,
                       IJwtProviderConfigurationPort jwtProviderConfigurationPort) {

        this.dishPersistencePort = dishPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.jwtProviderConfigurationPort = jwtProviderConfigurationPort;

    }

    @Override
    public Dish saveDish( Dish dish, String token ) {

        Long idRestaurant = dish.getRestaurant().getId();
        Restaurant restaurant = restaurantPersistencePort.getRestaurantById( idRestaurant );
        Long idOwner = restaurant.getIdOwner();
        String idUser = jwtProviderConfigurationPort.getIdFromToken(token);

        if ( !idUser.equals( valueOf(idOwner) ) ) {
            throw new OwnerNotAuthorizedException();
        }

        dish.setActive(true);
        return dishPersistencePort.saveDish(dish);

    }

    @Override
    public Dish updateDish(Dish dish, String token) {

        Long idRestaurant = dish.getRestaurant().getId();
        Restaurant restaurant = restaurantPersistencePort.getRestaurantById( idRestaurant );
        Long idOwner = restaurant.getIdOwner();
        String idUser = jwtProviderConfigurationPort.getIdFromToken(token);

        if ( !idUser.equals( valueOf(idOwner) ) ) {
            throw new OwnerNotAuthorizedException();
        }

        return dishPersistencePort.updateDish(dish);

    }

}
