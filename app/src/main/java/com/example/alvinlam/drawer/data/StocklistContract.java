package com.example.alvinlam.drawer.data;

import android.provider.BaseColumns;

/**
 * Created by Alvin Lam on 3/26/2017.
 */

public class StocklistContract {

    public static final class StocklistEntry implements BaseColumns {
        public static final String TABLE_NAME = "stocklist";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_CODE = "code";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_NET_CHANGE = "netChange";
        public static final String COLUMN_PE = "pe";
        public static final String COLUMN_HIGH = "high";
        public static final String COLUMN_LOW = "low";
        public static final String COLUMN_PRE_CLOSE = "preClose";
        public static final String COLUMN_VOLUME = "volume";
        public static final String COLUMN_TURNOVER = "turnover";
        public static final String COLUMN_LOT = "lot";
    }

}
