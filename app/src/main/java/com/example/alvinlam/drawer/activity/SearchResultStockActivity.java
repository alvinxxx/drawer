package com.example.alvinlam.drawer.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alvinlam.drawer.R;
import com.example.alvinlam.drawer.adapter.StockDetailAdapter;
import com.example.alvinlam.drawer.data.StockAlertDbFunction;
import com.example.alvinlam.drawer.data.StockDbFunction;
import com.example.alvinlam.drawer.data.StocklistContract;

import java.util.Locale;


public class SearchResultStockActivity extends AppCompatActivity{

    private static final String SHARE_HASHTAG = " #PocketCard";

    private Cursor cursor;
    private StockDbFunction dbFunction;
    private StockAlertDbFunction dbAFunction;

    private long id = 0;
    private String name, date, uptime, name_chi, industry;
    private int code;
    private double price, netChange, pe, high, low, volume,
            dy, dps, eps, sma20, std20, std20l, std20h, sma50, std50, std50l, std50h,
            sma100, std100, std100l, std100h, sma250, std250, std250l, std250h,
            l20, h20, l50, h50, l100, h100, l250, h250;

    MenuItem mAdd,mDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_detail_precontent);

        Toolbar toolbar = (Toolbar) findViewById(R.id.add_card_toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                String[] parsedStockData = intentThatStartedThisActivity.getStringArrayExtra(Intent.EXTRA_TEXT);

                id = Long.parseLong(parsedStockData[1]);
                name = parsedStockData[0];
                code = Integer.parseInt(parsedStockData[1]);
                date = parsedStockData[2];

                price = checkDouble(parsedStockData[3]);
                netChange =  checkDouble(parsedStockData[4]);
                pe =  checkDouble(parsedStockData[5]);
                high =  checkDouble(parsedStockData[6]);
                low =  checkDouble(parsedStockData[7]);
                volume =  checkDouble(parsedStockData[9]);
                dy =  checkDouble(parsedStockData[12]);
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
                uptime = parsedStockData[8];
                name_chi = parsedStockData[10];
                industry = parsedStockData[11];

            }
        }


        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        StockDetailAdapter adapter = new StockDetailAdapter(this, getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Find the tab layout that shows the tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        // Connect the tab layout with the view pager. This will
        //   1. Update the tab layout when the view pager is swiped
        //   2. Update the view pager when a tab is selected
        //   3. Set the tab layout's tab names with the view pager's adapter's titles
        //      by calling onPageTitle()
        tabLayout.setupWithViewPager(viewPager);
    }

    private Double checkDouble(String value) {
        if (value.equals("null"))
            return 0.0;
        else
            return Double.parseDouble(value);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_card_toolbar_menu, menu);
        mAdd= menu.findItem(R.id.action_add);
        mDelete = menu.findItem(R.id.action_delete);
        mDelete.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int lid = item.getItemId();
        Context context = this;
        Class destinationClass = AddCardActivity.class;
        dbFunction = new StockDbFunction(context);
        dbAFunction = new StockAlertDbFunction(context);

        //noinspection SimplifiableIfStatement
        if (lid == R.id.action_add) {

            // Add guest info to mDb
            dbFunction.replace(
                    id, name, code, date,
                    price, netChange, pe, high, low, volume,
                    dy, dps, eps, sma20, std20, std20l, std20h, sma50, std50, std50l, std50h,
                    sma100, std100, std100l, std100h, sma250, std250, std250l, std250h,
                    l20, h20, l50, h50, l100, h100, l250, h250,
                    uptime, name_chi, industry
            );
            //add default alert for each stock
            dbAFunction.insert(name, code, 1);
            dbAFunction.insert(name, code, 0);

            Toast.makeText(SearchResultStockActivity.this,"Stock Added",Toast.LENGTH_LONG).show();
            mAdd.setVisible(false);
            mDelete.setVisible(true);

            return true;

        }else if (lid == R.id.action_delete) {
            dbFunction.delete(id);
            Toast.makeText(SearchResultStockActivity.this,"Stock Deleted",Toast.LENGTH_LONG).show();
            mAdd.setVisible(true);
            mDelete.setVisible(false);
            return true;

        }else if (lid == android.R.id.home) {
            destinationClass = MainActivity.class;
            Intent intentToStartActivity = new Intent(context, destinationClass);
            startActivity(intentToStartActivity);
            return true;
        }else if (lid == R.id.action_share) {
            Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                    .setType("text/plain")
                    .setText("Name:  "+ name + "\n" + "Code:  "+ code + "\n" + "Date:  "+ date + "\n" + "Price:  "+ price + "\n" +
                            "Net change:  "+ netChange + "\n" + "PE:  "+ pe + "\n" + "High:  "+ high + "\n" + "Low:  "+low + "\n" +
                            "Volume:  "+ volume + "\n" +
                            SHARE_HASHTAG)
                    .getIntent();
            startActivity(shareIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
