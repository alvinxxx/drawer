package com.example.alvinlam.drawer.fragment;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.example.alvinlam.drawer.R;
import com.example.alvinlam.drawer.activity.AddCardActivity;
import com.example.alvinlam.drawer.activity.AddCardAddActivity;
import com.example.alvinlam.drawer.activity.MainActivity;
import com.example.alvinlam.drawer.activity.StockAlertAddActivity;
import com.example.alvinlam.drawer.adapter.StockAlertAdapter;
import com.example.alvinlam.drawer.data.StockAlertDbFunction;
import com.example.alvinlam.drawer.data.StockAlertTestUtil;
import com.example.alvinlam.drawer.data.StockDbFunction;
import com.example.alvinlam.drawer.data.StocklistContract;
import com.example.alvinlam.drawer.data.StocklistDbHelper;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class StockAlertFragment extends Fragment implements StockAlertAdapter.ListItemClickListener {

    private static final String TAG = AddCardActivity.class.getSimpleName();
    private static final String SHARE_HASHTAG = " #PocketCard";

    private RecyclerView stockAlertListRecyclerView;
    private StockDbFunction dbFunction;
    private StockAlertDbFunction dbAFunction;
    private SQLiteDatabase mDb;
    private Cursor cursor;
    private StockAlertAdapter mAdapter;
    private int code;
    private String name;


    private long id = 0;

    public StockAlertFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.stock_alert_content_list, container, false);

        stockAlertListRecyclerView = (RecyclerView) rootView.findViewById(R.id.stock_alert_list_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        stockAlertListRecyclerView.setLayoutManager(layoutManager);
        stockAlertListRecyclerView.setHasFixedSize(true);


        dbFunction = new StockDbFunction(getActivity().getApplicationContext());
        dbAFunction = new StockAlertDbFunction(getActivity().getApplicationContext());

        Intent intentThatStartedThisActivity = getActivity().getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_UID)) {
                id = intentThatStartedThisActivity.getLongExtra(Intent.EXTRA_UID, 0);

                cursor = dbFunction.selectByID(id);
                code = cursor.getInt(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_CODE));
                name = cursor.getString(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_NAME));

                cursor = dbAFunction.selectByCode(code);
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                }
                final String[] letter = {String.valueOf(code), name};
                FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.stock_alert_fab);
                fab.bringToFront();

                fab.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Class destinationClass = StockAlertAddActivity.class;
                        Intent intentToStartAddCardActivity = new Intent(getActivity().getApplicationContext(), destinationClass);
                        intentToStartAddCardActivity.putExtra(Intent.EXTRA_TEXT, letter);
                        startActivity(intentToStartAddCardActivity);
                    }
                });

            }else if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)){
                //String[] parsedStockData = intentThatStartedThisActivity.getStringArrayExtra(Intent.EXTRA_TEXT);

            }
        }

        if(cursor != null){
            mAdapter = new StockAlertAdapter(getActivity().getApplicationContext(), cursor, this);
            stockAlertListRecyclerView.setAdapter(mAdapter);
        }



        // Create an item touch helper to handle swiping items off the list
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            // Override onMove and simply return false inside
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //do nothing, we only care about swiping
                return false;
            }

            // Override onSwiped
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Inside, get the viewHolder's itemView's tag and store in a long variable id
                //get the id of the item being swiped
                long id = (long) viewHolder.itemView.getTag();
                //remove from DB
                dbAFunction.delete(id);
                //update the list
                mAdapter.swapCursor(dbAFunction.selectByCode(code));
            }

            // attach the ItemTouchHelper
        }).attachToRecyclerView(stockAlertListRecyclerView);



        return rootView;
    }


    @Override
    public void onListItemClick(View v, int position) {
        //TODO: switch
        Context context = getActivity().getApplicationContext();
        Class destinationClass = StockAlertAddActivity.class;

        long id = (long) v.getTag();
        Log.i(TAG, "onclick "+id);

        Intent intentToStartAddCardActivity = new Intent(context, destinationClass);
        intentToStartAddCardActivity.putExtra(Intent.EXTRA_UID, id);
        startActivity(intentToStartAddCardActivity);
    }


}
