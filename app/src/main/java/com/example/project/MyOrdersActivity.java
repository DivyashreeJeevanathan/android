package com.example.project;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MyOrdersActivity extends AppCompatActivity {
    private static final String TAG = "MyOrdersActivity";
    private TextView emptyTextView;
    private RecyclerView ordersRecyclerView;
    private OrderAdapterForMyOrders orderAdapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        dbHelper = new DatabaseHelper(this);

        emptyTextView = findViewById(R.id.emptyTextView);
        ordersRecyclerView = findViewById(R.id.ordersRecyclerView);
        ImageButton backButton = findViewById(R.id.backButton);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("USER_EMAIL", "");
        Log.d(TAG, "User email retrieved: " + userEmail);

        if (userEmail.isEmpty()) {
            Log.e(TAG, "User email is not available. Cannot retrieve orders.");
            emptyTextView.setVisibility(View.VISIBLE);
            ordersRecyclerView.setVisibility(View.GONE);
            return;
        }

        List<Order> orderList = new ArrayList<>();
        Cursor cursor = dbHelper.getOrdersByEmail(userEmail);

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        int bookNameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_BOOK_NAME);
                        int orderDateIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_DATE);
                        int priceIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_PRICE);

                        if (bookNameIndex >= 0 && orderDateIndex >= 0 && priceIndex >= 0) {
                            String bookName = cursor.getString(bookNameIndex);
                            String orderDate = cursor.getString(orderDateIndex);
                            double price = cursor.getDouble(priceIndex);
                            orderList.add(new Order(bookName, orderDate, price));
                        } else {
                            Log.e(TAG, "Invalid column index detected.");
                        }
                    } while (cursor.moveToNext());
                }
            } catch (Exception e) {
                Log.e(TAG, "Error reading cursor data: " + e.getMessage());
            } finally {
                cursor.close();
            }
        } else {
            Log.e(TAG, "Cursor is null.");
        }

        if (orderList.isEmpty()) {
            emptyTextView.setVisibility(View.VISIBLE);
            ordersRecyclerView.setVisibility(View.GONE);
        } else {
            ordersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            orderAdapter = new OrderAdapterForMyOrders(orderList);
            ordersRecyclerView.setAdapter(orderAdapter);
            ordersRecyclerView.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.GONE);
        }

        backButton.setOnClickListener(v -> onBackPressed());
    }
}
