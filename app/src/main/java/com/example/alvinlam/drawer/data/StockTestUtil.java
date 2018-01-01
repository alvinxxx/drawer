package com.example.alvinlam.drawer.data;

/**
 * Created by Alvin Lam on 3/26/2017.
 */

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class StockTestUtil {

    public static void insertFakeData(SQLiteDatabase db){
        if(db == null){
            return;
        }
        //create a list of fake guests
        List<ContentValues> list = new ArrayList<ContentValues>();

        ContentValues cv = new ContentValues();
        cv.put(StocklistContract.StocklistEntry.COLUMN_NAME, "CKH Holdings");
        cv.put(StocklistContract.StocklistEntry.COLUMN_CODE, "0001");
        cv.put(StocklistContract.StocklistEntry.COLUMN_DATE, "20140221");
        cv.put(StocklistContract.StocklistEntry.COLUMN_PRICE, "118.4");
        cv.put(StocklistContract.StocklistEntry.COLUMN_NET_CHANGE, "2.3");
        cv.put(StocklistContract.StocklistEntry.COLUMN_PE, "8.53");
        cv.put(StocklistContract.StocklistEntry.COLUMN_HIGH, "118.5");
        cv.put(StocklistContract.StocklistEntry.COLUMN_LOW, "117.2");
        cv.put(StocklistContract.StocklistEntry.COLUMN_PRE_CLOSE, "116.1");
        cv.put(StocklistContract.StocklistEntry.COLUMN_VOLUME, "2528");
        cv.put(StocklistContract.StocklistEntry.COLUMN_TURNOVER, "297996");
        cv.put(StocklistContract.StocklistEntry.COLUMN_LOT, "1000");
        list.add(cv);

        cv = new ContentValues();
        cv.put(StocklistContract.StocklistEntry.COLUMN_NAME, "CLP Holdings");
        cv.put(StocklistContract.StocklistEntry.COLUMN_CODE, "0002");
        cv.put(StocklistContract.StocklistEntry.COLUMN_DATE, "20140221");
        cv.put(StocklistContract.StocklistEntry.COLUMN_PRICE, "60.25");
        cv.put(StocklistContract.StocklistEntry.COLUMN_NET_CHANGE, "0.7");
        cv.put(StocklistContract.StocklistEntry.COLUMN_PE, "17.47");
        cv.put(StocklistContract.StocklistEntry.COLUMN_HIGH, "60.35");
        cv.put(StocklistContract.StocklistEntry.COLUMN_LOW, "59.6");
        cv.put(StocklistContract.StocklistEntry.COLUMN_PRE_CLOSE, "59.55");
        cv.put(StocklistContract.StocklistEntry.COLUMN_VOLUME, "2059");
        cv.put(StocklistContract.StocklistEntry.COLUMN_TURNOVER, "123707");
        cv.put(StocklistContract.StocklistEntry.COLUMN_LOT, "500");
        list.add(cv);

        cv = new ContentValues();
        cv.put(StocklistContract.StocklistEntry.COLUMN_NAME, "HK & China Gas");
        cv.put(StocklistContract.StocklistEntry.COLUMN_CODE, "0003");
        cv.put(StocklistContract.StocklistEntry.COLUMN_DATE, "20140221");
        cv.put(StocklistContract.StocklistEntry.COLUMN_PRICE, "16.28");
        cv.put(StocklistContract.StocklistEntry.COLUMN_NET_CHANGE, "0.38");
        cv.put(StocklistContract.StocklistEntry.COLUMN_PE, "20.14");
        cv.put(StocklistContract.StocklistEntry.COLUMN_HIGH, "16.32");
        cv.put(StocklistContract.StocklistEntry.COLUMN_LOW, "15.92");
        cv.put(StocklistContract.StocklistEntry.COLUMN_PRE_CLOSE, "15.9");
        cv.put(StocklistContract.StocklistEntry.COLUMN_VOLUME, "9058");
        cv.put(StocklistContract.StocklistEntry.COLUMN_TURNOVER, "146303");
        cv.put(StocklistContract.StocklistEntry.COLUMN_LOT, "1000");
        list.add(cv);


        //insert all guests in one transaction
        try
        {
            db.beginTransaction();
            //clear the table first
            db.delete (StocklistContract.StocklistEntry.TABLE_NAME,null,null);
            //go through the list and add one by one
            for(ContentValues c:list){
                db.insert(StocklistContract.StocklistEntry.TABLE_NAME, null, c);
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
