package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class OrderConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        // Initialize UI elements
        TextView confirmationMessageTextView = findViewById(R.id.confirmationMessageTextView);
        Button continueShoppingButton = findViewById(R.id.continueShoppingButton);

        // Set the confirmation message
        confirmationMessageTextView.setText("Order is confirmed!");

        // Handle continue shopping button click
        continueShoppingButton.setOnClickListener(v -> {
            // Navigate to the home page (or main activity)
            Intent intent = new Intent(OrderConfirmationActivity.this, HomeActivity.class);
            startActivity(intent);
            finish(); // Optionally finish this activity
        });
    }
}
