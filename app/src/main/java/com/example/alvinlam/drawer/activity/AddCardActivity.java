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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alvinlam.drawer.R;
import com.example.alvinlam.drawer.adapter.StockDetailAdapter;
import com.example.alvinlam.drawer.data.StockAlertDbFunction;
import com.example.alvinlam.drawer.data.StockDbFunction;
import com.example.alvinlam.drawer.data.StocklistContract;
import com.example.alvinlam.drawer.fragment.StockDetailFragment;

import java.util.Locale;

import static android.content.ContentValues.TAG;


public class AddCardActivity extends AppCompatActivity{
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
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        dbFunction = new StockDbFunction(getApplicationContext());
        dbAFunction = new StockAlertDbFunction(getApplicationContext());
        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_UID)) {
                id = intentThatStartedThisActivity.getLongExtra(Intent.EXTRA_UID, 0);

                cursor = dbFunction.selectByID(id);
                if (cursor != null) {
                    cursor.moveToFirst();

                    Log.i(AddCardActivity.class.getName(), String.valueOf(cursor.getColumnIndex("name")));

                    name = cursor.getString(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_NAME));
                    name_chi = cursor.getString(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_NAME_CHI));
                    String wholeName = name+"/"+name_chi;
                    toolbar.setTitle(wholeName);

                    code = cursor.getInt(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_CODE));
                    date = cursor.getString(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_DATE));
                    price = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_PRICE));
                    netChange = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_NET_CHANGE));
                    pe = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_PE));
                    high = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_HIGH));
                    low = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_LOW));
                    volume = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_VOLUME));
                    dy = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_DY));
                    dps = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_DPS));
                    eps = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_EPS));
                    sma20 = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_SMA20));
                    std20 = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD20));
                    std20l = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD20L));
                    std20h = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD20H));
                    sma50 = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_SMA50));
                    std50 = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD50));
                    std50l = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD50L));
                    std50h = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD50H));
                    sma100 = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_SMA100));
                    std100 = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD100));
                    std100l = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD100L));
                    std100h = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD100H));
                    sma250 = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_SMA250));
                    std250 = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD250));
                    std250l = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD250L));
                    std250h = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD250H));
                    l20 = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_20L));
                    h20 = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_20H));
                    l50 = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_50L));
                    h50 = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_50H));
                    l100 = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_100L));
                    h100 = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_100H));
                    l250 = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_250L));
                    h250 = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_250H));
                    uptime = cursor.getString(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_UPTIME));
                    name_chi = cursor.getString(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_NAME_CHI));
                    industry = cursor.getString(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_INDUSTRY));

                    cursor.close();
                }
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_card_toolbar_menu, menu);
        mAdd= menu.findItem(R.id.action_add);
        mDelete = menu.findItem(R.id.action_delete);
        mAdd.setVisible(false);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int lid = item.getItemId();
        Context context = this;
        Class destinationClass = SearchResultStockActivity.class;

        //noinspection SimplifiableIfStatement
        if (lid == R.id.action_add) {

            dbFunction = new StockDbFunction(context);
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

            Toast.makeText(context,"Stock Added",Toast.LENGTH_LONG).show();
            mAdd.setVisible(false);
            mDelete.setVisible(true);

            return true;

        }else if (lid == R.id.action_delete) {
            dbFunction.delete(id);
            dbAFunction.deleteByCode(code);
            Toast.makeText(context,"Stock Deleted",Toast.LENGTH_LONG).show();
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
                            "Volume:  "+ volume + "\n"  +
                            SHARE_HASHTAG)
                    .getIntent();
            startActivity(shareIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
