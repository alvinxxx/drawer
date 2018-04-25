package com.example.alvinlam.drawer.sync;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.example.alvinlam.drawer.R;
import com.example.alvinlam.drawer.activity.MainActivity;
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
public class ReminderFirebaseJobService extends JobService {

    private AsyncTask mBackgroundTask;
    private StockDbFunction dbFunction;
    private StockAlertDbFunction dbAFunction;
    private Cursor cursor;
    private Cursor cursorAlert;

    private long id = 0;
    private int code, active, buy ;
    private String name, current, condition, target, window, distance;
    private Double currentResult, windowResult, distanceResult, finalResult, value;
    MainActivity activity;


    /*
    public void setContext(MainActivity activity){
        this.activity = activity;
    }
    */

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
                //update db using replacebyarray
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

                // job finish update stock info, then check if the condition is met
                // get all alert records, get the related stock data, compare, put into array_buy and array_sell
                //if array length > 0, send respective notification

                boolean internet = NetworkUtils.hasInternetConnection(getApplicationContext());
                if (internet) {

                    dbFunction = new StockDbFunction(getApplicationContext());
                    dbAFunction = new StockAlertDbFunction(getApplicationContext());

                    cursorAlert = dbAFunction.select();
                    if(cursorAlert != null){
                        int length = cursorAlert.getCount();
                        List<String> noti_buy = new ArrayList<String>();
                        List<String> noti_sell = new ArrayList<String>();

                        if (length > 0) {
                            for (int i = 0; i < length; i++) {
                                cursorAlert.moveToPosition(i);
                                active = cursorAlert.getInt(cursorAlert.getColumnIndex(StocklistContract.StockAlertEntry.COLUMN_ACTIVE));

                                if (active == 1) {
                                    //name = cursorAlert.getString(cursorAlert.getColumnIndex(StocklistContract.StockAlertEntry.COLUMN_NAME));

                                    //get the latest stock data
                                    code = cursorAlert.getInt(cursorAlert.getColumnIndex(StocklistContract.StockAlertEntry.COLUMN_CODE));
                                    cursor = dbFunction.selectByID((long) code); //use code to get its data in stocklist table

                                    //get the alert user input subject
                                    current = cursorAlert.getString(cursorAlert.getColumnIndex(StocklistContract.StockAlertEntry.COLUMN_INDICATOR));
                                    //get the stock real subject data
                                    String[] array_current = getResources().getStringArray(R.array.array_indicator);
                                    if (current.equals(array_current[0])) {
                                        currentResult = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_PRICE));
                                    } else if (current.equals(array_current[1])) {
                                        currentResult = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_DY));
                                    } else if (current.equals(array_current[2])) {
                                        currentResult = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_PE));
                                    }

                                    //get the alert user input object
                                    target = cursorAlert.getString(cursorAlert.getColumnIndex(StocklistContract.StockAlertEntry.COLUMN_TARGET));
                                    String[] array_target = {"SMA", "Low", "High"};
                                    window = cursorAlert.getString(cursorAlert.getColumnIndex(StocklistContract.StockAlertEntry.COLUMN_WINDOW));
                                    String[] array_window = getResources().getStringArray(R.array.array_window);

                                    //get the stock real object data: SMA/Low/High
                                    if (target.equals(array_target[0])) {         //SMA
                                        if (window.equals(array_window[0])) {         //20
                                            windowResult = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_SMA20));
                                        } else if (window.equals(array_window[1])) {  //50
                                            windowResult = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_SMA50));
                                        } else if (window.equals(array_window[2])) {  //100
                                            windowResult = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_SMA100));
                                        } else if (window.equals(array_window[3])) {  //250
                                            windowResult = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_SMA250));
                                        }
                                    } else if (target.equals(array_target[1])) { //Low
                                        if (window.equals(array_window[0])) {         //20
                                            windowResult = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_20L));
                                        } else if (window.equals(array_window[1])) {  //50
                                            windowResult = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_50L));
                                        } else if (window.equals(array_window[2])) {  //100
                                            windowResult = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_100L));
                                        } else if (window.equals(array_window[3])) {  //250
                                            windowResult = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_250L));
                                        }
                                    } else if (target.equals(array_target[2])) { //High
                                        if (window.equals(array_window[0])) {         //20
                                            windowResult = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_20H));
                                        } else if (window.equals(array_window[1])) {  //50
                                            windowResult = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_50H));
                                        } else if (window.equals(array_window[2])) {  //100
                                            windowResult = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_100H));
                                        } else if (window.equals(array_window[3])) {  //250
                                            windowResult = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_250H));
                                        }
                                    }else{
                                        windowResult = Double.parseDouble(target);
                                    }

                                    if (target.equals(array_target[0])) {         //SMA
                                        //get the stock real object data: distance for SMA
                                        distance = cursorAlert.getString(cursorAlert.getColumnIndex(StocklistContract.StockAlertEntry.COLUMN_DISTANCE));

                                        //catch empty
                                        if(!TextUtils.isEmpty(distance)){
                                            //catch double
                                            try{
                                                value = Double.parseDouble(distance.split("\\*")[0]);
                                                String type = distance.split("\\*")[1];

                                                if (window.equals(array_window[0])) {   //20
                                                    if (type.equals("STD")) {
                                                        distanceResult = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD20));
                                                    } else if (type.equals("STD_L")) {
                                                        distanceResult = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD20L));
                                                    } else if (type.equals("STD_H")) {
                                                        distanceResult = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD20H));
                                                    }
                                                } else if (window.equals(array_window[1])) {
                                                    if (type.equals("STD")) {
                                                        distanceResult = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD50));
                                                    } else if (type.equals("STD_L")) {
                                                        distanceResult = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD50L));
                                                    } else if (type.equals("STD_H")) {
                                                        distanceResult = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD50H));
                                                    }
                                                } else if (window.equals(array_window[2])) {
                                                    if (type.equals("STD")) {
                                                        distanceResult = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD100));
                                                    } else if (type.equals("STD_L")) {
                                                        distanceResult = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD100L));
                                                    } else if (type.equals("STD_H")) {
                                                        distanceResult = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD100H));
                                                    }
                                                } else if (window.equals(array_window[3])) {
                                                    if (type.equals("STD")) {
                                                        distanceResult = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD250));
                                                    } else if (type.equals("STD_L")) {
                                                        distanceResult = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD250L));
                                                    } else if (type.equals("STD_H")) {
                                                        distanceResult = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD250H));
                                                    }
                                                }
                                            }catch (Exception e){
                                                value = 0.0;
                                                distanceResult = 0.0;
                                            }

                                        }else{
                                            value = 0.0;
                                            distanceResult = 0.0;
                                        }

                                    }else{
                                        value = 0.0;
                                        distanceResult = 0.0;
                                    }


                                    finalResult = windowResult + value * distanceResult;


                                    condition = cursorAlert.getString(cursorAlert.getColumnIndex(StocklistContract.StockAlertEntry.COLUMN_CONDITION));
                                    String[] array_condition = getResources().getStringArray(R.array.array_condition);

                                    //put into buy array
                                    buy = cursorAlert.getInt(cursorAlert.getColumnIndex(StocklistContract.StockAlertEntry.COLUMN_BUY));


                                    if (condition.equals(array_condition[0])) {         //less
                                        if (currentResult < finalResult) {
                                            if (buy == 1) {
                                                noti_buy.add(String.valueOf(code));
                                            } else if (buy == 0) {
                                                noti_sell.add(String.valueOf(code));
                                            }
                                        }
                                    } else if (condition.equals(array_condition[1])) {  //great
                                        if (currentResult > finalResult) {
                                            if (buy == 1) {
                                                noti_buy.add(String.valueOf(code));
                                            } else if (buy == 0) {
                                                noti_sell.add(String.valueOf(code));
                                            }
                                        }
                                    } else if (condition.equals(array_condition[2])) {  //equal
                                        if (currentResult == finalResult) {
                                            if (buy == 1) {
                                                noti_buy.add(String.valueOf(code));
                                            } else if (buy == 0) {
                                                noti_sell.add(String.valueOf(code));
                                            }
                                        }
                                    }

                                }

                            }
                        }
                        //update UI
                        //activity.updateView();


                        //send notification
                        if (noti_buy.size() > 0) {
                            noti_buy = removeDuplicates(noti_buy);
                            String joined = TextUtils.join(", ", noti_buy);

                            NotificationUtils.remindUserBuy(getApplicationContext(), joined);
                        }
                        if (noti_sell.size() > 0) {
                            noti_sell = removeDuplicates(noti_sell);
                            String joined = TextUtils.join(", ", noti_sell);

                            NotificationUtils.remindUserSell(getApplicationContext(), joined);
                        }
                    }



                    if (cursor != null) {
                        cursor.close();
                        cursorAlert.close();
                    }
                }
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