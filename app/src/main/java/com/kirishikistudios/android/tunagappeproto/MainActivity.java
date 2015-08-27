package com.kirishikistudios.android.tunagappeproto;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startHangout(View view){
        final String HANGOUT_URL = getString(R.string.sample_hangout_url);
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(HANGOUT_URL));
        startActivity(intent);
    }
}
