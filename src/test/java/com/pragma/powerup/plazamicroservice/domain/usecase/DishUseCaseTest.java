package com.pragma.powerup.plazamicroservice.domain.usecase;

import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.exceptions.CategoryNotFoundException;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.exceptions.RestaurantNotFoundException;
import com.pragma.powerup.plazamicroservice.domain.api.IDishServicePort;
import com.pragma.powerup.plazamicroservice.domain.model.Category;
import com.pragma.powerup.plazamicroservice.domain.model.Dish;
import com.pragma.powerup.plazamicroservice.domain.model.Restaurant;
import com.pragma.powerup.plazamicroservice.domain.spi.IDishPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DishUseCaseTest {

    private IDishPersistencePort dishPersistencePort;
    private IDishServicePort dishServicePort;

    @BeforeEach
    void setUp() {
        this.dishPersistencePort = mock(IDishPersistencePort.class);
        this.dishServicePort = new DishUseCase(dishPersistencePort);
    }

    @Test
    void saveDishCategoryException(){

        Category category = new Category( 0L, null, null );
        Restaurant restaurant = new Restaurant( 1L,null,null,null,null,null,null );
        Dish dish = new Dish( null, "", category, "", 1L, restaurant, "", true );
        doThrow(CategoryNotFoundException.class).when(dishPersistencePort).saveDish(dish);
        assertThrows(CategoryNotFoundException.class, ()-> dishPersistencePort.saveDish(dish));

    }

    @Test
    void saveDishRestaurantException(){

        Category category = new Category( 1L, null, null );
        Restaurant restaurant = new Restaurant( 0L,null,null,null,null,null,null );
        Dish dish = new Dish( null, "", category, "", 1L, restaurant, "", true );
        doThrow(RestaurantNotFoundException.class).when(dishPersistencePort).saveDish(dish);
        assertThrows(RestaurantNotFoundException.class, ()-> dishPersistencePort.saveDish(dish));

    }

    @Test
    void saveDishSuccessful(){

        Category category = new Category( 1L, "", "" );
        Restaurant restaurant = new Restaurant( 1L,"","",2L,"","","" );
        Dish dishReq = new Dish( null, "", category, "", 1L, restaurant, "", true );
        Dish dishRes = new Dish( 1L, "", category, "", 1L, restaurant, "", true );

        when(dishPersistencePort.saveDish(dishReq)).thenReturn(dishRes);
        assertNotNull(dishServicePort.saveDish(dishReq));

    }

}