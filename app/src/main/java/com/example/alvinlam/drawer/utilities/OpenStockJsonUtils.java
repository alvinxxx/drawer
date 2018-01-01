package com.example.alvinlam.drawer.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

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
     * Parse the JSON and convert it into ContentValues that can be inserted into our database.
     *
     * @param context         An application context, such as a service or activity context.
     * @param forecastJsonStr The JSON to parse into ContentValues.
     *
     * @return An array of ContentValues parsed from the JSON.
     */
    public static ContentValues[] getFullWeatherDataFromJson(Context context, String forecastJsonStr) {
        /** This will be implemented in a future lesson **/
        return null;
    }
}
