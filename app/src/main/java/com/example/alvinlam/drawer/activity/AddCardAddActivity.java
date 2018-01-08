package com.example.alvinlam.drawer.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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

import com.example.alvinlam.drawer.R;
import com.example.alvinlam.drawer.data.StocklistDbHelper;
import com.example.alvinlam.drawer.data.StockDbFunction;
import com.example.alvinlam.drawer.utilities.NetworkUtils;
import com.example.alvinlam.drawer.utilities.OpenStockJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;


public class AddCardAddActivity extends AppCompatActivity {

    private EditText mCodeEditText;

    private SQLiteDatabase mDb;
    private final static String LOG_TAG = MainActivity.class.getSimpleName();
    private long id = 0;
    private int edit = 0;
    private int code=0;
    private Cursor cursor;
    private StockDbFunction dbFunction;
    private ProgressBar mLoadingIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("addcardadd", "onCreate: "+"2");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_card_add_precontent);

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
            code = Integer.parseInt(mCodeEditText.getText().toString());
        } catch (NumberFormatException ex) {
            Log.e(LOG_TAG, "Failed to parse to number: " + ex.getMessage());
        }

        String stockQuery = mCodeEditText.getText().toString();
        URL stockSearchUrl = NetworkUtils.buildUrl(stockQuery);
        new StockQueryTask(AddCardAddActivity.this).execute(stockSearchUrl);


    }

    public Double checkDouble(String value){
        if (value == "null")
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
            URL searchUrl = params[0];
            String stockSearchResults = null;
            try {
                stockSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
                String[] fullJsonStockData = OpenStockJsonUtils.getFullStockDataFromJson(AddCardAddActivity.this, stockSearchResults);

                return fullJsonStockData;

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] parsedStockData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);

            String name;
            int code = 0;
            String date;
            double price = 0;
            double netChange = 0;
            double pe = 0;
            double high = 0;
            double low = 0;
            double preClose = 0;
            double volume = 0;
            double turnover = 0;
            double lot = 0;

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

            // Add guest info to mDb
            dbFunction.replace(
                    name,
                    code,
                    date,
                    price,
                    netChange,
                    pe,
                    high,
                    low,
                    preClose,
                    volume,
                    turnover,
                    lot
            );

            //activity.startActivity(new Intent(activity, BuiltInCamera.class));

            //Context context = this;
            Class destinationClass = MainActivity.class;
            Intent intentToStartActivity = new Intent(context, destinationClass);
            context.startActivity(intentToStartActivity);

        }
    }






}
