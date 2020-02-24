package com.example.appgcm;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

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
