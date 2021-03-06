package com.example.alvinlam.drawer.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.alvinlam.drawer.data.StocklistContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
        String name, code, date, price, netChange, pe, high, low, volume,
                dy, dps, eps, sma20, std20, std20l, std20h, sma50, std50, std50l, std50h,
                sma100, std100, std100l, std100h, sma250, std250, std250l, std250h,
                l20, h20, l50, h50, l100, h100, l250, h250,
                uptime, name_chi, industry;

        String baseA = stockJsonStrArray[0];

        final String DATE = "Date";
        final String HIGH = "High";
        final String LOW = "Low";
        final String CLOSE = "Close";
        final String VOLUME = "Volume";
        final String NETCHANGE = "netChange";
        final String UPTIME = "uptime";

        JSONArray valueArray = new JSONArray(baseA);
        JSONObject stockDataSet = valueArray.getJSONObject(0);

        date = stockDataSet.getString(DATE);
        high = stockDataSet.getString(HIGH);
        low = stockDataSet.getString(LOW);
        price = stockDataSet.getString(CLOSE);
        volume = stockDataSet.getString(VOLUME);
        netChange = stockDataSet.getString(NETCHANGE);
        uptime = stockDataSet.getString(UPTIME);


        String baseI = stockJsonStrArray[1];

        final String CODE = "code";
        final String NAME_ENG = "name_eng";
        final String NAME_CHI = "name_chi";
        final String INDUSTRY = "industry";

        valueArray = new JSONArray(baseI);
        stockDataSet = valueArray.getJSONObject(0);

        code = stockDataSet.getString(CODE);
        name = stockDataSet.getString(NAME_ENG);
        name_chi = stockDataSet.getString(NAME_CHI);
        industry = stockDataSet.getString(INDUSTRY);

        ///
        String baseF = stockJsonStrArray[2];

        final String DY = "dy";
        final String DPS = "dps";
        final String PE = "pe";
        final String EPS = "eps";

        valueArray = new JSONArray(baseF);
        stockDataSet = valueArray.getJSONObject(0);

        dy = stockDataSet.getString(DY);
        dps = stockDataSet.getString(DPS);
        pe = stockDataSet.getString(PE);
        eps = stockDataSet.getString(EPS);

        ///
        String baseT = stockJsonStrArray[3];
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
        stockDataSet = valueArray.getJSONObject(0);

        sma20 = stockDataSet.getString(SMA20);
        std20 = stockDataSet.getString(STD20);
        std20l = stockDataSet.getString(STD20L);
        std20h = stockDataSet.getString(STD20H);
        sma50 = stockDataSet.getString(SMA50);
        std50 = stockDataSet.getString(STD50);
        std50l = stockDataSet.getString(STD50L);
        std50h = stockDataSet.getString(STD50H);
        sma100 = stockDataSet.getString(SMA100);
        std100 = stockDataSet.getString(STD100);
        std100l = stockDataSet.getString(STD100L);
        std100h = stockDataSet.getString(STD100H);
        sma250 = stockDataSet.getString(SMA250);
        std250 = stockDataSet.getString(STD250);
        std250l = stockDataSet.getString(STD250L);
        std250h = stockDataSet.getString(STD250H);
        l20 = stockDataSet.getString(L20);
        h20 = stockDataSet.getString(H20);
        l50 = stockDataSet.getString(L50);
        h50 = stockDataSet.getString(H50);
        l100 = stockDataSet.getString(L100);
        h100 = stockDataSet.getString(H100);
        l250 = stockDataSet.getString(L250);
        h250 = stockDataSet.getString(H250);



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
        //parsedStockData[8] = preClose;
        parsedStockData[9] = volume;
        //parsedStockData[10] = turnover;
        //parsedStockData[11] = lot;
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
        parsedStockData[8] = uptime;
        parsedStockData[10] = name_chi;
        parsedStockData[11] = industry;

        return parsedStockData;
    }




    public static List<String[]> getRecommendDataFromJson(String stockJsonStr, int mode)
            throws JSONException {

        List<String[]> list = new ArrayList<>();

        String code, s_dy, s_pe;


        final String CODE = "code";
        final String S_DY = "s_dy";
        final String S_PE = "s_pe";

        JSONArray valueArray = new JSONArray(stockJsonStr);

        for (int i=0;i<valueArray.length();i++){
            String[] row = new String[2];
            JSONObject stockDataSet = valueArray.getJSONObject(i);
            code = stockDataSet.getString(CODE);
            row[0] = code;

            if(mode==0){
                s_dy = stockDataSet.getString(S_DY);
                row[1] = s_dy;
            }else{
                s_pe = stockDataSet.getString(S_PE);
                row[1] = s_pe;
            }

            list.add(row);
        }

        return list;
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
