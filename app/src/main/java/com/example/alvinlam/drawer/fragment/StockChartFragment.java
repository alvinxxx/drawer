package com.example.alvinlam.drawer.fragment;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alvinlam.drawer.R;
import com.example.alvinlam.drawer.activity.AddCardAddActivity;
import com.example.alvinlam.drawer.activity.MainActivity;
import com.example.alvinlam.drawer.activity.SearchResultStockActivity;
import com.example.alvinlam.drawer.data.Stock;
import com.example.alvinlam.drawer.data.StockDbFunction;
import com.example.alvinlam.drawer.data.StocklistContract;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class StockChartFragment extends Fragment {
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

    public StockChartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View rootView = inflater.inflate(R.layout.stock_chart_precontent, container, false);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.add_card_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        if(((AppCompatActivity)getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        LineChart chart = (LineChart) rootView.findViewById(R.id.chart);
        Stock s1 = new Stock();
        s1.setPrice(10);
        Stock s2 = new Stock();
        s2.setPrice(20);
        Stock s3 = new Stock();
        s3.setPrice(40);

        ArrayList<Stock> dataObjects = new ArrayList<Stock>();

        dataObjects.add(s1);
        dataObjects.add(s2);
        dataObjects.add(s3);

        List<Entry> entries = new ArrayList<Entry>();
        int i = 0;
        for (Stock data : dataObjects) {
            float f = (float)data.getPrice();
            // turn your data into Entry objects
            entries.add(new Entry(i, f));
            Log.d(TAG, "onCreate: "+i+" "+f);

            i++;
        }

        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate(); // refresh

        dbFunction = new StockDbFunction(getActivity().getApplicationContext());

        Intent intentThatStartedThisActivity = getActivity().getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                id = intentThatStartedThisActivity.getLongExtra(Intent.EXTRA_TEXT, 0);
                Log.i(TAG, "stockchart "+id);

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

        return rootView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.add_card_toolbar_menu, menu);
        mAdd= menu.findItem(R.id.action_add);
        mDelete = menu.findItem(R.id.action_delete);
        mAdd.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int lid = item.getItemId();
        Context context = getActivity().getApplicationContext();
        Class destinationClass = SearchResultStockActivity.class;

        //noinspection SimplifiableIfStatement
        if (lid == R.id.action_add) {
            Log.d(TAG, "onoption " + volume);
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
            Log.i(TAG, "chart home ");

            destinationClass = MainActivity.class;
            Intent intentToStartActivity = new Intent(context, destinationClass);
            startActivity(intentToStartActivity);
            return true;
        }else if (lid == R.id.action_share) {
            Intent shareIntent = ShareCompat.IntentBuilder.from(getActivity())
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
