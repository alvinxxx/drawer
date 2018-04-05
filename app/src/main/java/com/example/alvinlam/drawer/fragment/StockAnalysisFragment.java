
package com.example.alvinlam.drawer.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.alvinlam.drawer.R;
import com.example.alvinlam.drawer.activity.AddCardActivity;
import com.example.alvinlam.drawer.data.StockDbFunction;
import com.example.alvinlam.drawer.data.StocklistContract;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class StockAnalysisFragment extends Fragment {

    private static final String TAG = AddCardActivity.class.getSimpleName();
    private static final String SHARE_HASHTAG = " #PocketCard";

    private Cursor cursor;
    private StockDbFunction dbFunction;

    private EditText anaPEEditText, anaEPSEditText, anaDYEditText, anaDPSEditText;
    private EditText anaSMA20EditText, anaSMA50EditText, anaSMA100EditText, anaSMA250EditText;
    private EditText anaSTD20EditText, anaSTD50EditText, anaSTD100EditText, anaSTD250EditText;
    private EditText anaSTD20lEditText, anaSTD50lEditText, anaSTD100lEditText, anaSTD250lEditText;
    private EditText anaSTD20hEditText, anaSTD50hEditText, anaSTD100hEditText, anaSTD250hEditText;

    private long id = 0;
    private int code;
    private double dy, dps, pe, eps, sma20, std20, std20l, std20h, sma50, std50, std50l, std50h,
            sma100, std100, std100l, std100h, sma250, std250, std250l, std250h,
            l20, h20, l50, h50, l100, h100, l250, h250;


    public StockAnalysisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.add_ana_read_precontent, container, false);


        dbFunction = new StockDbFunction(getActivity().getApplicationContext());

        anaPEEditText = (EditText) rootView.findViewById(R.id.ana_pe_editText);
        anaEPSEditText = (EditText) rootView.findViewById(R.id.ana_eps_editText);
        anaDYEditText = (EditText) rootView.findViewById(R.id.ana_dy_editText);
        anaDPSEditText = (EditText) rootView.findViewById(R.id.ana_dps_editText);

        anaSMA20EditText = (EditText) rootView.findViewById(R.id.ana_sma20_editText);
        anaSMA50EditText = (EditText) rootView.findViewById(R.id.ana_sma50_editText);
        anaSMA100EditText = (EditText) rootView.findViewById(R.id.ana_sma100_editText);
        anaSMA250EditText = (EditText) rootView.findViewById(R.id.ana_sma250_editText);

        anaSTD20EditText = (EditText) rootView.findViewById(R.id.ana_std20_editText);
        anaSTD50EditText = (EditText) rootView.findViewById(R.id.ana_std50_editText);
        anaSTD100EditText = (EditText) rootView.findViewById(R.id.ana_std100_editText);
        anaSTD250EditText = (EditText) rootView.findViewById(R.id.ana_std250_editText);

        anaSTD20lEditText = (EditText) rootView.findViewById(R.id.ana_std20l_editText);
        anaSTD50lEditText = (EditText) rootView.findViewById(R.id.ana_std50l_editText);
        anaSTD100lEditText = (EditText) rootView.findViewById(R.id.ana_std100l_editText);
        anaSTD250lEditText = (EditText) rootView.findViewById(R.id.ana_std250l_editText);

        anaSTD20hEditText = (EditText) rootView.findViewById(R.id.ana_std20h_editText);
        anaSTD50hEditText = (EditText) rootView.findViewById(R.id.ana_std50h_editText);
        anaSTD100hEditText = (EditText) rootView.findViewById(R.id.ana_std100h_editText);
        anaSTD250hEditText = (EditText) rootView.findViewById(R.id.ana_std250h_editText);

        Intent intentThatStartedThisActivity = getActivity().getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_UID)) {
                id = intentThatStartedThisActivity.getLongExtra(Intent.EXTRA_UID, 0);

                cursor = dbFunction.selectByID(id);
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();

                    code = cursor.getInt(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_CODE));
                    pe = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_PE));
                    eps = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_EPS));
                    dy = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_DY));
                    dps = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_DPS));
                    sma20 = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_SMA20));
                    sma50 = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_SMA50));
                    sma100 = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_SMA100));
                    sma250 = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_SMA250));
                    std20 = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD20));
                    std50 = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD50));
                    std100 = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD100));
                    std250 = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD250));
                    std20l = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD20L));
                    std50l = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD50L));
                    std100l = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD100L));
                    std250l = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD250L));
                    std20h = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD20H));
                    std50h = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD50H));
                    std100h = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD100H));
                    std250h = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD250H));

                }
            }else if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)){
                String[] parsedStockData = intentThatStartedThisActivity.getStringArrayExtra(Intent.EXTRA_TEXT);

                id = Long.parseLong(parsedStockData[1]);


                pe = checkDouble(parsedStockData[5]);
                dy = checkDouble(parsedStockData[12]);
                dps = checkDouble(parsedStockData[13]);
                eps = checkDouble(parsedStockData[14]);
                sma20 = checkDouble(parsedStockData[15]);
                std20 = checkDouble(parsedStockData[16]);
                std20l = checkDouble(parsedStockData[17]);
                std20h = checkDouble(parsedStockData[18] );
                sma50 = checkDouble(parsedStockData[19]);
                std50 = checkDouble(parsedStockData[20]);
                std50l = checkDouble(parsedStockData[21] );
                std50h = checkDouble(parsedStockData[22] );
                sma100 = checkDouble(parsedStockData[23] );
                std100 = checkDouble(parsedStockData[24] );
                std100l = checkDouble(parsedStockData[25] );
                std100h = checkDouble(parsedStockData[26] );
                sma250 = checkDouble(parsedStockData[27] );
                std250 = checkDouble(parsedStockData[28] );
                std250l = checkDouble(parsedStockData[29]);
                std250h = checkDouble(parsedStockData[30]);
            }

            anaPEEditText.setText(String.format(Locale.getDefault(), "%.2f", pe));
            anaEPSEditText.setText(String.format(Locale.getDefault(), "%.2f", eps));
            anaDYEditText.setText(String.format(Locale.getDefault(), "%.2f", dy));
            anaDPSEditText.setText(String.format(Locale.getDefault(), "%.2f", dps));

            anaSMA20EditText.setText(String.format(Locale.getDefault(), "%.2f", sma20));
            anaSMA50EditText.setText(String.format(Locale.getDefault(), "%.2f", sma50));
            anaSMA100EditText.setText(String.format(Locale.getDefault(), "%.2f", sma100));
            anaSMA250EditText.setText(String.format(Locale.getDefault(), "%.2f", sma250));

            anaSTD20EditText.setText(String.format(Locale.getDefault(), "%.2f", std20));
            anaSTD50EditText.setText(String.format(Locale.getDefault(), "%.2f", std50));
            anaSTD100EditText.setText(String.format(Locale.getDefault(), "%.2f", std100));
            anaSTD250EditText.setText(String.format(Locale.getDefault(), "%.2f", std250));

            anaSTD20lEditText.setText(String.format(Locale.getDefault(), "%.2f", std20l));
            anaSTD50lEditText.setText(String.format(Locale.getDefault(), "%.2f", std50l));
            anaSTD100lEditText.setText(String.format(Locale.getDefault(), "%.2f", std100l));
            anaSTD250lEditText.setText(String.format(Locale.getDefault(), "%.2f", std250l));

            anaSTD20hEditText.setText(String.format(Locale.getDefault(), "%.2f", std20h));
            anaSTD50hEditText.setText(String.format(Locale.getDefault(), "%.2f", std50h));
            anaSTD100hEditText.setText(String.format(Locale.getDefault(), "%.2f", std100h));
            anaSTD250hEditText.setText(String.format(Locale.getDefault(), "%.2f", std250h));

        }
        disableEditText(anaPEEditText);
        disableEditText(anaEPSEditText);
        disableEditText(anaDYEditText);
        disableEditText(anaDPSEditText);

        disableEditText(anaSMA20EditText);
        disableEditText(anaSMA50EditText);
        disableEditText(anaSMA100EditText);
        disableEditText(anaSMA250EditText);

        disableEditText(anaSTD20EditText);
        disableEditText(anaSTD50EditText);
        disableEditText(anaSTD100EditText);
        disableEditText(anaSTD250EditText);

        disableEditText(anaSTD20lEditText);
        disableEditText(anaSTD50lEditText);
        disableEditText(anaSTD100lEditText);
        disableEditText(anaSTD250lEditText);

        disableEditText(anaSTD20hEditText);
        disableEditText(anaSTD50hEditText);
        disableEditText(anaSTD100hEditText);
        disableEditText(anaSTD250hEditText);

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
