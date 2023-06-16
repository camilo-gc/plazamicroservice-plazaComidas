package com.pragma.powerup.plazamicroservice.adapters.driving.http.handlers.impl;


import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.request.OrderUpdateRequestDto;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.handlers.IOrderHandler;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.mapper.IOrderDishRequestMapper;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.mapper.IOrderRequestMapper;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.mapper.IOrderResponseMapper;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.mapper.IOrderUpdateRequestMapper;
import com.pragma.powerup.plazamicroservice.domain.api.IOrderServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderHandlerImpl implements IOrderHandler {

    private final IOrderServicePort orderServicePort;
    private final IOrderRequestMapper orderRequestMapper;
    private final IOrderDishRequestMapper orderDishRequestMapper;
    private final IOrderResponseMapper orderResponseMapper;
    private final IOrderUpdateRequestMapper orderUpdateRequestMapper;


    @Override
    public void saveOrder(OrderRequestDto orderRequestDto, String authorizationHeader) {

        orderServicePort.saveOrder(
                orderRequestMapper.toOrder( orderRequestDto ),
                orderDishRequestMapper.toOrderDishList(orderRequestDto.getDishes()),
                authorizationHeader
        );

    }

    public List<OrderResponseDto> getOrderOfRestaurantByStatus(String token, String status, Pageable pageable){
        return orderResponseMapper.toResponseList(
                orderServicePort.getOrdersOfRestaurantByStatus(token, status, pageable)
        );
    }

    @Transactional
    public List<OrderResponseDto> assignToOrder(List<OrderUpdateRequestDto> orderUpdateRequestDtoList, String token){
        return orderResponseMapper.toResponseList(
                orderServicePort.assignToOrder(orderUpdateRequestMapper.toOrderList(orderUpdateRequestDtoList), token)
        );
    }

    public String orderReady(Long idOrder, String token) {
        return orderServicePort.orderReady(idOrder, token);
    }

    public String deliverOrder(Long idOrder, String code, String token) {
        return orderServicePort.deliverOrder(idOrder, code, token);
    }

}