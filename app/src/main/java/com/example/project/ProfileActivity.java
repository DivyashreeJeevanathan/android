package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

public class ProfileActivity extends AppCompatActivity {
    private ImageView editDetails;
    private ImageView myOrders;
    private TextView usernameText, userNumber, userAddress, userEmailText, userName, userEmail;
    private CardView backToHomeCardView;
    private DatabaseHelper dbHelper;
    private String loggedInUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));

        // Initialize UI components
        editDetails = findViewById(R.id.editDetails);
        myOrders = findViewById(R.id.myOrders);
        usernameText = findViewById(R.id.usernameText);
        userNumber = findViewById(R.id.userNumber);
        userAddress = findViewById(R.id.userAddress);
        userEmailText = findViewById(R.id.userEmailText);
        backToHomeCardView = findViewById(R.id.backToHome);
        userName = findViewById(R.id.username);
        userEmail = findViewById(R.id.userEmail);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Retrieve the logged-in user's email from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        loggedInUserEmail = sharedPreferences.getString("USER_EMAIL", "");

        if (loggedInUserEmail == null || loggedInUserEmail.isEmpty()) {
            Toast.makeText(this, "Error: No user email found", Toast.LENGTH_SHORT).show();
            return;
        }

        // Set up the click listeners
        editDetails.setOnClickListener(view -> {
            Intent intent1 = new Intent(ProfileActivity.this, EditActivity.class);
            startActivity(intent1);
        });

        backToHomeCardView.setOnClickListener(view -> {
            Intent intent1 = new Intent(ProfileActivity.this, HomeActivity.class);
            startActivity(intent1);
        });

        myOrders.setOnClickListener(view -> {
            Intent intent1 = new Intent(ProfileActivity.this, MyOrdersActivity.class);
            startActivity(intent1);
        });

        // Load user profile data
        loadUserProfile();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload user profile data when the activity resumes
        loadUserProfile();
    }

    private void loadUserProfile() {
        if (loggedInUserEmail == null || loggedInUserEmail.isEmpty()) {
            Toast.makeText(this, "Error: No user email found", Toast.LENGTH_SHORT).show();
            return;
        }

        // Query the database for the user details
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_USERS,
                new String[]{
                        DatabaseHelper.COLUMN_NAME,
                        DatabaseHelper.COLUMN_MOBILE,
                        DatabaseHelper.COLUMN_ADDRESS,
                        DatabaseHelper.COLUMN_EMAIL
                },
                DatabaseHelper.COLUMN_EMAIL + "=?",
                new String[]{loggedInUserEmail},
                null, null, null
        );

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME);
                    int mobileIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_MOBILE);
                    int addressIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ADDRESS);
                    int emailIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL);

                    if (nameIndex != -1 && mobileIndex != -1 && addressIndex != -1 && emailIndex != -1) {
                        String name = cursor.getString(nameIndex);
                        String mobile = cursor.getString(mobileIndex);
                        String address = cursor.getString(addressIndex);
                        String email = cursor.getString(emailIndex);

                        // Set the fetched data to the TextViews
                        usernameText.setText(name);
                        userName.setText(name);
                        userNumber.setText(mobile);
                        userAddress.setText(address);
                        userEmailText.setText(email);
                        userEmail.setText(email);
                    } else {
                        Toast.makeText(this, "Error: One or more columns are missing", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
                }
            } finally {
                cursor.close();
            }
        } else {
            Toast.makeText(this, "Error: Unable to retrieve user data", Toast.LENGTH_SHORT).show();
        }
    }
}
