package com.example.alvinlam.drawer.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Alvin Lam on 4/9/2017.
 */

public class StockDbFunction {
    private StocklistDbHelper dbHelper;

    public StockDbFunction(Context context) {
        dbHelper = new StocklistDbHelper(context);
    }

    public void insert(String name, int code, int date, int price, int netchange, int pe, int high, int low,
                       int perclose, int volume, int turnover, int lot) {

        //Open connection to write data
        SQLiteDatabase mDb = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(StocklistContract.StocklistEntry.COLUMN_NAME, name);
        cv.put(StocklistContract.StocklistEntry.COLUMN_CODE, code);
        cv.put(StocklistContract.StocklistEntry.COLUMN_DATE, date);
        cv.put(StocklistContract.StocklistEntry.COLUMN_PRICE, price);
        cv.put(StocklistContract.StocklistEntry.COLUMN_NET_CHANGE, netchange);
        cv.put(StocklistContract.StocklistEntry.COLUMN_PE, pe);
        cv.put(StocklistContract.StocklistEntry.COLUMN_HIGH, high);
        cv.put(StocklistContract.StocklistEntry.COLUMN_LOW, low);
        cv.put(StocklistContract.StocklistEntry.COLUMN_PRE_CLOSE, perclose);
        cv.put(StocklistContract.StocklistEntry.COLUMN_VOLUME, volume);
        cv.put(StocklistContract.StocklistEntry.COLUMN_TURNOVER, turnover);
        cv.put(StocklistContract.StocklistEntry.COLUMN_LOT, lot);
        // Inserting Row
        mDb.insert(StocklistContract.StocklistEntry.TABLE_NAME, null, cv);
        mDb.close(); // Closing database connection
    }
    public void update(long id, String name, int code, int date, int price, int netchange, int pe, int high, int low,
                       int perclose, int volume, int turnover, int lot) {
        //Open connection to write data
        SQLiteDatabase mDb = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(StocklistContract.StocklistEntry.COLUMN_NAME, name);
        cv.put(StocklistContract.StocklistEntry.COLUMN_CODE, code);
        cv.put(StocklistContract.StocklistEntry.COLUMN_DATE, date);
        cv.put(StocklistContract.StocklistEntry.COLUMN_PRICE, price);
        cv.put(StocklistContract.StocklistEntry.COLUMN_NET_CHANGE, netchange);
        cv.put(StocklistContract.StocklistEntry.COLUMN_PE, pe);
        cv.put(StocklistContract.StocklistEntry.COLUMN_HIGH, high);
        cv.put(StocklistContract.StocklistEntry.COLUMN_LOW, low);
        cv.put(StocklistContract.StocklistEntry.COLUMN_PRE_CLOSE, perclose);
        cv.put(StocklistContract.StocklistEntry.COLUMN_VOLUME, volume);
        cv.put(StocklistContract.StocklistEntry.COLUMN_TURNOVER, turnover);
        cv.put(StocklistContract.StocklistEntry.COLUMN_LOT, lot);

        mDb.update(StocklistContract.StocklistEntry.TABLE_NAME, cv, StocklistContract.StocklistEntry._ID + "=" + id, null);
        mDb.close(); // Closing database connection
    }

    public Cursor select() {
        SQLiteDatabase mDb = dbHelper.getReadableDatabase();
        Cursor cursor = mDb.query(
                StocklistContract.StocklistEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                StocklistContract.StocklistEntry.COLUMN_TIMESTAMP
        );
        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }

    public Cursor selectByID(long id) {
        SQLiteDatabase mDb = dbHelper.getReadableDatabase();
        Cursor cursor = mDb.query(
                StocklistContract.StocklistEntry.TABLE_NAME,
                null,
                StocklistContract.StocklistEntry._ID + "=" + id,
                null,
                null,
                null,
                StocklistContract.StocklistEntry.COLUMN_TIMESTAMP
        );
        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }

    public Cursor selectByName(String search) {
        //Open connection to read only
        SQLiteDatabase mDb = dbHelper.getReadableDatabase();
        Cursor cursor = mDb.query(
                StocklistContract.StocklistEntry.TABLE_NAME,
                null,
                StocklistContract.StocklistEntry.COLUMN_NAME + "  LIKE  '%" +search + "%' ",
                null,
                null,
                null,
                StocklistContract.StocklistEntry.COLUMN_TIMESTAMP

        );
        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }

    public boolean delete(long id) {
        SQLiteDatabase mDb = dbHelper.getWritableDatabase();
        //  Inside, call mDb.delete to pass in the TABLE_NAME and the condition that ._ID equals id
        return mDb.delete(StocklistContract.StocklistEntry.TABLE_NAME,
                StocklistContract.StocklistEntry._ID + "=" + id, null) > 0;
    }
}
