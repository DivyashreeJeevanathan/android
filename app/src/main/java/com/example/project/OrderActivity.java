package com.example.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderActivity extends AppCompatActivity {
    private static final String TAG = "OrderActivity";
    private TextView orderSummaryTextView;
    private TextView dateTextView;
    private TextView paymentMethodTextView;
    private TextView totalPriceTextView;
    private RecyclerView orderRecyclerView;
    private Button confirmOrderButton;
    private OrderAdapter orderAdapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        dbHelper = new DatabaseHelper(this);

        orderSummaryTextView = findViewById(R.id.orderSummaryTextView);
        dateTextView = findViewById(R.id.dateTextView);
        paymentMethodTextView = findViewById(R.id.paymentMethodTextView);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        orderRecyclerView = findViewById(R.id.orderRecyclerView);
        confirmOrderButton = findViewById(R.id.confirmOrderButton);

        // Get data from Intent
        List<Order> orderList = (List<Order>) getIntent().getSerializableExtra("orderList");
        double totalPrice = getIntent().getDoubleExtra("totalPrice", 0.0);

        if (orderList == null) {
            Log.e(TAG, "Order list is null. Make sure to pass the order list from the previous activity.");
            return;
        }

        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new OrderAdapter(orderList);
        orderRecyclerView.setAdapter(orderAdapter);

        String currentDate = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date());
        dateTextView.setText(String.format("Date: %s", currentDate));

        paymentMethodTextView.setText("Payment Method: Cash on Delivery");
        totalPriceTextView.setText(String.format("Total Price: ₹%.2f", totalPrice));

        confirmOrderButton.setOnClickListener(v -> {
            Log.d(TAG, "Confirm button clicked.");

            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            String userEmail = sharedPreferences.getString("USER_EMAIL", "");
            Log.d(TAG, "User email retrieved: '" + userEmail + "'");

            if (userEmail.isEmpty()) {
                Log.e(TAG, "User email is not available. Cannot save order.");
                return;
            }

            for (Order order : orderList) {
                dbHelper.insertOrder(order, userEmail);
            }

            Intent intent = new Intent(OrderActivity.this, OrderConfirmationActivity.class);
            startActivity(intent);
            finish();
        });

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> onBackPressed());
    }
}
