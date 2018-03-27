package com.example.alvinlam.drawer.data;

/**
 * Created by Alvin Lam on 3/26/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.alvinlam.drawer.data.StocklistContract.*;

public class StocklistDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "stocklist.db";
    private static final int DATABASE_VERSION = 14;

    public StocklistDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_STOCKLIST_TABLE = "CREATE TABLE " + StocklistEntry.TABLE_NAME + " (" +
                StocklistEntry._ID + " INTEGER PRIMARY KEY," +
                StocklistEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                StocklistEntry.COLUMN_CODE + " INTEGER NOT NULL UNIQUE, " +
                StocklistEntry.COLUMN_DATE + " TEXT NOT NULL, " +
                StocklistEntry.COLUMN_PRICE + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_NET_CHANGE + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_PE + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_HIGH + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_LOW + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_PRE_CLOSE + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_VOLUME + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_TURNOVER + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_LOT + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_DY + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_DPS + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_EPS + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_SMA20 + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_STD20 + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_STD20L + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_STD20H + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_SMA50 + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_STD50 + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_STD50L + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_STD50H + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_SMA100 + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_STD100 + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_STD100L + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_STD100H + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_SMA250 + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_STD250 + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_STD250L + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_STD250H + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_20L + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_20H + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_50L + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_50H + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_100L + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_100H + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_250L + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_250H + " DOUBLE NOT NULL, " +
                StocklistEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_STOCKLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // For now simply drop the table and create a new one. This means if you change the
        // DATABASE_VERSION the table will be dropped.
        // In a production app, this method might be modified to ALTER the table
        // instead of dropping it, so that existing data is not deleted.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + StocklistEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
