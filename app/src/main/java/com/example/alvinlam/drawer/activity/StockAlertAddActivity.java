package com.example.alvinlam.drawer.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alvinlam.drawer.R;
import com.example.alvinlam.drawer.data.StockAlertDbFunction;
import com.example.alvinlam.drawer.data.StockDbFunction;
import com.example.alvinlam.drawer.data.StocklistContract;
import com.example.alvinlam.drawer.data.StocklistDbHelper;
import com.example.alvinlam.drawer.utilities.NetworkUtils;
import com.example.alvinlam.drawer.utilities.OpenStockJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;


public class StockAlertAddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TextWatcher {


    //private SQLiteDatabase mDb;
    private final static String LOG_TAG = StockAlertAddActivity.class.getSimpleName();
    private StockDbFunction dbFunction;
    private StockAlertDbFunction dbAFunction;
    private Cursor cursor;
    private Cursor cursorResult;

    private Switch switchActive;
    private RadioButton buttonBuy, buttonSell;
    private Spinner spinnerCurrent, spinnerCondition, spinnerWindow;
    private AutoCompleteTextView autoCompleteTextViewTarget, autoCompleteTextViewDistance;
    private TextView textViewACode, textViewAName, textViewCurrentResult, textViewWindowResult, textViewDistanceResult, textViewFinalResult;

