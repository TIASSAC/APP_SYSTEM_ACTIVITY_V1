package com.example.appgcm;

import android.content.Intent;
import com.google.android.material.button.MaterialButton;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    MaterialButton btnIrActivity2;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //setTheme(R.style.AppTheme3);
        getSupportActionBar().setTitle("Actividad 2");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnIrActivity2 = (MaterialButton) findViewById(R.id.btnIrActividad2);

        btnIrActivity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                startActivity(intent);
                //Main2Activity.this.finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnAyuda:
                Toast.makeText(this,"Ayuda", Toast.LENGTH_LONG).show();
                return true;
            case R.id.btnSalir:
                Toast.makeText(this,"Salir", Toast.LENGTH_LONG).show();
                return true;
            case R.id.home:
                Toast.makeText(this,"Home", Toast.LENGTH_LONG).show();
                Main2Activity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
