package com.pragma.powerup.plazamicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.request.OrderUpdateRequestDto;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IOrderHandler {

    void saveOrder(OrderRequestDto orderRequestDto, String authorizationHeader);

    List<OrderResponseDto> getOrderOfRestaurantByStatus(String token, String status, Pageable pageable);

    List<OrderResponseDto> assignToOrder(List<OrderUpdateRequestDto> orderUpdateRequestDtoList, String token);
/*
    boolean orderReady(Long idOrder, String token);

    String deliverOrder(Long idOrder, String code, String token);

    void orderCanceled(Long idOrder, String token);
    */

}