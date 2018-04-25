package com.example.alvinlam.drawer.sync;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

public class ReminderUtilities {

    /*
     * Interval at which to remind the user to drink water. Use TimeUnit for convenience, rather
     * than writing out a bunch of multiplication ourselves and risk making a silly mistake.
     */
    private static final int REMINDER_INTERVAL_HOURS = 12;
    private static final int REMINDER_MAX_MINUTES = 60;
    private static final int REMINDER_INTERVAL_MINUTES = 1;
    private static final int REMINDER_INTERVAL_SECONDS = (int) (TimeUnit.MINUTES.toSeconds(REMINDER_INTERVAL_MINUTES));
    private static final int SYNC_FLEXTIME_SECONDS = 0;

    private static final int RECOMMEND_INTERVAL_SECONDS = (int) (TimeUnit.MINUTES.toSeconds(REMINDER_INTERVAL_MINUTES*REMINDER_MAX_MINUTES*REMINDER_INTERVAL_HOURS));

    private static final String REMINDER_JOB_TAG = "stock_reminder_tag";
    private static final String RECOMMEND_JOB_TAG = "stock_recommend_tag";

    private static boolean sInitialized;
    private static boolean sDailyInitialized;


    synchronized public static void scheduleQueryReminder(@NonNull final Context context) {
        Log.d("reminder", "scheduleQueryReminder: ");
        // COMPLETED (17) If the job has already been initialized, return
        if (sInitialized) return;

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job constraintReminderJob = dispatcher.newJobBuilder()
                .setService(ReminderFirebaseJobService.class)
                .setTag(REMINDER_JOB_TAG)
                //.setConstraints(Constraint.DEVICE_CHARGING)
                //.setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        REMINDER_INTERVAL_SECONDS,
                        REMINDER_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(constraintReminderJob);

        sInitialized = true;
    }

    synchronized public static void scheduleDailyQueryReminder(@NonNull final Context context) {
        Log.d("reminder", "scheduleDailyQueryReminder: ");
        // COMPLETED (17) If the job has already been initialized, return
        if (sDailyInitialized) return;

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job constraintReminderJob = dispatcher.newJobBuilder()
                .setService(DailyReminderFirebaseJobService.class)
                .setTag(RECOMMEND_JOB_TAG)
                //.setConstraints(Constraint.DEVICE_CHARGING)
                //.setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        RECOMMEND_INTERVAL_SECONDS,
                        RECOMMEND_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(constraintReminderJob);

        sDailyInitialized = true;
    }

}