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


    public StockChartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.stock_chart, container, false);


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



        return rootView;
    }




}
