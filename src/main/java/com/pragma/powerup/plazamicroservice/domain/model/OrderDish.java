package com.pragma.powerup.plazamicroservice.domain.model;

public class OrderDish {

    private Long id;
    private Order order;
    private Dish dish;
    private Integer quantity;

    public OrderDish(Long id, Order order, Dish dish, Integer quantity) {
        this.id = id;
        this.order = order;
        this.dish = dish;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}