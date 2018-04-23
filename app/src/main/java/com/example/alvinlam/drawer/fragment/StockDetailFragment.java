package com.example.alvinlam.drawer.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alvinlam.drawer.R;
import com.example.alvinlam.drawer.activity.AddCardActivity;
import com.example.alvinlam.drawer.data.StockDbFunction;
import com.example.alvinlam.drawer.data.StocklistContract;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class StockDetailFragment extends Fragment {

    private static final String TAG = AddCardActivity.class.getSimpleName();
    private static final String SHARE_HASHTAG = " #PocketCard";

    private Cursor cursor;
    private StockDbFunction dbFunction;

    private TextView mCodeTextView;
    private TextView mDateTextView;
    private TextView mPriceTextView;
    private TextView mNetChangeTextView;
    private TextView mHighTextView;
    private TextView mLowTextView;
    private TextView mVolumeTextView;
    private TextView mUptimeTextView;

    private long id = 0;
    private int code;
    private String date;
    private double price;
    private double netChange;
    private double high;
    private double low;
    private double volume;
    private String uptime;

    public StockDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.add_card_read_precontent, container, false);


        dbFunction = new StockDbFunction(getActivity().getApplicationContext());


        mCodeTextView = (TextView) rootView.findViewById(R.id.textViewCode2);
        mDateTextView = (TextView) rootView.findViewById(R.id.textViewDate2);
        mPriceTextView = (TextView) rootView.findViewById(R.id.textViewPrice2);
        mNetChangeTextView = (TextView) rootView.findViewById(R.id.textViewNetChange2);
        mHighTextView = (TextView) rootView.findViewById(R.id.textViewHigh2);
        mLowTextView = (TextView) rootView.findViewById(R.id.textViewLow2);
        mVolumeTextView = (TextView) rootView.findViewById(R.id.textViewVolume2);
        mUptimeTextView = (TextView) rootView.findViewById(R.id.textViewUptime2);


        Intent intentThatStartedThisActivity = getActivity().getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_UID)) {
                id = intentThatStartedThisActivity.getLongExtra(Intent.EXTRA_UID, 0);

                cursor = dbFunction.selectByID(id);
                //Log.i(TAG, "1 "+String.valueOf(cursor.getColumnIndex("name")));
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();

                    //Log.i(AddCardAddActivity.class.getName(), String.valueOf(cursor.getColumnIndex("name")));

                    code = cursor.getInt(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_CODE));
                    date = cursor.getString(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_DATE));
                    price = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_PRICE));
                    netChange = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_NET_CHANGE));
                    high = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_HIGH));
                    low = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_LOW));
                    volume = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_VOLUME));
                    uptime = cursor.getString(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_UPTIME));


                }
            }else if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)){
                String[] parsedStockData = intentThatStartedThisActivity.getStringArrayExtra(Intent.EXTRA_TEXT);

                id = Long.parseLong(parsedStockData[1]);
                code = Integer.parseInt(parsedStockData[1]);
                date = parsedStockData[2];
                price = checkDouble(parsedStockData[3]);
                netChange =  checkDouble(parsedStockData[4]);
                high =  checkDouble(parsedStockData[6]);
                low =  checkDouble(parsedStockData[7]);
                volume =  checkDouble(parsedStockData[9]);
                uptime = parsedStockData[8];



            }

            String[] tempDate = date.split("T");
            tempDate = tempDate[0].split("-");

            int day = Integer.parseInt(tempDate[2])+1;

            String[] tempTime = uptime.split("T");
            tempTime = tempTime[1].split(":");

            int hour = Integer.parseInt(tempTime[0])+8;

            String realDate = tempDate[0]+"-"+tempDate[1]+"-"+String.valueOf(day);
            String realTime = String.valueOf(hour)+":"+tempTime[1]+":"+tempDate[2];

            mCodeTextView.setText(String.format(Locale.getDefault(), "%d", code));
            mDateTextView.setText(realDate);
            mPriceTextView.setText(String.format(Locale.getDefault(), "%.2f", price));
            mNetChangeTextView.setText(String.format(Locale.getDefault(), "%.2f", netChange)+"%");
            mHighTextView.setText(String.format(Locale.getDefault(), "%.2f", high));
            mLowTextView.setText(String.format(Locale.getDefault(), "%.2f", low));
            mVolumeTextView.setText(String.format(Locale.getDefault(), "%.0f", volume)+"m");
            mUptimeTextView.setText(realTime);

        }

        return rootView;
    }

    private Double checkDouble(String value) {
        if (value.equals("null"))
            return 0.0;
        else
            return Double.parseDouble(value);
    }


}
