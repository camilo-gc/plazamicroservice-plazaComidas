package com.pragma.powerup.plazamicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.response.RestaurantNewResponseDto;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.response.RestaurantResponseDto;
import com.pragma.powerup.plazamicroservice.domain.model.Order;
import com.pragma.powerup.plazamicroservice.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderResponseMapper {

    @Mapping(target = "id_client", source = "idClient")
    @Mapping(target = "id_chef", source = "chef.idEmployee")
    @Mapping(target = "id_restaurant", source = "restaurant.id")
    OrderResponseDto toResponse(Order order);

    List<OrderResponseDto> toResponseList(List<Order> orderList);

}
