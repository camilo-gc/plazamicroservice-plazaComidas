package com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.entity.EmployeeRestaurantEntity;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.mappers.IEmployeeRestaurantEntityMapper;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.mappers.IRestaurantEntityMapper;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.repositories.IEmployeeRestaurantRepository;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
import com.pragma.powerup.plazamicroservice.domain.model.EmployeeRestaurant;
import com.pragma.powerup.plazamicroservice.domain.model.Restaurant;
import com.pragma.powerup.plazamicroservice.domain.spi.IEmployeeRestaurantPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Transactional
public class EmployeeRestaurantMysqlAdapter implements IEmployeeRestaurantPersistencePort {

    private final IEmployeeRestaurantRepository employeeRestaurantRepository;
    private final IEmployeeRestaurantEntityMapper employeeRestaurantEntityMapper;

    @Override
    public EmployeeRestaurant saveEmployeeRestaurant( Long idEmployee, Long idRestaurant ) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(idRestaurant);
        EmployeeRestaurant employeeRestaurant = new EmployeeRestaurant(null, idEmployee, restaurant);

        return employeeRestaurantEntityMapper.toEmployeeRestaurant(
                employeeRestaurantRepository.save(employeeRestaurantEntityMapper.toEntity(employeeRestaurant))
        );

    }

}
