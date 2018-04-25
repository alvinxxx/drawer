package com.example.alvinlam.drawer.utilities;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.alvinlam.drawer.activity.MainActivity;
import com.example.alvinlam.drawer.R;
import com.example.alvinlam.drawer.sync.ReminderIntentService;
import com.example.alvinlam.drawer.sync.ReminderTasks;

import java.util.List;

/**
 * Utility class for creating hydration notifications
 */
public class NotificationUtils {

    /*
     * This notification ID can be used to access our notification after we've displayed it. This
     * can be handy when we need to cancel the notification, or perhaps update it. This number is
     * arbitrary and can be set to whatever you like. 1138 is in no way significant.
     */
    private static final int REMINDER_NOTIFICATION_ID = 1138;
    private static final int REMINDER_NOTIFICATION_ID_BUY = 1139;
    private static final int REMINDER_NOTIFICATION_ID_SELL = 1140;

    /**
     * This pending intent id is used to uniquely reference the pending intent
     */
    private static final int REMINDER_PENDING_INTENT_ID = 3417;
    private static final int ACTION_YES_PENDING_INTENT_ID = 1;
    private static final int ACTION_NO_PENDING_INTENT_ID = 14;

    public static void clearAllNotifications(Context context) {
        Log.d("noti", "clearAllNotifications: ");
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void remindUserBuy(Context context, String codeString) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_icon_money)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.stock_notification_title_buy))
                .setContentText(context.getString(R.string.stock_notification_body_buy)+codeString)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(context.getString(R.string.stock_notification_body_buy)+codeString))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .addAction(noAction(context))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
        }

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(REMINDER_NOTIFICATION_ID_BUY, notificationBuilder.build());
    }

    public static void remindUserSell(Context context, String codeString) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_icon_money)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.stock_notification_title_sell))
                .setContentText(context.getString(R.string.stock_notification_body_sell)+codeString)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(context.getString(R.string.stock_notification_body_sell)+codeString))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .addAction(noAction(context))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
        }

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(REMINDER_NOTIFICATION_ID_SELL, notificationBuilder.build());
    }

    public static void remindUser(Context context, String codeString) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_icon_money)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.stock_notification_title))
                .setContentText(context.getString(R.string.stock_notification_body)+codeString)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.stock_notification_body)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                // COMPLETED (17) Add the two new actions using the addAction method and your helper methods
                //.addAction(yesAction(context))
                .addAction(noAction(context))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
        }

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(REMINDER_NOTIFICATION_ID, notificationBuilder.build());
    }

    private static NotificationCompat.Action noAction(Context context) {
        Intent ignoreReminderIntent = new Intent(context, ReminderIntentService.class);
        ignoreReminderIntent.setAction(ReminderTasks.ACTION_NO);
        PendingIntent ignoreReminderPendingIntent = PendingIntent.getService(
                context,
                ACTION_NO_PENDING_INTENT_ID,
                ignoreReminderIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action ignoreReminderAction = new NotificationCompat.Action(R.drawable.ic_icon_cancel,
                "Got it.",
                ignoreReminderPendingIntent);
        return ignoreReminderAction;
    }

    private static NotificationCompat.Action yesAction(Context context) {
        Intent yesIntent = new Intent(context, ReminderIntentService.class);
        yesIntent.setAction(ReminderTasks.ACTION_YES);
        PendingIntent incrementWaterPendingIntent = PendingIntent.getService(
                context,
                ACTION_YES_PENDING_INTENT_ID,
                yesIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Action yesAction = new NotificationCompat.Action(R.drawable.ic_icon_money,
                "Add the stock!",
                incrementWaterPendingIntent);
        return yesAction;
    }


    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(
                context,
                REMINDER_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }


    // COMPLETED (4) Create a helper method called largeIcon which takes in a Context as a parameter and
    // returns a Bitmap. This method is necessary to decode a bitmap needed for the notification.
    private static Bitmap largeIcon(Context context) {
        // COMPLETED (5) Get a Resources object from the context.
        Resources res = context.getResources();
        // COMPLETED (6) Create and return a bitmap using BitmapFactory.decodeResource, passing in the
        // resources object and R.drawable.ic_local_drink_black_24px
        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.drawable.ic_icon_money);
        return largeIcon;
    }
}

