package com.example.alvinlam.drawer.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.alvinlam.drawer.data.StocklistContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alvin Lam on 10/30/2017.
 */

public class OpenStockJsonUtils {
    /**
     * This method parses JSON from a web response and returns an array of Strings
     *
     * Later on, we'll be parsing the JSON into structured data within the
     * getFullWeatherDataFromJson function, leveraging the data we have stored in the JSON. For
     * now, we just convert the JSON into human-readable strings.
     *
     * @param stockJsonStr JSON response from server
     *
     * @return Array of Strings
     *
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static String[] getSimpleStockStringsFromJson(Context context, String stockJsonStr)
            throws JSONException {

        final String QUA_DATASET = "dataset";
        final String QUA_NAME = "name";

        final String QUA_COL = "column_names";
        final String QUA_DATA = "data";

        final String QUA_ERROR_CODE = "quandl_error";
        final String QUA_ERROR_MESSAGE = "message";


        /* String array to hold stock String */
        String[] parsedStockData = null;

        JSONObject stockJson = new JSONObject(stockJsonStr);

        /* Is there an error? */
        if (stockJson.has(QUA_ERROR_CODE)) {
            parsedStockData[0] = stockJson.getString(QUA_ERROR_MESSAGE);
            return parsedStockData;
        }

        JSONObject stockDataSet = stockJson.getJSONObject(QUA_DATASET);

        String name = stockDataSet.getString(QUA_NAME);

        JSONArray colArray = stockDataSet.getJSONArray(QUA_COL);
        JSONArray dataArray = stockDataSet.getJSONArray(QUA_DATA);

        parsedStockData = new String[colArray.length()];
        JSONArray valueArray = dataArray.getJSONArray(0);

