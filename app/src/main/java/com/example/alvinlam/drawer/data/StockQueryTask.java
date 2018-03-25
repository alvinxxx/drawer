package com.example.alvinlam.drawer.data;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;

import com.example.alvinlam.drawer.activity.AddCardActivity;
import com.example.alvinlam.drawer.activity.AddCardAddActivity;
import com.example.alvinlam.drawer.activity.MainActivity;
import com.example.alvinlam.drawer.activity.SearchResultStockActivity;
import com.example.alvinlam.drawer.utilities.NetworkUtils;
import com.example.alvinlam.drawer.utilities.OpenStockJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Alvin Lam on 1/9/2018.
 */

public class StockQueryTask extends AsyncTask<URL, Void, String[]> {

    Context context;
    private StockDbFunction dbFunction;

    public StockQueryTask(Context context) {
        this.context = context.getApplicationContext();
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String[] doInBackground(URL... params) {
        URL searchUrl = params[0];
        String stockSearchResults = null;
        try {
            boolean internet = NetworkUtils.hasInternetConnection(context);
            if(internet){
                stockSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl, context);
                String[] fullJsonStockData = OpenStockJsonUtils.getFullStockDataFromJson(context, stockSearchResults);
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
    protected void onPostExecute(String[] parsedStockData) {

        Class destinationClass = SearchResultStockActivity.class;
        Intent intentToStartActivity = new Intent(context, destinationClass);
        intentToStartActivity.putExtra(Intent.EXTRA_TEXT, parsedStockData);

        context.startActivity(intentToStartActivity);

    }
}
