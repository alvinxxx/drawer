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
    private long id = 0;
    private int edit = 0;
    private int phone=0, cphone=0;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        Toolbar toolbar = (Toolbar) findViewById(R.id.add_card_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                id = intentThatStartedThisActivity.getLongExtra(Intent.EXTRA_TEXT, 0);

                cursor = getCard(id);

                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();

                    //set edit to 1 as True
                    edit = 1;

                    //Log.i(AddCardActivity.class.getName(), String.valueOf(cursor.getColumnIndex("name")));

                    String name = cursor.getString(cursor.getColumnIndex(CardlistContract.CardlistEntry.COLUMN_NAME));
                    int phone = cursor.getInt(cursor.getColumnIndex(CardlistContract.CardlistEntry.COLUMN_PHONE));
                    String email = cursor.getString(cursor.getColumnIndex(CardlistContract.CardlistEntry.COLUMN_EMAIL));
                    String title = cursor.getString(cursor.getColumnIndex(CardlistContract.CardlistEntry.COLUMN_TITLE));
                    String website = cursor.getString(cursor.getColumnIndex(CardlistContract.CardlistEntry.COLUMN_WEBSITE));
                    String company = cursor.getString(cursor.getColumnIndex(CardlistContract.CardlistEntry.COLUMN_EMAIL));
                    int companyTelephone = cursor.getInt(cursor.getColumnIndex(CardlistContract.CardlistEntry.COLUMN_COMPANY_PHONE));
                    String companyAddress = cursor.getString(cursor.getColumnIndex(CardlistContract.CardlistEntry.COLUMN_COMPANY_ADDRESS));

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
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_card_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_send) {
            addToCardlist();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Cursor getCard(long id) {
        return mDb.query(
                CardlistContract.CardlistEntry.TABLE_NAME,
                null,
                CardlistContract.CardlistEntry._ID + "=" + id,
                null,
                null,
                null,
                CardlistContract.CardlistEntry.COLUMN_TIMESTAMP
        );
    }



    public void addToCardlist() {
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

    private long addNewCard(int edit, String name, int phone, String email, String title, String website, String company, int cphone, String caddress) {

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
        if (edit == 1)
            mDb.update(CardlistContract.CardlistEntry.TABLE_NAME, cv, CardlistContract.CardlistEntry._ID + "=" + id, null);
        else
            mDb.insert(CardlistContract.CardlistEntry.TABLE_NAME, null, cv);

        return 0;
    }


}
