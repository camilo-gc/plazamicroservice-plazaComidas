package com.pragma.powerup.plazamicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.request.DishRequestDto;

public interface IDishHandler {

    void saveDish(DishRequestDto dishRequestDto);

}
