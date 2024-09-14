package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnItemRemovedListener {
    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private TextView totalPriceTextView;
    private ImageButton backButton;
    private List<CartItem> cartItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        Button placeOrderButton = findViewById(R.id.placeOrderButton);
        backButton = findViewById(R.id.backButton);

        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Cart cart = Cart.getInstance();
        cartItemList = cart.getCartItems();

        cartAdapter = new CartAdapter(cartItemList, this);
        cartRecyclerView.setAdapter(cartAdapter);

        updateTotalPrice();

        backButton.setOnClickListener(v -> finish());

        placeOrderButton.setOnClickListener(v -> {
            if (cartItemList.isEmpty()) {
                showEmptyCartDialog();
            } else {
                List<Order> orderList = new ArrayList<>();
                for (CartItem item : cartItemList) {
                    orderList.add(new Order(
                            item.getName(),
                            new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date()),
                            item.getPrice()
                    ));
                }

                double totalPrice = calculateTotalPrice();

                Cart.getInstance().clear();
                cartItemList.clear();
                cartAdapter.notifyDataSetChanged();
                updateTotalPrice();

                Intent intent = new Intent(CartActivity.this, OrderActivity.class);
                intent.putExtra("orderList", new ArrayList<>(orderList));
                intent.putExtra("totalPrice", totalPrice);
                startActivity(intent);
            }
        });
    }

    private double calculateTotalPrice() {
        double total = 0;
        for (CartItem item : cartItemList) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    private void updateTotalPrice() {
        double totalPrice = calculateTotalPrice();
        totalPriceTextView.setText(String.format("Total Price: ₹%.2f", totalPrice));
    }

    private void showEmptyCartDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Cart Empty")
                .setMessage("Your cart is empty. Please add items to your cart before placing an order.")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public void onItemRemoved() {
        updateTotalPrice();
    }
}
