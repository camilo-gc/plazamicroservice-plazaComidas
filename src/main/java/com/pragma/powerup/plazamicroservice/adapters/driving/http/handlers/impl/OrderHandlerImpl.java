package com.pragma.powerup.plazamicroservice.adapters.driving.http.handlers.impl;


import com.pragma.powerup.plazamicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.handlers.IOrderHandler;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.mapper.IOrderDishRequestMapper;
import com.pragma.powerup.plazamicroservice.adapters.driving.http.mapper.IOrderRequestMapper;
import com.pragma.powerup.plazamicroservice.domain.api.IOrderServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OrderHandlerImpl implements IOrderHandler {

    private final IOrderServicePort orderServicePort;
    private final IOrderRequestMapper orderRequestMapper;
    private final IOrderDishRequestMapper orderDishRequestMapper;

    @Override
    public void saveOrder(OrderRequestDto orderRequestDto, String authorizationHeader) {

        orderServicePort.saveOrder(
                orderRequestMapper.toOrder( orderRequestDto ),
                orderDishRequestMapper.toOrderDishList(orderRequestDto.getDishes()),
                authorizationHeader
        );

    }

}