package com.example.alvinlam.drawer.sync;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.example.alvinlam.drawer.R;
import com.example.alvinlam.drawer.data.StockAlertDbFunction;
import com.example.alvinlam.drawer.data.StockDbFunction;
import com.example.alvinlam.drawer.data.StocklistContract;
import com.example.alvinlam.drawer.utilities.NetworkUtils;
import com.example.alvinlam.drawer.utilities.NotificationUtils;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.RetryStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

// COMPLETED (3) ReminderFirebaseJobService should extend from JobService
public class DailyReminderFirebaseJobService extends JobService {

    private AsyncTask mBackgroundTask;
    private StockDbFunction dbFunction;
    private StockAlertDbFunction dbAFunction;
    private Cursor cursor;
    private Cursor cursorAlert;

    private long id = 0;
    private int code, active, buy;
    private String name, current, condition, target, window, distance;
    private Double currentResult, windowResult, distanceResult, finalResult;

    // COMPLETED (4) Override onStartJob
    /**
     * The entry point to your Job. Implementations should offload work to another thread of
     * execution as soon as possible.
     *
     * This is called by the Job Dispatcher to tell us we should start our job. Keep in mind this
     * method is run on the application's main thread, so we need to offload work to a background
     * thread.
     *
     * @return whether there is more work remaining.
     */
    @Override
    public boolean onStartJob(final JobParameters jobParameters) {

        // COMPLETED (5) By default, jobs are executed on the main thread, so make an anonymous class extending
        //  AsyncTask called mBackgroundTask.
        // Here's where we make an AsyncTask so that this is no longer on the main thread
        mBackgroundTask = new AsyncTask() {

            // COMPLETED (6) Override doInBackground
            @Override
            protected Object doInBackground(Object[] params) {
                // COMPLETED (7) Use ReminderTasks to execute the new charging reminder task you made, use
                // this service as the context (ReminderFirebaseJobService.this) and return null
                // when finished.
                Context context = DailyReminderFirebaseJobService.this;
                //update db using replacebyarray
                ReminderTasks.executeTask(context, ReminderTasks.ACTION_DAILY);
                //System.out.println("Daily bg");
                return null;
            }


        };

        // COMPLETED (9) Execute the AsyncTask
        mBackgroundTask.execute();
        // COMPLETED (10) Return true
        return true;
    }

    static List<String> removeDuplicates(List<String> list) {

        // Store unique items in result.
        List<String> result = new ArrayList<>();

        // Record encountered Strings in HashSet.
        HashSet<String> set = new HashSet<>();

        // Loop over argument list.
        for (String item : list) {

            // If String is not in set, add it to the list and the set.
            if (!set.contains(item)) {
                result.add(item);
                set.add(item);
            }
        }
        return result;
    }


    // COMPLETED (11) Override onStopJob
    /**
     * Called when the scheduling engine has decided to interrupt the execution of a running job,
     * most likely because the runtime constraints associated with the job are no longer satisfied.
     *
     * @return whether the job should be retried
     * @see Job.Builder#setRetryStrategy(RetryStrategy)
     * @see RetryStrategy
     */
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        // COMPLETED (12) If mBackgroundTask is valid, cancel it
        // COMPLETED (13) Return true to signify the job should be retried
        if (mBackgroundTask != null) mBackgroundTask.cancel(true);
        return true;
    }
}