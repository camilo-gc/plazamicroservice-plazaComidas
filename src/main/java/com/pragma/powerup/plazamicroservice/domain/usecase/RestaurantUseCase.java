package com.pragma.powerup.plazamicroservice.domain.usecase;

import com.pragma.powerup.plazamicroservice.domain.dto.User;
import com.pragma.powerup.plazamicroservice.domain.exceptions.OwnerNotAuthorizedException;
import com.pragma.powerup.plazamicroservice.domain.exceptions.RoleNotAllowedForCreationException;
import com.pragma.powerup.plazamicroservice.configuration.Constants;
import com.pragma.powerup.plazamicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.plazamicroservice.domain.model.Restaurant;
import com.pragma.powerup.plazamicroservice.domain.spi.IEmployeePersistencePort;
import com.pragma.powerup.plazamicroservice.domain.spi.IJwtProviderConfigurationPort;
import com.pragma.powerup.plazamicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.plazamicroservice.domain.spi.IUserApiFeignPort;
import com.pragma.powerup.plazamicroservice.domain.utils.FieldValidation;
import org.springframework.data.domain.Pageable;

import java.util.List;


public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserApiFeignPort userApiFeignPort;
    private final IJwtProviderConfigurationPort jwtProviderConfigurationPort;
    private final IEmployeePersistencePort employeeRestaurantPersistencePort;


    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort, IUserApiFeignPort userApiFeignPort,
                             IEmployeePersistencePort employeeRestaurantPersistencePort,
                             IJwtProviderConfigurationPort jwtProviderConfigurationPort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userApiFeignPort = userApiFeignPort;
        this.employeeRestaurantPersistencePort = employeeRestaurantPersistencePort;
        this.jwtProviderConfigurationPort = jwtProviderConfigurationPort;
    }

    public Restaurant saveRestaurant(Restaurant restaurant, String authorizationHeader) {

        FieldValidation.restaurantValidate(restaurant);

        User owner = userApiFeignPort.findOwnerById( restaurant.getIdOwner(), authorizationHeader );

        if ( !owner.getIdRole().equals(Constants.OWNER_ROLE_ID) ) {
            throw new RoleNotAllowedForCreationException();
        }

        return restaurantPersistencePort.saveRestaurant(restaurant);

    }

    @Override
    public User addEmployeeToRestaurant(Long idRestaurant, User employee, String token) {

        Restaurant restaurant = restaurantPersistencePort.findRestaurantById( idRestaurant );
        Long idOwner = restaurant.getIdOwner();
        String idUser = jwtProviderConfigurationPort.getIdFromToken(token.substring(7));

        if (!idUser.equals( String.valueOf(idOwner) ) ) {
            throw new OwnerNotAuthorizedException();
        }

        User newEmployee = userApiFeignPort.saveEmployee( employee, token );
        employeeRestaurantPersistencePort.saveEmployee( newEmployee.getId(), restaurant.getId());

        return newEmployee;
    }

    @Override
    public List<Restaurant> getAllRestaurants(Pageable pageable) {
        return restaurantPersistencePort.findAllRestaurants(pageable);
    }

}
