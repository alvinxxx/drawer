package com.example.alvinlam.drawer.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.alvinlam.drawer.R;
import com.example.alvinlam.drawer.data.CardlistContract;
import com.example.alvinlam.drawer.data.CardlistDbHelper;


public class AddCardActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mPhoneEditText;
    private EditText mEmailEditText;
    private EditText mTitleEditText;
    private EditText mWebsiteEditText;
    private EditText mCompanyEditText;
    private EditText mCPhoneEditText;
    private EditText mCAddressEditText;

    private SQLiteDatabase mDb;
    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        CardlistDbHelper dbHelper = new CardlistDbHelper(this);
        mDb = dbHelper.getWritableDatabase();

        mNameEditText = (EditText) this.findViewById(R.id.add_name_editText);
        mPhoneEditText = (EditText) this.findViewById(R.id.add_phone_editText);
        mEmailEditText = (EditText) this.findViewById(R.id.add_email_editText);
        mTitleEditText = (EditText) this.findViewById(R.id.add_title_editText);
        mWebsiteEditText = (EditText) this.findViewById(R.id.add_web_editText);
        mCompanyEditText = (EditText) this.findViewById(R.id.add_company_editText);
        mCPhoneEditText = (EditText) this.findViewById(R.id.add_company_phone_editText);
        mCAddressEditText = (EditText) this.findViewById(R.id.add_company_address_editText);
    }

    public void addToCardlist(View view) {
        if (mNameEditText.getText().length() == 0 ||
                mPhoneEditText.getText().length() == 0 &&
                mEmailEditText.getText().length() == 0 &&
                mTitleEditText.getText().length() == 0 &&
                mWebsiteEditText.getText().length() == 0 &&
                mCompanyEditText.getText().length() == 0 &&
                mCPhoneEditText.getText().length() == 0 &&
                mCAddressEditText.getText().length() == 0) {
            return;
        }

        int phone=0, cphone=0;
        try {
            phone = Integer.parseInt(mPhoneEditText.getText().toString());
            cphone = Integer.parseInt(mPhoneEditText.getText().toString());
        } catch (NumberFormatException ex) {
            Log.e(LOG_TAG, "Failed to parse to number: " + ex.getMessage());
        }

        // Add guest info to mDb
        addNewCard(mNameEditText.getText().toString(),
                phone,
                mEmailEditText.getText().toString(),
                mTitleEditText.getText().toString(),
                mWebsiteEditText.getText().toString(),
                mCompanyEditText.getText().toString(),
                cphone,
                mCAddressEditText.getText().toString()
                );


        //clear UI text fields
        mNameEditText.getText().clear();
        mPhoneEditText.getText().clear();
        mEmailEditText.getText().clear();
        mTitleEditText.getText().clear();
        mWebsiteEditText.getText().clear();
        mCompanyEditText.getText().clear();
        mCPhoneEditText.getText().clear();
        mCAddressEditText.getText().clear();

        Context context = this;
        Class destinationClass = MainActivity.class;
        Intent intentToStartMainActivity = new Intent(context, destinationClass);
        startActivity(intentToStartMainActivity);
    }

    private long addNewCard(String name, int phone, String email, String title, String website, String company, int cphone, String caddress) {

        ContentValues cv = new ContentValues();
        cv.put(CardlistContract.CardlistEntry.COLUMN_NAME, name);
        cv.put(CardlistContract.CardlistEntry.COLUMN_PHONE, phone);
        cv.put(CardlistContract.CardlistEntry.COLUMN_EMAIL, email);
        cv.put(CardlistContract.CardlistEntry.COLUMN_TITLE, title);
        cv.put(CardlistContract.CardlistEntry.COLUMN_WEBSITE, website);
        cv.put(CardlistContract.CardlistEntry.COLUMN_COMPANY, company);
        cv.put(CardlistContract.CardlistEntry.COLUMN_COMPANY_PHONE, cphone);
        cv.put(CardlistContract.CardlistEntry.COLUMN_COMPANY_ADDRESS, caddress);

        // call insert to run an insert query on TABLE_NAME with the ContentValues created
        return mDb.insert(CardlistContract.CardlistEntry.TABLE_NAME, null, cv);
    }


}
