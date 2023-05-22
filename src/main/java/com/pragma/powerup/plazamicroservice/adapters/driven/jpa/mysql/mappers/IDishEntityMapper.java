package com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.mappers;

import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.plazamicroservice.domain.model.Dish;
import com.pragma.powerup.plazamicroservice.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishEntityMapper {

    @Mapping(target = "categoryEntity.id", source = "category.id")
    @Mapping(target = "restaurantEntity.id", source = "restaurant.id")
    @Mapping(target = "url_image", source = "urlImage")
    DishEntity toEntity(Dish dish);

    @Mapping(target = "category.id", source = "categoryEntity.id")
    @Mapping(target = "restaurant.id", source = "restaurantEntity.id")
    @Mapping(target = "urlImage", source = "url_image")
    Dish toDish(DishEntity dishEntity);

}
