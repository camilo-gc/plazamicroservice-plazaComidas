package com.pragma.powerup.plazamicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.plazamicroservice.domain.model.Dish;
import com.pragma.powerup.plazamicroservice.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishRequestMapper {

    @Mapping(target = "category.id", source = "id_category")
    @Mapping(target = "restaurant.id", source = "id_restaurant")
    @Mapping(target = "urlImage", source = "url_image")
    Dish toDish(DishRequestDto dishRequestDto);

}
