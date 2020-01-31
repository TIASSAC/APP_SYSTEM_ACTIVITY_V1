package com.example.appgcm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class Main3Activity extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //setTheme(R.style.AppTheme3);
        getSupportActionBar().setTitle("Actividad 3");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
