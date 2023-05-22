package com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDishRepository extends JpaRepository<DishEntity, Long> {
    //Optional<UserEntity> findByDni(String dni);
}