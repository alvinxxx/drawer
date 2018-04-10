package com.example.alvinlam.drawer.fragment;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.example.alvinlam.drawer.R;
import com.example.alvinlam.drawer.activity.AddCardActivity;
import com.example.alvinlam.drawer.activity.ImageActivity;
import com.example.alvinlam.drawer.activity.StockAlertAddActivity;
import com.example.alvinlam.drawer.adapter.StockAlertAdapter;
import com.example.alvinlam.drawer.data.StockAlertDbFunction;
import com.example.alvinlam.drawer.data.StockDbFunction;
import com.example.alvinlam.drawer.data.StocklistContract;
import com.example.alvinlam.drawer.utilities.NetworkUtils;
import com.example.alvinlam.drawer.utilities.OpenStockJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class StockRecommendFragment extends Fragment {

    private static final String TAG = AddCardActivity.class.getSimpleName();
    private static final String SHARE_HASHTAG = " #PocketCard";

    private long id = 0;


    static String[] spaceProbeHeaders={"Name","Sharpe Dividend","Sharpe PE"};

    public StockRecommendFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.stock_recommend_content, container, false);
        final Context context = rootView.getContext();





        URL stockSearchUrl = NetworkUtils.buildUrlR();
        new StockRecommendTask(getContext(), rootView).execute(stockSearchUrl);


        return rootView;
    }

    public class StockRecommendTask extends AsyncTask<URL, Void, List<String[]> > {

        Context context;
        View rootView;

        public StockRecommendTask(Context context, View rootView) {
            this.context = context.getApplicationContext();
            this.rootView=rootView;

        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<String[]> doInBackground(URL... params) {
            URL searchUrl = params[0];
            Log.d("image", "doInBackground: "+searchUrl);
            String stockSearchResults = null;
            try {
                boolean internet = NetworkUtils.hasInternetConnection(context);
                if(internet){
                    stockSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl, context);
                    List<String[]> fullJsonStockData = OpenStockJsonUtils.getRecommendDataFromJson(stockSearchResults, 0);
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
        protected void onPostExecute(final List<String[]> parsedStockData) {


            if(parsedStockData!=null){
                TableView<String[]> tableView = (TableView<String[]>) rootView.findViewById(R.id.tableView);


                //process data

                //SET PROP
                tableView.setHeaderBackgroundColor(Color.parseColor("#2ecc71"));
                tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(context,spaceProbeHeaders));
                tableView.setColumnCount(3);
                tableView.setDataAdapter(new SimpleTableDataAdapter(context, parsedStockData));


                tableView.addDataClickListener(new TableDataClickListener() {
                    @Override
                    public void onDataClicked(int rowIndex, Object clickedData) {
                        Toast.makeText(context, ((String[])clickedData)[1], Toast.LENGTH_SHORT).show();
                    }
                });
            }


        }
    }





}
