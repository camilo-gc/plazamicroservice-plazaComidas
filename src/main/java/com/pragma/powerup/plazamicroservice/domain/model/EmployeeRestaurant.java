package com.pragma.powerup.plazamicroservice.domain.model;

public class EmployeeRestaurant {

    private Long id;

    private Long idEmployee;

    private Restaurant restaurant;

    public EmployeeRestaurant(Long id, Long idEmployee, Restaurant restaurant) {
        this.id = id;
        this.idEmployee = idEmployee;
        this.restaurant = restaurant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Long idEmployee) {
        this.idEmployee = idEmployee;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
