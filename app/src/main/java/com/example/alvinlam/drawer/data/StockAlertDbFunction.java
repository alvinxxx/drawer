package com.example.alvinlam.drawer.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Alvin Lam on 4/9/2017.
 */

public class StockAlertDbFunction {
    private StocklistDbHelper dbHelper;

    public StockAlertDbFunction(Context context) {
        dbHelper = new StocklistDbHelper(context);
    }

    public void insert(String name, int code, int order) {
        String indicator = "Price";
        String conditionBuy = "Less than";
        String conditionSell = "Greater than";
        String window = "20";
        String target = "SMA";
        String distanceBuy = "-2 STD_L";
        String distanceSell = "+2 STD_H";


        //Open connection to write data
        SQLiteDatabase mDb = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(StocklistContract.StockAlertEntry.COLUMN_NAME, name);
        cv.put(StocklistContract.StockAlertEntry.COLUMN_CODE, code);
        cv.put(StocklistContract.StockAlertEntry.COLUMN_ACTIVE, 1);
        cv.put(StocklistContract.StockAlertEntry.COLUMN_BUY, order);
        cv.put(StocklistContract.StockAlertEntry.COLUMN_INDICATOR, indicator);
        //condition
        cv.put(StocklistContract.StockAlertEntry.COLUMN_WINDOW, window);
        cv.put(StocklistContract.StockAlertEntry.COLUMN_TARGET, target);
        //distance

        if(order == 1){
            cv.put(StocklistContract.StockAlertEntry.COLUMN_CONDITION, conditionBuy);
            cv.put(StocklistContract.StockAlertEntry.COLUMN_DISTANCE, distanceBuy);
        }else{
            cv.put(StocklistContract.StockAlertEntry.COLUMN_CONDITION, conditionSell);
            cv.put(StocklistContract.StockAlertEntry.COLUMN_DISTANCE, distanceSell);
        }


        mDb.insert(StocklistContract.StockAlertEntry.TABLE_NAME, null, cv);
        mDb.close(); // Closing database connection
    }

    public void insert(String name, int code, int active, int order, String indicator, String condition,
                        String window, String target,  String distance) {

        //Open connection to write data
        SQLiteDatabase mDb = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(StocklistContract.StockAlertEntry.COLUMN_NAME, name);
        cv.put(StocklistContract.StockAlertEntry.COLUMN_CODE, code);
        cv.put(StocklistContract.StockAlertEntry.COLUMN_ACTIVE, active);
        cv.put(StocklistContract.StockAlertEntry.COLUMN_BUY, order);
        cv.put(StocklistContract.StockAlertEntry.COLUMN_INDICATOR, indicator);
        cv.put(StocklistContract.StockAlertEntry.COLUMN_CONDITION, condition);
        cv.put(StocklistContract.StockAlertEntry.COLUMN_WINDOW, window);
        cv.put(StocklistContract.StockAlertEntry.COLUMN_TARGET, target);
        cv.put(StocklistContract.StockAlertEntry.COLUMN_DISTANCE, distance);

        mDb.insert(StocklistContract.StockAlertEntry.TABLE_NAME, null, cv);
        mDb.close(); // Closing database connection
    }

    public void replace(long id, String name, int code, int active, int order, String indicator, String condition,
                        String window, String target,  String distance
                        ) {

        //Open connection to write data
        SQLiteDatabase mDb = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(StocklistContract.StockAlertEntry._ID, id);
        cv.put(StocklistContract.StockAlertEntry.COLUMN_NAME, name);
        cv.put(StocklistContract.StockAlertEntry.COLUMN_CODE, code);
        cv.put(StocklistContract.StockAlertEntry.COLUMN_ACTIVE, active);
        cv.put(StocklistContract.StockAlertEntry.COLUMN_BUY, order);
        cv.put(StocklistContract.StockAlertEntry.COLUMN_INDICATOR, indicator);
        cv.put(StocklistContract.StockAlertEntry.COLUMN_CONDITION, condition);
        cv.put(StocklistContract.StockAlertEntry.COLUMN_WINDOW, window);
        cv.put(StocklistContract.StockAlertEntry.COLUMN_TARGET, target);
        cv.put(StocklistContract.StockAlertEntry.COLUMN_DISTANCE, distance);

        long result = mDb.insertWithOnConflict(
                StocklistContract.StockAlertEntry.TABLE_NAME, null, cv, SQLiteDatabase.CONFLICT_IGNORE);

        if (result == -1) {
            mDb.update(
                    StocklistContract.StockAlertEntry.TABLE_NAME, cv,
                    StocklistContract.StockAlertEntry._ID + "=" + id, null);
        }

        //mDb.replace(StocklistContract.StocklistEntry.TABLE_NAME, null, cv);
        mDb.close(); // Closing database connection
    }

