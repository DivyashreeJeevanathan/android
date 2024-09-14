package com.example.project;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {
    private EditText editName, editMobile, editAddress, editEmail;
    private Button saveButton;
    private ImageButton backButton;
    private SharedPreferences sharedPreferences;
    private DatabaseHelper dbHelper;
    private String loggedInUserEmail;

    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_EMAIL = "USER_EMAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Initialize UI components
        editName = findViewById(R.id.editName);
        editMobile = findViewById(R.id.editMobile);
        editAddress = findViewById(R.id.editAddress);
        editEmail = findViewById(R.id.editEmail);
        saveButton = findViewById(R.id.saveButton);
        backButton = findViewById(R.id.backButton);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        loggedInUserEmail = sharedPreferences.getString(KEY_EMAIL, "");

        if (loggedInUserEmail == null || loggedInUserEmail.isEmpty()) {
            Toast.makeText(this, "Error: No user email found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Fetch and display current user details
        loadUserProfile();

        // Disable the email field to prevent editing
        editEmail.setEnabled(false);

        // Set up the backButton click listener
        backButton.setOnClickListener(view -> finish());

        // Set up the save button click listener
        saveButton.setOnClickListener(view -> saveProfileChanges());
    }

    private void loadUserProfile() {
        // Query the database for the user details
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(
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

            if (cursor != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME);
                int mobileIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_MOBILE);
                int addressIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ADDRESS);
                int emailIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL);

                if (nameIndex != -1 && mobileIndex != -1 && addressIndex != -1 && emailIndex != -1) {
                    String name = cursor.getString(nameIndex);
                    String mobile = cursor.getString(mobileIndex);
                    String address = cursor.getString(addressIndex);
                    String email = cursor.getString(emailIndex);

                    // Set the fetched data to the EditText fields
                    editName.setText(name);
                    editMobile.setText(mobile);
                    editAddress.setText(address);
                    editEmail.setText(email);
                } else {
                    Toast.makeText(this, "Error: One or more columns are missing", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void saveProfileChanges() {
        // Retrieve updated profile information from the UI
        String name = editName.getText().toString();
        String mobile = editMobile.getText().toString();
        String address = editAddress.getText().toString();
        String email = editEmail.getText().toString();

        // Validate the inputs
        if (name.isEmpty() || mobile.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update the profile information in the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, name);
        values.put(DatabaseHelper.COLUMN_MOBILE, mobile);
        values.put(DatabaseHelper.COLUMN_ADDRESS, address);

        int rowsUpdated = db.update(
                DatabaseHelper.TABLE_USERS,
                values,
                DatabaseHelper.COLUMN_EMAIL + "=?",
                new String[]{email}
        );

        if (rowsUpdated > 0) {
            // Save the updated profile information in SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(DatabaseHelper.COLUMN_NAME, name);
            editor.putString(DatabaseHelper.COLUMN_MOBILE, mobile);
            editor.putString(DatabaseHelper.COLUMN_ADDRESS, address);
            editor.putString(DatabaseHelper.COLUMN_EMAIL, email);
            editor.apply();

            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();

            // Notify ProfileActivity to refresh
            Intent resultIntent = new Intent();
            setResult(RESULT_OK, resultIntent);
            finish();
        } else {
            Toast.makeText(this, "Error updating profile", Toast.LENGTH_SHORT).show();
        }
    }
}
