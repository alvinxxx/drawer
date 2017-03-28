package com.example.alvinlam.drawer.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.alvinlam.drawer.R;
import com.example.alvinlam.drawer.adapter.CardlistAdapter;
import com.example.alvinlam.drawer.data.CardlistContract;
import com.example.alvinlam.drawer.data.CardlistDbHelper;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, CardlistAdapter.ListItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private  static  final int NUM_LIST_ITEMS = 100;
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
                Intent intentToStartAddCardActivity = new Intent(MainActivity.this, destinationClass);
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

        // Create an item touch helper to handle swiping items off the list
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            // Override onMove and simply return false inside
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //do nothing, we only care about swiping
                return false;
            }

            // Override onSwiped
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Inside, get the viewHolder's itemView's tag and store in a long variable id
                //get the id of the item being swiped
                long id = (long) viewHolder.itemView.getTag();
                //remove from DB
                removeCard(id);
                //update the list
                mAdapter.swapCursor(getAllCards());
            }

            // attach the ItemTouchHelper
        }).attachToRecyclerView(cardlistRecyclerView);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_settings, menu);
        return true;
    }

    private void openLocationInMap() {
        String addressString = "1600 Ampitheatre Parkway, CA";
        Uri geoLocation = Uri.parse("geo:0,0?q=" + addressString);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d(TAG, "Couldn't call " + geoLocation.toString()
                    + ", no receiving apps installed!");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id){
            case R.id.action_settings:
                return true;
            case R.id.action_refresh:
                mAdapter = new CardlistAdapter(this, cursor, this);
                cardlistRecyclerView.setAdapter(mAdapter);
                return true;
            case R.id.action_map:
                openLocationInMap();
        }

        return super.onOptionsItemSelected(item);
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

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

    private boolean removeCard(long id) {
        //  Inside, call mDb.delete to pass in the TABLE_NAME and the condition that ._ID equals id
        return mDb.delete(CardlistContract.CardlistEntry.TABLE_NAME, CardlistContract.CardlistEntry._ID + "=" + id, null) > 0;
    }

}
