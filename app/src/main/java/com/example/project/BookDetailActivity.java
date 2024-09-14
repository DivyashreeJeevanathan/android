package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BookDetailActivity extends AppCompatActivity {
    private TextView bookNameTextView;
    private TextView bookAuthorTextView;
    private TextView bookPriceTextView;
    private ImageView bookImageView;
    private Button addToCartButton;
    private ImageButton backButton;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        // Initialize views
        bookNameTextView = findViewById(R.id.bookNameTextView);
        bookAuthorTextView = findViewById(R.id.bookAuthorTextView);
        bookPriceTextView = findViewById(R.id.bookPriceTextView);
        bookImageView = findViewById(R.id.bookImageView);
        addToCartButton = findViewById(R.id.addToCartButton);
        backButton = findViewById(R.id.backButton);

        // Get the book object from the intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("book")) {
            book = intent.getParcelableExtra("book");
            if (book != null) {
                // Set book details to views
                bookNameTextView.setText(book.getName());
                bookAuthorTextView.setText(String.format("Author: %s", book.getAuthor()));
                bookPriceTextView.setText(String.format("₹%.2f", book.getPrice())); // Display price in INR
                bookImageView.setImageResource(book.getImageResourceId()); // Set book image
            }
        }

        backButton.setOnClickListener(v -> finish());

        // Set the click listener for the Add to Cart button
        addToCartButton.setOnClickListener(v -> {
            if (book != null) {
                Cart cart = Cart.getInstance();
                // Pass imageResourceId from the Book to CartItem
                CartItem cartItem = new CartItem(book.getName(), book.getPrice(), 1, book.getImageResourceId());
                cart.addItem(cartItem);

                Intent cartIntent = new Intent(BookDetailActivity.this, CartActivity.class);
                startActivity(cartIntent);
                finish(); // Optionally close the activity
            } else {
                Toast.makeText(BookDetailActivity.this, "Book data is missing", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
