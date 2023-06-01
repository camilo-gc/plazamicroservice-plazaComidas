package com.pragma.powerup.plazamicroservice.adapters.driven.userapi.repositories;

import com.pragma.powerup.plazamicroservice.adapters.driven.userapi.entity.UserDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "userApi", url = "${feign.userApi.url}")
public interface IUserApiRepository {

    @GetMapping("/user/{id}")
    ResponseEntity<UserDto> findById(@PathVariable("id") Long id, @RequestHeader("Authorization") String token);

    @PostMapping("/user/new/employee")
    ResponseEntity<UserDto> saveEmployee(@Valid @RequestBody UserDto userDto, @RequestHeader("Authorization") String token);

}
