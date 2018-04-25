
package com.example.alvinlam.drawer.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableHeaderClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class StockAnalysisFragment extends Fragment {

    private static final String TAG = AddCardActivity.class.getSimpleName();
    private static final String SHARE_HASHTAG = " #PocketCard";

    private Cursor cursor;
    private StockDbFunction dbFunction;

    private TextView anaPETextView, anaEPSTextView, anaDYTextView, anaDPSTextView;


    private long id = 0;
    private int code;
    private double dy, dps, pe, eps, sma20, std20, std20l, std20h, sma50, std50, std50l, std50h,
            sma100, std100, std100l, std100h, sma250, std250, std250l, std250h,
            l20, h20, l50, h50, l100, h100, l250, h250;

    static String[] spaceProbeHeaders={"Day","SMA","STD","STD_L","STD_H"};


    public StockAnalysisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.add_ana_read_precontent, container, false);


        dbFunction = new StockDbFunction(getActivity().getApplicationContext());

        anaPETextView = (TextView) rootView.findViewById(R.id.textViewAnaPE2);
        anaEPSTextView = (TextView) rootView.findViewById(R.id.textViewAnaEPS2);
        anaDYTextView = (TextView) rootView.findViewById(R.id.textViewAnaDY2);
        anaDPSTextView = (TextView) rootView.findViewById(R.id.textViewAnaDPS2);


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

            anaPETextView.setText(String.format(Locale.getDefault(), "%.2f", pe));
            anaEPSTextView.setText(String.format(Locale.getDefault(), "%.2f", eps));
            anaDYTextView.setText(String.format(Locale.getDefault(), "%.2f", dy));
            anaDPSTextView.setText(String.format(Locale.getDefault(), "%.2f", dps));


            List<String[]> rowList = new ArrayList<>();
            String[] row20 = {"20", String.format(Locale.getDefault(), "%.2f", sma20),
                    String.format(Locale.getDefault(), "%.2f", std20),
                    String.format(Locale.getDefault(), "%.2f", std20l),
                    String.format(Locale.getDefault(), "%.2f", std20h)};
            String[] row50 = {"50", String.format(Locale.getDefault(), "%.2f", sma50),
                    String.format(Locale.getDefault(), "%.2f", std50),
                    String.format(Locale.getDefault(), "%.2f", std50l),
                    String.format(Locale.getDefault(), "%.2f", std50h)};
            String[] row100 = {"100", String.format(Locale.getDefault(), "%.2f", sma100),
                    String.format(Locale.getDefault(), "%.2f", std100),
                    String.format(Locale.getDefault(), "%.2f", std100l),
                    String.format(Locale.getDefault(), "%.2f", std100h)};
            String[] row250 = {"250", String.format(Locale.getDefault(), "%.2f", sma250),
                    String.format(Locale.getDefault(), "%.2f", std250),
                    String.format(Locale.getDefault(), "%.2f", std250l),
                    String.format(Locale.getDefault(), "%.2f", std250h)};
            rowList.add(row20);
            rowList.add(row50);
            rowList.add(row100);
            rowList.add(row250);

            TableView<String[]> tableView = (TableView<String[]>) rootView.findViewById(R.id.tableViewAnaTechTable);

            SimpleTableHeaderAdapter headerAdapter = new SimpleTableHeaderAdapter(getActivity().getApplicationContext(),spaceProbeHeaders);
            headerAdapter.setTextColor(getResources().getColor(R.color.colorWhite));
            SimpleTableDataAdapter dataAdapter = new SimpleTableDataAdapter(getActivity().getApplicationContext(), rowList);
            dataAdapter.setTextColor(getResources().getColor(R.color.colorWhite));
            //SET PROP
            tableView.setHeaderBackgroundColor(getResources().getColor(R.color.colorAccent));
            tableView.setHeaderAdapter(headerAdapter);
            tableView.setColumnCount(5);
            tableView.setDataAdapter(dataAdapter);


            tableView.addHeaderClickListener(new TableHeaderClickListener() {
                @Override
                public void onHeaderClicked(int rowIndex) {
                    //Toast.makeText(context, rowIndex, Toast.LENGTH_SHORT).show();
                }
            });


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
