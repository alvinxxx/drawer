package com.example.alvinlam.drawer.data.old;

import android.provider.BaseColumns;

/**
 * Created by Alvin Lam on 3/26/2017.
 */

public class CardlistContract {

    public static final class CardlistEntry implements BaseColumns {
        public static final String TABLE_NAME = "cardlist";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PHONE = "telephone";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_WEBSITE = "website";
        public static final String COLUMN_COMPANY = "company";
        public static final String COLUMN_COMPANY_PHONE = "companyTelephone";
        public static final String COLUMN_COMPANY_ADDRESS = "companyAddress";
    }

    public static final class MyCardEntry implements BaseColumns {
        public static final String TABLE_NAME = "mycard";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PHONE = "telephone";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_WEBSITE = "website";
        public static final String COLUMN_COMPANY = "company";
        public static final String COLUMN_COMPANY_PHONE = "companyTelephone";
        public static final String COLUMN_COMPANY_ADDRESS = "companyAddress";
    }
}
