package com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.mappers;

import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.entity.EmployeeEntity;
import com.pragma.powerup.plazamicroservice.domain.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IEmployeeEntityMapper {

    @Mapping(target = "restaurantEntity.id", source = "restaurant.id")
    EmployeeEntity toEntity(Employee employeeRestaurant);

    @Mapping(target = "restaurant.id", source = "restaurantEntity.id")
    Employee toEmployee(EmployeeEntity employeeRestaurantEntity);

    List<Employee> toEmployeeList(List<EmployeeEntity> employeeEntityList);

}
