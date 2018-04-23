package com.example.alvinlam.drawer.data;

/**
 * Created by Alvin Lam on 3/26/2017.
 */

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class RiskAssessTestUtil {

    public static void insertData(SQLiteDatabase db){

        String[] questions = {
                "What is the objective of your stock investment?",
                "Which of the following best describes your investment approach?",
                "What makes up the biggest portion of your investments?",
                "What makes up your biggest financial commitment?",
                "What is the biggest part of your savings for retirement? ",
                "Among the four hypothetical portfolios below, which one will you choose as your  retirement investment? "
        };

        List<String[]> listStringAnswer = new ArrayList<String[]>();
        String[] a1 = {
                "a) To achieve a rate of return comparable to the Hong Kong dollar bank deposit rate",
                "b) To preserve the value of investment and try to beat inflation",
                "c) To achieve an annual return of 10-15%",
                "d) To achieve an annual return of over 15%"
        };
        String[] a2 = {
                "a) I cannot tolerate any loss.",
                "b) I am willing to accept up to 5% annual loss for a higher return",
                "c) I am willing to accept up to 15% annual loss for a higher return",
                "d) I am willing to accept over 15% annual loss for a higher return"
        };
        String[] a3 = {
                "a) Bank deposits",
                "b) Fixed income securities / Treasury bonds / Government bonds",
                "c) Mutual funds / Unit trusts",
                "d) Stocks"
        };
        String[] a4 = {
                "a) Mortgage",
                "b) Supporting family",
                "c) My financial commitment represents less than 1/3 of your current earnings",
                "d) I have no financial obligation apart from personal spending"
        };
        String[] a5 = {
                "a) I have no additional savings for retirement",
                "b) Bank deposits",
                "c) Life insurance / Investment-linked plan",
                "d) Other types of investments such as properties, funds and stocks"
        };
        String[] a6 = {
                "a) Portfolio A: 3% annual return, 12% gain in the best year, 0% loss in the worst year",
                "b) Portfolio B: 5% annual return, 25% gain in the best year, 8% loss in the worst year",
                "c) Portfolio C: 8% annual return, 40% gain in the best year, 20% loss in the worst year",
                "d) Portfolio D: 11% annual return, 50% gain in the best year, 38% loss in the worst year"
        };
        listStringAnswer.add(a1);
        listStringAnswer.add(a2);
        listStringAnswer.add(a3);
        listStringAnswer.add(a4);
        listStringAnswer.add(a5);
        listStringAnswer.add(a6);
        Log.d("risk test", "insert: "+"finish adding");


        if(db == null){
            return;
        }
        //create a list of fake guests
        List<ContentValues> listQuestions = new ArrayList<ContentValues>();
        List<ContentValues> listAnswers = new ArrayList<ContentValues>();
        List<ContentValues> listRecords = new ArrayList<ContentValues>();

        int i = 1;
        for(String question : questions){
            ContentValues cv = new ContentValues();
            cv.put(StocklistContract.RiskAssessQuestionEntry._ID, i);
            cv.put(StocklistContract.RiskAssessQuestionEntry.COLUMN_QUESTION, question);
            listQuestions.add(cv);

            String[] answers = listStringAnswer.get(i-1);
            int j = 1;
            for(String answer : answers) {
                cv = new ContentValues();
                cv.put(StocklistContract.RiskAssessAnswerEntry.COLUMN_ANSWER, answer);
                cv.put(StocklistContract.RiskAssessAnswerEntry.COLUMN_SCORE, j*2);
                cv.put(StocklistContract.RiskAssessAnswerEntry.COLUMN_QID, i);
                listAnswers.add(cv);
                j++;
            }

            cv = new ContentValues();
            cv.put(StocklistContract.RiskAssessRecordEntry.COLUMN_QID, i);
            cv.put(StocklistContract.RiskAssessRecordEntry.COLUMN_SCORE, 0);
            listRecords.add(cv);

            i++;
        }
        Log.d("risk test", "insert: "+"finish listing");


        //insert all guests in one transaction
        try
        {
            db.beginTransaction();
            //clear the table first
            db.delete (StocklistContract.RiskAssessQuestionEntry.TABLE_NAME,null,null);
            db.delete (StocklistContract.RiskAssessAnswerEntry.TABLE_NAME,null,null);
            db.delete (StocklistContract.RiskAssessRecordEntry.TABLE_NAME,null,null);
            //go through the list and add one by one
            for(ContentValues c:listQuestions){
                db.insert(StocklistContract.RiskAssessQuestionEntry.TABLE_NAME, null, c);
            }
            for(ContentValues c:listAnswers){
                db.insert(StocklistContract.RiskAssessAnswerEntry.TABLE_NAME, null, c);
            }
            for(ContentValues c:listRecords){
                db.insert(StocklistContract.RiskAssessRecordEntry.TABLE_NAME, null, c);
            }
            Log.d("risk test", "insert: "+"finish insert");

            db.setTransactionSuccessful();
        }
        catch (SQLException e) {
            //too bad :(
            Log.d("riskassess", "test: "+e);
        }
        finally
        {
            db.endTransaction();
        }

    }
}
