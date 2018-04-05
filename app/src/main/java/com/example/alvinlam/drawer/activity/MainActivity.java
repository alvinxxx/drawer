package com.example.alvinlam.drawer.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.alvinlam.drawer.R;
import com.example.alvinlam.drawer.adapter.CardlistAdapter;
import com.example.alvinlam.drawer.data.StockAlertDbFunction;
import com.example.alvinlam.drawer.data.StockQueryTask;
import com.example.alvinlam.drawer.data.StocklistContract;
import com.example.alvinlam.drawer.data.StocklistDbHelper;
import com.example.alvinlam.drawer.data.StockDbFunction;
import com.example.alvinlam.drawer.sync.ReminderUtilities;
import com.example.alvinlam.drawer.utilities.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, CardlistAdapter.ListItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private CardlistAdapter mAdapter;
    private RecyclerView cardlistRecyclerView;
    private SQLiteDatabase mDb;
    private StockDbFunction dbFunction;
    private StockAlertDbFunction dbAFunction;

    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.main_fab);
        fab.bringToFront();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Class destinationClass = AddCardAddActivity.class;
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

        StocklistDbHelper dbHelper = new StocklistDbHelper(this);
        dbFunction = new StockDbFunction(this);
        mDb = dbHelper.getWritableDatabase();

        cursor = dbFunction.select();
        /*
        if(cursor == null){

            StockTestUtil.insertFakeData(mDb);
            cursor = dbFunction.select();
        }
        */
        Log.d("main", "onCreate: "+"2");

        if(cursor != null){
            mAdapter = new CardlistAdapter(this, cursor, this);
            cardlistRecyclerView.setAdapter(mAdapter);
        }
/*
        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            mAdapter.swapCursor(dbFunction.select());
        }
*/
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
                //String codeString = ((TextView) viewHolder.itemView.findViewById(R.id.codeATextView)).getText().toString();
                //int code = Integer.parseInt(codeString);
                cursor = dbFunction.selectByID(id);
                int code = cursor.getInt(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_CODE));

                dbAFunction = new StockAlertDbFunction(getApplicationContext());

                //remove from DB
                dbFunction.delete(id);
                dbAFunction.deleteByCode(code);
                //update the list
                mAdapter.swapCursor(dbFunction.select());
            }

            // attach the ItemTouchHelper
        }).attachToRecyclerView(cardlistRecyclerView);


        boolean internet = NetworkUtils.hasInternetConnection(this);
        if(internet) {
            // COMPLETED (23) Schedule the charging reminder
            ReminderUtilities.scheduleQueryReminder(this);
        }else{
            //no internet toast
            Toast.makeText(MainActivity.this,"No internet",Toast.LENGTH_LONG).show();
        }

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
        getMenuInflater().inflate(R.menu.main_toolbar_menu, menu);


        // Associate searchable configuration with the SearchView
        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();
        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(TAG, "onQueryTextSubmit ");


                if (s.length() == 0) {
                    return false;
                }

                try {
                    int code = Integer.parseInt(s);
                } catch (NumberFormatException ex) {
                    Log.e(TAG, "Failed to parse to number: " + ex.getMessage());
                }

                URL stockSearchUrl = NetworkUtils.buildUrl(s);
                URL stockSearchUrlF = NetworkUtils.buildUrlF(s);
                URL stockSearchUrlT = NetworkUtils.buildUrlT(s);

                boolean internet = NetworkUtils.hasInternetConnection(MainActivity.this);
                if(internet) {
                    new StockQueryTask(MainActivity.this).execute(stockSearchUrl, stockSearchUrlF, stockSearchUrlT);
                }else{
                    //no internet toast
                    Toast.makeText(MainActivity.this,"No internet",Toast.LENGTH_LONG).show();
                }


//                cursor= dbFunction.selectByName(s);
//                if (cursor==null){
//                    Toast.makeText(MainActivity.this,"No records found!",Toast.LENGTH_LONG).show();
//                }else{
//                    Toast.makeText(MainActivity.this, cursor.getCount() + " records found!",Toast.LENGTH_LONG).show();
//                }
//                mAdapter.swapCursor(cursor);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(TAG, "onQueryTextChange ");
                if (s.length() == 0) {
                    return false;
                }

                try {
                    int code = Integer.parseInt(s);
                } catch (NumberFormatException ex) {
                    Log.e(TAG, "Failed to parse to number: " + ex.getMessage());
                }

                //URL stockSearchUrl = NetworkUtils.buildUrl(s);
                //new StockQueryTask(MainActivity.this).execute(stockSearchUrl);
                return false;
            }

        });

        return true;
    }

    /**
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
    }**/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id){
            case R.id.action_refresh:
                mAdapter = new CardlistAdapter(this, cursor, this);
                cardlistRecyclerView.setAdapter(mAdapter);
                return true;
            //case R.id.action_map:
                //openLocationInMap();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(View v, int position) {
        Context context = this;
        Class destinationClass = AddCardActivity.class;

        long id = (long) v.getTag();
        Log.i(TAG, "0 "+id);

        Intent intentToStartAddCardActivity = new Intent(context, destinationClass);
        intentToStartAddCardActivity.putExtra(Intent.EXTRA_UID, id);
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
        } else if (id == R.id.nav_image) {
            destinationClass = ImageActivity.class;
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
