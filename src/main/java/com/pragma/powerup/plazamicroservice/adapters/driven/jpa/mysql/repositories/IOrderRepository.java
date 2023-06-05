package com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("SELECT o FROM  OrderEntity o WHERE o.idClient = :idClient AND o.status in ( :pending, :preparation, :ready )")
    List<OrderEntity> findByIdClientAndStatus(
            @Param("idClient") Long idClient,
            @Param("pending") String statusPending,
            @Param("preparation") String statusPreparation,
            @Param("ready") String statusReady
    );

    @Query("SELECT o FROM  OrderEntity o WHERE o.restaurantEntity.id = :idRestaurant AND o.status = :status")
    List<OrderEntity> findByRestaurantAndStatus(
            @Param("idRestaurant") Long idRestaurant,
            @Param("status") String status,
            Pageable pageable
    );

}