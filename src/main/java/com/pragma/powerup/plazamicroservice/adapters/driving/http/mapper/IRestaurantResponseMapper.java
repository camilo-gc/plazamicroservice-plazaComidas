package com.pragma.powerup.plazamicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.response.RestaurantResponseDto;
import com.pragma.powerup.plazamicroservice.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantResponseMapper {
    @Mapping(target = "id_owner", source = "idOwner")
    @Mapping(target = "url_logo", source = "urlLogo")
    RestaurantResponseDto toResponse(Restaurant restaurant);
    List<RestaurantResponseDto> toResponseList(List<Restaurant> restaurantList);
}
