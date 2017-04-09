package com.example.alvinlam.drawer.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Alvin Lam on 4/9/2017.
 */

public class DbFunction {
    private CardlistDbHelper dbHelper;

    public DbFunction(Context context) {
        dbHelper = new CardlistDbHelper(context);
    }

    public void insert(String name, int phone, String email, String title, String website, String company, int cphone, String caddress) {

        //Open connection to write data
        SQLiteDatabase mDb = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CardlistContract.CardlistEntry.COLUMN_NAME, name);
        cv.put(CardlistContract.CardlistEntry.COLUMN_PHONE, phone);
        cv.put(CardlistContract.CardlistEntry.COLUMN_EMAIL, email);
        cv.put(CardlistContract.CardlistEntry.COLUMN_TITLE, title);
        cv.put(CardlistContract.CardlistEntry.COLUMN_WEBSITE, website);
        cv.put(CardlistContract.CardlistEntry.COLUMN_COMPANY, company);
        cv.put(CardlistContract.CardlistEntry.COLUMN_COMPANY_PHONE, cphone);
        cv.put(CardlistContract.CardlistEntry.COLUMN_COMPANY_ADDRESS, caddress);

        // Inserting Row
        mDb.insert(CardlistContract.CardlistEntry.TABLE_NAME, null, cv);
        mDb.close(); // Closing database connection
    }
    public void update(long id, String name, int phone, String email, String title, String website, String company, int cphone, String caddress) {

        //Open connection to write data
        SQLiteDatabase mDb = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CardlistContract.CardlistEntry.COLUMN_NAME, name);
        cv.put(CardlistContract.CardlistEntry.COLUMN_PHONE, phone);
        cv.put(CardlistContract.CardlistEntry.COLUMN_EMAIL, email);
        cv.put(CardlistContract.CardlistEntry.COLUMN_TITLE, title);
        cv.put(CardlistContract.CardlistEntry.COLUMN_WEBSITE, website);
        cv.put(CardlistContract.CardlistEntry.COLUMN_COMPANY, company);
        cv.put(CardlistContract.CardlistEntry.COLUMN_COMPANY_PHONE, cphone);
        cv.put(CardlistContract.CardlistEntry.COLUMN_COMPANY_ADDRESS, caddress);

        mDb.update(CardlistContract.CardlistEntry.TABLE_NAME, cv, CardlistContract.CardlistEntry._ID + "=" + id, null);
        mDb.close(); // Closing database connection
    }

    public Cursor select() {
        SQLiteDatabase mDb = dbHelper.getReadableDatabase();
        Cursor cursor = mDb.query(
                CardlistContract.CardlistEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                CardlistContract.CardlistEntry.COLUMN_TIMESTAMP
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
                CardlistContract.CardlistEntry.TABLE_NAME,
                null,
                CardlistContract.CardlistEntry._ID + "=" + id,
                null,
                null,
                null,
                CardlistContract.CardlistEntry.COLUMN_TIMESTAMP
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
                CardlistContract.CardlistEntry.TABLE_NAME,
                null,
                CardlistContract.CardlistEntry.COLUMN_NAME + "  LIKE  '%" +search + "%' ",
                null,
                null,
                null,
                CardlistContract.CardlistEntry.COLUMN_TIMESTAMP

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
        return mDb.delete(CardlistContract.CardlistEntry.TABLE_NAME, CardlistContract.CardlistEntry._ID + "=" + id, null) > 0;
    }
}
