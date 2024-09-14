package com.example.project;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static Cart instance;
    private List<CartItem> cartItems;

    private Cart() {
        cartItems = new ArrayList<>();
    }

    public static synchronized Cart getInstance() {
        if (instance == null) {
            instance = new Cart();
        }
        return instance;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void addItem(CartItem item) {
        cartItems.add(item);
    }

    public void removeItem(CartItem item) {
        cartItems.remove(item);
    }

    public void clear() {
        cartItems.clear();
    }
}
