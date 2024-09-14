package com.example.project;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "userdb.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_MOBILE = "mobile";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";

    public static final String TABLE_ORDERS = "orders";
    public static final String COLUMN_ORDER_ID = "order_id";
    public static final String COLUMN_ORDER_BOOK_NAME = "book_name";
    public static final String COLUMN_ORDER_DATE = "order_date";
    public static final String COLUMN_ORDER_PRICE = "price";
    public static final String COLUMN_ORDER_EMAIL = "email";

    private static final String TABLE_CREATE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_MOBILE + " TEXT, " +
                    COLUMN_ADDRESS + " TEXT, " +
                    COLUMN_EMAIL + " TEXT PRIMARY KEY, " +
                    COLUMN_PASSWORD + " TEXT);";

    private static final String TABLE_CREATE_ORDERS =
            "CREATE TABLE " + TABLE_ORDERS + " (" +
                    COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ORDER_BOOK_NAME + " TEXT, " +
                    COLUMN_ORDER_DATE + " TEXT, " +
                    COLUMN_ORDER_PRICE + " REAL, " +
                    COLUMN_ORDER_EMAIL + " TEXT, " +
                    "FOREIGN KEY (" + COLUMN_ORDER_EMAIL + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_EMAIL + "));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_USERS);
        db.execSQL(TABLE_CREATE_ORDERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    public void insertOrder(Order order, String email) {
        if (email == null || email.isEmpty()) {
            Log.e("DatabaseHelper", "Email is null or empty. Cannot insert order.");
            return;
        }

        ContentValues values = new ContentValues();
        values.put(COLUMN_ORDER_BOOK_NAME, order.getBookName());
        values.put(COLUMN_ORDER_DATE, order.getOrderDate());
        values.put(COLUMN_ORDER_PRICE, order.getPrice());
        values.put(COLUMN_ORDER_EMAIL, email);

        SQLiteDatabase db = this.getWritableDatabase();
        try {
            long result = db.insert(TABLE_ORDERS, null, values);
            if (result == -1) {
                Log.e("DatabaseHelper", "Failed to insert order.");
            } else {
                Log.d("DatabaseHelper", "Order inserted successfully.");
            }
        } finally {
            db.close();
        }
    }

    public Cursor getOrdersByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(
                    TABLE_ORDERS,
                    new String[]{COLUMN_ORDER_BOOK_NAME, COLUMN_ORDER_DATE, COLUMN_ORDER_PRICE},
                    COLUMN_ORDER_EMAIL + "=?",
                    new String[]{email},
                    null, null, null
            );
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error while fetching orders: " + e.getMessage());
        }
        return cursor;
    }
}
