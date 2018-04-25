package com.example.alvinlam.drawer.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alvinlam.drawer.R;
import com.example.alvinlam.drawer.data.RiskAssessDbFunction;
import com.example.alvinlam.drawer.data.StockAlertDbFunction;
import com.example.alvinlam.drawer.data.StockDbFunction;
import com.example.alvinlam.drawer.data.StocklistContract;


public class RiskAssessActivity extends AppCompatActivity {


    //private SQLiteDatabase mDb;
    private final static String LOG_TAG = RiskAssessActivity.class.getSimpleName();
    private StockDbFunction dbFunction;
    private StockAlertDbFunction dbAFunction;
    private RiskAssessDbFunction dbRAFunction;
    private Cursor cursor, cursorAnswer;

    private TextView textViewRADQid, textViewRADScore, textViewRADQuestion;
    private RadioGroup radioGroupRAD;
    private RadioButton radioButtonRADA, radioButtonRADB, radioButtonRADC, radioButtonRADD;
    private String qidLabel, scoreLabel, question, answer;
    private Button previous;

    int qid = 1, score, scoreA, scoreB, scoreC, scoreD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("risk assess", "onCreate: "+"2");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.risk_assess_data_precontent);

        Toolbar toolbar = (Toolbar) findViewById(R.id.add_card_toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        previous = (Button) findViewById(R.id.previous_button);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goPrevious(view);
            }
        });

        dbRAFunction = new RiskAssessDbFunction(this);

        textViewRADQid = (TextView) this.findViewById(R.id.textViewRADQid);
        textViewRADScore = (TextView) this.findViewById(R.id.textViewRADScore);
        textViewRADQuestion = (TextView) this.findViewById(R.id.textViewRADQuestion);
        radioGroupRAD = (RadioGroup) this.findViewById(R.id.radioGroupRAD);


        radioButtonRADA = (RadioButton) findViewById(R.id.radioButtonRADA);
        radioButtonRADB = (RadioButton) findViewById(R.id.radioButtonRADB);
        radioButtonRADC = (RadioButton) findViewById(R.id.radioButtonRADC);
        radioButtonRADD = (RadioButton) findViewById(R.id.radioButtonRADD);


        textViewRADQid.setText("1");

        cursor = dbRAFunction.selectQuestionByQid(1);
        if (cursor.getCount() > 0) {
            Log.d("riskassess q1", "onCreate: "+cursor.getCount());
            cursor.moveToFirst();
            question = cursor.getString(cursor.getColumnIndex(StocklistContract.RiskAssessQuestionEntry.COLUMN_QUESTION));
            textViewRADQuestion.setText(question);
        }

        cursorAnswer = dbRAFunction.selectAnswerByQid(1);
        Log.d("riskassess a", "onCreate: "+cursorAnswer.getCount());
        if (cursorAnswer.getCount() > 0) {
            cursorAnswer.moveToFirst();
            answer = cursorAnswer.getString(cursorAnswer.getColumnIndex(StocklistContract.RiskAssessAnswerEntry.COLUMN_ANSWER));
            scoreA = cursorAnswer.getInt(cursorAnswer.getColumnIndex(StocklistContract.RiskAssessAnswerEntry.COLUMN_SCORE));
            radioButtonRADA.setText(answer);

            cursorAnswer.moveToNext();
            answer = cursorAnswer.getString(cursorAnswer.getColumnIndex(StocklistContract.RiskAssessAnswerEntry.COLUMN_ANSWER));
            scoreB = cursorAnswer.getInt(cursorAnswer.getColumnIndex(StocklistContract.RiskAssessAnswerEntry.COLUMN_SCORE));
            radioButtonRADB.setText(answer);

            cursorAnswer.moveToNext();
            answer = cursorAnswer.getString(cursorAnswer.getColumnIndex(StocklistContract.RiskAssessAnswerEntry.COLUMN_ANSWER));
            scoreC = cursorAnswer.getInt(cursorAnswer.getColumnIndex(StocklistContract.RiskAssessAnswerEntry.COLUMN_SCORE));
            radioButtonRADC.setText(answer);

            cursorAnswer.moveToNext();
            answer = cursorAnswer.getString(cursorAnswer.getColumnIndex(StocklistContract.RiskAssessAnswerEntry.COLUMN_ANSWER));
            scoreD = cursorAnswer.getInt(cursorAnswer.getColumnIndex(StocklistContract.RiskAssessAnswerEntry.COLUMN_SCORE));
            radioButtonRADD.setText(answer);
        }

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_UID)) {

            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_card_add_toolbar_menu, menu);
        return true;
    }


    public void onRadioButtonClicked(View view) {


        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButtonRADA:
                if (checked)
                    textViewRADScore.setText(String.valueOf(scoreA));
                    break;
            case R.id.radioButtonRADB:
                if (checked)
                    textViewRADScore.setText(String.valueOf(scoreB));
                    break;
            case R.id.radioButtonRADC:
                if (checked)
                    textViewRADScore.setText(String.valueOf(scoreC));
                    break;
            case R.id.radioButtonRADD:
                if (checked)
                    textViewRADScore.setText(String.valueOf(scoreD));
                    break;
        }

        //next question
        addToRiskAssess();
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int lid = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (lid == R.id.action_send) {
            addToRiskAssess();

            return true;
        }else if (lid == android.R.id.home) {
            goBack();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goBack(){
        if(cursor != null) {
            cursor.close();
        }
        if(cursorAnswer != null) {
            cursorAnswer.close();
        }

        Context context = this;
        Class destinationClass = MyCardActivity.class;
        Intent intentToStartActivity = new Intent(context, destinationClass);
        startActivity(intentToStartActivity);
    }

    public void goPrevious(View view){
        qidLabel = textViewRADQid.getText().toString();

        try {
            qid = Integer.parseInt(qidLabel);
        } catch (NumberFormatException ex) {
            Log.e(LOG_TAG, "Failed to parse to number: " + ex.getMessage());
        }
        radioGroupRAD.clearCheck();

        qid--;
        //draw next question
        if(qid > 0){
            qidLabel = String.valueOf(qid);
            textViewRADQid.setText(qidLabel);
            score = 0;
            scoreLabel = String.valueOf(score);
            textViewRADScore.setText(scoreLabel);

            cursor = dbRAFunction.selectQuestionByQid(qid);
            cursor.moveToFirst();
            question = cursor.getString(cursor.getColumnIndex(StocklistContract.RiskAssessQuestionEntry.COLUMN_QUESTION));
            textViewRADQuestion.setText(question);

            cursorAnswer = dbRAFunction.selectAnswerByQid(qid);
            cursorAnswer.moveToFirst();
            answer = cursorAnswer.getString(cursorAnswer.getColumnIndex(StocklistContract.RiskAssessAnswerEntry.COLUMN_ANSWER));
            radioButtonRADA.setText(answer);

            cursorAnswer.moveToNext();
            answer = cursorAnswer.getString(cursorAnswer.getColumnIndex(StocklistContract.RiskAssessAnswerEntry.COLUMN_ANSWER));
            radioButtonRADB.setText(answer);

            cursorAnswer.moveToNext();
            answer = cursorAnswer.getString(cursorAnswer.getColumnIndex(StocklistContract.RiskAssessAnswerEntry.COLUMN_ANSWER));
            radioButtonRADC.setText(answer);

            cursorAnswer.moveToNext();
            answer = cursorAnswer.getString(cursorAnswer.getColumnIndex(StocklistContract.RiskAssessAnswerEntry.COLUMN_ANSWER));
            radioButtonRADD.setText(answer);
        }else{
            goBack();
        }

    }


    public void addToRiskAssess() {

        //get the current qid, cursor getnext, update current qid
        qidLabel = textViewRADQid.getText().toString();
        scoreLabel = textViewRADScore.getText().toString();


        if (radioGroupRAD.getCheckedRadioButtonId() == -1) {
            // no radio buttons are checked
            Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show();
        } else {
            // one of the radio buttons is checked
            try {
                qid = Integer.parseInt(qidLabel);
                score = Integer.parseInt(scoreLabel);
            } catch (NumberFormatException ex) {
                Log.e(LOG_TAG, "Failed to parse to number: " + ex.getMessage());
            }
            //get question count, if qid == count, delete all in stocklist and stockalert
            if(qid == dbRAFunction.selectQuestion().getCount()){

                dbFunction = new StockDbFunction(this);
                dbAFunction = new StockAlertDbFunction(this);

                //remove from DB
                dbFunction.deleteAll();
                dbAFunction.deleteAll();
            }

            dbRAFunction.replace(qid, score);
            radioGroupRAD.clearCheck();
            Log.d("riskassess", "addto: "+cursor.getCount());

            //draw next question
            if(qid < dbRAFunction.selectQuestion().getCount()){
                qid++;
                qidLabel = String.valueOf(qid);
                textViewRADQid.setText(qidLabel);
                score = 0;
                scoreLabel = String.valueOf(score);
                textViewRADScore.setText(scoreLabel);

                cursor = dbRAFunction.selectQuestionByQid(qid);
                cursor.moveToFirst();
                question = cursor.getString(cursor.getColumnIndex(StocklistContract.RiskAssessQuestionEntry.COLUMN_QUESTION));
                textViewRADQuestion.setText(question);

                cursorAnswer = dbRAFunction.selectAnswerByQid(qid);
                cursorAnswer.moveToFirst();
                answer = cursorAnswer.getString(cursorAnswer.getColumnIndex(StocklistContract.RiskAssessAnswerEntry.COLUMN_ANSWER));
                radioButtonRADA.setText(answer);

                cursorAnswer.moveToNext();
                answer = cursorAnswer.getString(cursorAnswer.getColumnIndex(StocklistContract.RiskAssessAnswerEntry.COLUMN_ANSWER));
                radioButtonRADB.setText(answer);

                cursorAnswer.moveToNext();
                answer = cursorAnswer.getString(cursorAnswer.getColumnIndex(StocklistContract.RiskAssessAnswerEntry.COLUMN_ANSWER));
                radioButtonRADC.setText(answer);

                cursorAnswer.moveToNext();
                answer = cursorAnswer.getString(cursorAnswer.getColumnIndex(StocklistContract.RiskAssessAnswerEntry.COLUMN_ANSWER));
                radioButtonRADD.setText(answer);
            }else{
                goBack();
            }
        }








    }







}
