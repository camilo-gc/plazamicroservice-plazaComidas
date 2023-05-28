package com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.adapter;


import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.exceptions.OwnerNotFoundException;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.exceptions.UnauthorizedOwnerValidationException;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.repositories.IUserApiRepository;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.response.UserResponseDto;
import com.pragma.powerup.plazamicroservice.domain.spi.IUserApiFeignPort;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;


@RequiredArgsConstructor
@CommonsLog
public class UserApiAdapter implements IUserApiFeignPort {

    private final IUserApiRepository userApiRepository;

    @Override
    public UserResponseDto findOwnerById(Long id, String authorizationHeader) {

        ResponseEntity<UserResponseDto> responseEntity = null;

        try {

            responseEntity = userApiRepository.findById( id, authorizationHeader );

        } catch ( FeignException e ){

            if ( e.status() == 401 ) {
                log.error( "401 -> Find Unauthorized" );
                throw new UnauthorizedOwnerValidationException();
            }
            if ( e.status() == 404 ) {
                log.error( "404 -> Owner not found" );
                throw new OwnerNotFoundException();
            }
            if ( e.status() == 500 ) {
                log.error("500 -> UserApi internal error");
                throw new HttpServerErrorException(HttpStatusCode.valueOf(e.status()));
            }
            if ( e.status() == -1 ) {
                log.error( "-1 -> UserApi not available" );
                throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }

        return responseEntity.getBody();
        
    }
}
