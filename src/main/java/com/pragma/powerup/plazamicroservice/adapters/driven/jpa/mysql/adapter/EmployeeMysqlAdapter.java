package com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.mappers.IEmployeeEntityMapper;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.repositories.IEmployeeRepository;
import com.pragma.powerup.plazamicroservice.domain.model.Employee;
import com.pragma.powerup.plazamicroservice.domain.model.Restaurant;
import com.pragma.powerup.plazamicroservice.domain.spi.IEmployeePersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Transactional
public class EmployeeMysqlAdapter implements IEmployeePersistencePort {

    private final IEmployeeRepository employeeRepository;
    private final IEmployeeEntityMapper employeeEntityMapper;

    @Override
    public Employee saveEmployee(Long idEmployee, Long idRestaurant ) {

        Restaurant restaurant = new Restaurant();
        restaurant.setId(idRestaurant);
        Employee employee = new Employee(null, idEmployee, restaurant);

        return employeeEntityMapper.toEmployee(
                employeeRepository.save(employeeEntityMapper.toEntity(employee))
        );

    }

    @Override
    public Employee findByIdEmployee(Long idEmployee) {
        return employeeEntityMapper.toEmployee(
                employeeRepository.findByIdEmployee( idEmployee )
        );
    }

}