        for (int i = 0; i < colArray.length()-1; i++) {
                parsedStockData[i] = colArray.getString(i) + " : " + valueArray.getString(i);
        }
        parsedStockData[colArray.length()-1]=name;
        Log.d("tag", "getSimpleStockStringsFromJson: "+parsedStockData[12]);
        return parsedStockData;
    }



    public static String[] getFullStockDataFromJson(String stockJsonStr)
            throws JSONException {
        final String QUA_DATASET = "dataset";
        final String QUA_NAME = "name";
        final String QUA_CODE = "dataset_code";
        final String QUA_DATA = "data";
        final String QUA_ERROR_CODE = "quandl_error";

        String name;
        String code;
        String date;
        String price;
        String netChange;
        String pe;
        String high;
        String low;
        String preClose;
        String volume;
        String turnover;
        String lot;

        JSONObject stockJson = new JSONObject(stockJsonStr);

        /* Is there an error? */
        if (stockJson.has(QUA_ERROR_CODE)) {
            return null;
        }

        JSONObject stockDataSet = stockJson.getJSONObject(QUA_DATASET);

        name = stockDataSet.getString(QUA_NAME);
        code = stockDataSet.getString(QUA_CODE);

        JSONArray valueArray = stockDataSet.getJSONArray(QUA_DATA).getJSONArray(0);

        date = valueArray.getString(0);
        price = valueArray.getString(1);
        netChange = valueArray.getString(3);
        pe = valueArray.getString(6);
        high = valueArray.getString(7);
        low = valueArray.getString(8);
        preClose = valueArray.getString(9);
        volume = valueArray.getString(10);
        turnover = valueArray.getString(11);
        lot = valueArray.getString(12);

        /* String array to hold stock String */
        String[] parsedStockData = new String[12];
        parsedStockData[0] = name;
        parsedStockData[1] = code;
        parsedStockData[2] = date;
        parsedStockData[3] = price;
        parsedStockData[4] = netChange;
        parsedStockData[5] = pe;
        parsedStockData[6] = high;
        parsedStockData[7] = low;
        parsedStockData[8] = preClose;
        parsedStockData[9] = volume;
        parsedStockData[10] = turnover;
        parsedStockData[11] = lot;

        return parsedStockData;
    }

    public static String[] getFullStockDataFromArray(String[] stockJsonStrArray)
            throws JSONException {
        //String[] fullJsonStockData  = new String[0];
        String name, code, date, price, netChange, pe, high, low, preClose, volume, turnover, lot,
                dy, dps, eps, sma20, std20, std20l, std20h, sma50, std50, std50l, std50h,
                sma100, std100, std100l, std100h, sma250, std250, std250l, std250h,
                l20, h20, l50, h50, l100, h100, l250, h250;

        String base = stockJsonStrArray[0];
        final String QUA_DATASET = "dataset";
        final String QUA_NAME = "name";
        final String QUA_CODE = "dataset_code";
        final String QUA_DATA = "data";
        final String QUA_ERROR_CODE = "quandl_error";

        JSONObject stockJson = new JSONObject(base);

        /* Is there an error? */
        if (stockJson.has(QUA_ERROR_CODE)) {
            return null;
        }

        JSONObject stockDataSet = stockJson.getJSONObject(QUA_DATASET);

        name = stockDataSet.getString(QUA_NAME);
        code = stockDataSet.getString(QUA_CODE);

        JSONArray valueArray = stockDataSet.getJSONArray(QUA_DATA).getJSONArray(0);

        date = valueArray.getString(0);
        price = valueArray.getString(1);
        netChange = valueArray.getString(3);
        //pe = valueArray.getString(6);
        high = valueArray.getString(7);
        low = valueArray.getString(8);
        preClose = valueArray.getString(9);
        volume = valueArray.getString(10);
        turnover = valueArray.getString(11);
        lot = valueArray.getString(12);

        ///
        String baseF = stockJsonStrArray[1];
        //Log.d("openjson", "getFullStockDataFromArray: "+baseF);

        final String DY = "dy";
        final String DPS = "dps";
        final String PE = "pe";
        final String EPS = "eps";

        valueArray = new JSONArray(baseF);
        //W/System.err: org.json.JSONException: Index 2 out of range [0..1)
        stockDataSet = valueArray.getJSONObject(0);

        dy = stockDataSet.getString(DY);
        dps = stockDataSet.getString(DPS);
        pe = stockDataSet.getString(PE);
        eps = stockDataSet.getString(EPS);
        //Log.d("openjson", "getFullStockDataFromArray3: "+dps);

        ///
        String baseT = stockJsonStrArray[2];
        final String SMA20 = "mean_20";
        final String STD20 = "std_20";
        final String STD20L = "std_20l";
        final String STD20H = "std_20h";
        final String SMA50 = "mean_50";
        final String STD50 = "std_50";
        final String STD50L = "std_50l";
        final String STD50H = "std_50h";
        final String SMA100 = "mean_100";
        final String STD100 = "std_100";
        final String STD100L = "std_100l";
        final String STD100H = "std_100h";
        final String SMA250 = "mean_250";
        final String STD250 = "std_250";
        final String STD250L = "std_250l";
        final String STD250H = "std_250h";
        final String L20 = "low_20";
        final String H20 = "high_20";
        final String L50 = "low_50";
        final String H50 = "high_50";
        final String L100 = "low_100";
        final String H100 = "high_100";
        final String L250 = "low_250";
        final String H250 = "high_250";

        valueArray = new JSONArray(baseT);
        stockJson = valueArray.getJSONObject(0);

        sma20 = stockJson.getString(SMA20);
        std20 = stockJson.getString(STD20);
        std20l = stockJson.getString(STD20L);
        std20h = stockJson.getString(STD20H);
        sma50 = stockJson.getString(SMA50);
        std50 = stockJson.getString(STD50);
        std50l = stockJson.getString(STD50L);
        std50h = stockJson.getString(STD50H);
        sma100 = stockJson.getString(SMA100);
        std100 = stockJson.getString(STD100);
        std100l = stockJson.getString(STD100L);
        std100h = stockJson.getString(STD100H);
        sma250 = stockJson.getString(SMA250);
        std250 = stockJson.getString(STD250);
        std250l = stockJson.getString(STD250L);
        std250h = stockJson.getString(STD250H);
        l20 = stockJson.getString(L20);
        h20 = stockJson.getString(H20);
        l50 = stockJson.getString(L50);
        h50 = stockJson.getString(H50);
        l100 = stockJson.getString(L100);
        h100 = stockJson.getString(H100);
        l250 = stockJson.getString(L250);
        h250 = stockJson.getString(H250);



        /* String array to hold stock String */
        String[] parsedStockData = new String[39];
        parsedStockData[0] = name;
        parsedStockData[1] = code;
        parsedStockData[2] = date;
        parsedStockData[3] = price;
        parsedStockData[4] = netChange;
        parsedStockData[5] = pe;
        parsedStockData[6] = high;
        parsedStockData[7] = low;
        parsedStockData[8] = preClose;
        parsedStockData[9] = volume;
        parsedStockData[10] = turnover;
        parsedStockData[11] = lot;
        parsedStockData[12] = dy;
        parsedStockData[13] = dps;
        parsedStockData[14] = eps;
        parsedStockData[15] = sma20;
        parsedStockData[16] = std20;
        parsedStockData[17] = std20l;
        parsedStockData[18] = std20h;
        parsedStockData[19] = sma50;
        parsedStockData[20] = std50;
        parsedStockData[21] = std50l;
        parsedStockData[22] = std50h;
        parsedStockData[23] = sma100;
        parsedStockData[24] = std100;
        parsedStockData[25] = std100l;
        parsedStockData[26] = std100h;
        parsedStockData[27] = sma250;
        parsedStockData[28] = std250;
        parsedStockData[29] = std250l;
        parsedStockData[30] = std250h;
        parsedStockData[31] = l20;
        parsedStockData[32] = h20;
        parsedStockData[33] = l50;
        parsedStockData[34] = h50;
        parsedStockData[35] = l100;
        parsedStockData[36] = h100;
        parsedStockData[37] = l250;
        parsedStockData[38] = h250;

        return parsedStockData;
    }


    /**
     * Parse the JSON and convert it into ContentValue that can be inserted into our database.
     *
     * @param context         An application context, such as a service or activity context.
     * @param stockJsonStr The JSON to parse into ContentValue.
     *
     * @return A ContentValue parsed from the JSON.
     */
    public static String[][] getChartDataFromJson(Context context, String stockJsonStr)
            throws JSONException {
        final String QUA_DATASET = "dataset";
        final String QUA_NAME = "name";
        final String QUA_CODE = "dataset_code";
        final String QUA_DATA = "data";
        final String QUA_ERROR_CODE = "quandl_error";

        String name;
        String code;
        String date;
        String price;
        int days = 5;
        String[][] parsedStockData = new String[days][2];


        JSONObject stockJson = new JSONObject(stockJsonStr);

        /* Is there an error? */
        if (stockJson.has(QUA_ERROR_CODE)) {
            return null;
        }

        JSONObject stockDataSet = stockJson.getJSONObject(QUA_DATASET);

        name = stockDataSet.getString(QUA_NAME);
        code = stockDataSet.getString(QUA_CODE);

        for (int i=0;i<days;i++){
            JSONArray valueArray = stockDataSet.getJSONArray(QUA_DATA).getJSONArray(i);
            date = valueArray.getString(0);
            price = valueArray.getString(1);

            parsedStockData[i][0] = date;
            parsedStockData[i][1] = price;
            //Log.d("a", "getChartDataFromJson: "+parsedStockData[i][0]);
            //Log.d("b", "getChartDataFromJson: "+parsedStockData[i][1]);


        }



        return parsedStockData;
    }
}
