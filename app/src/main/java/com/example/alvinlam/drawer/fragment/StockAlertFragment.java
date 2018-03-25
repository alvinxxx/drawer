package com.example.alvinlam.drawer.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.alvinlam.drawer.R;
import com.example.alvinlam.drawer.activity.AddCardActivity;
import com.example.alvinlam.drawer.activity.AddCardAddActivity;
import com.example.alvinlam.drawer.activity.MainActivity;
import com.example.alvinlam.drawer.data.StockDbFunction;
import com.example.alvinlam.drawer.data.StocklistContract;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class StockAlertFragment extends Fragment {

    private static final String TAG = AddCardActivity.class.getSimpleName();
    private static final String SHARE_HASHTAG = " #PocketCard";

    private RecyclerView stockAlertListRecyclerView;


    public StockAlertFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.stock_alert_content_list, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.main_fab);
        /*fab.bringToFront();

        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Class destinationClass = AddCardAddActivity.class;
                Intent intentToStartAddCardActivity = new Intent(MainActivity.this, destinationClass);
                startActivity(intentToStartAddCardActivity);
            }
        });*/

        stockAlertListRecyclerView = (RecyclerView) rootView.findViewById(R.id.stock_alert_list_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        stockAlertListRecyclerView.setLayoutManager(layoutManager);
        stockAlertListRecyclerView.setHasFixedSize(true);

        Intent intentThatStartedThisActivity = getActivity().getIntent();




        return rootView;
    }





}
