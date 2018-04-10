package com.example.alvinlam.drawer.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

/**
 * Created by Alvin Lam on 10/29/2017.
 */

public class NetworkUtils {

    final static String STOCK_BASE_URL =
            "https://www.quandl.com/api/v3/datasets/HKEX/";
    final static String FORMAT = ".json";
    final static String ROWS_PARAM = "rows";
    final static String KEY_PARAM = "api_key";
    final static String COLS_PARAM = "column_index";
    final static String ORDER_PARAM = "asc";
    private static final int rows = 1;
    private static final int cols = 1;
    private static final String asc = "asc";
    private static final String key = "MmE7qENKc5bKfKzb2JoP";

    final static String STOCK_BASE_URL_Y =
            "https://query1.finance.yahoo.com/v10/finance/quoteSummary/";
    final static String FORMAT_Y = ".HK";
    final static String MODULES_PARAM = "modules";
    private static final String module = "financialData";

    final static String STOCK_BASE_URL_F =
            "http://alvinxxx.ddns.net:3000/f/";
    final static String STOCK_BASE_URL_T =
            "http://alvinxxx.ddns.net:3001/t/";

    //20180411
    // ?_where=(catn,eq,1)&_sort=-s_pe
    final static String STOCK_BASE_URL_R =
            "http://alvinxxx.ddns.net:3002/r/";

    // 1/?_sort=-date&_size=1
    private final static String SORT = "_sort";
    private final static String SIZE = "_size";
    private final static String WHERE = "_where";

    private static final String desc = "-";
    private static final String sort_col = "date";
    private static final String sort_col_pe = "s3_pe";

    private static final int response_size = 1;

    private static final String TAG = "NetworkUtils";

    /**
     * Builds the URL used to query Stock.
     *
     * @param stockSearchQuery The keyword that will be queried for.
     * @return The URL to use to query the weather server.
     */
    public static URL buildUrl(String stockSearchQuery) {

        stockSearchQuery = String.format(Locale.getDefault(),  "%05d", Integer.parseInt(stockSearchQuery));

        // build the proper query URL
        String stock_url = STOCK_BASE_URL + stockSearchQuery + FORMAT;
        Uri builtUri = Uri.parse(stock_url).buildUpon()
                .appendQueryParameter(ROWS_PARAM, Integer.toString(rows))
                .appendQueryParameter(KEY_PARAM, key)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "buildUrl: "+ builtUri);
        return url;
    }

    public static URL buildUrl(String stockSearchQuery, int days) {

        stockSearchQuery = String.format(Locale.getDefault(),  "%05d", Integer.parseInt(stockSearchQuery));

        // build the proper query URL
        String stock_url = STOCK_BASE_URL + stockSearchQuery + FORMAT;
        Uri builtUri = Uri.parse(stock_url).buildUpon()
                .appendQueryParameter(ROWS_PARAM, Integer.toString(days))
                .appendQueryParameter(COLS_PARAM, Integer.toString(cols))
                .appendQueryParameter(ORDER_PARAM, asc)
                .appendQueryParameter(KEY_PARAM, key)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "buildUrl: "+ builtUri);
        return url;
    }

    public static URL buildUrlY(String stockSearchQuery) {
        //0001.HK?modules=defaultKeyStatistics
        stockSearchQuery = String.format(Locale.getDefault(),  "%04d", Integer.parseInt(stockSearchQuery));

        // build the proper query URL
        String stock_url = STOCK_BASE_URL_Y + stockSearchQuery + FORMAT_Y;
        Uri builtUri = Uri.parse(stock_url).buildUpon()
                .appendQueryParameter(MODULES_PARAM, module)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "buildUrlY: "+ builtUri);
        return url;
    }

    public static URL buildUrlF(String stockSearchQuery) {

        //http://alvinxxx.ddns.net:3001/f/1/?_sort=-date&_size=1
        // build the proper query URL
        String stock_url = STOCK_BASE_URL_F + stockSearchQuery;
        Uri builtUri = Uri.parse(stock_url).buildUpon()
                .appendQueryParameter(SORT, desc+sort_col)
                .appendQueryParameter(SIZE, Integer.toString(response_size))
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "buildUrlF: "+ builtUri);
        return url;
    }

    public static URL buildUrlT(String stockSearchQuery) {

        //http://alvinxxx.ddns.net:3001/t/1/?_sort=-date&_size=1
        // build the proper query URL
        String stock_url = STOCK_BASE_URL_T + stockSearchQuery;
        Uri builtUri = Uri.parse(stock_url).buildUpon()
                .appendQueryParameter(SORT, desc+sort_col)
                .appendQueryParameter(SIZE, Integer.toString(response_size))
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "buildUrlT: "+ builtUri);
        return url;
    }

    public static URL buildUrlR(int mode, int cat) {
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        DateFormat hourFormat = new SimpleDateFormat("HH");
        String date = dateFormat.format(currentTime);
        String hour = hourFormat.format(currentTime);
        long dt = Long.parseLong(date);
        int hr = Integer.parseInt(hour);

        //http://alvinxxx.ddns.net:3002/r/20180411?_where=(catn,eq,1)&_sort=-s_pe

        String CATEGORY = "catn";

        if(mode == 0){
            CATEGORY = "catn";
        }else{
            CATEGORY = "catn3";
        }

        if(hr < 17){
            dt -= 1;
            date = String.valueOf(dt);
        }

        // build the proper query URL
        String stock_url = STOCK_BASE_URL_R + date;
        Uri builtUri = Uri.parse(stock_url).buildUpon()
                .appendQueryParameter(WHERE, "("+CATEGORY+",eq,"+cat+")")
                .appendQueryParameter(SORT, desc+sort_col_pe)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "buildUrlR: "+ builtUri);
        return url;
    }

    public static boolean hasInternetConnection(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiNetwork = null;
        if (cm != null) {
            wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (wifiNetwork != null && wifiNetwork.isConnected()) {
                return true;
            }
        }
        NetworkInfo mobileNetwork = null;
        if (cm != null) {
            mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mobileNetwork != null && mobileNetwork.isConnected()) {
                return true;
            }
        }
        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnected()) {
                return true;
            }
        }
        return false;
    }



    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url, Context mContext) throws IOException {
        int response = 0;
        boolean internet = hasInternetConnection(mContext);
        System.out.println(internet);
        while(internet){
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            response = urlConnection.getResponseCode();
            if(response == HttpURLConnection.HTTP_OK){
                try {
                    InputStream in = urlConnection.getInputStream();

                    Scanner scanner = new Scanner(in);
                    scanner.useDelimiter("\\A");

                    boolean hasInput = scanner.hasNext();
                    if (hasInput) {
                        return scanner.next();
                    } else {
                        return null;
                    }
                } finally {
                    urlConnection.disconnect();
                }
            }

        }
        return null;
    }
}