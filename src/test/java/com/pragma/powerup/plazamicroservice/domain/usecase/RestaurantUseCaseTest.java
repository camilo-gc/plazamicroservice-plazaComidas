package com.pragma.powerup.plazamicroservice.domain.usecase;

import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.exceptions.*;
import com.pragma.powerup.plazamicroservice.domain.dto.User;
import com.pragma.powerup.plazamicroservice.domain.exceptions.OwnerNotAuthorizedException;
import com.pragma.powerup.plazamicroservice.domain.exceptions.RoleNotAllowedForCreationException;
import com.pragma.powerup.plazamicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.plazamicroservice.domain.exceptions.FieldValidationException;
import com.pragma.powerup.plazamicroservice.domain.model.Restaurant;
import com.pragma.powerup.plazamicroservice.domain.spi.IEmployeePersistencePort;
import com.pragma.powerup.plazamicroservice.domain.spi.IJwtProviderConfigurationPort;
import com.pragma.powerup.plazamicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.plazamicroservice.domain.spi.IUserApiFeignPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {

    private IRestaurantPersistencePort restaurantPersistencePort;
    private IEmployeePersistencePort employeeRestaurantPersistencePort;
    private IUserApiFeignPort userApiFeignPort;
    private IRestaurantServicePort restaurantServicePort;
    private IJwtProviderConfigurationPort jwtProviderConfigurationPort;

    @BeforeEach
    void setUp() {
        restaurantPersistencePort = mock(IRestaurantPersistencePort.class);
        employeeRestaurantPersistencePort = mock(IEmployeePersistencePort.class);
        userApiFeignPort = mock(IUserApiFeignPort.class);
        jwtProviderConfigurationPort = mock(IJwtProviderConfigurationPort.class);
        restaurantServicePort = new RestaurantUseCase(
                restaurantPersistencePort,
                userApiFeignPort,
                employeeRestaurantPersistencePort,
                jwtProviderConfigurationPort
        );

    }

    @Test
    void saveRestaurantFieldValidation(){

        Restaurant restaurant = new Restaurant( null, "", "", 0L, "", "", "");

        assertThrows(FieldValidationException.class, () -> restaurantServicePort.saveRestaurant(restaurant, "token"));

    }

    @Test
    void saveRestaurantUnauthorizedValidationException(){

        Restaurant restaurant = new Restaurant( null, "Pare&coma", "av 0", 0L,
                "+573333333333", "pare&coma.com/recursos/logo.jpg", "987987987987");

        doThrow(UnauthorizedOwnerValidationException.class).when(userApiFeignPort).findOwnerById(restaurant.getIdOwner(), "token");
        assertThrows(UnauthorizedOwnerValidationException.class, () -> restaurantServicePort.saveRestaurant(restaurant, "token"));

    }

    @Test
    void saveRestaurantOwnerNotFoundException(){

        Restaurant restaurant = new Restaurant( null, "Pare&coma", "av 0", 0L,
                "+573333333333", "pare&coma.com/recursos/logo.jpg", "987987987");

        doThrow(OwnerNotFoundException.class).when(userApiFeignPort).findOwnerById(restaurant.getIdOwner(), "token");
        assertThrows(OwnerNotFoundException.class, () -> restaurantServicePort.saveRestaurant(restaurant, "token"));

    }

    @Test
    void saveRestaurantFindOwnerInternalError(){

        String token = "$2a$10$2edn/0De4Lk2IovglOz8fuC8z3b7FsctfiotMd9LMRitQnUgyPOW6";
        Restaurant restaurant = new Restaurant( null, "Pare&coma", "av 0", 2L,
                "+573333333333", "pare&coma.com/recursos/logo.jpg", "987987987");

        doThrow(HttpServerErrorException.class).when(userApiFeignPort).findOwnerById(restaurant.getIdOwner(), token);
        assertThrows(HttpServerErrorException.class, () -> restaurantServicePort.saveRestaurant(restaurant, token));

    }

    @Test
    void saveRestaurantOwnerNotValidException(){

        Restaurant restaurant = new Restaurant( null, "Pare&coma", "av 0", 2L,
                "+573333333333", "pare&coma.com/recursos/logo.jpg", "987987987987");
        User owner = new User( 3L, "Pepito", "Perez", "333", "+573333333333",
                 "", "pepitoperez@gmail.com",  "1234",3L);

        when(userApiFeignPort.findOwnerById(restaurant.getIdOwner(), "token")).thenReturn(owner);
        assertThrows(RoleNotAllowedForCreationException.class, () -> restaurantServicePort.saveRestaurant(restaurant, "token"));

    }

    @Test
    void saveRestaurantSuccessful(){

        Restaurant restaurant = new Restaurant( null, "Pare&coma", "av 0", 2L,
                "+573333333333", "pare&coma.com/recursos/logo.jpg", "987987987987");
        User owner = new User( 2L, "Pepito", "Perez", "333", "+573333333333",
                 "","pepitoperez@gmail.com", "1234",2L);

        when(userApiFeignPort.findOwnerById(restaurant.getIdOwner(), "token")).thenReturn(owner);
        when(restaurantPersistencePort.saveRestaurant(restaurant)).thenReturn(restaurant);
        assertNotNull(restaurantServicePort.saveRestaurant(restaurant, "token"));

    }


    @Test
    void addEmployeeRestaurantNotFoundException(){

        User user = new User( null, "Pepito", "Perez", "111",
                "+555555555555", "01-01-0101", "pepitoperez@gmail.com",
                "$2a$10$2edn/0De4Lk2IovglOz8fuC8z3b7FsctfiotMd9LMRitQnUgyPOW6", 2L);

        doThrow(RestaurantNotFoundException.class).when(restaurantPersistencePort).findRestaurantById( 0L );
        assertThrows(
                RestaurantNotFoundException.class,
                ()-> restaurantServicePort.addEmployeeToRestaurant(0L, user, "token")
        );

    }

    @Test
    void addEmployeeRestaurantOwnerException(){

        String token = "Bearer $2a$10$2edn/0De4Lk2IovglOz8fuC8z3b7FsctfiotMd9LMRitQnUgyPOW6";
        User employee = new User( null, "Pepito", "Perez", "111",
                "+555555555555", "01-01-0101", "pepitoperez@gmail.com",
                "$2a$10$2edn/0De4Lk2IovglOz8fuC8z3b7FsctfiotMd9LMRitQnUgyPOW6", 2L);
        Restaurant restaurant = new Restaurant( 1L, "pepe food", "string", 2L,
                "+793247501667", "http://pepefood.com/recursos/logo.jpg", "111" );

        when(restaurantPersistencePort.findRestaurantById(1L)).thenReturn(restaurant);
        when(jwtProviderConfigurationPort.getIdFromToken(token.substring(7))).thenReturn("6");

        assertThrows(
                OwnerNotAuthorizedException.class,
                ()-> restaurantServicePort.addEmployeeToRestaurant( restaurant.getId(), employee, token)
        );

    }

    @Test
    void addEmployeeUnauthorizedException() {

        String token = "Bearer $2a$10$2edn/0De4Lk2IovglOz8fuC8z3b7FsctfiotMd9LMRitQnUgyPOW6";
        User employee = new User( null, "Pepito", "Perez", "111",
                "+555555555555", "01-01-0101", "pepitoperez@gmail.com",
                "$2a$10$2edn/0De4Lk2IovglOz8fuC8z3b7FsctfiotMd9LMRitQnUgyPOW6", 2L);
        Restaurant restaurant = new Restaurant( 2L, "pepe food", "string", 2L,
                "+793247501667", "http://pepefood.com/recursos/logo.jpg", "111" );

        when(restaurantPersistencePort.findRestaurantById(2L)).thenReturn(restaurant);
        when(jwtProviderConfigurationPort.getIdFromToken(token.substring(7))).thenReturn("2");

        doThrow(UnauthorizedOwnerValidationException.class).when(userApiFeignPort).saveEmployee(employee, token);
        assertThrows(
                UnauthorizedOwnerValidationException.class,
                () -> restaurantServicePort.addEmployeeToRestaurant( restaurant.getId(), employee, token )
        );
    }

    @Test
    void addEmployeeAlreadyExistsException() {

        String token = "Bearer $2a$10$2edn/0De4Lk2IovglOz8fuC8z3b7FsctfiotMd9LMRitQnUgyPOW6";
        User employee = new User( null, "Pepito", "Perez", "111",
                "+555555555555", "01-01-0101", "pepitoperez@gmail.com",
                "$2a$10$2edn/0De4Lk2IovglOz8fuC8z3b7FsctfiotMd9LMRitQnUgyPOW6", 2L);
        Restaurant restaurant = new Restaurant( 2L, "pepe food", "string", 2L,
                "+793247501667", "http://pepefood.com/recursos/logo.jpg", "111" );

        when(restaurantPersistencePort.findRestaurantById(2L)).thenReturn(restaurant);
        when(jwtProviderConfigurationPort.getIdFromToken(token.substring(7))).thenReturn("2");

        doThrow(UserAlreadyExistsException.class).when(userApiFeignPort).saveEmployee(employee, token);
        assertThrows(
                UserAlreadyExistsException.class,
                () -> restaurantServicePort.addEmployeeToRestaurant( restaurant.getId(), employee, token )
        );
    }

    @Test
    void addEmployeeServerErrorException() {

        String token = "Bearer $2a$10$2edn/0De4Lk2IovglOz8fuC8z3b7FsctfiotMd9LMRitQnUgyPOW6";
        User employee = new User( null, "Pepito", "Perez", "111",
                "+555555555555", "01-01-0101", "pepitoperez@gmail.com",
                "$2a$10$2edn/0De4Lk2IovglOz8fuC8z3b7FsctfiotMd9LMRitQnUgyPOW6", 2L);
        Restaurant restaurant = new Restaurant( 2L, "pepe food", "string", 2L,
                "+793247501667", "http://pepefood.com/recursos/logo.jpg", "111" );

        when(restaurantPersistencePort.findRestaurantById(2L)).thenReturn(restaurant);
        when(jwtProviderConfigurationPort.getIdFromToken(token.substring(7))).thenReturn("2");

        doThrow(HttpServerErrorException.class).when(userApiFeignPort).saveEmployee(employee, token);
        assertThrows(
                HttpServerErrorException.class,
                () -> restaurantServicePort.addEmployeeToRestaurant( restaurant.getId(), employee, token )
        );
    }

    @Test
    void addEmployeeRestaurantSuccessful() {

        String token = "Bearer $2a$10$2edn/0De4Lk2IovglOz8fuC8z3b7FsctfiotMd9LMRitQnUgyPOW6";
        User employee = new User( null, "Pepito", "Perez", "111",
                "+555555555555", "01-01-0101", "pepitoperez@gmail.com",
                "$2a$10$2edn/0De4Lk2IovglOz8fuC8z3b7FsctfiotMd9LMRitQnUgyPOW6", 2L);
        Restaurant restaurant = new Restaurant( 2L, "pepe food", "string", 2L,
                "+793247501667", "http://pepefood.com/recursos/logo.jpg", "111" );

        when(restaurantPersistencePort.findRestaurantById(2L)).thenReturn(restaurant);
        when(jwtProviderConfigurationPort.getIdFromToken(token.substring(7))).thenReturn("2");
        when(userApiFeignPort.saveEmployee( employee, token )).thenReturn(employee);

        employeeRestaurantPersistencePort.saveEmployee(employee.getId(), restaurant.getId());
        verify(employeeRestaurantPersistencePort).saveEmployee(employee.getId(), restaurant.getId());

        assertNotNull(restaurantServicePort.addEmployeeToRestaurant( restaurant.getId(), employee, token ));

    }


    @Test
    void getAllRestaurantsNotFound(){

        int page = 1;
        int size = 10;
        Pageable pageable = PageRequest.of( page-1, size, Sort.by(Sort.Direction.ASC, "name") );

        doThrow(NoDataFoundException.class).when(restaurantPersistencePort).findAllRestaurants(pageable);
        assertThrows(NoDataFoundException.class, ()-> restaurantServicePort.getAllRestaurants(pageable));

    }

    @Test
    void getAllRestaurantsSuccessful(){

        int page = 1;
        int size = 10;
        Pageable pageable = PageRequest.of( page-1, size, Sort.by(Sort.Direction.ASC, "name") );
        Restaurant restaurant = new Restaurant( 2L, "Pare&coma", "av 0", 2L,
                "+573333333333", "pare&coma.com/recursos/logo.jpg", "987987987987");

        List<Restaurant> restaurantList = new ArrayList<>();
        restaurantList.add(restaurant);

        when(restaurantPersistencePort.findAllRestaurants(pageable)).thenReturn(restaurantList);
        assertNotNull(restaurantServicePort.getAllRestaurants(pageable));
        assertFalse(restaurantServicePort.getAllRestaurants(pageable).isEmpty());

    }



}
