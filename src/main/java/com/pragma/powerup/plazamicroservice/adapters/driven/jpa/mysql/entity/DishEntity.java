package com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dishes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DishEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "id_category")
    private CategoryEntity categoryEntity;
    private String description;
    private String price;
    @ManyToOne
    @JoinColumn(name = "id_restaurant")
    private RestaurantEntity restaurantEntity;
    private String url_image;
    private Boolean active;
}
