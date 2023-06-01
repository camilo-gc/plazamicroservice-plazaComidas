package com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.mappers;

import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.entity.EmployeeRestaurantEntity;
import com.pragma.powerup.plazamicroservice.domain.model.EmployeeRestaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IEmployeeRestaurantEntityMapper {

    @Mapping(target = "id_employee", source = "idEmployee")
    @Mapping(target = "restaurantEntity.id", source = "restaurant.id")
    EmployeeRestaurantEntity toEntity(EmployeeRestaurant employeeRestaurant);

    @Mapping(target = "restaurant.id", source = "restaurantEntity.id")
    EmployeeRestaurant toEmployeeRestaurant(EmployeeRestaurantEntity employeeRestaurantEntity);

    List<EmployeeRestaurant> toEmployeeRestaurantList(List<EmployeeRestaurantEntity> employeeRestaurantEntity);

}
