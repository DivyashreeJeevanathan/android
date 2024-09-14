package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class BookAccessoriesDetailActivity extends AppCompatActivity {

    private TextView accessoryNameTextView;
    private TextView accessoryPriceTextView;
    private ImageView accessoryImageView;
    private Button addToCartButton;
    private ImageButton backButton;
    private BookAccessories bookAccessories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_accessories_detail);

        // Initialize views
        accessoryNameTextView = findViewById(R.id.accessoryNameTextView);
        accessoryPriceTextView = findViewById(R.id.accessoryPriceTextView);
        accessoryImageView = findViewById(R.id.accessoryImageView);
        addToCartButton = findViewById(R.id.addToCartButton);
        backButton = findViewById(R.id.backButton);


        // Get the BookAccessories object from the intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("bookAccessories")) {
            bookAccessories = intent.getParcelableExtra("bookAccessories");
            if (bookAccessories != null) {
                // Set accessory details to views
                accessoryNameTextView.setText(bookAccessories.getName());
                accessoryPriceTextView.setText(String.format("₹%.2f", bookAccessories.getPrice())); // Display price in INR
                accessoryImageView.setImageResource(bookAccessories.getImageResourceId()); // Set accessory image
            }
        }
        backButton.setOnClickListener(v -> finish());

        // Set the click listener for the Add to Cart button
        addToCartButton.setOnClickListener(v -> {
            if (bookAccessories != null) {
                Cart cart = Cart.getInstance();
                // Pass imageResourceId from the BookAccessories to CartItem
                CartItem cartItem = new CartItem(bookAccessories.getName(), bookAccessories.getPrice(), 1, bookAccessories.getImageResourceId());
                cart.addItem(cartItem);

                Intent cartIntent = new Intent(BookAccessoriesDetailActivity.this, CartActivity.class);
                startActivity(cartIntent);
                finish(); // Optionally close the activity
            } else {
                Toast.makeText(BookAccessoriesDetailActivity.this, "Accessory data is missing", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
