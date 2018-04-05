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
        public static final String COLUMN_DY = "dy";
        public static final String COLUMN_DPS = "dps";
        public static final String COLUMN_EPS = "eps";
        public static final String COLUMN_SMA20 = "sma20";
        public static final String COLUMN_STD20 = "std20";
        public static final String COLUMN_STD20L = "std20l";
        public static final String COLUMN_STD20H = "std20h";
        public static final String COLUMN_SMA50 = "sma50";
        public static final String COLUMN_STD50 = "std50";
        public static final String COLUMN_STD50L = "std50l";
        public static final String COLUMN_STD50H = "std50h";
        public static final String COLUMN_SMA100 = "sma100";
        public static final String COLUMN_STD100 = "std100";
        public static final String COLUMN_STD100L = "std100l";
        public static final String COLUMN_STD100H = "std100h";
        public static final String COLUMN_SMA250 = "sma250";
        public static final String COLUMN_STD250 = "std250";
        public static final String COLUMN_STD250L = "std250l";
        public static final String COLUMN_STD250H = "std250h";
        public static final String COLUMN_20L = "l20";
        public static final String COLUMN_20H = "h20";
        public static final String COLUMN_50L = "l50";
        public static final String COLUMN_50H = "h50";
        public static final String COLUMN_100L = "l100";
        public static final String COLUMN_100H = "h100";
        public static final String COLUMN_250L = "l250";
        public static final String COLUMN_250H = "h250";
    }

    public static final class StockAlertEntry implements BaseColumns {
        public static final String TABLE_NAME = "stockAlert";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_CODE = "code";
        public static final String COLUMN_ACTIVE = "active";
        public static final String COLUMN_BUY = "buy";
        public static final String COLUMN_INDICATOR = "indicator";
        public static final String COLUMN_CONDITION = "condition";
        public static final String COLUMN_WINDOW = "window";
        public static final String COLUMN_TARGET = "target";
        public static final String COLUMN_DISTANCE = "distance";
    }

    public static final class RiskAssessQuestionEntry implements BaseColumns {
        public static final String TABLE_NAME = "raq";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_QUESTION = "question";
    }

    public static final class RiskAssessAnswerEntry implements BaseColumns {
        public static final String TABLE_NAME = "raa";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_ANSWER = "answer";
        public static final String COLUMN_SCORE = "score";
        public static final String COLUMN_QID = "qid";
    }

    public static final class RiskAssessRecordEntry implements BaseColumns {
        public static final String TABLE_NAME = "rar";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_QID= "qid";
        public static final String COLUMN_SCORE = "score";
    }
}
