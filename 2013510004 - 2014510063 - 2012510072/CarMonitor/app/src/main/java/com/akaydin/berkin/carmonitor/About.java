package com.akaydin.berkin.carmonitor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by berkin on 18.05.2016.
 */
public class About extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
