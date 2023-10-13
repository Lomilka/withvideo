package com.itproger.mygame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
    public void toUroki(View view){
        Intent intentUroki = new Intent(MenuActivity.this, MainUrokiActivity.class);
        startActivity(intentUroki);
    }
    public void toTest(View view){
        Intent intentTest = new Intent(MenuActivity.this, MainTestActivity.class);
        startActivity(intentTest);
    }
    public void toProgress(View view){
        Intent intentProgress = new Intent(MenuActivity.this, MainProgressActivity.class);
        startActivity(intentProgress);
    }
}