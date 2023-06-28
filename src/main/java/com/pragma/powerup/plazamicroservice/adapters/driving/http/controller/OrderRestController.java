package com.pragma.powerup.plazamicroservice.adapters.driving.http.controller;

import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.request.OrderUpdateRequestDto;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.handlers.IOrderHandler;
import com.pragma.powerup.plazamicroservice.configuration.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
//@SecurityRequirement(name = "jwt")
public class OrderRestController {

    private final IOrderHandler orderHandler;

    @Operation(summary = "Add a new order",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Order created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Dish is not in restaurant",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "404", description = "Dish not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PostMapping("")
    public ResponseEntity<Map<String, String>> saveOrder(@Valid @RequestBody OrderRequestDto orderRequestDto,
                                                              @RequestHeader HttpHeaders headers) {
        String token = Objects.requireNonNull(headers.get("Authorization")).get(0).substring(7);
        orderHandler.saveOrder(orderRequestDto, token);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.ORDER_CREATED_MESSAGE));

    }


    @Operation(summary = "Get orders by status",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All orders returned",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = OrderResponseDto.class)))),
                    @ApiResponse(responseCode = "404", description = "No data found",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @GetMapping("")
    public ResponseEntity<List<OrderResponseDto>> getOrderOfRestaurantsByStatus(@RequestParam String status,
                                                                    @RequestParam(defaultValue = "1") Integer page,
                                                                    @RequestParam(defaultValue = "10") Integer size,
                                                                    @RequestHeader HttpHeaders headers) {

        String token = Objects.requireNonNull(headers.get("Authorization")).get(0).substring(7);
        return ResponseEntity.ok(
                orderHandler.getOrderOfRestaurantByStatus(token, status,
                        PageRequest.of( page-1, size )
                )
        );

    }

    @Operation(summary = "Assign to orders",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful assignment",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = OrderResponseDto.class)))),
                    @ApiResponse(responseCode = "404", description = "Order not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "409", description = "Bad Request",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PutMapping("/assign")
    public ResponseEntity<List<OrderResponseDto>> assignToOrder(@Valid @RequestBody List<@Valid OrderUpdateRequestDto> orderUpdateRequestDtoList,
                                                                @RequestHeader HttpHeaders headers){

        String token = Objects.requireNonNull(headers.get("Authorization")).get(0).substring(7);
        return ResponseEntity.ok(
                orderHandler.assignToOrder( orderUpdateRequestDtoList, token)
        );

    }
    /*
    @Operation(summary = "Order ready",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Order ready",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "404", description = "Order not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "409", description = "Bad Request",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PutMapping("/ready/{order_id}")
    public ResponseEntity<Map<String, String>> changeOrderToReady( @PathVariable("order_id") Long orderId, @RequestHeader HttpHeaders headers) {
        String token = Objects.requireNonNull(headers.get("Authorization")).get(0);
        return ResponseEntity.status(HttpStatus.OK).body(
                Collections.singletonMap( Constants.SENT_CODE_KEY, String.valueOf(orderHandler.orderReady(orderId, token)) )
        );
    }

    @Operation(summary = "Order delivered",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Order delivered",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "404", description = "Order not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "409", description = "Bad Request",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PutMapping("/deliver/{order_id}")
    public ResponseEntity<Map<String, String>> changeOrderToDeliver(@PathVariable("order_id") Long idOrder, @RequestParam("verification_code") String verificationCode, @RequestHeader HttpHeaders headers) {
        String token = Objects.requireNonNull(headers.get("Authorization")).get(0);
        return ResponseEntity.status(HttpStatus.OK).body(
                Collections.singletonMap(Constants.VERIFICATION_STATUS_KEY, orderHandler.deliverOrder(idOrder, verificationCode, token))
        );
    }

    @Operation(summary = "Order canceled",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Order canceled",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "404", description = "Order not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "409", description = "Bad Request",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PutMapping("/cancel/{order_id}")
    public ResponseEntity<Map<String, String>> changeOrderToCancel( @PathVariable("order_id") Long idOrder, @RequestHeader HttpHeaders headers) {
        String token = Objects.requireNonNull(headers.get("Authorization")).get(0);
        orderHandler.orderCanceled(idOrder, token);
        return ResponseEntity.status(HttpStatus.OK).body(
                Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.ORDER_CANCEL_MESSAGE)
        );
    }
    */
}