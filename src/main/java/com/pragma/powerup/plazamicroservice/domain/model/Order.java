package com.pragma.powerup.plazamicroservice.domain.model;


import java.util.Date;


public class Order {

    private Long id;
    private Long idClient;
    private Date date;
    private String status;
    private Employee chef;
    private Restaurant restaurant;

    public Order(Long id, Long idClient, Date date, String status, Employee chef, Restaurant restaurant) {
        this.id = id;
        this.idClient = idClient;
        this.date = date;
        this.status = status;
        this.chef = chef;
        this.restaurant = restaurant;
    }

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Employee getChef() {
        return chef;
    }

    public void setChef(Employee chef) {
        this.chef = chef;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

}