    public Double checkDouble(String value){
        if (value.equals("null"))
            return 0.0;
        else
            return Double.parseDouble(value);
    }

    public void replaceByArray(String[] parsedStockData) {
        long id;
        String name, indicator, condition, window, target, distance;
        int code = 0, active =0, order = 0;

        id = Long.parseLong(parsedStockData[0]);
        name = parsedStockData[1];
        code = Integer.parseInt(parsedStockData[2]);
        active = Integer.parseInt(parsedStockData[3]);
        order = Integer.parseInt(parsedStockData[4]);
        indicator = parsedStockData[5];
        condition = parsedStockData[6];
        window = parsedStockData[7];
        target = parsedStockData[8];
        distance = parsedStockData[9];

        SQLiteDatabase mDb = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(StocklistContract.StockAlertEntry._ID, id);
        cv.put(StocklistContract.StockAlertEntry.COLUMN_NAME, name);
        cv.put(StocklistContract.StockAlertEntry.COLUMN_CODE, code);
        cv.put(StocklistContract.StockAlertEntry.COLUMN_ACTIVE, active);
        cv.put(StocklistContract.StockAlertEntry.COLUMN_BUY, order);
        cv.put(StocklistContract.StockAlertEntry.COLUMN_INDICATOR, indicator);
        cv.put(StocklistContract.StockAlertEntry.COLUMN_CONDITION, condition);
        cv.put(StocklistContract.StockAlertEntry.COLUMN_WINDOW, window);
        cv.put(StocklistContract.StockAlertEntry.COLUMN_TARGET, target);
        cv.put(StocklistContract.StockAlertEntry.COLUMN_DISTANCE, distance);


        long result = mDb.insertWithOnConflict(
                StocklistContract.StockAlertEntry.TABLE_NAME, null, cv, SQLiteDatabase.CONFLICT_IGNORE);
        if (result == -1) {
            mDb.update(
                    StocklistContract.StockAlertEntry.TABLE_NAME, cv,
                    StocklistContract.StockAlertEntry._ID + "=" + id, null);
        }
        mDb.close(); // Closing database connection

    }

    public Cursor select() {
        SQLiteDatabase mDb = dbHelper.getReadableDatabase();
        Cursor cursor = mDb.query(
                StocklistContract.StockAlertEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                StocklistContract.StockAlertEntry.COLUMN_TIMESTAMP
        );
        //Log.d("stockdbfunc", "select: "+cursor);

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
                StocklistContract.StockAlertEntry.TABLE_NAME,
                null,
                StocklistContract.StockAlertEntry._ID + "=" + id,
                null,
                null,
                null,
                StocklistContract.StockAlertEntry.COLUMN_TIMESTAMP
        );
        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }

    public Cursor selectByCode(int code) {
        SQLiteDatabase mDb = dbHelper.getReadableDatabase();
        Cursor cursor = mDb.query(
                StocklistContract.StockAlertEntry.TABLE_NAME,
                null,
                StocklistContract.StockAlertEntry.COLUMN_CODE + "=" + code,
                null,
                null,
                null,
                StocklistContract.StockAlertEntry.COLUMN_TIMESTAMP
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
                StocklistContract.StockAlertEntry.TABLE_NAME,
                null,
                StocklistContract.StockAlertEntry.COLUMN_NAME + "  LIKE  '%" +search + "%' ",
                null,
                null,
                null,
                StocklistContract.StockAlertEntry.COLUMN_TIMESTAMP

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
        return mDb.delete(StocklistContract.StockAlertEntry.TABLE_NAME,
                StocklistContract.StockAlertEntry._ID + "=" + id, null) > 0;
    }

    public boolean deleteByCode(int code) {
        SQLiteDatabase mDb = dbHelper.getWritableDatabase();
        //  Inside, call mDb.delete to pass in the TABLE_NAME and the condition that ._ID equals id
        return mDb.delete(StocklistContract.StockAlertEntry.TABLE_NAME,
                StocklistContract.StockAlertEntry.COLUMN_CODE + "=" + code, null) > 0;
    }
}
