package com.example.alvinlam.drawer.data.old;

/**
 * Created by Alvin Lam on 3/26/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CardlistDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cardlist.db";
    private static final int DATABASE_VERSION = 4;

    public CardlistDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_CARDLIST_TABLE = "CREATE TABLE " + CardlistContract.CardlistEntry.TABLE_NAME + " (" +
                CardlistContract.CardlistEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CardlistContract.CardlistEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                CardlistContract.CardlistEntry.COLUMN_PHONE + " INTEGER NOT NULL, " +
                CardlistContract.CardlistEntry.COLUMN_EMAIL + " TEXT NOT NULL, " +
                CardlistContract.CardlistEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                CardlistContract.CardlistEntry.COLUMN_WEBSITE + " TEXT NOT NULL, " +
                CardlistContract.CardlistEntry.COLUMN_COMPANY + " TEXT NOT NULL, " +
                CardlistContract.CardlistEntry.COLUMN_COMPANY_PHONE + " INTEGER NOT NULL, " +
                CardlistContract.CardlistEntry.COLUMN_COMPANY_ADDRESS + " TEXT NOT NULL, " +
                CardlistContract.CardlistEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_CARDLIST_TABLE);

        final String SQL_CREATE_MYCARD_TABLE = "CREATE TABLE " + CardlistContract.MyCardEntry.TABLE_NAME + " (" +
                CardlistContract.MyCardEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CardlistContract.MyCardEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                CardlistContract.MyCardEntry.COLUMN_PHONE + " INTEGER NOT NULL, " +
                CardlistContract.MyCardEntry.COLUMN_EMAIL + " TEXT NOT NULL, " +
                CardlistContract.MyCardEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                CardlistContract.MyCardEntry.COLUMN_WEBSITE + " TEXT NOT NULL, " +
                CardlistContract.MyCardEntry.COLUMN_COMPANY + " TEXT NOT NULL, " +
                CardlistContract.MyCardEntry.COLUMN_COMPANY_PHONE + " INTEGER NOT NULL, " +
                CardlistContract.MyCardEntry.COLUMN_COMPANY_ADDRESS + " TEXT NOT NULL, " +
                CardlistContract.MyCardEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_MYCARD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // For now simply drop the table and create a new one. This means if you change the
        // DATABASE_VERSION the table will be dropped.
        // In a production app, this method might be modified to ALTER the table
        // instead of dropping it, so that existing data is not deleted.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CardlistContract.CardlistEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CardlistContract.MyCardEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
