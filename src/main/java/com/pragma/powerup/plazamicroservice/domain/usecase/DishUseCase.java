package com.pragma.powerup.plazamicroservice.domain.usecase;


import com.pragma.powerup.plazamicroservice.domain.api.IDishServicePort;
import com.pragma.powerup.plazamicroservice.domain.exceptions.OwnerNotAuthorizedException;
import com.pragma.powerup.plazamicroservice.domain.model.Dish;
import com.pragma.powerup.plazamicroservice.domain.model.Restaurant;
import com.pragma.powerup.plazamicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup.plazamicroservice.domain.spi.IJwtProviderConfigurationPort;
import com.pragma.powerup.plazamicroservice.domain.spi.IRestaurantPersistencePort;
import org.springframework.data.domain.Pageable;

import java.util.List;

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
        Restaurant restaurant = restaurantPersistencePort.findRestaurantById( idRestaurant );
        Long idOwner = restaurant.getIdOwner();
        String idUser = jwtProviderConfigurationPort.getIdFromToken(token);

        if ( !idUser.equals( valueOf(idOwner) ) ) {
            throw new OwnerNotAuthorizedException();
        }

        dish.setActive(true);
        return dishPersistencePort.saveDish(dish);

    }

    @Override
    public Dish updateDish(Dish dishReq, String token) {

        Dish dish = dishPersistencePort.findDishById(dishReq.getId());
        Long idRestaurant = dish.getRestaurant().getId();
        Restaurant restaurant = restaurantPersistencePort.findRestaurantById( idRestaurant );
        Long idOwner = restaurant.getIdOwner();
        String idUser = jwtProviderConfigurationPort.getIdFromToken(token);

        if ( !idUser.equals( valueOf(idOwner) ) ) {
            throw new OwnerNotAuthorizedException();
        }

        dish.setDescription(dishReq.getDescription());
        dish.setPrice(dishReq.getPrice());

        return dishPersistencePort.updateDish(dish);

    }

    @Override
    public Dish activeDish(Dish dishReq, String token) {

        Dish dish = dishPersistencePort.findDishById(dishReq.getId());
        Long idRestaurant = dish.getRestaurant().getId();
        Restaurant restaurant = restaurantPersistencePort.findRestaurantById( idRestaurant );
        Long idOwner = restaurant.getIdOwner();
        String idUser = jwtProviderConfigurationPort.getIdFromToken(token);

        if ( !idUser.equals( valueOf(idOwner) ) ) {
            throw new OwnerNotAuthorizedException();
        }

        dish.setActive(dishReq.getActive());

        return dishPersistencePort.updateDish(dish);

    }

    //@Override
    public List<Dish> getDishesByRestaurantAndCategory(Long idRestaurant, Long idCategory, Pageable pageable){
        return dishPersistencePort.findDishesByRestaurantAndCategory( idRestaurant, idCategory, pageable );
    }


}
