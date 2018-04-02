package com.example.alvinlam.drawer.sync;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.example.alvinlam.drawer.data.StockAlertDbFunction;
import com.example.alvinlam.drawer.data.StockDbFunction;
import com.example.alvinlam.drawer.data.StocklistContract;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.RetryStrategy;

// COMPLETED (3) ReminderFirebaseJobService should extend from JobService
public class ReminderFirebaseJobService extends JobService {

    private AsyncTask mBackgroundTask;
    private StockDbFunction dbFunction;
    private StockAlertDbFunction dbAFunction;
    private Cursor cursor;
    private Cursor cursorAlert;

    private long id = 0;
    private int code, active, buy;
    private String name, current, currentResult, condition, target, window, windowResult, distance, distanceResult, finalResult;


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
                Context context = ReminderFirebaseJobService.this;
                ReminderTasks.executeTask(context, ReminderTasks.ACTION_QUERY);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                // COMPLETED (8) Override onPostExecute and called jobFinished. Pass the job parameters
                // and false to jobFinished. This will inform the JobManager that your job is done
                // and that you do not want to reschedule the job.

                /*
                 * Once the AsyncTask is finished, the job is finished. To inform JobManager that
                 * you're done, you call jobFinished with the jobParamters that were passed to your
                 * job and a boolean representing whether the job needs to be rescheduled. This is
                 * usually if something didn't work and you want the job to try running again.
                 */

                jobFinished(jobParameters, false);

                //TODO job finish update stock info, then check if the condition is met
                // get all alert records, get the related stock data, compare, put into array_buy and array_sell
                //if array length > 0, send respective notification


                dbFunction = new StockDbFunction(getApplicationContext());
                dbAFunction = new StockAlertDbFunction(getApplicationContext());

                cursorAlert = dbAFunction.select();

                code = cursorAlert.getInt(cursorAlert.getColumnIndex(StocklistContract.StockAlertEntry.COLUMN_CODE));
                //name = cursorAlert.getString(cursorAlert.getColumnIndex(StocklistContract.StockAlertEntry.COLUMN_NAME));
                active = cursorAlert.getInt(cursorAlert.getColumnIndex(StocklistContract.StockAlertEntry.COLUMN_ACTIVE));
                buy = cursorAlert.getInt(cursorAlert.getColumnIndex(StocklistContract.StockAlertEntry.COLUMN_BUY));
                current = cursorAlert.getString(cursorAlert.getColumnIndex(StocklistContract.StockAlertEntry.COLUMN_INDICATOR));
                condition = cursorAlert.getString(cursorAlert.getColumnIndex(StocklistContract.StockAlertEntry.COLUMN_CONDITION));
                target = cursorAlert.getString(cursorAlert.getColumnIndex(StocklistContract.StockAlertEntry.COLUMN_TARGET));
                window = cursorAlert.getString(cursorAlert.getColumnIndex(StocklistContract.StockAlertEntry.COLUMN_WINDOW));
                distance = cursorAlert.getString(cursorAlert.getColumnIndex(StocklistContract.StockAlertEntry.COLUMN_DISTANCE));



            }
        };

        // COMPLETED (9) Execute the AsyncTask
        mBackgroundTask.execute();
        // COMPLETED (10) Return true
        return true;
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