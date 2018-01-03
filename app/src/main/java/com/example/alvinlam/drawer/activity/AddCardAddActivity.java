package com.example.alvinlam.drawer.activity;

import android.content.ContentValues;
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
import com.example.alvinlam.drawer.data.StocklistContract;
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
        setContentView(R.layout.add_card_precontent);

        Toolbar toolbar = (Toolbar) findViewById(R.id.add_card_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        StocklistDbHelper dbHelper = new StocklistDbHelper(this);
        dbFunction = new StockDbFunction(this);
        mDb = dbHelper.getWritableDatabase();

        mCodeEditText = (EditText) this.findViewById(R.id.add_code_editText);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                id = intentThatStartedThisActivity.getLongExtra(Intent.EXTRA_TEXT, 0);

                cursor = dbFunction.selectByID(id);

                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();

                    //set edit to 1 as True
                    edit = 1;

                    //Log.i(AddCardAddActivity.class.getName(), String.valueOf(cursor.getColumnIndex("name")));

                    int code = cursor.getInt(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_CODE));
                    mCodeEditText.setText(Integer.toString(code));
                }

            }
        }
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

    private void makeStockSearchQuery() {
        String stockQuery = mCodeEditText.getText().toString();
        URL stockSearchUrl = NetworkUtils.buildUrl(stockQuery);
        String stockSearchResults = null;
        new StockQueryTask().execute(stockSearchUrl);

    }

    public class StockQueryTask extends AsyncTask<URL, Void, String[]> {

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
            int code;
            long date;
            double price;
            double netchange;
            double pe;
            double high;
            double low;
            double perclose;
            double volume;
            double turnover;
            double lot;

            name = parsedStockData[0];
            code = Integer.getInteger(parsedStockData[1]);
            date = Long.parseLong(parsedStockData[2]);
            price = Double.parseDouble(parsedStockData[3]);
            netchange = Double.parseDouble(parsedStockData[4]);
            pe = Double.parseDouble(parsedStockData[5]);
            high = Double.parseDouble(parsedStockData[6]);
            low = Double.parseDouble(parsedStockData[7]);
            perclose = Double.parseDouble(parsedStockData[8]);
            volume = Double.parseDouble(parsedStockData[9]);
            turnover = Double.parseDouble(parsedStockData[10]);
            lot = Double.parseDouble(parsedStockData[11]);

            // Add guest info to mDb
            dbFunction.insert(
                    name,
                    code,
                    date,
                    price,
                    netchange,
                    pe,
                    high,
                    low,
                    perclose,
                    volume,
                    turnover,
                    lot
            );
        }
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

        // call insert to run an insert query on TABLE_NAME with the ContentValues created
        if (edit == 1) {
        /*
            dbFunction.update(id,
                    mNameEditText.getText().toString(),
                    phone,
                    mEmailEditText.getText().toString(),
                    mTitleEditText.getText().toString(),
                    mWebsiteEditText.getText().toString(),
                    mCompanyEditText.getText().toString(),
                    cphone,
                    mCAddressEditText.getText().toString()
            );
        */
        }else
            makeStockSearchQuery();


        Context context = this;
        Class destinationClass = MainActivity.class;
        Intent intentToStartActivity = new Intent(context, destinationClass);
        startActivity(intentToStartActivity);
    }




}
