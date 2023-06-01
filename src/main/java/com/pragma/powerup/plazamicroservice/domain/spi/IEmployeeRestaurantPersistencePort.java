package com.pragma.powerup.plazamicroservice.domain.spi;

import com.pragma.powerup.plazamicroservice.domain.model.EmployeeRestaurant;


public interface IEmployeeRestaurantPersistencePort {

    EmployeeRestaurant saveEmployeeRestaurant(Long idEmployee, Long idRestaurant);

}
