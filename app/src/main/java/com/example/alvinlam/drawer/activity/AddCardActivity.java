package com.example.alvinlam.drawer.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.alvinlam.drawer.R;
import com.example.alvinlam.drawer.data.CardlistContract;
import com.example.alvinlam.drawer.data.CardlistDbHelper;
import com.example.alvinlam.drawer.data.DbFunction;


public class AddCardActivity extends AppCompatActivity{

    private static final String TAG = AddCardActivity.class.getSimpleName();

    private SQLiteDatabase mDb;
    private Cursor cursor;
    private DbFunction dbFunction;
    private long id = 0;
    private int edit = 0;
    private int phone=0, cphone=0;

    private EditText mNameEditText;
    private EditText mPhoneEditText;
    private EditText mEmailEditText;
    private EditText mTitleEditText;
    private EditText mWebsiteEditText;
    private EditText mCompanyEditText;
    private EditText mCPhoneEditText;
    private EditText mCAddressEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_card);

        Toolbar toolbar = (Toolbar) findViewById(R.id.add_card_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dbFunction = new DbFunction(this);

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

                cursor = dbFunction.selectByID(id);

                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();


                    //Log.i(AddCardAddActivity.class.getName(), String.valueOf(cursor.getColumnIndex("name")));

                    String name = cursor.getString(cursor.getColumnIndex(CardlistContract.CardlistEntry.COLUMN_NAME));
                    int phone = cursor.getInt(cursor.getColumnIndex(CardlistContract.CardlistEntry.COLUMN_PHONE));
                    String email = cursor.getString(cursor.getColumnIndex(CardlistContract.CardlistEntry.COLUMN_EMAIL));
                    String title = cursor.getString(cursor.getColumnIndex(CardlistContract.CardlistEntry.COLUMN_TITLE));
                    String website = cursor.getString(cursor.getColumnIndex(CardlistContract.CardlistEntry.COLUMN_WEBSITE));
                    String company = cursor.getString(cursor.getColumnIndex(CardlistContract.CardlistEntry.COLUMN_COMPANY));
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

        disableEditText(mNameEditText);
        disableEditText(mPhoneEditText);
        disableEditText(mEmailEditText);
        disableEditText(mTitleEditText);
        disableEditText(mWebsiteEditText);
        disableEditText(mCompanyEditText);
        disableEditText(mCPhoneEditText);
        disableEditText(mCAddressEditText);

    }

    public void disableEditText(EditText et){
        et.setCursorVisible(false);
        et.setLongClickable(false);
        et.setClickable(false);
        et.setFocusable(false);
        et.setSelected(false);
        et.setKeyListener(null);
        et.setBackgroundResource(android.R.color.transparent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_card_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int lid = item.getItemId();
        Context context = this;
        Class destinationClass = AddCardAddActivity.class;

        //noinspection SimplifiableIfStatement
        if (lid == R.id.action_edit) {
            Intent intentToStartActivity = new Intent(context, destinationClass);
            intentToStartActivity.putExtra(Intent.EXTRA_TEXT, id);
            startActivity(intentToStartActivity);
            return true;
        }else if (lid == android.R.id.home) {
            destinationClass = MainActivity.class;
            Intent intentToStartActivity = new Intent(context, destinationClass);
            intentToStartActivity.putExtra(Intent.EXTRA_TEXT, id);
            startActivity(intentToStartActivity);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

}
