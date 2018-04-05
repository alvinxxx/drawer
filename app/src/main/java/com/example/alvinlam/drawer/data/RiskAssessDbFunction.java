package com.example.alvinlam.drawer.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Alvin Lam on 4/9/2017.
 */

public class RiskAssessDbFunction {
    private StocklistDbHelper dbHelper;

    public RiskAssessDbFunction(Context context) {
        dbHelper = new StocklistDbHelper(context);
    }

    public void insert(int qid, int score) {

        //Open connection to write data
        SQLiteDatabase mDb = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(StocklistContract.RiskAssessRecordEntry.COLUMN_QID, qid);
        cv.put(StocklistContract.RiskAssessRecordEntry.COLUMN_SCORE, score);

        mDb.insert(StocklistContract.RiskAssessRecordEntry.TABLE_NAME, null, cv);
        mDb.close(); // Closing database connection
    }


    public void replace(int qid, int score) {

        //Open connection to write data
        SQLiteDatabase mDb = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(StocklistContract.RiskAssessRecordEntry.COLUMN_QID, qid);
        cv.put(StocklistContract.RiskAssessRecordEntry.COLUMN_SCORE, score);

        long result = mDb.insertWithOnConflict(
                StocklistContract.RiskAssessRecordEntry.TABLE_NAME, null, cv, SQLiteDatabase.CONFLICT_IGNORE);

        if (result == -1) {
            mDb.update(
                    StocklistContract.RiskAssessRecordEntry.TABLE_NAME, cv,
                    StocklistContract.RiskAssessRecordEntry.COLUMN_QID + "=" + qid, null);
        }

        //mDb.replace(StocklistContract.StocklistEntry.TABLE_NAME, null, cv);
        mDb.close(); // Closing database connection
    }



    public Cursor selectQuestion() {
        SQLiteDatabase mDb = dbHelper.getReadableDatabase();
        Cursor cursor = mDb.query(
                StocklistContract.RiskAssessQuestionEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                StocklistContract.RiskAssessQuestionEntry.COLUMN_TIMESTAMP
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

    public Cursor selectQuestionByQid(long qid) {
        SQLiteDatabase mDb = dbHelper.getReadableDatabase();
        Cursor cursor = mDb.query(
                StocklistContract.RiskAssessQuestionEntry.TABLE_NAME,
                null,
                StocklistContract.RiskAssessQuestionEntry._ID + "=" + qid,
                null,
                null,
                null,
                StocklistContract.RiskAssessQuestionEntry.COLUMN_TIMESTAMP
        );
        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }

    public Cursor selectAnswerByQid(long qid) {
        SQLiteDatabase mDb = dbHelper.getReadableDatabase();
        Cursor cursor = mDb.query(
                StocklistContract.RiskAssessAnswerEntry.TABLE_NAME,
                null,
                StocklistContract.RiskAssessAnswerEntry.COLUMN_QID + "=" + qid,
                null,
                null,
                null,
                StocklistContract.RiskAssessAnswerEntry.COLUMN_TIMESTAMP
        );
        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }

    public Cursor selectTotalScore() {
        String[] columns = new String[] { "SUM(score)" };

        SQLiteDatabase mDb = dbHelper.getReadableDatabase();
        /**
        Cursor cursor = mDb.rawQuery
                ("SELECT SUM("+ StocklistContract.RiskAssessRecordEntry.COLUMN_SCORE+") as Total FROM "
                                + StocklistContract.RiskAssessRecordEntry.TABLE_NAME,
                null);
         **/
        Cursor cursor = mDb.query(
                StocklistContract.RiskAssessRecordEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                StocklistContract.RiskAssessRecordEntry.COLUMN_TIMESTAMP
        );


        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }



}
