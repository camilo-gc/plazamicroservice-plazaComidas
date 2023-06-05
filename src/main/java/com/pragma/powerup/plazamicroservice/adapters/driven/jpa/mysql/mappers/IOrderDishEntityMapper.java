package com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.mappers;


import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.entity.OrderDishEntity;
import com.pragma.powerup.plazamicroservice.domain.model.OrderDish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderDishEntityMapper {

    @Mapping(target = "orderEntity.id", source = "order.id")
    @Mapping(target = "dishEntity.id", source = "dish.id")
    OrderDishEntity toEntity(OrderDish orderDish);

    @Mapping(target = "order.id", source = "orderEntity.id")
    @Mapping(target = "dish.id", source = "dishEntity.id")
    OrderDish toOrderDish(OrderDishEntity orderDisEntity);

    List<OrderDish> toOrderDishList(List<OrderDishEntity> orderDishEntityList);

}
