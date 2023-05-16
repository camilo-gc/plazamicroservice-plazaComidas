package com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.adapter;


import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.exceptions.OwnerNotFoundException;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.repositories.IUserApiRepository;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.response.UserResponseDto;
import com.pragma.powerup.plazamicroservice.domain.spi.IUserApiPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;


@RequiredArgsConstructor
public class UserApiAdapter implements IUserApiPersistencePort {

    private final IUserApiRepository userApiRepository;

    @Override
    public UserResponseDto findOwnerById(Long id, String authorizationHeader) {

        ResponseEntity<UserResponseDto> responseEntity = userApiRepository.findById( id, authorizationHeader );//orElseThrow(UserNotFoundException::new);

        if (responseEntity.getStatusCode().equals( 401 )) {
            //TODO: crear exception de sin permisos
            //throw new HttpClientErrorException;
            System.err.println(responseEntity.getStatusCode());
        }
        if (responseEntity.getStatusCode().equals( 404 )) {
            throw new OwnerNotFoundException();
        }
        if (responseEntity.getStatusCode().is5xxServerError()) {
            //TODO: crear exception de error servidor
            //throw new HttpServerErrorException;
            System.err.println(responseEntity.getStatusCode());
        }

        return responseEntity.getBody();
    }
}
