package com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "employees_restaurants")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeRestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long id_employee;
    @ManyToOne
    @JoinColumn(name = "id_restaurant")
    private RestaurantEntity restaurantEntity;

}