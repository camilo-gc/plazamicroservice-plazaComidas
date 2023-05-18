package com.pragma.powerup.plazamicroservice.domain.spi;

import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.response.UserResponseDto;

import java.net.ConnectException;

public interface IUserApiPersistencePort {

    UserResponseDto findOwnerById(Long id, String authorizationHeader);

}
