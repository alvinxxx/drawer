package com.example.alvinlam.drawer.sync;

import android.content.Context;

import com.example.alvinlam.drawer.utilities.NotificationUtils;

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

    }
}

