package com.pragma.powerup.plazamicroservice.domain.usecase;

import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.exceptions.CategoryNotFoundException;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.exceptions.DishNotFoundException;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.exceptions.NoDataFoundException;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.exceptions.RestaurantNotFoundException;
import com.pragma.powerup.plazamicroservice.domain.api.IDishServicePort;
import com.pragma.powerup.plazamicroservice.domain.exceptions.OwnerNotAuthorizedException;
import com.pragma.powerup.plazamicroservice.domain.model.Category;
import com.pragma.powerup.plazamicroservice.domain.model.Dish;
import com.pragma.powerup.plazamicroservice.domain.model.Restaurant;
import com.pragma.powerup.plazamicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup.plazamicroservice.domain.spi.IJwtProviderConfigurationPort;
import com.pragma.powerup.plazamicroservice.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DishUseCaseTest {

    private IDishPersistencePort dishPersistencePort;
    private IJwtProviderConfigurationPort jwtProviderConfigurationPort;
    private IRestaurantPersistencePort restaurantPersistencePort;
    private IDishServicePort dishServicePort;

    @BeforeEach
    void setUp() {
        this.dishPersistencePort = mock(IDishPersistencePort.class);
        this.jwtProviderConfigurationPort = mock(IJwtProviderConfigurationPort.class);
        this.restaurantPersistencePort = mock(IRestaurantPersistencePort.class);
        this.dishServicePort = new DishUseCase(dishPersistencePort, restaurantPersistencePort, jwtProviderConfigurationPort);
    }


    @Test
    void saveDishRestaurantException(){

        Category category = new Category( 1L, "Entrada", "plato suave para iniciar" );
        Restaurant restaurant = new Restaurant( 200L, "pepe food", "string", 2L,
                "+793247501667", "http://pepefood.com/recursos/logo.jpg", "111" );
        Dish dish = new Dish( null, "asd", category, "asd", 1L, restaurant, "asd", null );

        doThrow(RestaurantNotFoundException.class).when(restaurantPersistencePort).findRestaurantById(dish.getRestaurant().getId());
        assertThrows(RestaurantNotFoundException.class, ()-> dishServicePort.saveDish(dish, "token"));

    }

    @Test
    void saveDishOwnerException(){

        Category category = new Category( 1L, "Entrada", "plato suave para iniciar" );
        Restaurant restaurant = new Restaurant( 1L, "pepe food", "string", 2L,
                "+793247501667", "http://pepefood.com/recursos/logo.jpg", "111" );

        Dish dish = new Dish( null, "", category, "", 1L, restaurant, "", true );
        when(restaurantPersistencePort.findRestaurantById(dish.getRestaurant().getId())).thenReturn(restaurant);
        when(jwtProviderConfigurationPort.getIdFromToken("token")).thenReturn("6");

        assertThrows(OwnerNotAuthorizedException.class, ()-> dishServicePort.saveDish(dish, "token"));

    }

    @Test
    void saveDishCategoryException(){

        Category category = new Category( 155L, "Entrada", "plato suave para iniciar" );
        Restaurant restaurant = new Restaurant( 2L, "pepe food", "string", 2L,
                "+793247501667", "http://pepefood.com/recursos/logo.jpg", "111" );
        Dish dish = new Dish( 20L, "asd", category, "asd", 1L, restaurant, "asd", true );

        when(restaurantPersistencePort.findRestaurantById(dish.getRestaurant().getId())).thenReturn(restaurant);
        when(jwtProviderConfigurationPort.getIdFromToken("token")).thenReturn("2");
        doThrow(CategoryNotFoundException.class).when(dishPersistencePort).saveDish(dish);
        assertThrows(CategoryNotFoundException.class, ()-> dishServicePort.saveDish(dish, "token"));

    }

    @Test
    void saveDishSuccessful(){

        Category category = new Category( 155L, "Entrada", "plato suave para iniciar" );
        Restaurant restaurant = new Restaurant( 2L, "pepe food", "string", 2L,
                "+793247501667", "http://pepefood.com/recursos/logo.jpg", "111" );
        Dish dish = new Dish( 20L, "asd", category, "asd", 1L, restaurant, "asd", true );

        when(restaurantPersistencePort.findRestaurantById(dish.getRestaurant().getId())).thenReturn(restaurant);
        when(jwtProviderConfigurationPort.getIdFromToken("token")).thenReturn("2");
        when(dishPersistencePort.saveDish(dish)).thenReturn(dish);
        assertNotNull(dishServicePort.saveDish(dish, "token"));

    }


    @Test
    void updateDishNotFoundException() {

        Category category = new Category(1L, "Entrada", "plato suave para iniciar");
        Restaurant restaurant = new Restaurant(1L, "pepe food", "string", 2L,
                "+793247501667", "http://pepefood.com/recursos/logo.jpg", "111");
        Dish dish = new Dish(20L, "asd", category, "asd", 1L, restaurant, "asd", true);

        doThrow(DishNotFoundException.class).when(dishPersistencePort).findDishById(dish.getId());
        assertThrows(DishNotFoundException.class, () -> dishServicePort.updateDish(dish, "token"));

    }

    @Test
    void updateDishRestaurantException() {

        Category category = new Category( 1L, "Entrada", "plato suave para iniciar" );
        Restaurant restaurant = new Restaurant( 200L, "pepe food", "string", 2L,
                "+793247501667", "http://pepefood.com/recursos/logo.jpg", "111" );
        Dish dish = new Dish( 2L, "asd", category, "asd", 1L, restaurant, "asd", null );

        when(dishPersistencePort.findDishById(dish.getId())).thenReturn(dish);
        doThrow(RestaurantNotFoundException.class).when(restaurantPersistencePort).findRestaurantById(dish.getRestaurant().getId());
        assertThrows(RestaurantNotFoundException.class, ()-> dishServicePort.updateDish(dish, "token"));

    }

    @Test
    void updateDishOwnerException(){

        Category category = new Category( 1L, "Entrada", "plato suave para iniciar" );
        Restaurant restaurant = new Restaurant( 1L, "pepe food", "string", 2L,
                "+793247501667", "http://pepefood.com/recursos/logo.jpg", "111" );
        Dish dish = new Dish( 2L, "", category, "", 1L, restaurant, "", true );

        when(dishPersistencePort.findDishById(dish.getId())).thenReturn(dish);
        when(restaurantPersistencePort.findRestaurantById(dish.getRestaurant().getId())).thenReturn(restaurant);
        when(jwtProviderConfigurationPort.getIdFromToken("token")).thenReturn("6");

        assertThrows(OwnerNotAuthorizedException.class, ()-> dishServicePort.updateDish(dish, "token"));

    }

    @Test
    void updateDishSuccessful(){

        Category category = new Category( 155L, "Entrada", "plato suave para iniciar" );
        Restaurant restaurant = new Restaurant( 2L, "pepe food", "string", 2L,
                "+793247501667", "http://pepefood.com/recursos/logo.jpg", "111" );
        Dish dish = new Dish( 2L, "asd", category, "asd", 1L, restaurant, "asd", true );

        when(dishPersistencePort.findDishById(dish.getId())).thenReturn(dish);
        when(restaurantPersistencePort.findRestaurantById(dish.getRestaurant().getId())).thenReturn(restaurant);
        when(jwtProviderConfigurationPort.getIdFromToken("token")).thenReturn("2");
        when(dishPersistencePort.updateDish(dish)).thenReturn(dish);
        assertNotNull(dishServicePort.updateDish(dish, "token"));

    }


    @Test
    void activeDishNotFoundException() {

        Category category = new Category(1L, "Entrada", "plato suave para iniciar");
        Restaurant restaurant = new Restaurant(1L, "pepe food", "string", 2L,
                "+793247501667", "http://pepefood.com/recursos/logo.jpg", "111");
        Dish dish = new Dish(20L, "asd", category, "asd", 1L, restaurant, "asd", true);

        doThrow(DishNotFoundException.class).when(dishPersistencePort).findDishById(dish.getId());
        assertThrows(DishNotFoundException.class, () -> dishServicePort.activeDish(dish, "token"));

    }

    @Test
    void activeDishRestaurantException() {

        Category category = new Category( 1L, "Entrada", "plato suave para iniciar" );
        Restaurant restaurant = new Restaurant( 200L, "pepe food", "string", 2L,
                "+793247501667", "http://pepefood.com/recursos/logo.jpg", "111" );
        Dish dish = new Dish( 2L, "asd", category, "asd", 1L, restaurant, "asd", null );

        when(dishPersistencePort.findDishById(dish.getId())).thenReturn(dish);
        doThrow(RestaurantNotFoundException.class).when(restaurantPersistencePort).findRestaurantById(dish.getRestaurant().getId());
        assertThrows(RestaurantNotFoundException.class, ()-> dishServicePort.activeDish(dish, "token"));

    }

    @Test
    void activeDishOwnerException(){

        Category category = new Category( 1L, "Entrada", "plato suave para iniciar" );
        Restaurant restaurant = new Restaurant( 1L, "pepe food", "string", 2L,
                "+793247501667", "http://pepefood.com/recursos/logo.jpg", "111" );
        Dish dish = new Dish( 2L, "", category, "", 1L, restaurant, "", true );

        when(dishPersistencePort.findDishById(dish.getId())).thenReturn(dish);
        when(restaurantPersistencePort.findRestaurantById(dish.getRestaurant().getId())).thenReturn(restaurant);
        when(jwtProviderConfigurationPort.getIdFromToken("token")).thenReturn("6");

        assertThrows(OwnerNotAuthorizedException.class, ()-> dishServicePort.activeDish(dish, "token"));

    }

    @Test
    void activeDishSuccessful(){

        Category category = new Category( 155L, "Entrada", "plato suave para iniciar" );
        Restaurant restaurant = new Restaurant( 2L, "pepe food", "string", 2L,
                "+793247501667", "http://pepefood.com/recursos/logo.jpg", "111" );
        Dish dish = new Dish( 2L, "asd", category, "asd", 1L, restaurant, "asd", true );

        when(dishPersistencePort.findDishById(dish.getId())).thenReturn(dish);
        when(restaurantPersistencePort.findRestaurantById(dish.getRestaurant().getId())).thenReturn(restaurant);
        when(jwtProviderConfigurationPort.getIdFromToken("token")).thenReturn("2");
        when(dishPersistencePort.updateDish(dish)).thenReturn(dish);
        assertNotNull(dishServicePort.activeDish(dish, "token"));

    }


    @Test
    void getDishesByRestaurantAndCategoryNotFound(){

        Long idRestaurant = 4L;
        Long idCategory = 2L;
        int page = 1;
        int size = 10;
        Pageable pageable = PageRequest.of( page-1, size, Sort.by(Sort.Direction.ASC, "name") );

        doThrow(NoDataFoundException.class).when(dishPersistencePort).findDishesByRestaurantAndCategory( idRestaurant, idCategory, pageable );
        assertThrows(NoDataFoundException.class, () -> dishServicePort.getDishesByRestaurantAndCategory( idRestaurant, idCategory, pageable ));

    }

    @Test
    void getDishesByRestaurantAndCategorySuccessful(){

        Long idRestaurant = 4L;
        Long idCategory = 2L;
        int page = 1;
        int size = 10;
        Pageable pageable = PageRequest.of( page-1, size, Sort.by(Sort.Direction.ASC, "name") );
        Category category = new Category( idCategory, "Entrada", "plato suave para iniciar" );
        Restaurant restaurant = new Restaurant( idRestaurant, "pepe food", "string", 2L,
                "+793247501667", "http://pepefood.com/recursos/logo.jpg", "111" );
        Dish dish = new Dish( 2L, "asd", category, "asd", 1L, restaurant, "asd", true );
        List<Dish> dishList = new ArrayList<>();
        dishList.add(dish);

        when(dishPersistencePort.findDishesByRestaurantAndCategory( idRestaurant, idCategory, pageable )).thenReturn(dishList);
        assertNotNull(dishServicePort.getDishesByRestaurantAndCategory( idRestaurant, idCategory, pageable ));
        assertFalse(dishServicePort.getDishesByRestaurantAndCategory( idRestaurant, idCategory, pageable ).isEmpty());

    }


}