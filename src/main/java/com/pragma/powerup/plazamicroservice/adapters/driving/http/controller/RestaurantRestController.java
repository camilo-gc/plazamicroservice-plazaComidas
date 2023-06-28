package com.pragma.powerup.plazamicroservice.adapters.driving.http.controller;

import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.request.UserRequestDto;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.response.RestaurantNewResponseDto;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.handlers.IRestaurantHandler;
import com.pragma.powerup.plazamicroservice.configuration.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
@CommonsLog
//@SecurityRequirement(name = "jwt")
public class RestaurantRestController {
    private final IRestaurantHandler restaurantHandler;
    /*
    @Operation(summary = "Add a new restaurant",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Restaurant created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Restaurant already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "403", description = "Role not allowed for restaurant creation",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PostMapping("/new")
    public ResponseEntity<Map<String, String>> saveRestaurant(@Valid @RequestBody RestaurantRequestDto restaurantRequestDto,
                                                              @RequestHeader HttpHeaders headers) {
        String token = Objects.requireNonNull(headers.get("Authorization")).get(0);
        restaurantHandler.saveRestaurant(restaurantRequestDto, token);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.RESTAURANT_CREATED_MESSAGE));
    }

    @Operation(summary = "Add a new employee",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Employee created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Employee already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "403", description = "Role not allowed for user creation",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PostMapping("/new/employee")
    public ResponseEntity<Map<String, String>> addEmployeeToRestaurant(@Valid @RequestBody UserRequestDto userRequestDto,
                                                            @RequestHeader HttpHeaders headers) {
        restaurantHandler.addEmployeeToRestaurant(userRequestDto, headers.get("Authorization").get(0));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.EMPLOYEE_CREATED_MESSAGE));
    }
    */
    @Operation(summary = "Get all the restaurants",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All restaurants returned",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = RestaurantNewResponseDto.class)))),
                    @ApiResponse(responseCode = "404", description = "No data found",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @GetMapping("")
    public ResponseEntity<List<RestaurantNewResponseDto>> getAllRestaurants(@RequestParam(defaultValue = "1") Integer page,
                                                                             @RequestParam(defaultValue = "10") Integer size) {
        log.info("url_env: " + System.getenv("URL_DB_ENV"));
        log.info("username_env: " + System.getenv("USERNAME_DB_ENV"));
        log.info("password_env: " + System.getenv("PASSWORD_DB_ENV"));
        return ResponseEntity.ok(
                restaurantHandler.getAllRestaurants(
                        PageRequest.of( page-1, size, Sort.by(Sort.Direction.ASC, "name") )
                )
        );
    }

}
