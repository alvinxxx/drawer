package com.example.alvinlam.drawer.fragment;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
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
import com.example.alvinlam.drawer.data.StockQueryTask;
import com.example.alvinlam.drawer.data.StocklistContract;
import com.example.alvinlam.drawer.utilities.NetworkUtils;
import com.example.alvinlam.drawer.utilities.OpenStockJsonUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class StockChartFragment extends Fragment {

    private long id = 0;
    private Cursor cursor;
    private StockDbFunction dbFunction;
    int days = 5;

    private String name;
    private int code;
    private String date;
    private double price;
    private double netChange;
    private static final String TAG = "StockChartFragment";


    public StockChartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.stock_chart, container, false);

        Intent intentThatStartedThisActivity = getActivity().getIntent();
        dbFunction = new StockDbFunction(getActivity().getApplicationContext());
        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_UID)) {
                id = intentThatStartedThisActivity.getLongExtra(Intent.EXTRA_UID, 0);

                cursor = dbFunction.selectByID(id);
                Log.i(TAG, "1 " + String.valueOf(cursor.getColumnIndex("name")));
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();

                    Log.i(AddCardAddActivity.class.getName(), String.valueOf(cursor.getColumnIndex("name")));

                    name = cursor.getString(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_NAME));
                    code = cursor.getInt(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_CODE));
                    date = cursor.getString(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_DATE));
                    price = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_PRICE));
                    netChange = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_NET_CHANGE));


                }
            } else if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                String[] parsedStockData = intentThatStartedThisActivity.getStringArrayExtra(Intent.EXTRA_TEXT);

                id = Long.parseLong(parsedStockData[1]);
                name = parsedStockData[0];
                code = Integer.parseInt(parsedStockData[1]);
                date = parsedStockData[2];

                price = checkDouble(parsedStockData[3]);
                netChange = checkDouble(parsedStockData[4]);


            }
        }
        URL stockSearchUrl = NetworkUtils.buildUrl(String.valueOf(code), days);
        new StockChartTask(getContext(), rootView).execute(stockSearchUrl);

        return rootView;
    }

    private Double checkDouble(String value) {
        if (value.equals("null"))
            return 0.0;
        else
            return Double.parseDouble(value);
    }



    public class StockChartTask extends AsyncTask<URL, Void, String[][]> {

        Context context;
        View rootView;

        public StockChartTask(Context context, View rootView) {
            this.context = context.getApplicationContext();
            this.rootView=rootView;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String[][] doInBackground(URL... params) {
            URL searchUrl = params[0];
            Log.d(TAG, "doInBackground: "+searchUrl);
            String stockSearchResults = null;
            try {
                boolean internet = NetworkUtils.hasInternetConnection(context);
                if(internet){
                    stockSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl, context);
                    String[][] fullJsonStockData = OpenStockJsonUtils.getChartDataFromJson(context, stockSearchResults);
                    return fullJsonStockData;
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(final String[][] parsedStockData) {

            if(parsedStockData!=null){
                LineChart chart = (LineChart) rootView.findViewById(R.id.chart);
                List<Entry> entries = new ArrayList<Entry>();

                float chartPrice = 0;
                for (int i = 0; i<parsedStockData.length; i++) {
                    try{
                        chartPrice =Float.parseFloat(parsedStockData[i][1]);
                    }catch(NumberFormatException e){
                        Log.d(TAG, "onPostExecute: number format");
                    }
                    // turn your data into Entry objects
                    entries.add(new Entry(i, chartPrice));
                }

                LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
                dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

                LineData lineData = new LineData(dataSet);
                chart.setData(lineData);
                chart.invalidate(); // refresh


                IAxisValueFormatter formatter = new IAxisValueFormatter() {

                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        return parsedStockData[(int) value][0];
                    }

                };

                XAxis xAxis = chart.getXAxis();
                xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
                xAxis.setValueFormatter(formatter);
            }


        }
    }
}
