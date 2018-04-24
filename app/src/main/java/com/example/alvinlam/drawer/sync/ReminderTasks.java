package com.example.alvinlam.drawer.sync;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.view.View;

import com.example.alvinlam.drawer.activity.AddCardAddActivity;
import com.example.alvinlam.drawer.activity.MainActivity;
import com.example.alvinlam.drawer.data.StockDbFunction;
import com.example.alvinlam.drawer.data.StocklistContract;
import com.example.alvinlam.drawer.utilities.NetworkUtils;
import com.example.alvinlam.drawer.utilities.NotificationUtils;
import com.example.alvinlam.drawer.utilities.OpenStockJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class ReminderTasks {

    public static final String ACTION_YES = "action-yes";
    //  COMPLETED (2) Add a public static constant called ACTION_NO
    public static final String ACTION_NO = "action-no";
    public static final String ACTION_QUERY = "query-stock";


    public static void executeTask(Context context, String action) {
        if (ACTION_YES.equals(action)) {
            doYes(context);
        } else if (ACTION_NO.equals(action)) {
            NotificationUtils.clearAllNotifications(context);
        } else if (ACTION_QUERY.equals(action)) {
            doQuery(context);
        }
        //      COMPLETED (3) If the user ignored the reminder, clear the notification
    }

    private static void doYes(Context context) {
        //PreferenceUtilities.doYes(context);
        //      COMPLETED (4) If the water count was incremented, clear any notifications
        NotificationUtils.clearAllNotifications(context);

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


}

