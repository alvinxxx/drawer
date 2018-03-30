package com.example.alvinlam.drawer.data;

/**
 * Created by Alvin Lam on 3/26/2017.
 */

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.alvinlam.drawer.R;

import java.util.ArrayList;
import java.util.List;

public class StockAlertTestUtil {

    public static void insertFakeData(SQLiteDatabase db){
        if(db == null){
            return;
        }
        //create a list of fake guests
        List<ContentValues> list = new ArrayList<ContentValues>();

        ContentValues cv = new ContentValues();
        cv.put(StocklistContract.StockAlertEntry.COLUMN_NAME, "CKH Holdings");
        cv.put(StocklistContract.StockAlertEntry.COLUMN_CODE, 1);
        cv.put(StocklistContract.StockAlertEntry.COLUMN_ACTIVE, 1); //active?
        cv.put(StocklistContract.StockAlertEntry.COLUMN_BUY, 1); //buy
        cv.put(StocklistContract.StockAlertEntry.COLUMN_INDICATOR, "Price");
        cv.put(StocklistContract.StockAlertEntry.COLUMN_CONDITION, "Less than \\u003c");
        cv.put(StocklistContract.StockAlertEntry.COLUMN_WINDOW, 20);
        cv.put(StocklistContract.StockAlertEntry.COLUMN_TARGET, "SMA");
        cv.put(StocklistContract.StockAlertEntry.COLUMN_DISTANCE, "-2STD");

        list.add(cv);

        cv = new ContentValues();
        cv.put(StocklistContract.StockAlertEntry.COLUMN_NAME, "CKH Holdings");
        cv.put(StocklistContract.StockAlertEntry.COLUMN_CODE, 1);
        cv.put(StocklistContract.StockAlertEntry.COLUMN_ACTIVE, 1); //active?
        cv.put(StocklistContract.StockAlertEntry.COLUMN_BUY, 0); //sell
        cv.put(StocklistContract.StockAlertEntry.COLUMN_INDICATOR, "Price");
        cv.put(StocklistContract.StockAlertEntry.COLUMN_CONDITION, "Less than \\u003c");
        cv.put(StocklistContract.StockAlertEntry.COLUMN_WINDOW, 20);
        cv.put(StocklistContract.StockAlertEntry.COLUMN_TARGET, "SMA");
        cv.put(StocklistContract.StockAlertEntry.COLUMN_DISTANCE, "-2STD");
        list.add(cv);

        //insert all guests in one transaction
        try
        {
            db.beginTransaction();
            //clear the table first
            db.delete (StocklistContract.StockAlertEntry.TABLE_NAME,null,null);
            //go through the list and add one by one
            for(ContentValues c:list){
                db.insert(StocklistContract.StockAlertEntry.TABLE_NAME, null, c);
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
