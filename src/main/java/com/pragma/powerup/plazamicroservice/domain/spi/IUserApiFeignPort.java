package com.pragma.powerup.plazamicroservice.domain.spi;

import com.pragma.powerup.plazamicroservice.domain.dto.User;


public interface IUserApiFeignPort {

    User findUserById(Long id, String authorizationHeader);

    User saveEmployee(User user, String token);

}
