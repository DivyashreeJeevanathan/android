package com.example.project;

public class CartItem {
    private String name;
    private double price;
    private int quantity;
    private int imageResourceId;

    public CartItem(String name, double price, int quantity, int imageResourceId) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
