package com.example.alvinlam.drawer.activity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.example.alvinlam.drawer.data.StocklistDbHelper;
import com.example.alvinlam.drawer.utilities.NetworkUtils;
import com.example.alvinlam.drawer.utilities.OpenStockJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;


public class StockAlertAddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    //private SQLiteDatabase mDb;
    private final static String LOG_TAG = StockAlertAddActivity.class.getSimpleName();
    private StockAlertDbFunction dbFunction;

    private Switch switchActive;
    private RadioButton buttonBuy, buttonSell;
    private Spinner spinnerCurrent, spinnerCondition, spinnerWindow;
    private AutoCompleteTextView autoCompleteTextViewTarget, autoCompleteTextViewDistance;
    private TextView textViewACode, textViewAName, textViewCurrentResult, textViewWindowResult, textViewDistanceResult, textViewFinalResult;


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

        StocklistDbHelper dbHelper = new StocklistDbHelper(this);
        dbFunction = new StockAlertDbFunction(this);
        //mDb = dbHelper.getWritableDatabase();

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
        spinnerCondition.setSelection(adapterCondition.getPosition("Less than \\u003c"));
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

        String[] array_distance = {"-1STD", "-2STD", "1STD", "2STD"};

        ArrayAdapter<String> adapterDistance = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, array_distance);
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
        switch (parent.getId()){
            case R.id.spinnerCurrent:
                //cursor: Get current indicator

                //setText of textview

                //Do something
                Toast.makeText(this, "Current Selected: " + parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.spinnerCondtion:
                //Do another thing
                Toast.makeText(this, "Condition Selected: " + parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.spinnerWindow:
                //cursor: Get current indicator

                //setText of textview Window

                //setText of textview FinalResult

                //Do another thing
                Toast.makeText(this, "Window Selected: " + parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
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
            Class destinationClass = MainActivity.class;
            Intent intentToStartActivity = new Intent(context, destinationClass);
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
        dbFunction.insert(name, code, active, buy, current, condition,
                window, target, distance);

    }







}
