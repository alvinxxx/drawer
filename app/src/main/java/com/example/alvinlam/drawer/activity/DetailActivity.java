package com.example.alvinlam.drawer.activity;

import android.content.Intent;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.alvinlam.drawer.R;

public class DetailActivity extends AppCompatActivity {
    private static final String CARD_SHARE_HASHTAG = " #PocketCard";
    private String mCard;
    private TextView mCardDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mCardDisplay = (TextView) findViewById(R.id.detail_card_xml);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                mCard = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
                mCardDisplay.setText(mCard);
            }
        }
    }

    private Intent createShareCardIntent() {
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(mCard + CARD_SHARE_HASHTAG)
                .getIntent();
        return shareIntent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        menuItem.setIntent(createShareCardIntent());
        return true;
    }
}
