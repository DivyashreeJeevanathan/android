package com.example.project;

import java.io.Serializable;

public class Order implements Serializable {
    private String bookName;
    private String orderDate;
    private double price;

    public Order(String bookName, String orderDate, double price) {
        this.bookName = bookName;
        this.orderDate = orderDate;
        this.price = price;
    }

    public String getBookName() {
        return bookName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public double getPrice() {
        return price;
    }
}
