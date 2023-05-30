package com.pragma.powerup.plazamicroservice.adapters.driving.http.controller;

import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.response.RestaurantResponseDto;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantRestController {
    private final IRestaurantHandler restaurantHandler;

    @Operation(summary = "Add a new restaurant",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Restaurant created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Restaurant already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "403", description = "Role not allowed for restaurant creation",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PostMapping("/new")
    @SecurityRequirement(name = "jwt")
    public ResponseEntity<Map<String, String>> saveRestaurant(@Valid @RequestBody RestaurantRequestDto restaurantRequestDto,
                                                              @RequestHeader HttpHeaders headers) {
        String token = Objects.requireNonNull(headers.get("Authorization")).get(0);
        restaurantHandler.saveRestaurant(restaurantRequestDto, token);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.RESTAURANT_CREATED_MESSAGE));
    }

    @Operation(summary = "Get a restaurant",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Restaurant returned",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Role not allowed for search a restaurant",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "404", description = "Restaurant not found with the provided id",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "jwt")
    public ResponseEntity<RestaurantResponseDto> getRestaurantById(@PathVariable("id") Long id){
        return ResponseEntity.ok(restaurantHandler.getRestaurantById(id));
    }

    @Operation(summary = "Get all the restaurants",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All restaurants returned",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = RestaurantResponseDto.class)))),
                    @ApiResponse(responseCode = "404", description = "No data found",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @GetMapping("")
    public ResponseEntity<List<RestaurantResponseDto>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantHandler.getAllRestaurants());
    }

}
