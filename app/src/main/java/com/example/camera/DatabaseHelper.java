package com.example.camera;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "finance.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "transactions";

    // Column names
    private static final String ID = "id";
    private static final String TYPE = "type";
    private static final String CATEGORY = "category";
    private static final String AMOUNT = "amount";
    private static final String DATE = "date";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TYPE + " TEXT, " +
                CATEGORY + " TEXT, " +
                AMOUNT + " REAL, " +
                DATE + " TEXT)";
        db.execSQL(createQuery);

        // Optional: Indexes for faster queries (especially on large datasets)
        // db.execSQL("CREATE INDEX idx_type ON " + TABLE_NAME + "(" + TYPE + ")");
        // db.execSQL("CREATE INDEX idx_date ON " + TABLE_NAME + "(" + DATE + ")");
    }

    // Upgrade strategy: drop and recreate
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insert a transaction record
    public void insertTransaction(String type, String category, double amount, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TYPE, type);
        values.put(CATEGORY, category);
        values.put(AMOUNT, amount);
        values.put(DATE, date);

        db.insert(TABLE_NAME, null, values);
        db.close(); // Always close after writing
    }

    // Retrieve all transactions in reverse chronological order
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_NAME + " ORDER BY " + ID + " DESC", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Transaction txn = new Transaction(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(TYPE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(CATEGORY)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(AMOUNT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DATE))
                );
                transactions.add(txn);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return transactions;
    }

    // Get total income or expense amount
    public double getTotalAmount(String type) {
        double total = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT SUM(" + AMOUNT + ") FROM " + TABLE_NAME + " WHERE " + TYPE + " = ?",
                new String[]{type}
        );

        if (cursor != null && cursor.moveToFirst()) {
            total = cursor.getDouble(0);
            cursor.close();
        }
        db.close();
        return total;
    }

    // Get total expense grouped by category
    public Map<String, Float> getCategoryWiseExpenseSum() {
        Map<String, Float> map = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT " + CATEGORY + ", SUM(" + AMOUNT + ") FROM " + TABLE_NAME +
                        " WHERE " + TYPE + " = 'Expense' GROUP BY " + CATEGORY,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String category = cursor.getString(0);
                float totalAmount = cursor.getFloat(1);
                map.put(category, totalAmount);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return map;
    }

    // Get expense sum by category between selected dates (yyyy-MM-dd format)
    public Map<String, Float> getCategoryWiseExpenseSumBetween(String startDate, String endDate) {
        Map<String, Float> data = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT " + CATEGORY + ", SUM(" + AMOUNT + ") FROM " + TABLE_NAME +
                        " WHERE " + TYPE + " = 'Expense' AND " + DATE + " BETWEEN ? AND ? GROUP BY " + CATEGORY,
                new String[]{startDate, endDate}
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String category = cursor.getString(0);
                float amount = cursor.getFloat(1);
                data.put(category, amount);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return data;
    }
}
