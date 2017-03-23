package com.example.alvinlam.drawer.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.alvinlam.drawer.R;

public class DetailActivity extends AppCompatActivity {
    private TextView mView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mView = (TextView) findViewById(R.id.tv_display);
    }
}
