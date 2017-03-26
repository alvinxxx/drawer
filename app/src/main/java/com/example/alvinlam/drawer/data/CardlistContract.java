package com.example.alvinlam.drawer.data;

import android.provider.BaseColumns;

/**
 * Created by Alvin Lam on 3/26/2017.
 */

public class CardlistContract {

    public static final class CardlistEntry implements BaseColumns {
        public static final String TABLE_NAME = "cardlist";
        public static final String COLUMN_TIMESTAMP = "Timestamp";
        public static final String COLUMN_NAME = "Name";
        public static final String COLUMN_PHONE = "Telephone No.";
        public static final String COLUMN_EMAIL = "Email address";
        public static final String COLUMN_TITLE = "Job title";
        public static final String COLUMN_WEBSITE = "Website";
        public static final String COLUMN_COMPANY = "Company name";
        public static final String COLUMN_COMPANY_PHONE = "Company phone number";
        public static final String COLUMN_COMPANY_ADDRESS = "Company address";
    }
}
