package com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.mappers;

import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.plazamicroservice.domain.model.Dish;
import com.pragma.powerup.plazamicroservice.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantEntityMapper {

    @Mapping(target = "id_owner", source = "idOwner")
    @Mapping(target = "url_logo", source = "urlLogo")
    RestaurantEntity toEntity(Restaurant restaurant);

    @Mapping(target = "idOwner", source = "id_owner")
    @Mapping(target = "urlLogo", source = "url_logo")
    Restaurant toRestaurant(RestaurantEntity restaurantEntity);

    List<Restaurant> toRestaurantList(List<RestaurantEntity> restaurantEntityList);
}
