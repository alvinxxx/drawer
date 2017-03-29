package com.example.alvinlam.drawer.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.alvinlam.drawer.R;
import com.example.alvinlam.drawer.adapter.CardlistAdapter;
import com.example.alvinlam.drawer.data.CardlistContract;
import com.example.alvinlam.drawer.data.CardlistDbHelper;

/**
 * Created by Alvin Lam on 3/29/2017.
 */

public class MyCardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, CardlistAdapter.ListItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private CardlistAdapter mAdapter;
    private RecyclerView cardlistRecyclerView;
    private SQLiteDatabase mDb;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.main_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Class destinationClass = AddCardActivity.class;
                Intent intentToStartAddCardActivity = new Intent(MyCardActivity.this, destinationClass);
                startActivity(intentToStartAddCardActivity);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.drawer_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        cardlistRecyclerView = (RecyclerView) findViewById(R.id.main_card_list_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        cardlistRecyclerView.setLayoutManager(layoutManager);
        cardlistRecyclerView.setHasFixedSize(true);

        CardlistDbHelper dbHelper = new CardlistDbHelper(this);
        mDb = dbHelper.getWritableDatabase();

        cursor = getAllCards();
        mAdapter = new CardlistAdapter(this, cursor, this);
        cardlistRecyclerView.setAdapter(mAdapter);
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

    @Override
    public void onListItemClick(View v, int position) {
        Context context = this;
        Class destinationClass = AddCardActivity.class;

        long id = (long) v.getTag();

        Intent intentToStartAddCardActivity = new Intent(context, destinationClass);
        intentToStartAddCardActivity.putExtra(Intent.EXTRA_TEXT, id);
        startActivity(intentToStartAddCardActivity);
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
        intentToStartActivity.putExtra(Intent.EXTRA_TEXT, id);
        startActivity(intentToStartActivity);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private Cursor getAllCards() {
        return mDb.query(
                CardlistContract.CardlistEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                CardlistContract.CardlistEntry.COLUMN_TIMESTAMP
        );
    }
}
