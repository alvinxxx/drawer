package com.example.alvinlam.drawer.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alvinlam.drawer.R;
import com.example.alvinlam.drawer.data.RiskAssessDbFunction;
import com.example.alvinlam.drawer.data.RiskAssessTestUtil;
import com.example.alvinlam.drawer.data.StocklistContract;
import com.example.alvinlam.drawer.data.StocklistDbHelper;
import com.example.alvinlam.drawer.data.old.DbFunction;
import com.example.alvinlam.drawer.utilities.NotificationUtils;

/**
 * Created by Alvin Lam on 3/29/2017.
 */

public class MyCardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MyCardActivity.class.getSimpleName();
    private static final String SHARE_HASHTAG = " #PocketCard";

    private SQLiteDatabase mDb;
    private Cursor cursor;
    private RiskAssessDbFunction dbFunction;

    private TextView textViewRASTop, textViewRASTitle, textViewRASLabel, textViewRASResult, textViewRASTypeLabel, textViewRASTypeValue, textViewRASDesValue;
    private Button fab, recommend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_card);

        Toolbar toolbar = (Toolbar) findViewById(R.id.add_card_toolbar);
        setSupportActionBar(toolbar);

        fab = (Button) findViewById(R.id.risk_assess_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Class destinationClass = RiskAssessActivity.class;
                Intent intentToStartAddCardActivity = new Intent(MyCardActivity.this, destinationClass);
                startActivity(intentToStartAddCardActivity);
            }
        });

        recommend = (Button) findViewById(R.id.goto_rec_button);
        recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Class destinationClass = ImageActivity.class;
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

        dbFunction = new RiskAssessDbFunction(this);
        //select Q and A, if null, insert Q and A
        cursor = dbFunction.selectQuestion();
        if(cursor == null){
            Log.d("mycard", "onCreate if: ");
            StocklistDbHelper dbHelper = new StocklistDbHelper(this);
            mDb = dbHelper.getWritableDatabase();
            RiskAssessTestUtil.insertData(mDb);
        }
        //cursor = dbFunction.selectQuestion();

        Log.d("mycard", "onCreate after if: ");

        cursor = dbFunction.selectTotalScore();
        textViewRASTop = (TextView) findViewById(R.id.textViewRASTop);
        textViewRASTitle = (TextView) findViewById(R.id.textViewRASTitle);
        textViewRASLabel = (TextView) findViewById(R.id.textViewRASLabel);
        textViewRASResult = (TextView) findViewById(R.id.textViewRASResult);
        textViewRASTypeLabel = (TextView) findViewById(R.id.textViewRASTypeLabel);
        textViewRASTypeValue = (TextView) findViewById(R.id.textViewRASTypeValue);
        textViewRASDesValue = (TextView) findViewById(R.id.textViewRASDesValue);

        if(cursor == null){
            recommend.setVisibility(View.INVISIBLE);
            textViewRASTop.setVisibility(View.VISIBLE);
            textViewRASTitle.setVisibility(View.VISIBLE);
            textViewRASLabel.setVisibility(View.INVISIBLE);
            textViewRASResult.setText(0);
            textViewRASTypeLabel.setVisibility(View.INVISIBLE);
            textViewRASTypeValue.setVisibility(View.INVISIBLE);
            textViewRASDesValue.setVisibility(View.INVISIBLE);

        }else{
            int total = cursor.getInt(0);// get final total
            //Log.d("riskdb", "selectTotalScore: "+  total);
            textViewRASResult.setText(String.valueOf(total));
            //score--> advice

            if (total >= 12 && total <= 19){
                textViewRASTypeValue.setText(R.string.investor_type_1);
                textViewRASDesValue.setText(R.string.investor_des_1);
            }else if (total >= 20 && total <= 28){
                textViewRASTypeValue.setText(R.string.investor_type_2);
                textViewRASDesValue.setText(R.string.investor_des_2);
            }else if (total >= 29 && total <= 37){
                textViewRASTypeValue.setText(R.string.investor_type_3);
                textViewRASDesValue.setText(R.string.investor_des_3);
            }else if (total >= 38 && total <= 46){
                textViewRASTypeValue.setText(R.string.investor_type_4);
                textViewRASDesValue.setText(R.string.investor_des_4);
            }else if (total >= 47 && total <= 54){
                textViewRASTypeValue.setText(R.string.investor_type_5);
                textViewRASDesValue.setText(R.string.investor_des_5);
            }

            if(total != 0){
                recommend.setVisibility(View.VISIBLE);
                fab.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                textViewRASTop.setVisibility(View.INVISIBLE);
                textViewRASTitle.setVisibility(View.INVISIBLE);
                textViewRASLabel.setVisibility(View.VISIBLE);
                textViewRASResult.setVisibility(View.VISIBLE);
                textViewRASTypeValue.setVisibility(View.VISIBLE);
                textViewRASDesValue.setVisibility(View.VISIBLE);
                textViewRASTypeLabel.setVisibility(View.VISIBLE);
                textViewRASTypeValue.setVisibility(View.VISIBLE);
                textViewRASDesValue.setVisibility(View.VISIBLE);
                fab.setText("PRESS ME TO START AGAIN");

            }

        }



        if(cursor != null) {
            cursor.close();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.add_card_toolbar_menu, menu);
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
