package com.pragma.powerup.plazamicroservice.domain.spi;


import com.pragma.powerup.plazamicroservice.domain.model.Employee;

public interface IEmployeePersistencePort {

    Employee saveEmployee(Long idEmployee, Long idRestaurant);

}
