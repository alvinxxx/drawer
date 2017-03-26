package com.example.alvinlam.drawer.data;

/**
 * Created by Alvin Lam on 3/26/2017.
 */

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    public static void insertFakeData(SQLiteDatabase db){
        if(db == null){
            return;
        }
        //create a list of fake guests
        List<ContentValues> list = new ArrayList<ContentValues>();

        ContentValues cv = new ContentValues();
        cv.put(CardlistContract.CardlistEntry.COLUMN_NAME, "John");
        cv.put(CardlistContract.CardlistEntry.COLUMN_PHONE, "10011001");
        cv.put(CardlistContract.CardlistEntry.COLUMN_EMAIL, "john@gmail.com");
        cv.put(CardlistContract.CardlistEntry.COLUMN_TITLE, "programmer");
        cv.put(CardlistContract.CardlistEntry.COLUMN_WEBSITE, "www.john.com");
        cv.put(CardlistContract.CardlistEntry.COLUMN_COMPANY, "John company");
        cv.put(CardlistContract.CardlistEntry.COLUMN_COMPANY_PHONE, "20012001");
        cv.put(CardlistContract.CardlistEntry.COLUMN_COMPANY_ADDRESS, "John street");
        list.add(cv);

        cv = new ContentValues();
        cv.put(CardlistContract.CardlistEntry.COLUMN_NAME, "Larry");
        cv.put(CardlistContract.CardlistEntry.COLUMN_PHONE, "10021002");
        cv.put(CardlistContract.CardlistEntry.COLUMN_EMAIL, "Larry@gmail.com");
        cv.put(CardlistContract.CardlistEntry.COLUMN_TITLE, "designer");
        cv.put(CardlistContract.CardlistEntry.COLUMN_WEBSITE, "www.Larry.com");
        cv.put(CardlistContract.CardlistEntry.COLUMN_COMPANY, "Larry company");
        cv.put(CardlistContract.CardlistEntry.COLUMN_COMPANY_PHONE, "20022002");
        cv.put(CardlistContract.CardlistEntry.COLUMN_COMPANY_ADDRESS, "Larry street");
        list.add(cv);

        cv = new ContentValues();
        cv.put(CardlistContract.CardlistEntry.COLUMN_NAME, "Kim");
        cv.put(CardlistContract.CardlistEntry.COLUMN_PHONE, "10031003");
        cv.put(CardlistContract.CardlistEntry.COLUMN_EMAIL, "Kim@gmail.com");
        cv.put(CardlistContract.CardlistEntry.COLUMN_TITLE, "designer");
        cv.put(CardlistContract.CardlistEntry.COLUMN_WEBSITE, "www.Kim.com");
        cv.put(CardlistContract.CardlistEntry.COLUMN_COMPANY, "Kim company");
        cv.put(CardlistContract.CardlistEntry.COLUMN_COMPANY_PHONE, "20032003");
        cv.put(CardlistContract.CardlistEntry.COLUMN_COMPANY_ADDRESS, "Kim street");
        list.add(cv);


        //insert all guests in one transaction
        try
        {
            db.beginTransaction();
            //clear the table first
            db.delete (CardlistContract.CardlistEntry.TABLE_NAME,null,null);
            //go through the list and add one by one
            for(ContentValues c:list){
                db.insert(CardlistContract.CardlistEntry.TABLE_NAME, null, c);
            }
            db.setTransactionSuccessful();
        }
        catch (SQLException e) {
            //too bad :(
        }
        finally
        {
            db.endTransaction();
        }

    }
}