    private long id = 0;
    private int code, active, buy;
    private String name, current, currentResult, condition, target, window, windowResult, distance, distanceResult, finalResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("stockalertadd", "onCreate: "+"2");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_alert_read_precontent);

        Toolbar toolbar = (Toolbar) findViewById(R.id.add_card_toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        dbAFunction = new StockAlertDbFunction(this);

        textViewACode = (TextView) this.findViewById(R.id.textViewACode);
        textViewAName = (TextView) this.findViewById(R.id.textViewAName);
        switchActive = (Switch) this.findViewById(R.id.switchActive);
        buttonBuy = (RadioButton) this.findViewById(R.id.buttonBuy);
        buttonSell = (RadioButton) this.findViewById(R.id.buttonSell);
        spinnerCurrent = (Spinner) this.findViewById(R.id.spinnerCurrent);
        spinnerCondition = (Spinner) this.findViewById(R.id.spinnerCondtion);
        spinnerWindow = (Spinner) this.findViewById(R.id.spinnerWindow);
        autoCompleteTextViewTarget = (AutoCompleteTextView) this.findViewById(R.id.autoCompleteTextViewTarget);
        autoCompleteTextViewDistance = (AutoCompleteTextView) this.findViewById(R.id.autoCompleteTextViewDistance);
        textViewCurrentResult = (TextView) this.findViewById(R.id.textViewCurrentResult);
        textViewWindowResult = (TextView) this.findViewById(R.id.textViewWindowResult);
        textViewDistanceResult = (TextView) this.findViewById(R.id.textViewDistanceResult);
        textViewFinalResult = (TextView) this.findViewById(R.id.textViewFinalResult);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_UID)) {
                id = intentThatStartedThisActivity.getLongExtra(Intent.EXTRA_UID, 0);

                cursor = dbAFunction.selectByID(id);
                Log.i("stockalert", "onclick "+id);
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();

                    code = cursor.getInt(cursor.getColumnIndex(StocklistContract.StockAlertEntry.COLUMN_CODE));
                    name = cursor.getString(cursor.getColumnIndex(StocklistContract.StockAlertEntry.COLUMN_NAME));
                    active = cursor.getInt(cursor.getColumnIndex(StocklistContract.StockAlertEntry.COLUMN_ACTIVE));
                    buy = cursor.getInt(cursor.getColumnIndex(StocklistContract.StockAlertEntry.COLUMN_BUY));
                    current = cursor.getString(cursor.getColumnIndex(StocklistContract.StockAlertEntry.COLUMN_INDICATOR));
                    condition = cursor.getString(cursor.getColumnIndex(StocklistContract.StockAlertEntry.COLUMN_CONDITION));
                    target = cursor.getString(cursor.getColumnIndex(StocklistContract.StockAlertEntry.COLUMN_TARGET));
                    window = cursor.getString(cursor.getColumnIndex(StocklistContract.StockAlertEntry.COLUMN_WINDOW));
                    distance = cursor.getString(cursor.getColumnIndex(StocklistContract.StockAlertEntry.COLUMN_DISTANCE));

                    Log.i(StockAlertAddActivity.class.getName(), name);


                }
            }
            textViewACode.setText(String.format(Locale.getDefault(), "%d", code));
            textViewAName.setText(name);
        }


        ArrayAdapter<CharSequence> adapterCurrent = ArrayAdapter.createFromResource(this,
                R.array.array_indicator, android.R.layout.simple_spinner_item);
        adapterCurrent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCurrent.setAdapter(adapterCurrent);
        spinnerCurrent.setSelection(adapterCurrent.getPosition("Price"));
        spinnerCurrent.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterCondition = ArrayAdapter.createFromResource(this,
                R.array.array_condition, android.R.layout.simple_spinner_item);
        adapterCondition.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCondition.setAdapter(adapterCondition);
        spinnerCondition.setSelection(adapterCondition.getPosition("Less than"));
        spinnerCondition.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterWindow = ArrayAdapter.createFromResource(this,
                R.array.array_window, android.R.layout.simple_spinner_item);
        adapterWindow.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWindow.setAdapter(adapterWindow);
        spinnerWindow.setSelection(adapterWindow.getPosition("20"));
        spinnerWindow.setOnItemSelectedListener(this);

        String[] array_target = {"SMA", "Low", "High"};

        ArrayAdapter<String> adapterTarget = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, array_target);
        autoCompleteTextViewTarget.setThreshold(1);//will start working from first character
        autoCompleteTextViewTarget.setAdapter(adapterTarget);//setting the adapter data into the AutoCompleteTextView

        String[] array_distance = { "-2 STD", "-2 STD_L","-2 STD_H",
                                    "-1 STD", "-1 STD_L","-1 STD_H", "0",
                                    "+1 STD", "+1 STD_L","+1 STD_H",
                                    "+2 STD", "+2 STD_L","+2 STD_H",};

        ArrayAdapter<String> adapterDistance = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, array_distance);
        autoCompleteTextViewTarget.addTextChangedListener(this);
        autoCompleteTextViewTarget.setThreshold(1);//will start working from first character
        autoCompleteTextViewTarget.setAdapter(adapterDistance);//setting the adapter data into the AutoCompleteTextView
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_card_add_toolbar_menu, menu);
        return true;
    }


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        Double result = 0.0;
        String selectValue = parent.getSelectedItem().toString();

        //cursor: Get current indicator
        cursorResult = dbFunction.selectByID((long) code);

        switch (parent.getId()){
            case R.id.spinnerCurrent:
                String[] array_current = getResources().getStringArray(R.array.array_indicator);

                if(selectValue.equals(array_current[0])){
                    result = cursorResult.getDouble(cursorResult.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_PRICE));
                }else if (selectValue.equals(array_current[1])){
                    result = cursorResult.getDouble(cursorResult.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_DY));
                }else if (selectValue.equals(array_current[2])){
                    result = cursorResult.getDouble(cursorResult.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_PE));
                }

                //setText of textview
                textViewCurrentResult.setText(String.format(Locale.getDefault(), "%.2f", result));
                break;

            case R.id.spinnerWindow:
                String[] array_window = getResources().getStringArray(R.array.array_window);

                if(selectValue.equals(array_window[0])){
                    result = cursorResult.getDouble(cursorResult.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_SMA20));
                }else if (selectValue.equals(array_window[1])){
                    result = cursorResult.getDouble(cursorResult.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_SMA50));
                }else if (selectValue.equals(array_window[2])){
                    result = cursorResult.getDouble(cursorResult.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_SMA100));
                }else if (selectValue.equals(array_window[3])){
                    result = cursorResult.getDouble(cursorResult.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_SMA250));
                }
                //setText of textview Window
                textViewWindowResult.setText(String.format(Locale.getDefault(), "%.2f", result));
                //setText of textview FinalResult
                //Double finalResult = result+;
                //textViewFinalResult.setText(String.format(Locale.getDefault(), "%.2f", result));

                break;
        }
    }
    //spinner
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    //autoCompleteTextView
    @Override
    public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
        cursorResult = dbFunction.selectByID((long) code);


        if(count > 5){
            new Handler().postDelayed(new Runnable(){
                public void run(){
                    //write db insertion logic here...
                    Double result=0.0, resultFinal, sma;
                    String[] array_window = getResources().getStringArray(R.array.array_window);

                    window = spinnerWindow.getSelectedItem().toString();
                    String distance = s.toString();
                    int value = Integer.parseInt(distance.split(" ")[0]);
                    String type = distance.split(" ")[1];

                    if (window.equals(array_window[0])) {
                        if (type.equals("STD")) {
                            result = cursorResult.getDouble(cursorResult.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD20));
                        } else if (type.equals("STD_L")) {
                            result = cursorResult.getDouble(cursorResult.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD20L));
                        } else if (type.equals("STD_H")) {
                            result = cursorResult.getDouble(cursorResult.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD20H));
                        }
                    } else if (window.equals(array_window[1])) {
                        if (type.equals("STD")) {
                            result = cursorResult.getDouble(cursorResult.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD50));
                        } else if (type.equals("STD_L")) {
                            result = cursorResult.getDouble(cursorResult.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD50L));
                        } else if (type.equals("STD_H")) {
                            result = cursorResult.getDouble(cursorResult.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD50H));
                        }
                    } else if (window.equals(array_window[2])) {
                        if (type.equals("STD")) {
                            result = cursorResult.getDouble(cursorResult.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD100));
                        } else if (type.equals("STD_L")) {
                            result = cursorResult.getDouble(cursorResult.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD100L));
                        } else if (type.equals("STD_H")) {
                            result = cursorResult.getDouble(cursorResult.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD100H));
                        }
                    } else if (window.equals(array_window[3])) {
                        if (type.equals("STD")) {
                            result = cursorResult.getDouble(cursorResult.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD250));
                        } else if (type.equals("STD_L")) {
                            result = cursorResult.getDouble(cursorResult.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD250L));
                        } else if (type.equals("STD_H")) {
                            result = cursorResult.getDouble(cursorResult.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD250H));
                        }
                    }
                    textViewDistanceResult.setText(String.format(Locale.getDefault(), "%.2f", result));

                    sma = Double.parseDouble(textViewWindowResult.getText().toString());
                    resultFinal = sma + value*result;
                    textViewFinalResult.setText(String.format(Locale.getDefault(), "%.2f", resultFinal));

                }},700);
        }

    }

    @Override
    public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {

    }

    @Override
    public void afterTextChanged(final Editable s) {
        //System.out.println("TEXT CHANGED BY afterTextChanged");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int lid = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (lid == R.id.action_send) {
            addToStockAlert();

            return true;
        }else if (lid == android.R.id.home) {
            Context context = this;
            Class destinationClass = AddCardActivity.class;
            Intent intentToStartActivity = new Intent(context, destinationClass);
            //intentToStartActivity.putExtra(Intent.EXTRA_UID, (long)code);

            startActivity(intentToStartActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addToStockAlert() {
        //get the output
        String name = textViewAName.getText().toString();
        int code = 0;
        try {
            code = Integer.parseInt(textViewACode.getText().toString());
        } catch (NumberFormatException ex) {
            Log.e(LOG_TAG, "Failed to parse to number: " + ex.getMessage());
        }

        boolean activeBoolean = switchActive.isChecked();
        int active = 0;
        if(activeBoolean){active = 1;}
        boolean buyBoolean = buttonBuy.isChecked();
        int buy = 0;
        if(buyBoolean){buy = 1;}


        String current = spinnerCurrent.getSelectedItem().toString();
        String condition = spinnerCondition.getSelectedItem().toString();
        String target = autoCompleteTextViewTarget.getText().toString();
        String window = spinnerWindow.getSelectedItem().toString();
        String distance = autoCompleteTextViewDistance.getText().toString();

        //send to db
        dbAFunction.insert(name, code, active, buy, current, condition,
                window, target, distance);

    }







}
