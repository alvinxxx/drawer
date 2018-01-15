package com.example.alvinlam.drawer.fragment;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alvinlam.drawer.R;
import com.example.alvinlam.drawer.activity.AddCardActivity;
import com.example.alvinlam.drawer.activity.AddCardAddActivity;
import com.example.alvinlam.drawer.activity.MainActivity;
import com.example.alvinlam.drawer.activity.SearchResultStockActivity;
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

    private EditText mNameEditText;
    private EditText mCodeEditText;
    private EditText mDateEditText;
    private EditText mPriceEditText;
    private EditText mNetChangeEditText;
    private EditText mPEEditText;
    private EditText mHighEditText;
    private EditText mLowEditText;
    private EditText mPreCloseEditText;
    private EditText mVolumeEditText;
    private EditText mTurnoverEditText;
    private EditText mLotEditText;

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

    public StockDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.add_card_read_precontent, container, false);


        dbFunction = new StockDbFunction(getActivity().getApplicationContext());


        mNameEditText = (EditText) rootView.findViewById(R.id.add_name_editText);
        mCodeEditText = (EditText) rootView.findViewById(R.id.add_code_editText);
        mDateEditText = (EditText) rootView.findViewById(R.id.add_date_editText);
        mPriceEditText = (EditText) rootView.findViewById(R.id.add_price_editText);
        mNetChangeEditText = (EditText) rootView.findViewById(R.id.add_net_change_editText);
        mPEEditText = (EditText) rootView.findViewById(R.id.add_pe_editText);
        mHighEditText = (EditText) rootView.findViewById(R.id.add_high_editText);
        mLowEditText = (EditText) rootView.findViewById(R.id.add_low_editText);
        mPreCloseEditText = (EditText) rootView.findViewById(R.id.add_pre_close_editText);
        mVolumeEditText = (EditText) rootView.findViewById(R.id.add_volume_editText);
        mTurnoverEditText = (EditText) rootView.findViewById(R.id.add_turnover_editText);
        mLotEditText = (EditText) rootView.findViewById(R.id.add_lot_editText);


        Intent intentThatStartedThisActivity = getActivity().getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_UID)) {
                id = intentThatStartedThisActivity.getLongExtra(Intent.EXTRA_UID, 0);

                cursor = dbFunction.selectByID(id);
                Log.i(TAG, "1 "+String.valueOf(cursor.getColumnIndex("name")));
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
            }else if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)){
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
                preClose =  checkDouble(parsedStockData[8]);
                volume =  checkDouble(parsedStockData[9]);
                turnover =  checkDouble(parsedStockData[10]);
                lot =  checkDouble(parsedStockData[11]);

            }
            mNameEditText.setText(name);
            mCodeEditText.setText(String.format(Locale.getDefault(), "%d", code));
            mDateEditText.setText(date);
            mPriceEditText.setText(String.format(Locale.getDefault(), "%.2f", price));
            mNetChangeEditText.setText(String.format(Locale.getDefault(), "%.2f", netChange));
            mPEEditText.setText(String.format(Locale.getDefault(), "%.2f", pe));
            mHighEditText.setText(String.format(Locale.getDefault(), "%.2f", high));
            mLowEditText.setText(String.format(Locale.getDefault(), "%.2f", low));
            mPreCloseEditText.setText(String.format(Locale.getDefault(), "%.2f", preClose));
            mVolumeEditText.setText(String.format(Locale.getDefault(), "%.2f", volume));
            mTurnoverEditText.setText(String.format(Locale.getDefault(), "%.2f", turnover));
            mLotEditText.setText(String.format(Locale.getDefault(), "%.2f", lot));

        }
        disableEditText(mNameEditText);
        disableEditText(mCodeEditText);
        disableEditText(mDateEditText);
        disableEditText(mPriceEditText);
        disableEditText(mNetChangeEditText);
        disableEditText(mPEEditText);
        disableEditText(mHighEditText);
        disableEditText(mLowEditText);
        disableEditText(mPreCloseEditText);
        disableEditText(mVolumeEditText);
        disableEditText(mTurnoverEditText);
        disableEditText(mLotEditText);



        return rootView;
    }


    public void disableEditText(EditText et){
        et.setCursorVisible(false);
        et.setLongClickable(false);
        et.setClickable(false);
        et.setFocusable(false);
        et.setSelected(false);
        et.setKeyListener(null);
        et.setBackgroundResource(android.R.color.transparent);
    }

    private Double checkDouble(String value) {
        if (value.equals("null"))
            return 0.0;
        else
            return Double.parseDouble(value);
    }


}
