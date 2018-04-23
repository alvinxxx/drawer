package com.example.alvinlam.drawer.data;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.alvinlam.drawer.activity.SearchResultStockActivity;
import com.example.alvinlam.drawer.utilities.NetworkUtils;
import com.example.alvinlam.drawer.utilities.OpenStockJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Alvin Lam on 1/9/2018.
 */

public class StockRecommendQueryTask extends AsyncTask<URL, Void, String[]> {

    public Double checkDouble(String value){
        if (value.equals("null"))
            return 0.0;
        else
            return Double.parseDouble(value);
    }

    Context context;
    private StockDbFunction dbFunction;
    private StockAlertDbFunction dbAFunction;

    public StockRecommendQueryTask(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String[] doInBackground(URL... params) {
        boolean internet = NetworkUtils.hasInternetConnection(context);
        if(internet) {
            String[] arrayJSONstring =  new String[params.length];
            int i =0;
            for(URL searchUrl : params){
                String stockSearchResults = null;
                try {
                    stockSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl, context);
                    arrayJSONstring[i] = stockSearchResults;

                } catch (IOException e) {
                    e.printStackTrace();
                }
                i++;
            }

            String[] fullJsonStockData  = new String[1];
            try {
                fullJsonStockData = OpenStockJsonUtils.getFullStockDataFromArray(arrayJSONstring);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return fullJsonStockData;
        }
        return null;
    }

    @Override
    protected void onPostExecute(String[] parsedStockData) {
        long id;
        String name, date, uptime, name_chi, industry;
        int code = 0;
        double price, netChange, pe, high, low, volume,
                dy, dps, eps, sma20, std20, std20l, std20h, sma50, std50, std50l, std50h,
                sma100, std100, std100l, std100h, sma250, std250, std250l, std250h,
                l20, h20, l50, h50, l100, h100, l250, h250;


        id = Long.parseLong(parsedStockData[1]);
        name = parsedStockData[0];
        code = Integer.parseInt(parsedStockData[1]);
        date = parsedStockData[2];

        price = checkDouble(parsedStockData[3]);
        netChange =  checkDouble(parsedStockData[4]);
        pe =  checkDouble(parsedStockData[5]);
        high =  checkDouble(parsedStockData[6]);
        low =  checkDouble(parsedStockData[7]);
        volume =  checkDouble(parsedStockData[9]);
        dy = checkDouble(parsedStockData[12]);
        dps =  checkDouble(parsedStockData[13]);
        eps =  checkDouble(parsedStockData[14]);
        sma20 =  checkDouble(parsedStockData[15]);
        std20 =  checkDouble(parsedStockData[16]);
        std20l =  checkDouble(parsedStockData[17]);
        std20h =  checkDouble(parsedStockData[18]);
        sma50 =  checkDouble(parsedStockData[19]);
        std50 =  checkDouble(parsedStockData[20]);
        std50l =  checkDouble(parsedStockData[21]);
        std50h =  checkDouble(parsedStockData[22]);
        sma100 =  checkDouble(parsedStockData[23]);
        std100 =  checkDouble(parsedStockData[24]);
        std100l =  checkDouble(parsedStockData[25]);
        std100h =  checkDouble(parsedStockData[26]);
        sma250 =  checkDouble(parsedStockData[27]);
        std250 =  checkDouble(parsedStockData[28]);
        std250l =  checkDouble(parsedStockData[29]);
        std250h =  checkDouble(parsedStockData[30]);
        l20 =  checkDouble(parsedStockData[31]);
        h20 =  checkDouble(parsedStockData[32]);
        l50 =  checkDouble(parsedStockData[33]);
        h50 =  checkDouble(parsedStockData[34]);
        l100 =  checkDouble(parsedStockData[35]);
        h100 =  checkDouble(parsedStockData[36]);
        l250 =  checkDouble(parsedStockData[37]);
        h250 =  checkDouble(parsedStockData[38]);
        uptime = parsedStockData[8];
        name_chi = parsedStockData[10];
        industry = parsedStockData[11];

        dbFunction = new StockDbFunction(context);
        dbAFunction = new StockAlertDbFunction(context);

        // Add guest info to mDb
        dbFunction.replace(
                id, name, code, date,
                price, netChange, pe, high, low, volume,
                dy, dps, eps, sma20, std20, std20l, std20h, sma50, std50, std50l, std50h,
                sma100, std100, std100l, std100h, sma250, std250, std250l, std250h,
                l20, h20, l50, h50, l100, h100, l250, h250,
                uptime, name_chi, industry
        );
        //add default alert for each stock
        dbAFunction.insert(name, code, 1);
        dbAFunction.insert(name, code, 0);


    }
}
