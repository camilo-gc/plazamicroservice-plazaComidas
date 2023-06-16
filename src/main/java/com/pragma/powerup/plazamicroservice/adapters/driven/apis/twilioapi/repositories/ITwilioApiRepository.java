package com.pragma.powerup.plazamicroservice.adapters.driven.apis.twilioapi.repositories;

import com.pragma.powerup.plazamicroservice.adapters.driven.apis.twilioapi.dto.SmsApiDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "twilioApi", url = "${feign.twilioApi.url}")
public interface ITwilioApiRepository {

    @PostMapping("/notify/sms")
    public ResponseEntity<Map<String, String>> notifyOrderStatus(@Valid @RequestBody SmsApiDto smsApiDto, @RequestHeader("Authorization") String token);

    @PostMapping("/notify/send-code")
    public ResponseEntity<Map<String, String>> sendCodeSms(@Valid @RequestBody SmsApiDto smsApiDto, @RequestHeader("Authorization") String token);


}
