package com.example.alvinlam.drawer.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.alvinlam.drawer.R;
import com.example.alvinlam.drawer.data.CardlistContract;


public class MyCardAddActivity extends AppCompatActivity {

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
    private long id = 0;
    private int edit = 0;
    private int phone=0, cphone=0;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_card_add_precontent);

        Toolbar toolbar = (Toolbar) findViewById(R.id.add_card_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
/*
        CardlistDbHelper dbHelper = new CardlistDbHelper(this);
        mDb = dbHelper.getWritableDatabase();

        mNameEditText = (EditText) this.findViewById(R.id.add_name_editText);
        mPhoneEditText = (EditText) this.findViewById(R.id.add_code_editText);
        mEmailEditText = (EditText) this.findViewById(R.id.add_net_change_editText);
        mTitleEditText = (EditText) this.findViewById(R.id.add_date_editText);
        mWebsiteEditText = (EditText) this.findViewById(R.id.add_price_editText);
        mCompanyEditText = (EditText) this.findViewById(R.id.add_pe_editText);
        mCPhoneEditText = (EditText) this.findViewById(R.id.add_high_editText);
        mCAddressEditText = (EditText) this.findViewById(R.id.add_company_address_editText);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
                cursor = getCard();

                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();

                    //set edit to 1 as True
                    edit = 1;

                    //Log.i(AddCardAddActivity.class.getName(), String.valueOf(cursor.getColumnIndex("name")));
                    id = cursor.getColumnIndex(CardlistContract.MyCardEntry._ID);

                    String name = cursor.getString(cursor.getColumnIndex(CardlistContract.MyCardEntry.COLUMN_NAME));
                    int phone = cursor.getInt(cursor.getColumnIndex(CardlistContract.MyCardEntry.COLUMN_PHONE));
                    String email = cursor.getString(cursor.getColumnIndex(CardlistContract.MyCardEntry.COLUMN_EMAIL));
                    String title = cursor.getString(cursor.getColumnIndex(CardlistContract.MyCardEntry.COLUMN_TITLE));
                    String website = cursor.getString(cursor.getColumnIndex(CardlistContract.MyCardEntry.COLUMN_WEBSITE));
                    String company = cursor.getString(cursor.getColumnIndex(CardlistContract.MyCardEntry.COLUMN_EMAIL));
                    int companyTelephone = cursor.getInt(cursor.getColumnIndex(CardlistContract.MyCardEntry.COLUMN_COMPANY_PHONE));
                    String companyAddress = cursor.getString(cursor.getColumnIndex(CardlistContract.MyCardEntry.COLUMN_COMPANY_ADDRESS));

                    mNameEditText.setText(name);
                    mPhoneEditText.setText(Integer.toString(phone));
                    mEmailEditText.setText(email);
                    mTitleEditText.setText(title);
                    mWebsiteEditText.setText(website);
                    mCompanyEditText.setText(company);
                    mCPhoneEditText.setText(Integer.toString(companyTelephone));
                    mCAddressEditText.setText(companyAddress);


                }


        }
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_card_add_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int lid = item.getItemId();
/*
        //noinspection SimplifiableIfStatement
        if (lid == R.id.action_send) {
            addToCardlist(id);
            return true;
        }else if (lid == android.R.id.home) {
            Context context = this;
            Class destinationClass = MyCardActivity.class;
            Intent intentToStartActivity = new Intent(context, destinationClass);
            intentToStartActivity.putExtra(Intent.EXTRA_TEXT, id);
            startActivity(intentToStartActivity);
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

    private Cursor getCard() {
        return mDb.query(
                CardlistContract.MyCardEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                CardlistContract.MyCardEntry.COLUMN_TIMESTAMP
        );
    }



    public void addToCardlist(long bid) {
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


        try {
            phone = Integer.parseInt(mPhoneEditText.getText().toString());
            cphone = Integer.parseInt(mPhoneEditText.getText().toString());
        } catch (NumberFormatException ex) {
            Log.e(LOG_TAG, "Failed to parse to number: " + ex.getMessage());
        }
        // Add guest info to mDb
        addNewCard(edit,
                mNameEditText.getText().toString(),
                phone,
                mEmailEditText.getText().toString(),
                mTitleEditText.getText().toString(),
                mWebsiteEditText.getText().toString(),
                mCompanyEditText.getText().toString(),
                cphone,
                mCAddressEditText.getText().toString()
        );

        Context context = this;
        Class destinationClass = MyCardActivity.class;
        Intent intentToStartMainActivity = new Intent(context, destinationClass);
        startActivity(intentToStartMainActivity);
    }

    private long addNewCard(int edit, String mname, int phone, String email, String title, String website, String company, int cphone, String caddress) {

        ContentValues cv = new ContentValues();
        cv.put(CardlistContract.MyCardEntry.COLUMN_NAME, mname);
        cv.put(CardlistContract.MyCardEntry.COLUMN_PHONE, phone);
        cv.put(CardlistContract.MyCardEntry.COLUMN_EMAIL, email);
        cv.put(CardlistContract.MyCardEntry.COLUMN_TITLE, title);
        cv.put(CardlistContract.MyCardEntry.COLUMN_WEBSITE, website);
        cv.put(CardlistContract.MyCardEntry.COLUMN_COMPANY, company);
        cv.put(CardlistContract.MyCardEntry.COLUMN_COMPANY_PHONE, cphone);
        cv.put(CardlistContract.MyCardEntry.COLUMN_COMPANY_ADDRESS, caddress);

        //Log.i("add", "addToCardlist:cvout  "+ cv.get(CardlistContract.MyCardEntry.COLUMN_NAME));

        // call insert to run an insert query on TABLE_NAME with the ContentValues created
        if (edit == 1){
            //Log.i("add", "addToCardlist:cv "+ cv.get(CardlistContract.MyCardEntry.COLUMN_NAME));

            mDb.update(CardlistContract.MyCardEntry.TABLE_NAME, cv, null, null);
            //Log.i("add", "addToCardlist: "+cursor.getString(cursor.getColumnIndex(CardlistContract.MyCardEntry.COLUMN_NAME)));
        }
        else {
            mDb.insert(CardlistContract.MyCardEntry.TABLE_NAME, null, cv);
        }
        return 0;
    }


}
