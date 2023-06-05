package com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.adapter;


import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.mappers.IOrderEntityMapper;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.repositories.IOrderRepository;
import com.pragma.powerup.plazamicroservice.configuration.Constants;
import com.pragma.powerup.plazamicroservice.domain.model.Order;
import com.pragma.powerup.plazamicroservice.domain.spi.IOrderPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Transactional
public class OrderMysqlAdapter implements IOrderPersistencePort {

    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;

    @Override
    public List<Order> findOrderInProcessByClient(Long idClient) {

        return orderEntityMapper.toOrderList(

                orderRepository.findByIdClientAndStatus(
                        idClient,
                        Constants.ORDER_STATUS_PENDING,
                        Constants.ORDER_STATUS_PREPARATION,
                        Constants.ORDER_STATUS_READY
                )

        );

    }

    @Override
    public Order saveOrder(Order order) {
        return orderEntityMapper.toOrder(
                orderRepository.save( orderEntityMapper.toEntity(order) )
        );
    }


}