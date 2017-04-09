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

/**
 * Created by Alvin Lam on 3/29/2017.
 */

public class MyCardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MyCardActivity.class.getSimpleName();

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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.drawer_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        CardlistDbHelper dbHelper = new CardlistDbHelper(this);
        //dbFunction = new DbFunction(this);
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
                cursor = getMyCard();
                        //dbFunction.select();

                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();

                    //Log.i(AddCardActivity.class.getName(), String.valueOf(cursor.getColumnIndex("name")));

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
        Class destinationClass = MyCardAddActivity.class;

        //noinspection SimplifiableIfStatement
        if (lid == R.id.action_edit) {
            Intent intentToStartActivity = new Intent(context, destinationClass);
            startActivity(intentToStartActivity);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Context context = this;
        Class destinationClass = MainActivity.class;

        if (id == R.id.nav_friend_card) {
            destinationClass = MainActivity.class;
        } else if (id == R.id.nav_my_card) {
            destinationClass = MyCardActivity.class;
        } else if (id == R.id.account) {
            destinationClass = AccountActivity.class;
        } else if (id == R.id.nav_settings) {
            destinationClass = SettingActivity.class;
        }

        Intent intentToStartActivity = new Intent(context, destinationClass);
        startActivity(intentToStartActivity);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private Cursor getMyCard() {
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
}
