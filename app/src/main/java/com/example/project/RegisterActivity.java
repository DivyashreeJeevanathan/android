package com.example.project;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private EditText nameField, mobileField, addressField, emailField, passwordField;
    private Button registerButton;
    private TextView back;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameField = findViewById(R.id.regName);
        mobileField = findViewById(R.id.Phone);
        addressField = findViewById(R.id.Address);
        emailField = findViewById(R.id.regEmail);
        passwordField = findViewById(R.id.regPassword);
        registerButton = findViewById(R.id.registerButton);
        back = findViewById(R.id.loginText);

        dbHelper = new DatabaseHelper(this);

        back.setOnClickListener(view -> {
            Intent in = new Intent(RegisterActivity.this, AuthActivity.class);
            startActivity(in);
        });

        registerButton.setOnClickListener(view -> registerUser());
    }

    private void registerUser() {
        String name = nameField.getText().toString().trim();
        String mobile = mobileField.getText().toString().trim();
        String address = addressField.getText().toString().trim();
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        if (name.isEmpty() || mobile.isEmpty() || address.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!mobile.matches("\\d{10}")) {
            Toast.makeText(this, "Mobile number must be exactly 10 digits", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_USERS,
                new String[]{DatabaseHelper.COLUMN_EMAIL},
                DatabaseHelper.COLUMN_EMAIL + "=?",
                new String[]{email},
                null, null, null
        );

        if (cursor.getCount() > 0) {
            cursor.close();
            Toast.makeText(this, "Email already registered", Toast.LENGTH_SHORT).show();
            return;
        }

        cursor.close();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, name);
        values.put(DatabaseHelper.COLUMN_MOBILE, mobile);
        values.put(DatabaseHelper.COLUMN_ADDRESS, address);
        values.put(DatabaseHelper.COLUMN_EMAIL, email);
        values.put(DatabaseHelper.COLUMN_PASSWORD, password);

        long newRowId = db.insert(DatabaseHelper.TABLE_USERS, null, values);
        if (newRowId != -1) {
            Toast.makeText(this, "Registration successful! Please log in.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterActivity.this, AuthActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
        }
    }
}
