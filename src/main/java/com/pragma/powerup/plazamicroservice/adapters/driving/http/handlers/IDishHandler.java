package com.pragma.powerup.plazamicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.request.DishActiveRequestDto;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.request.DishUpdateRequestDto;

public interface IDishHandler {

    void saveDish( DishRequestDto dishRequestDto, String token );

    void updateDish( DishUpdateRequestDto dishUpdateRequestDto, Long idDish, String token );

    void activeDish(DishActiveRequestDto dishActiveRequestDto, Long idDish, String token );
}
