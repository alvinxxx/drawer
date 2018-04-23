package com.example.alvinlam.drawer.fragment;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.alvinlam.drawer.R;
import com.example.alvinlam.drawer.activity.AddCardActivity;
import com.example.alvinlam.drawer.data.RiskAssessDbFunction;
import com.example.alvinlam.drawer.data.StockAlertDbFunction;
import com.example.alvinlam.drawer.data.StockDbFunction;
import com.example.alvinlam.drawer.data.StockRecommendQueryTask;
import com.example.alvinlam.drawer.data.StocklistContract;
import com.example.alvinlam.drawer.utilities.NetworkUtils;
import com.example.alvinlam.drawer.utilities.OpenStockJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableHeaderClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class StockRecommendFragment extends Fragment {

    private static final String TAG = AddCardActivity.class.getSimpleName();
    private static final String SHARE_HASHTAG = " #PocketCard";

    private long id = 0;
    private Cursor cursor;
    private RiskAssessDbFunction dbRAFunction;
    private StockDbFunction dbFunction;

    static String[] spaceProbeHeaders={"Name","Sharpe Ratio"};

    public StockRecommendFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.stock_recommend_content, container, false);
        final Context context = rootView.getContext();


        //get my category
        dbRAFunction = new RiskAssessDbFunction(context);
        cursor = dbRAFunction.selectTotalScore();
        int total = cursor.getInt(0);// get final total
        int cat = 1;
        if (total >= 12 && total <= 19){
            cat = 1;
        }else if (total >= 20 && total <= 28){
            cat = 2;
        }else if (total >= 29 && total <= 37){
            cat = 3;
        }else if (total >= 38 && total <= 46){
            cat = 4;
        }else if (total >= 47 && total <= 54){
            cat = 5;
        }

        boolean internet = NetworkUtils.hasInternetConnection(context);
        if(internet) {
            URL stockSearchUrl = NetworkUtils.buildUrlR(0, cat);
            new StockRecommendTask(context, rootView).execute(stockSearchUrl);
        }else{
            //no internet toast
            Toast.makeText(context,"No internet",Toast.LENGTH_LONG).show();
        }




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
        protected void onPostExecute(final List<String[]> parsedStockDataList) {

            List<String> codeList = new ArrayList<>();
            dbFunction = new StockDbFunction(getActivity().getApplicationContext());

            if(parsedStockDataList!=null){
                cursor = dbFunction.select();

                // select stocklist code
                if(cursor != null){
                    cursor.moveToFirst();

                    for ( int i=0; i<cursor.getCount(); i++){
                        int code = cursor.getInt(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_CODE));
                        codeList.add(String.valueOf(code));
                        cursor.moveToNext();
                    }

                    cursor.close();
                }

                //for each stock, add to watchlist
                for (String[] parsedStockData : parsedStockDataList){
                    String stockQuery = parsedStockData[0];
                    //System.out.println(stockQuery);
                    //add only not exist
                    if(! codeList.contains(stockQuery) ){
                        URL stockSearchUrlA = NetworkUtils.buildUrlA(stockQuery);
                        URL stockSearchUrlI = NetworkUtils.buildUrlI(stockQuery);
                        URL stockSearchUrlF = NetworkUtils.buildUrlF(stockQuery);
                        URL stockSearchUrlT = NetworkUtils.buildUrlT(stockQuery);

                        boolean internet = NetworkUtils.hasInternetConnection(context);
                        if(internet) {
                            new StockRecommendQueryTask(context).execute(stockSearchUrlA, stockSearchUrlI, stockSearchUrlF, stockSearchUrlT);
                        }else{
                            //no internet toast
                            Toast.makeText(context,"No internet",Toast.LENGTH_LONG).show();
                        }
                    }

                }



                TableView<String[]> tableView = (TableView<String[]>) rootView.findViewById(R.id.tableView);


                //SET PROP
                tableView.setHeaderBackgroundColor(Color.parseColor("#2ecc71"));
                tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(context,spaceProbeHeaders));
                tableView.setColumnCount(2);
                tableView.setDataAdapter(new SimpleTableDataAdapter(context, parsedStockDataList));

                tableView.addHeaderClickListener(new TableHeaderClickListener() {
                    @Override
                    public void onHeaderClicked(int rowIndex) {
                        //Toast.makeText(context, rowIndex, Toast.LENGTH_SHORT).show();
                    }
                });
                /*
                tableView.addDataClickListener(new TableDataClickListener() {
                    @Override
                    public void onDataClicked(int rowIndex, Object clickedData) {
                        Toast.makeText(context, ((String[])clickedData)[0], Toast.LENGTH_SHORT).show();

                        String s = ((String[])clickedData)[0];

                        try {
                            int code = Integer.parseInt(s);
                        } catch (NumberFormatException ex) {
                            Log.e(TAG, "Failed to parse to number: " + ex.getMessage());
                        }

                        URL stockSearchUrlA = NetworkUtils.buildUrlA(s);
                        URL stockSearchUrlI = NetworkUtils.buildUrlI(s);
                        URL stockSearchUrlF = NetworkUtils.buildUrlF(s);
                        URL stockSearchUrlT = NetworkUtils.buildUrlT(s);

                        boolean internet = NetworkUtils.hasInternetConnection(getContext());
                        if(internet) {
                            new StockQueryTask(getActivity().getApplicationContext()).execute(stockSearchUrlA, stockSearchUrlI, stockSearchUrlF, stockSearchUrlT);
                        }else{
                            //no internet toast
                            Toast.makeText(getActivity().getApplicationContext(),"No internet",Toast.LENGTH_LONG).show();
                        }



                    }
                });*/

            }


        }


    }





}
