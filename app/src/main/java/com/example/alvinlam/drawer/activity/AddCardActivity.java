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
import com.example.alvinlam.drawer.data.StockDbFunction;
import com.example.alvinlam.drawer.data.StocklistContract;
import com.example.alvinlam.drawer.fragment.StockDetailFragment;

import java.util.Locale;

import static android.content.ContentValues.TAG;


public class AddCardActivity extends AppCompatActivity{
    private static final String SHARE_HASHTAG = " #PocketCard";

    private Cursor cursor;
    private StockDbFunction dbFunction;

    private long id = 0;
    private String name;
    private int code;
    private String date;
    private double price;
    private double netChange;
    private double pe;
    private double high;
    private double low;
    private double preClose;
    private double volume;
    private double turnover;
    private double lot;
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

        dbFunction = new StockDbFunction(getApplicationContext());

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_UID)) {
                id = intentThatStartedThisActivity.getLongExtra(Intent.EXTRA_UID, 0);

                cursor = dbFunction.selectByID(id);
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();

                    Log.i(AddCardAddActivity.class.getName(), String.valueOf(cursor.getColumnIndex("name")));

                    name = cursor.getString(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_NAME));
                    code = cursor.getInt(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_CODE));
                    date = cursor.getString(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_DATE));
                    price = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_PRICE));
                    netChange = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_NET_CHANGE));
                    pe = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_PE));
                    high = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_HIGH));
                    low = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_LOW));
                    preClose = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_PRE_CLOSE));
                    volume = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_VOLUME));
                    turnover = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_TURNOVER));
                    lot = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_LOT));
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
                    id,
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
            Toast.makeText(context,"Stock Added",Toast.LENGTH_LONG).show();
            mAdd.setVisible(false);
            mDelete.setVisible(true);

            return true;

        }else if (lid == R.id.action_delete) {
            dbFunction.delete(id);
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
                            "Volume:  "+ volume + "\n" + "Turnover:  "+ turnover + "\n" + "Lot:  "+ lot + "\n" +
                            SHARE_HASHTAG)
                    .getIntent();
            startActivity(shareIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
