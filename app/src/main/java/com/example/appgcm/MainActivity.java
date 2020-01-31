package com.example.appgcm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.appgcm.Fragments.MainFragment;
import com.example.appgcm.Listeners.MainListener;
import com.example.appgcm.Util.CustomAnimation;
import com.example.appgcm.Util.NavigationFragment;

public class MainActivity extends AppCompatActivity implements MainListener {
    Toolbar toolbar;
    SharedPreferences sharedPreferences;
    private MainFragment mainFragment = new MainFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //navigationBarStatusBar();

        //sharedPreferences.edit().putInt("THEME",1).apply();

        //sharedPreferences = getSharedPreferences("VALUES", MODE_PRIVATE);
        //int theme = sharedPreferences.getInt("THEME",1);
        //setTheme(R.style.AppTheme2);
        getSupportActionBar().setTitle("Menú Principal");
        goToMainFragment();

    }


    private void goToMainFragment(){
        NavigationFragment.addFragment(null, mainFragment, "MainFragment", this,
                R.id.main_activity_content, false, CustomAnimation.RIGTH_LEFT);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void navigationBarStatusBar() {

        // Fix portrait issues
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Fix issues for KitKat setting Status Bar color primary
            if (Build.VERSION.SDK_INT >= 19) {
                TypedValue typedValue19 = new TypedValue();
                MainActivity.this.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue19, true);
                final int color = typedValue19.data;
                FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
                statusBar.setBackgroundColor(color);
            }

            // Fix issues for Lollipop, setting Status Bar color primary dark
            if (Build.VERSION.SDK_INT >= 21) {
                TypedValue typedValue21 = new TypedValue();
                MainActivity.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue21, true);
                final int color = typedValue21.data;
                FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
                statusBar.setBackgroundColor(color);
            }
        }


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (Build.VERSION.SDK_INT >= 19) {
                TypedValue typedValue19 = new TypedValue();
                MainActivity.this.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue19, true);
                final int color = typedValue19.data;
                FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
                statusBar.setBackgroundColor(color);
            }
            if (Build.VERSION.SDK_INT >= 21) {
                TypedValue typedValue = new TypedValue();
                MainActivity.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
                final int color = typedValue.data;
                FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
                statusBar.setBackgroundColor(color);
            }
        }
    }


    @Override
    public void goToMain2() {
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        startActivity(intent);
        //this.finish();
    }
}
