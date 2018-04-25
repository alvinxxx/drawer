package com.example.alvinlam.drawer.sync;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.alvinlam.drawer.activity.AddCardAddActivity;
import com.example.alvinlam.drawer.activity.MainActivity;
import com.example.alvinlam.drawer.data.RiskAssessDbFunction;
import com.example.alvinlam.drawer.data.StockDbFunction;
import com.example.alvinlam.drawer.data.StocklistContract;
import com.example.alvinlam.drawer.fragment.StockRecommendFragment;
import com.example.alvinlam.drawer.utilities.NetworkUtils;
import com.example.alvinlam.drawer.utilities.NotificationUtils;
import com.example.alvinlam.drawer.utilities.OpenStockJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ReminderTasks {

    public static final String ACTION_YES = "action-yes";
    //  COMPLETED (2) Add a public static constant called ACTION_NO
    public static final String ACTION_NO = "action-no";
    public static final String ACTION_QUERY = "query-stock";
    public static final String ACTION_DAILY = "query-daily";


    public static void executeTask(Context context, String action) {
        if (ACTION_YES.equals(action)) {
            doYes(context);
        } else if (ACTION_NO.equals(action)) {
            NotificationUtils.clearAllNotifications(context);
        } else if (ACTION_QUERY.equals(action)) {
            doQuery(context);
        }else if (ACTION_DAILY.equals(action)) {
            doDailyQuery(context);
        }
        //      COMPLETED (3) If the user ignored the reminder, clear the notification
    }

    private static void doYes(Context context) {
        //PreferenceUtilities.doYes(context);
        //      COMPLETED (4) If the water count was incremented, clear any notifications
        NotificationUtils.clearAllNotifications(context);

    }

    private static void doDailyQuery(Context context) {
        RiskAssessDbFunction dbRAFunction = new RiskAssessDbFunction(context);

        Cursor cursor = dbRAFunction.selectTotalScore();
        int total = cursor.getInt(0);// get final total
        int cat = 0;
        if (total >= 12 && total <= 19){
            cat = 1;
        }else if (total >= 20 && total <= 28){
            cat = 2;
        }else if (total >= 29 && total <= 37){
            cat = 3;
        }else if (total >= 38 && total <= 46){
            cat = 4;
        }else if (total >= 47 && total <= 54){
            cat = 5;
        }

        URL stockSearchUrl = NetworkUtils.buildUrlR(0, cat);

        //System.out.println(total);


        String stockSearchResults = null;
        try {
            boolean internet = NetworkUtils.hasInternetConnection(context);
            if(internet && total != 0){
                stockSearchResults = NetworkUtils.getResponseFromHttpUrl(stockSearchUrl, context);
                List<String[]> fullJsonStockData = OpenStockJsonUtils.getRecommendDataFromJson(stockSearchResults, 0);

                String parsedDataString = toDataString(fullJsonStockData);
                //Log.d("Reminder",parsedDataString);
                NotificationUtils.remindUser(context, parsedDataString);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void doQuery(Context context) {
        //PreferenceUtilities.incrementChargingReminderCount(context);
        //NotificationUtils.remindUser(context);
        StockDbFunction dbFunction = new StockDbFunction(context);

        Cursor mCursor = dbFunction.select();
        if(mCursor != null){
            int length = mCursor.getCount();
            long[] id_list = new long[length];
            long id = 0;

            if (length > 0){
                for (int i = 0; i < mCursor.getCount(); i++) {
                    mCursor.moveToPosition(i);
                    //id_list[i] = mCursor.getLong(mCursor.getColumnIndex(StocklistContract.StocklistEntry._ID));
                    id = mCursor.getLong(mCursor.getColumnIndex(StocklistContract.StocklistEntry._ID));
                    URL stockSearchUrlA = NetworkUtils.buildUrlA(String.valueOf(id));
                    URL stockSearchUrlI = NetworkUtils.buildUrlI(String.valueOf(id));
                    URL stockSearchUrlF = NetworkUtils.buildUrlF(String.valueOf(id));
                    URL stockSearchUrlT = NetworkUtils.buildUrlT(String.valueOf(id));

                    String[] arrayJSONstring =  new String[4];

                    try {
                        boolean internet = NetworkUtils.hasInternetConnection(context);
                        if(internet){
                            arrayJSONstring[0] = NetworkUtils.getResponseFromHttpUrl(stockSearchUrlA, context);
                            arrayJSONstring[1] = NetworkUtils.getResponseFromHttpUrl(stockSearchUrlI, context);
                            arrayJSONstring[2] = NetworkUtils.getResponseFromHttpUrl(stockSearchUrlF, context);
                            arrayJSONstring[3] = NetworkUtils.getResponseFromHttpUrl(stockSearchUrlT, context);

                            String[] fullJsonStockData = OpenStockJsonUtils.getFullStockDataFromArray(arrayJSONstring);
                            System.out.println(fullJsonStockData);
                            dbFunction.replaceByArray(fullJsonStockData);

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

    }

    private static String toDataString(List<String[]> parsedStockDataList){

        List<String> noti_buy = new ArrayList<String>();
        for (String[] parsedStockData : parsedStockDataList){
            String code = parsedStockData[0];
            noti_buy.add(String.valueOf(code));
        }
        String joined = TextUtils.join(", ", noti_buy);

        return joined;
    }

}

