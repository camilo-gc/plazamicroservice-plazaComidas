package com.pragma.powerup.plazamicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.request.DishActiveRequestDto;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.request.DishUpdateRequestDto;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.handlers.IDishHandler;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.mapper.IDishRequestMapper;
import com.pragma.powerup.plazamicroservice.domain.api.IDishServicePort;
import com.pragma.powerup.plazamicroservice.domain.model.Dish;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DishHandlerImpl implements IDishHandler {

    private final IDishServicePort dishServicePort;
    private final IDishRequestMapper dishRequestMapper;


    @Override
    public void saveDish(DishRequestDto dishRequestDto, String token) {
        dishServicePort.saveDish( dishRequestMapper.toDish( dishRequestDto ), token );
    }

    @Override
    public void updateDish( DishUpdateRequestDto dishUpdateRequestDto, Long idDish, String token ) {
        Dish dish = dishRequestMapper.toDish( dishUpdateRequestDto );
        dish.setId(idDish);
        dishServicePort.updateDish( dish, token );
    }

    @Override
    public void activeDish(DishActiveRequestDto dishActiveRequestDto, Long idDish, String token ) {
        Dish dish = dishRequestMapper.toDish( dishActiveRequestDto );
        dish.setId(idDish);
        dishServicePort.activeDish(  dish, token );
    }

}
