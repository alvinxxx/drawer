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


    /**
     * Parse the JSON and convert it into ContentValue that can be inserted into our database.
     *
     * @param context         An application context, such as a service or activity context.
     * @param stockJsonStr The JSON to parse into ContentValue.
     *
     * @return A ContentValue parsed from the JSON.
     */
    public static String[] getFullStockDataFromJson(Context context, String stockJsonStr)
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
        int days = 20;
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
        }



        return parsedStockData;
    }
}
