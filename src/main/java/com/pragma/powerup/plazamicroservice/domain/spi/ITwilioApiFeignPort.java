package com.pragma.powerup.plazamicroservice.domain.spi;

import java.util.Map;

public interface ITwilioApiFeignPort {

    void notifyOrderStatus(String message, String phone, String authorizationHeader);

    Map<String, String> sendCodeVerification(String message, String phone, String authorizationHeader);

    Map<String, String> validateCodeVerification(String code, String phone, String authorizationHeader);
}
