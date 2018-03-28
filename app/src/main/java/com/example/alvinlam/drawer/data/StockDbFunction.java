package com.example.alvinlam.drawer.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Alvin Lam on 4/9/2017.
 */

public class StockDbFunction {
    private StocklistDbHelper dbHelper;

    public StockDbFunction(Context context) {
        dbHelper = new StocklistDbHelper(context);
    }

    /*
    public void insert(long id,String name, int code, String date, double price, double netChange, double pe, double high, double low,
                       double preClose, double volume, double turnover, double lot) {

        //Open connection to write data
        SQLiteDatabase mDb = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(StocklistContract.StocklistEntry._ID, id);
        cv.put(StocklistContract.StocklistEntry.COLUMN_NAME, name);
        cv.put(StocklistContract.StocklistEntry.COLUMN_CODE, code);
        cv.put(StocklistContract.StocklistEntry.COLUMN_DATE, date);
        cv.put(StocklistContract.StocklistEntry.COLUMN_PRICE, price);
        cv.put(StocklistContract.StocklistEntry.COLUMN_NET_CHANGE, netChange);
        cv.put(StocklistContract.StocklistEntry.COLUMN_PE, pe);
        cv.put(StocklistContract.StocklistEntry.COLUMN_HIGH, high);
        cv.put(StocklistContract.StocklistEntry.COLUMN_LOW, low);
        cv.put(StocklistContract.StocklistEntry.COLUMN_PRE_CLOSE, preClose);
        cv.put(StocklistContract.StocklistEntry.COLUMN_VOLUME, volume);
        cv.put(StocklistContract.StocklistEntry.COLUMN_TURNOVER, turnover);
        cv.put(StocklistContract.StocklistEntry.COLUMN_LOT, lot);
        // Inserting Row
        mDb.insert(StocklistContract.StocklistEntry.TABLE_NAME, null, cv);
        mDb.close(); // Closing database connection
    }
    public void update(long id, String name, int code, String date, double price, double netChange, double pe, double high, double low,
                       double preClose, double volume, double turnover, double lot) {
        //Open connection to write data
        SQLiteDatabase mDb = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(StocklistContract.StocklistEntry._ID, id);
        cv.put(StocklistContract.StocklistEntry.COLUMN_NAME, name);
        cv.put(StocklistContract.StocklistEntry.COLUMN_CODE, code);
        cv.put(StocklistContract.StocklistEntry.COLUMN_DATE, date);
        cv.put(StocklistContract.StocklistEntry.COLUMN_PRICE, price);
        cv.put(StocklistContract.StocklistEntry.COLUMN_NET_CHANGE, netChange);
        cv.put(StocklistContract.StocklistEntry.COLUMN_PE, pe);
        cv.put(StocklistContract.StocklistEntry.COLUMN_HIGH, high);
        cv.put(StocklistContract.StocklistEntry.COLUMN_LOW, low);
        cv.put(StocklistContract.StocklistEntry.COLUMN_PRE_CLOSE, preClose);
        cv.put(StocklistContract.StocklistEntry.COLUMN_VOLUME, volume);
        cv.put(StocklistContract.StocklistEntry.COLUMN_TURNOVER, turnover);
        cv.put(StocklistContract.StocklistEntry.COLUMN_LOT, lot);

        mDb.update(StocklistContract.StocklistEntry.TABLE_NAME, cv, StocklistContract.StocklistEntry._ID + "=" + id, null);
        mDb.close(); // Closing database connection
    }
    */
    public void replace(long id, String name, int code, String date, double price, double netChange, double pe, double high, double low,
                        double preClose, double volume, double turnover, double lot, double dy, double dps, double eps,
                        double sma20, double std20, double std20l, double std20h, double sma50, double std50, double std50l, double std50h,
                        double sma100, double std100, double std100l, double std100h, double sma250, double std250, double std250l, double std250h,
                        double l20, double h20, double l50, double h50, double l100, double h100, double l250, double h250
                        ) {

        //Open connection to write data
        SQLiteDatabase mDb = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(StocklistContract.StocklistEntry._ID, id);
        cv.put(StocklistContract.StocklistEntry.COLUMN_NAME, name);
        cv.put(StocklistContract.StocklistEntry.COLUMN_CODE, code);
        cv.put(StocklistContract.StocklistEntry.COLUMN_DATE, date);
        cv.put(StocklistContract.StocklistEntry.COLUMN_PRICE, price);
        cv.put(StocklistContract.StocklistEntry.COLUMN_NET_CHANGE, netChange);
        cv.put(StocklistContract.StocklistEntry.COLUMN_PE, pe);
        cv.put(StocklistContract.StocklistEntry.COLUMN_HIGH, high);
        cv.put(StocklistContract.StocklistEntry.COLUMN_LOW, low);
        cv.put(StocklistContract.StocklistEntry.COLUMN_PRE_CLOSE, preClose);
        cv.put(StocklistContract.StocklistEntry.COLUMN_VOLUME, volume);
        cv.put(StocklistContract.StocklistEntry.COLUMN_TURNOVER, turnover);
        cv.put(StocklistContract.StocklistEntry.COLUMN_LOT, lot);
        cv.put(StocklistContract.StocklistEntry.COLUMN_DY, dy);
        cv.put(StocklistContract.StocklistEntry.COLUMN_DPS, dps);
        cv.put(StocklistContract.StocklistEntry.COLUMN_EPS, eps);
        cv.put(StocklistContract.StocklistEntry.COLUMN_SMA20, sma20);
        cv.put(StocklistContract.StocklistEntry.COLUMN_STD20, std20);
        cv.put(StocklistContract.StocklistEntry.COLUMN_STD20L, std20l);
        cv.put(StocklistContract.StocklistEntry.COLUMN_STD20H, std20h);
        cv.put(StocklistContract.StocklistEntry.COLUMN_SMA50, sma50);
        cv.put(StocklistContract.StocklistEntry.COLUMN_STD50, std50);
        cv.put(StocklistContract.StocklistEntry.COLUMN_STD50L, std50l);
        cv.put(StocklistContract.StocklistEntry.COLUMN_STD50H, std50h);
        cv.put(StocklistContract.StocklistEntry.COLUMN_SMA100, sma100);
        cv.put(StocklistContract.StocklistEntry.COLUMN_STD100, std100);
        cv.put(StocklistContract.StocklistEntry.COLUMN_STD100L, std100l);
        cv.put(StocklistContract.StocklistEntry.COLUMN_STD100H, std100h);
        cv.put(StocklistContract.StocklistEntry.COLUMN_SMA250, sma250);
        cv.put(StocklistContract.StocklistEntry.COLUMN_STD250, std250);
        cv.put(StocklistContract.StocklistEntry.COLUMN_STD250L, std250l);
        cv.put(StocklistContract.StocklistEntry.COLUMN_STD250H, std250h);
        cv.put(StocklistContract.StocklistEntry.COLUMN_20L, l20);
        cv.put(StocklistContract.StocklistEntry.COLUMN_20H, h20);
        cv.put(StocklistContract.StocklistEntry.COLUMN_50L, l50);
        cv.put(StocklistContract.StocklistEntry.COLUMN_50H, h50);
        cv.put(StocklistContract.StocklistEntry.COLUMN_100L, l100);
        cv.put(StocklistContract.StocklistEntry.COLUMN_100H, h100);
        cv.put(StocklistContract.StocklistEntry.COLUMN_250L, l250);
        cv.put(StocklistContract.StocklistEntry.COLUMN_250H, h250);

        long result = mDb.insertWithOnConflict(StocklistContract.StocklistEntry.TABLE_NAME, null, cv, SQLiteDatabase.CONFLICT_IGNORE);
        if (result == -1) {
            mDb.update(StocklistContract.StocklistEntry.TABLE_NAME, cv, StocklistContract.StocklistEntry._ID + "=" + id, null);
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
        String name, date;
        int code = 0;
        double price = 0, netChange = 0, pe = 0, high = 0, low = 0, preClose = 0, volume = 0, turnover = 0, lot = 0,
                dy=0, dps = 0, eps = 0, sma20 = 0, std20 = 0, std20l = 0, std20h = 0, sma50 = 0, std50 = 0, std50l = 0, std50h = 0,
                sma100 = 0, std100 = 0, std100l = 0, std100h = 0, sma250 = 0, std250 = 0, std250l = 0, std250h = 0,
                l20 = 0, h20 = 0, l50 = 0, h50 = 0, l100 = 0, h100 = 0, l250 = 0, h250 = 0;

        id = Long.parseLong(parsedStockData[1]);
        name = parsedStockData[0];
        code = Integer.parseInt(parsedStockData[1]);
        date = parsedStockData[2];

        price = checkDouble(parsedStockData[3]);
        netChange =  checkDouble(parsedStockData[4]);
        pe =  checkDouble(parsedStockData[5]);
        high =  checkDouble(parsedStockData[6]);
        low =  checkDouble(parsedStockData[7]);
        preClose =  checkDouble(parsedStockData[8]);
        volume =  checkDouble(parsedStockData[9]);
        turnover =  checkDouble(parsedStockData[10]);
        lot =  checkDouble(parsedStockData[11]);
        dy =  checkDouble(parsedStockData[12]);
        dps =  checkDouble(parsedStockData[13]);
        eps =  checkDouble(parsedStockData[14]);
        sma20 =  checkDouble(parsedStockData[15]);
        std20 =  checkDouble(parsedStockData[16]);
        std20l =  checkDouble(parsedStockData[17]);
        std20h =  checkDouble(parsedStockData[18]);
        sma50 =  checkDouble(parsedStockData[19]);
        std50 =  checkDouble(parsedStockData[20]);
        std50l =  checkDouble(parsedStockData[21]);
        std50h =  checkDouble(parsedStockData[22]);
        sma100 =  checkDouble(parsedStockData[23]);
        std100 =  checkDouble(parsedStockData[24]);
        std100l =  checkDouble(parsedStockData[25]);
        std100h =  checkDouble(parsedStockData[26]);
        sma250 =  checkDouble(parsedStockData[27]);
        std250 =  checkDouble(parsedStockData[28]);
        std250l =  checkDouble(parsedStockData[29]);
        std250h =  checkDouble(parsedStockData[30]);
        l20 =  checkDouble(parsedStockData[31]);
        h20 =  checkDouble(parsedStockData[32]);
        l50 =  checkDouble(parsedStockData[33]);
        h50 =  checkDouble(parsedStockData[34]);
        l100 =  checkDouble(parsedStockData[35]);
        h100 =  checkDouble(parsedStockData[36]);
        l250 =  checkDouble(parsedStockData[37]);
        h250 =  checkDouble(parsedStockData[38]);



        SQLiteDatabase mDb = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(StocklistContract.StocklistEntry._ID, id);
        cv.put(StocklistContract.StocklistEntry.COLUMN_NAME, name);
        cv.put(StocklistContract.StocklistEntry.COLUMN_CODE, code);
        cv.put(StocklistContract.StocklistEntry.COLUMN_DATE, date);
        cv.put(StocklistContract.StocklistEntry.COLUMN_PRICE, price);
        cv.put(StocklistContract.StocklistEntry.COLUMN_NET_CHANGE, netChange);
        cv.put(StocklistContract.StocklistEntry.COLUMN_PE, pe);
        cv.put(StocklistContract.StocklistEntry.COLUMN_HIGH, high);
        cv.put(StocklistContract.StocklistEntry.COLUMN_LOW, low);
        cv.put(StocklistContract.StocklistEntry.COLUMN_PRE_CLOSE, preClose);
        cv.put(StocklistContract.StocklistEntry.COLUMN_VOLUME, volume);
        cv.put(StocklistContract.StocklistEntry.COLUMN_TURNOVER, turnover);
        cv.put(StocklistContract.StocklistEntry.COLUMN_LOT, lot);
        cv.put(StocklistContract.StocklistEntry.COLUMN_DY, dy);
        cv.put(StocklistContract.StocklistEntry.COLUMN_DPS, dps);
        cv.put(StocklistContract.StocklistEntry.COLUMN_EPS, eps);
        cv.put(StocklistContract.StocklistEntry.COLUMN_SMA20, sma20);
        cv.put(StocklistContract.StocklistEntry.COLUMN_STD20, std20);
        cv.put(StocklistContract.StocklistEntry.COLUMN_STD20L, std20l);
        cv.put(StocklistContract.StocklistEntry.COLUMN_STD20H, std20h);
        cv.put(StocklistContract.StocklistEntry.COLUMN_SMA50, sma50);
        cv.put(StocklistContract.StocklistEntry.COLUMN_STD50, std50);
        cv.put(StocklistContract.StocklistEntry.COLUMN_STD50L, std50l);
        cv.put(StocklistContract.StocklistEntry.COLUMN_STD50H, std50h);
        cv.put(StocklistContract.StocklistEntry.COLUMN_SMA100, sma100);
        cv.put(StocklistContract.StocklistEntry.COLUMN_STD100, std100);
        cv.put(StocklistContract.StocklistEntry.COLUMN_STD100L, std100l);
        cv.put(StocklistContract.StocklistEntry.COLUMN_STD100H, std100h);
        cv.put(StocklistContract.StocklistEntry.COLUMN_SMA250, sma250);
        cv.put(StocklistContract.StocklistEntry.COLUMN_STD250, std250);
        cv.put(StocklistContract.StocklistEntry.COLUMN_STD250L, std250l);
        cv.put(StocklistContract.StocklistEntry.COLUMN_STD250H, std250h);
        cv.put(StocklistContract.StocklistEntry.COLUMN_20L, l20);
        cv.put(StocklistContract.StocklistEntry.COLUMN_20H, h20);
        cv.put(StocklistContract.StocklistEntry.COLUMN_50L, l50);
        cv.put(StocklistContract.StocklistEntry.COLUMN_50H, h50);
        cv.put(StocklistContract.StocklistEntry.COLUMN_100L, l100);
        cv.put(StocklistContract.StocklistEntry.COLUMN_100H, h100);
        cv.put(StocklistContract.StocklistEntry.COLUMN_250L, l250);
        cv.put(StocklistContract.StocklistEntry.COLUMN_250H, h250);

        long result = mDb.insertWithOnConflict(StocklistContract.StocklistEntry.TABLE_NAME, null, cv, SQLiteDatabase.CONFLICT_IGNORE);
        if (result == -1) {
            mDb.update(StocklistContract.StocklistEntry.TABLE_NAME, cv, StocklistContract.StocklistEntry._ID + "=" + id, null);
        }
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
