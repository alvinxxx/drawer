package com.example.alvinlam.drawer.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alvinlam.drawer.R;
import com.example.alvinlam.drawer.data.StockDbFunction;
import com.example.alvinlam.drawer.data.StocklistContract;

import java.util.Locale;


public class AddCardActivity extends AppCompatActivity{

    private static final String TAG = AddCardActivity.class.getSimpleName();
    private static final String SHARE_HASHTAG = " #PocketCard";

    private SQLiteDatabase mDb;
    private Cursor cursor;
    private StockDbFunction dbFunction;
    private long id = 0;

    private EditText mNameEditText;
    private EditText mCodeEditText;
    private EditText mDateEditText;
    private EditText mPriceEditText;
    private EditText mNetChangeEditText;
    private EditText mPEEditText;
    private EditText mHighEditText;
    private EditText mLowEditText;
    private EditText mPreCloseEditText;
    private EditText mVolumeEditText;
    private EditText mTurnoverEditText;
    private EditText mLotEditText;

    private String name;
    private int code;
    private String date;
    private double price;
    private double netChange;
    private double pe;
    private double high;
    private double low;
    private double preClose;
    private double volume;
    private double turnover;
    private double lot;
    MenuItem mAdd,mDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_card_read_precontent);

        Toolbar toolbar = (Toolbar) findViewById(R.id.add_card_toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        dbFunction = new StockDbFunction(this);


        mNameEditText = (EditText) this.findViewById(R.id.add_name_editText);
        mCodeEditText = (EditText) this.findViewById(R.id.add_code_editText);
        mDateEditText = (EditText) this.findViewById(R.id.add_date_editText);
        mPriceEditText = (EditText) this.findViewById(R.id.add_price_editText);
        mNetChangeEditText = (EditText) this.findViewById(R.id.add_net_change_editText);
        mPEEditText = (EditText) this.findViewById(R.id.add_pe_editText);
        mHighEditText = (EditText) this.findViewById(R.id.add_high_editText);
        mLowEditText = (EditText) this.findViewById(R.id.add_low_editText);
        mPreCloseEditText = (EditText) this.findViewById(R.id.add_pre_close_editText);
        mVolumeEditText = (EditText) this.findViewById(R.id.add_volume_editText);
        mTurnoverEditText = (EditText) this.findViewById(R.id.add_turnover_editText);
        mLotEditText = (EditText) this.findViewById(R.id.add_lot_editText);


        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                id = intentThatStartedThisActivity.getLongExtra(Intent.EXTRA_TEXT, 0);

                cursor = dbFunction.selectByID(id);
                Log.i(TAG, "1 "+String.valueOf(cursor.getColumnIndex("name")));
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();

                    Log.i(AddCardAddActivity.class.getName(), String.valueOf(cursor.getColumnIndex("name")));

                    name = cursor.getString(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_NAME));
                    code = cursor.getInt(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_CODE));
                    date = cursor.getString(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_DATE));
                    price = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_PRICE));
                    netChange = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_NET_CHANGE));
                    pe = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_PE));
                    high = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_HIGH));
                    low = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_LOW));
                    preClose = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_PRE_CLOSE));
                    volume = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_VOLUME));
                    turnover = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_TURNOVER));
                    lot = cursor.getDouble(cursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_LOT));

                    mNameEditText.setText(name);
                    mCodeEditText.setText(String.format(Locale.getDefault(), "%d", code));
                    mDateEditText.setText(date);
                    mPriceEditText.setText(String.format(Locale.getDefault(), "%.2f", price));
                    mNetChangeEditText.setText(String.format(Locale.getDefault(), "%.2f", netChange));
                    mPEEditText.setText(String.format(Locale.getDefault(), "%.2f", pe));
                    mHighEditText.setText(String.format(Locale.getDefault(), "%.2f", high));
                    mLowEditText.setText(String.format(Locale.getDefault(), "%.2f", low));
                    mPreCloseEditText.setText(String.format(Locale.getDefault(), "%.2f", preClose));
                    mVolumeEditText.setText(String.format(Locale.getDefault(), "%.2f", volume));
                    mTurnoverEditText.setText(String.format(Locale.getDefault(), "%.2f", turnover));
                    mLotEditText.setText(String.format(Locale.getDefault(), "%.2f", lot));
                }
            }
        }
        disableEditText(mNameEditText);
        disableEditText(mCodeEditText);
        disableEditText(mDateEditText);
        disableEditText(mPriceEditText);
        disableEditText(mNetChangeEditText);
        disableEditText(mPEEditText);
        disableEditText(mHighEditText);
        disableEditText(mLowEditText);
        disableEditText(mPreCloseEditText);
        disableEditText(mVolumeEditText);
        disableEditText(mTurnoverEditText);
        disableEditText(mLotEditText);
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
        getMenuInflater().inflate(R.menu.add_card_toolbar_menu, menu);
        mAdd= menu.findItem(R.id.action_add);
        mDelete = menu.findItem(R.id.action_delete);
        mAdd.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int lid = item.getItemId();
        Context context = this;
        Class destinationClass = SearchResultStockActivity.class;

        //noinspection SimplifiableIfStatement
        if (lid == R.id.action_add) {
            Log.d(TAG, "onoption " + volume);
            dbFunction = new StockDbFunction(context);
            // Add guest info to mDb
            dbFunction.replace(
                    id,
                    name,
                    code,
                    date,
                    price,
                    netChange,
                    pe,
                    high,
                    low,
                    preClose,
                    volume,
                    turnover,
                    lot
            );
            Toast.makeText(AddCardActivity.this,"Stock Added",Toast.LENGTH_LONG).show();
            mAdd.setVisible(false);
            mDelete.setVisible(true);

            return true;

        }else if (lid == R.id.action_delete) {
            dbFunction.delete(id);
            Toast.makeText(AddCardActivity.this,"Stock Deleted",Toast.LENGTH_LONG).show();
            mAdd.setVisible(true);
            mDelete.setVisible(false);
            return true;

        }else if (lid == android.R.id.home) {
            destinationClass = MainActivity.class;
            Intent intentToStartActivity = new Intent(context, destinationClass);
            startActivity(intentToStartActivity);
            return true;
        }else if (lid == R.id.action_share) {
            Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                    .setType("text/plain")
                    .setText("Name:  "+ name + "\n" + "Code:  "+ code + "\n" + "Date:  "+ date + "\n" + "Price:  "+ price + "\n" +
                            "Net change:  "+ netChange + "\n" + "PE:  "+ pe + "\n" + "High:  "+ high + "\n" + "Low:  "+low + "\n" +
                            "Volume:  "+ volume + "\n" + "Turnover:  "+ turnover + "\n" + "Lot:  "+ lot + "\n" +
                            SHARE_HASHTAG)
                    .getIntent();
            startActivity(shareIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
