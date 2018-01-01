package com.example.alvinlam.drawer.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper
{

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = " cardlist.db";

    // Contacts table name
    private static final String TABLE_CARDS = " contacts";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_IMAGE = "image";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CARDS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_IMAGE + " BLOB" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARDS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read) Operations
     */

    public// Adding new card
    void addContact(Card card) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, card._name); // Card Name
        values.put(KEY_IMAGE, card._image); // Card Phone

        // Inserting Row
        db.insert(TABLE_CARDS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    Card getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CARDS, new String[] { KEY_ID,
                        KEY_NAME, KEY_IMAGE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Card card = new Card(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getBlob(1));

        // return card
        return card;

    }

    // Getting All Contacts
    public List<Card> getAllContacts() {
        List<Card> cardList = new ArrayList<Card>();
        // Select All Query
        String selectQuery = "SELECT  * FROM contacts ORDER BY name";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Card card = new Card();
                card.setID(Integer.parseInt(cursor.getString(0)));
                card.setName(cursor.getString(1));
                card.setImage(cursor.getBlob(2));
                // Adding card to list
                cardList.add(card);
            } while (cursor.moveToNext());
        }
        // close inserting data from database
        db.close();
        // return contact list
        return cardList;
    }
}