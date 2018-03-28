package com.example.alvinlam.drawer.activity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.alvinlam.drawer.R;
import com.example.alvinlam.drawer.data.StockDbFunction;
import com.example.alvinlam.drawer.data.StocklistDbHelper;
import com.example.alvinlam.drawer.utilities.NetworkUtils;
import com.example.alvinlam.drawer.utilities.OpenStockJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;


public class StockAlertAddActivity extends AppCompatActivity {

    private EditText mCodeEditText;

    private SQLiteDatabase mDb;
    private final static String LOG_TAG = StockAlertAddActivity.class.getSimpleName();
    private StockDbFunction dbFunction;
    private ProgressBar mLoadingIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("stockalertadd", "onCreate: "+"2");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_alert_read_precontent);

        Toolbar toolbar = (Toolbar) findViewById(R.id.add_card_toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        StocklistDbHelper dbHelper = new StocklistDbHelper(this);
        dbFunction = new StockDbFunction(this);
        mDb = dbHelper.getWritableDatabase();

        mCodeEditText = (EditText) this.findViewById(R.id.add_code_editText);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_card_add_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int lid = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (lid == R.id.action_send) {
            addToCardlist();

            return true;
        }else if (lid == android.R.id.home) {
            Context context = this;
            Class destinationClass = MainActivity.class;
            Intent intentToStartActivity = new Intent(context, destinationClass);
            startActivity(intentToStartActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addToCardlist() {
        if (mCodeEditText.getText().length() == 0) {
            return;
        }

        try {
            int code = Integer.parseInt(mCodeEditText.getText().toString());
        } catch (NumberFormatException ex) {
            Log.e(LOG_TAG, "Failed to parse to number: " + ex.getMessage());
        }

        String stockQuery = mCodeEditText.getText().toString();
        URL stockSearchUrl = NetworkUtils.buildUrl(stockQuery);
        URL stockSearchUrlF = NetworkUtils.buildUrlF(stockQuery);
        URL stockSearchUrlT = NetworkUtils.buildUrlT(stockQuery);

        boolean internet = NetworkUtils.hasInternetConnection(this);
        if(internet) {
            new StockQueryTask(StockAlertAddActivity.this).execute(stockSearchUrl, stockSearchUrlF, stockSearchUrlT);
        }else{
            //no internet toast
            Toast.makeText(StockAlertAddActivity.this,"No internet",Toast.LENGTH_LONG).show();
        }

    }

    public Double checkDouble(String value){
        if (value.equals("null"))
            return 0.0;
        else
            return Double.parseDouble(value);
    }

    public class StockQueryTask extends AsyncTask<URL, Void, String[]> {

        Context context;
        private StockQueryTask(Context context) {
            this.context = context.getApplicationContext();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(URL... params) {
            boolean internet = NetworkUtils.hasInternetConnection(context);
            if(internet) {
                String[] arrayJSONstring = new String[params.length];
                int i = 0;
                for (URL searchUrl : params) {
                    String stockSearchResults = null;
                    try {
                        stockSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl, context);
                        arrayJSONstring[i] = stockSearchResults;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    i++;
                }

                String[] fullJsonStockData = new String[1];
                try {
                    fullJsonStockData = OpenStockJsonUtils.getFullStockDataFromArray(arrayJSONstring);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return fullJsonStockData;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] parsedStockData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);

            long id;
            String name;
            int code = 0;
            String date;
            double price, netChange, pe, high, low, preClose, volume, turnover, lot,
                    dy, dps, eps, sma20, std20, std20l, std20h, sma50, std50, std50l, std50h,
                    sma100, std100, std100l, std100h, sma250, std250, std250l, std250h,
                    l20, h20, l50, h50, l100, h100, l250, h250;

            id = Long.parseLong(parsedStockData[1]);
            name = parsedStockData[0];
            code = Integer.parseInt(parsedStockData[1]);
            date = parsedStockData[2];

            price = checkDouble(parsedStockData[3]);
            netChange =  checkDouble(parsedStockData[4]);
            pe =  checkDouble(parsedStockData[5]);
            high =  checkDouble(parsedStockData[6]);
            low =  checkDouble(parsedStockData[7]);
            preClose =  checkDouble(parsedStockData[8]);
            volume =  checkDouble(parsedStockData[9]);
            turnover =  checkDouble(parsedStockData[10]);
            lot =  checkDouble(parsedStockData[11]);
            dy = checkDouble(parsedStockData[12]);
            dps =  checkDouble(parsedStockData[13]);
            eps =  checkDouble(parsedStockData[14]);
            sma20 =  checkDouble(parsedStockData[15]);
            std20 =  checkDouble(parsedStockData[16]);
            std20l =  checkDouble(parsedStockData[17]);
            std20h =  checkDouble(parsedStockData[18]);
            sma50 =  checkDouble(parsedStockData[19]);
            std50 =  checkDouble(parsedStockData[20]);
            std50l =  checkDouble(parsedStockData[21]);
            std50h =  checkDouble(parsedStockData[22]);
            sma100 =  checkDouble(parsedStockData[23]);
            std100 =  checkDouble(parsedStockData[24]);
            std100l =  checkDouble(parsedStockData[25]);
            std100h =  checkDouble(parsedStockData[26]);
            sma250 =  checkDouble(parsedStockData[27]);
            std250 =  checkDouble(parsedStockData[28]);
            std250l =  checkDouble(parsedStockData[29]);
            std250h =  checkDouble(parsedStockData[30]);
            l20 =  checkDouble(parsedStockData[31]);
            h20 =  checkDouble(parsedStockData[32]);
            l50 =  checkDouble(parsedStockData[33]);
            h50 =  checkDouble(parsedStockData[34]);
            l100 =  checkDouble(parsedStockData[35]);
            h100 =  checkDouble(parsedStockData[36]);
            l250 =  checkDouble(parsedStockData[37]);
            h250 =  checkDouble(parsedStockData[38]);



            // Add guest info to mDb
            dbFunction.replace(
                    id, name, code, date,
                    price, netChange, pe, high, low, preClose, volume, turnover, lot,
                    dy, dps, eps, sma20, std20, std20l, std20h, sma50, std50, std50l, std50h,
                    sma100, std100, std100l, std100h, sma250, std250, std250l, std250h,
                    l20, h20, l50, h50, l100, h100, l250, h250
            );

            //activity.startActivity(new Intent(activity, BuiltInCamera.class));

            //Context context = this;
            Class destinationClass = MainActivity.class;
            Intent intentToStartActivity = new Intent(context, destinationClass);
            context.startActivity(intentToStartActivity);

        }
    }






}
