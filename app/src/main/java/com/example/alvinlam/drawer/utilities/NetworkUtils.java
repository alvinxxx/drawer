package com.example.alvinlam.drawer.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Alvin Lam on 10/29/2017.
 */

public class NetworkUtils {

    final static String STOCK_BASE_URL =
            "https://www.quandl.com/api/v3/datasets/HKEX/";
    final static String FORMAT = ".json";
    final static String ROWS_PARAM = "rows";
    private static final int rows = 1;
    private static final String TAG = "NetworkUtils";

    /**
     * Builds the URL used to query Stock.
     *
     * @param stockSearchQuery The keyword that will be queried for.
     * @return The URL to use to query the weather server.
     */
    public static URL buildUrl(String stockSearchQuery) {

        stockSearchQuery = String.format("%05d", Integer.parseInt(stockSearchQuery));

        // build the proper query URL
        String stock_url = STOCK_BASE_URL + stockSearchQuery + FORMAT;
        Uri builtUri = Uri.parse(stock_url).buildUpon()
                .appendQueryParameter(ROWS_PARAM, Integer.toString(rows))
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



    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
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