package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AuthActivity extends AppCompatActivity {
    private TextInputEditText emailField, passwordField;
    private TextInputLayout emailLayout, passwordLayout;
    private Button loginButton;
    private TextView registerText;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));

        // Initialize UI elements
        emailField = findViewById(R.id.loginEmail);
        passwordField = findViewById(R.id.loginPassword);
        emailLayout = findViewById(R.id.loginEmailLayout);
        passwordLayout = findViewById(R.id.loginPasswordLayout);
        loginButton = findViewById(R.id.loginButton);
        registerText = findViewById(R.id.registerText);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Set up the click listener for the login button
        loginButton.setOnClickListener(view -> loginUser());

        // Set click listener on the registerText TextView
        registerText.setOnClickListener(view -> {
            Intent intent = new Intent(AuthActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser() {
        // Get user input from the EditText fields
        String email = emailField.getText() != null ? emailField.getText().toString().trim() : "";
        String password = passwordField.getText() != null ? passwordField.getText().toString().trim() : "";

        // Validate input
        if (!isInputFilled(email, password)) {
            Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            return; // Stop login if input is invalid
        }

        // Check user credentials in the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_USERS,
                new String[]{DatabaseHelper.COLUMN_EMAIL},
                DatabaseHelper.COLUMN_EMAIL + "=? AND " + DatabaseHelper.COLUMN_PASSWORD + "=?",
                new String[]{email, password},
                null, null, null
        );

        if (cursor.getCount() > 0) {
            // Valid login
            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();

            // Save email to SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("USER_EMAIL", email);
            editor.apply();

            // Start HomeActivity
            Intent intent = new Intent(AuthActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Invalid login
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }

    private boolean isInputFilled(String email, String password) {
        boolean isValid = true;

        // Validate email
        if (email.isEmpty()) {
            emailLayout.setError("Email cannot be empty");
            isValid = false;
        } else {
            emailLayout.setError(null);
        }

        // Validate password
        if (password.isEmpty()) {
            passwordLayout.setError("Password cannot be empty");
            isValid = false;
        } else {
            passwordLayout.setError(null);
        }

        return isValid;
    }
}
