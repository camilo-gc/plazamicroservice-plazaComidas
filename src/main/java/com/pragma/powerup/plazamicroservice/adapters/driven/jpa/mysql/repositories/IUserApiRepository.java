package com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.response.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "userApi", url = "${feign.userApi.url}")
public interface IUserApiRepository {

    //TODO: interceptor
    @GetMapping("/user/{id}")
    ResponseEntity<UserResponseDto> findById(@PathVariable("id") Long id, @RequestHeader("Authorization") String authorizationHeader);


}
