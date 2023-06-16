package com.pragma.powerup.plazamicroservice.adapters.driven.apis.twilioapi.adapter;


import com.pragma.powerup.plazamicroservice.adapters.driven.apis.twilioapi.dto.SmsApiDto;
import com.pragma.powerup.plazamicroservice.adapters.driven.apis.twilioapi.dto.ValidCodeApiDto;
import com.pragma.powerup.plazamicroservice.adapters.driven.apis.twilioapi.mappers.ITwilioApiMapper;
import com.pragma.powerup.plazamicroservice.adapters.driven.apis.twilioapi.repositories.ITwilioApiRepository;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.exceptions.OwnerNotFoundException;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.exceptions.UnauthorizedOwnerValidationException;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.exceptions.UserAlreadyExistsException;
import com.pragma.powerup.plazamicroservice.configuration.Constants;
import com.pragma.powerup.plazamicroservice.domain.spi.ITwilioApiFeignPort;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Map;


@RequiredArgsConstructor
@CommonsLog
public class TwilioApiAdapter implements ITwilioApiFeignPort {

    private final ITwilioApiRepository twilioApiRepository;

    @Override
    public void notifyOrderStatus(String message, String phone, String authorizationHeader) {

        SmsApiDto smsApiDto = new SmsApiDto(message, phone);

        try {

            twilioApiRepository.notifyOrderStatus(smsApiDto, authorizationHeader);

        } catch (FeignException e) {

            if (e.status() == 401) {
                log.error("401 -> Unauthorized");
                throw new UnauthorizedOwnerValidationException();
            }
            if (e.status() == 500) {
                log.error("500 -> TwilioApi internal error");
                throw new HttpServerErrorException(HttpStatusCode.valueOf(e.status()));
            }
            if (e.status() == -1) {
                log.error("-1 -> TwilioApi not available");
                throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }

    }

    @Override
    public Map<String, String> sendCodeVerification(String message, String phone, String authorizationHeader) {

        SmsApiDto smsApiDto = new SmsApiDto(message, phone);
        ResponseEntity<Map<String, String>> responseEntity = null;

        try {

            responseEntity = twilioApiRepository.sendCodeSms(smsApiDto, authorizationHeader);

        } catch (FeignException e) {

            if (e.status() == 401) {
                log.error("401 -> Unauthorized");
                throw new UnauthorizedOwnerValidationException();
            }
            if (e.status() == 500) {
                log.error("500 -> TwilioApi internal error");
                throw new HttpServerErrorException(HttpStatusCode.valueOf(e.status()));
            }
            if (e.status() == -1) {
                log.error("-1 -> TwilioApi not available");
                throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }

        return responseEntity.getBody();

    }


    public Map<String, String> validateCodeVerification(String code, String phone, String authorizationHeader){

        ValidCodeApiDto validCodeApiDto = new ValidCodeApiDto(code, phone);
        ResponseEntity<Map<String, String>> responseEntity = null;

        try {

            responseEntity = twilioApiRepository.validCodeSms(validCodeApiDto, authorizationHeader);

        } catch (FeignException e) {

            if (e.status() == 401) {
                log.error("401 -> Unauthorized");
                throw new UnauthorizedOwnerValidationException();
            }
            if (e.status() == 500) {
                log.error("500 -> TwilioApi internal error");
                throw new HttpServerErrorException(HttpStatusCode.valueOf(e.status()));
            }
            if (e.status() == -1) {
                log.error("-1 -> TwilioApi not available");
                throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }

        return responseEntity.getBody();
    }

}
