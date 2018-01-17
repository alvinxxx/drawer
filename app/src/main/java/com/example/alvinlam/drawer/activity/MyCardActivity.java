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
import android.view.View;
import android.widget.EditText;

import com.example.alvinlam.drawer.R;
import com.example.alvinlam.drawer.data.Stock;
import com.example.alvinlam.drawer.data.old.CardlistContract;
import com.example.alvinlam.drawer.utilities.NotificationUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alvin Lam on 3/29/2017.
 */

public class MyCardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MyCardActivity.class.getSimpleName();
    private static final String SHARE_HASHTAG = " #PocketCard";

    private SQLiteDatabase mDb;
    private Cursor cursor;

    private EditText mNameEditText;
    private EditText mPhoneEditText;
    private EditText mEmailEditText;
    private EditText mTitleEditText;
    private EditText mWebsiteEditText;
    private EditText mCompanyEditText;
    private EditText mCPhoneEditText;
    private EditText mCAddressEditText;

    private String name;
    private int phone;
    private String email;
    private String title;
    private String website;
    private String company;
    private int companyTelephone;
    private String companyAddress;

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


    }

    // COMPLETED (14) Create a method called testNotification that triggers NotificationUtils' remindUserBecauseCharging
    public void testNotification(View view) {
        NotificationUtils.remindUserBecauseCharging(this);
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


}
