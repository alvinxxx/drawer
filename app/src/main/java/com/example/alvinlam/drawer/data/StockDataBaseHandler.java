package com.example.alvinlam.drawer.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class StockDataBaseHandler extends SQLiteOpenHelper
{

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 10;

    // Database Name
    private static final String DATABASE_NAME = " stocklist.db";

    // Portfolio table name
    private static final String TABLE_STOCKS = " stocks";

    // Portfolio Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PRICE = "price";
    private static final String KEY_NET_CHANGE = "net";
    private static final String KEY_IMAGE = "image";

    public StockDataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STOCKS_TABLE = "CREATE TABLE " + TABLE_STOCKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PRICE + " DOUBLE," + KEY_NET_CHANGE + " DOUBLE,"
                + KEY_IMAGE + " BLOB" + ")";
        db.execSQL(CREATE_STOCKS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STOCKS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read) Operations
     */

    public// Adding new stock
    void addStock(Stock stock) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, stock._name); // Card Name
        values.put(KEY_PRICE, stock._price); // Card price
        values.put(KEY_NET_CHANGE, stock._netChange); // Card netChange
        values.put(KEY_IMAGE, stock._image); // Card image

        // Inserting Row
        db.insert(TABLE_STOCKS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single stock
    Stock getStock(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_STOCKS, new String[] { KEY_ID,
                        KEY_NAME, KEY_PRICE, KEY_NET_CHANGE, KEY_IMAGE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Stock stock = new Stock(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getDouble(2), cursor.getDouble(3), cursor.getBlob(4));

        // return stock
        return stock;

    }

    // Getting All Stocks
    public List<Stock> getAllStocks() {
        List<Stock> stockList = new ArrayList<Stock>();
        // Select All Query
        String selectQuery = "SELECT  * FROM stocks";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Stock stock = new Stock();
                stock.setID(Integer.parseInt(cursor.getString(0)));
                stock.setName(cursor.getString(1));
                stock.setPrice(Double.parseDouble(cursor.getString(2)));
                stock.setNetChange(Double.parseDouble(cursor.getString(3)));
                stock.setImage(cursor.getBlob(4));
                // Adding card to list
                stockList.add(stock);
            } while (cursor.moveToNext());
        }
        // close inserting data from database
        db.close();
        // return contact list
        return stockList;
    }
}