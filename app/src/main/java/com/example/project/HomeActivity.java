package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    private ImageButton booksButton;
    private ImageButton bookAccessoriesButton;
    private ImageButton cartButton;
    private ImageButton myProfileButton;
    private ImageButton logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize ImageButtons
        booksButton = findViewById(R.id.booksButton);
        bookAccessoriesButton = findViewById(R.id.bookAccessoriesButton);
        cartButton = findViewById(R.id.cartButton);
        myProfileButton = findViewById(R.id.myProfileButton);
        logoutButton = findViewById(R.id.logoutButton);

        // Set button click listeners
        booksButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, BookListActivity.class);
            startActivity(intent);
        });

        bookAccessoriesButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, BookAccessoriesActivity.class);
            startActivity(intent);
        });

        cartButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
        });

        myProfileButton.setOnClickListener(v -> {
            // Retrieve email from SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
            String userEmail = sharedPreferences.getString("USER_EMAIL", "");

            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            intent.putExtra("USER_EMAIL", userEmail);
            startActivity(intent);
        });

        logoutButton.setOnClickListener(v -> {
            // Clear SharedPreferences and return to AuthActivity
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("USER_EMAIL");
            editor.apply();

            Intent intent = new Intent(HomeActivity.this, AuthActivity.class);
            startActivity(intent);
            finish(); // Close the current activity
        });
    }
}
