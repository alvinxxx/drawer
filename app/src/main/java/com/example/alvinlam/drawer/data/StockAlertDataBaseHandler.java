package com.example.alvinlam.drawer.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class StockAlertDataBaseHandler extends SQLiteOpenHelper
{

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = " stocklist.db";

    // Portfolio table name
    private static final String TABLE_STOCKS_ALERT = " stock_alert";

    // Portfolio Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_CURRENT = "current";
    private static final String KEY_CONDITION = "condition";
    private static final String KEY_EXPECT = "expect";
    private static final String KEY_DISTANCE = "distance";
    private static final String KEY_IMAGE = "image";

    public StockAlertDataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STOCKS_ALERT_TABLE = "CREATE TABLE " + TABLE_STOCKS_ALERT + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_CURRENT + " DOUBLE," + KEY_CONDITION + " TEXT,"
                + KEY_EXPECT + " DOUBLE," + KEY_DISTANCE + " DOUBLE,"
                + KEY_IMAGE + " BLOB" + ")";
        db.execSQL(CREATE_STOCKS_ALERT_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STOCKS_ALERT);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read) Operations
     */

    public// Adding new stock
    void addStock(StockAlert stock) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, stock._name); // Card Name
        values.put(KEY_CURRENT, stock._current); // Card price
        values.put(KEY_CONDITION, stock._condition); // Card netChange
        values.put(KEY_EXPECT, stock._expect); // Card netChange
        values.put(KEY_DISTANCE, stock._distance); // Card netChange
        values.put(KEY_IMAGE, stock._image); // Card image

        // Inserting Row
        db.insert(TABLE_STOCKS_ALERT, null, values);
        db.close(); // Closing database connection
    }

    // Getting single stock
    StockAlert getStock(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_STOCKS_ALERT, new String[] { KEY_ID,
                        KEY_NAME, KEY_CURRENT, KEY_CONDITION, KEY_EXPECT, KEY_DISTANCE, KEY_IMAGE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        StockAlert stock = new StockAlert(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getDouble(2), cursor.getString(3),
                cursor.getDouble(4), cursor.getDouble(5),cursor.getBlob(6));

        // return stock
        return stock;

    }

    // Getting All Stocks
    public List<StockAlert> getAllStockAlerts() {
        List<StockAlert> stockAlertList = new ArrayList<StockAlert>();
        // Select All Query
        String selectQuery = "SELECT  * FROM stock_alert";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                StockAlert stock = new StockAlert();
                stock.set_id(Integer.parseInt(cursor.getString(0)));
                stock.set_name(cursor.getString(1));
                stock.set_current(Double.parseDouble(cursor.getString(2)));
                stock.set_condition(cursor.getString(3));
                stock.set_expect(Double.parseDouble(cursor.getString(4)));
                stock.set_expect(Double.parseDouble(cursor.getString(5)));
                stock.set_image(cursor.getBlob(6));
                // Adding card to list
                stockAlertList.add(stock);
            } while (cursor.moveToNext());
        }
        // close inserting data from database
        db.close();
        // return contact list
        return stockAlertList;
    }
}