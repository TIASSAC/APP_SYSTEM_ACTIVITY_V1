package com.example.appgcm;

import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.appgcm.Entities.ConfigurationEntity;
import com.example.appgcm.Fragments.ActivedPersonListFragment;
import com.example.appgcm.Fragments.ButtonFragment;
import com.example.appgcm.Fragments.ConfigurationFragment;
import com.example.appgcm.Fragments.MainFragment;
import com.example.appgcm.Fragments.PersonListFragment;
import com.example.appgcm.Fragments.EventFragment;
import com.example.appgcm.Listeners.MainListener;
import com.example.appgcm.Storage.DB.CRUDOperations;
import com.example.appgcm.Storage.DB.MyDatabase;
import com.example.appgcm.Util.CustomAnimation;
import com.example.appgcm.Util.CustomProgressDialog;
import com.example.appgcm.Util.Internet.ValidateConn;
import com.example.appgcm.Util.NavigationFragment;
import com.example.appgcm.Storage.PreferencesHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainListener {
    Toolbar toolbar;
    SharedPreferences sharedPreferences;
    private MainFragment mainFragment = new MainFragment();
    private ButtonFragment buttonFragment = new ButtonFragment();
    private ActivedPersonListFragment activedPersonListFragment = new ActivedPersonListFragment();
    private PersonListFragment personListFragment = new PersonListFragment();

    //ADDITIONAL CODE
    private ImageButton btnPrincipalMA;
    private ImageButton btnEventosMA;
    private ImageButton btnLecturasMA;
    private ImageButton btnConfiguracionMA;
    Thread thread;

    private Handler handler = new Handler();
    public static final String TAG = "NfcTag";
    public static final String MIME_TEXT_PLAIN = "text/plain";

    CustomProgressDialog customProgressDialog = new CustomProgressDialog(MainActivity.this);


    private EventFragment eventFragment = new EventFragment();
    private ConfigurationFragment configurationFragment = new ConfigurationFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnPrincipalMA = (ImageButton)findViewById(R.id.btnPrincipalMA);
        btnEventosMA = (ImageButton)findViewById(R.id.btnEventosMA);
        btnLecturasMA = (ImageButton)findViewById(R.id.btnLecturasMA);
        btnConfiguracionMA = (ImageButton)findViewById(R.id.btnConfiguracionMA);

        getSupportActionBar().setTitle("Menú Principal");
        activateMigrationData();
        goToMainFragment();

        btnPrincipalMA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainFragment();
            }
        });

        btnEventosMA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToEventFragment();
            }
        });

        btnLecturasMA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { goToActivedPersonListFragment();
            }
        });


        btnConfiguracionMA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { goToConfigurationFragment();
            }
        });


    }

    private void activateMigrationData(){
        final CRUDOperations crudOperations = new CRUDOperations(new MyDatabase(MainActivity.this));
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        int migrationLapse = 15000;
                        List<ConfigurationEntity> lst = new ArrayList<ConfigurationEntity>();
                        lst = crudOperations.getAllConfiguration();
                        if(lst.size()>0){
                            migrationLapse = lst.get(0).getMigrationLapse() * 1000;
                        }
                        Thread.sleep(migrationLapse);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ValidateConn validateConn = new ValidateConn(MainActivity.this,"2");
                            validateConn.mstartConn();
                        }
                    });
                }
            }
        });
        thread.start();


    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }


    @Override
    public void showProgressDialog() {
        customProgressDialog.showProgressDialog();
    }

    @Override
    public void dismissProgressDialog() {
        customProgressDialog.dismissProgressDialog();
    }

    @Override
    public void updateActionBar(String texto) {
        getSupportActionBar().setTitle(texto);
    }


    @Override
    public void goToMainFragment(){
        NavigationFragment.addFragment(null, mainFragment, "MainFragment", this,
                R.id.main_activity_content, false, CustomAnimation.LEFT_RIGHT);
    }

    @Override
    public void goToButtonFragment() {
        NavigationFragment.addFragment(null, buttonFragment, "MainFragment", this,
                R.id.main_activity_content, false, CustomAnimation.LEFT_RIGHT);
    }

    public void goToEventFragment(){
        NavigationFragment.addFragment(null, eventFragment, "ReadingGroupFragment", this,
                R.id.main_activity_content, false, CustomAnimation.LEFT_RIGHT);

    }

    public void goToPersonListFragment(){
        NavigationFragment.addFragment(null, personListFragment, "personListFragment", this,
                R.id.main_activity_content, false, CustomAnimation.LEFT_RIGHT);

    }

    public void goToActivedPersonListFragment(){
        NavigationFragment.addFragment(null, activedPersonListFragment, "activedPersonListFragment", this,
                R.id.main_activity_content, false, CustomAnimation.LEFT_RIGHT);

    }

    public void goToConfigurationFragment(){
        NavigationFragment.addFragment(null, configurationFragment, "ConfigurationFragment", this,
                R.id.main_activity_content, false, CustomAnimation.LEFT_RIGHT);

    }

    @Override
    public void CloseSession() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        PreferencesHelper.signOut(getApplication());
        this.finish();
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
//            case R.id.btnAyuda:
//                Toast.makeText(this,"Ayuda", Toast.LENGTH_LONG).show();
//                return true;
            case R.id.btnSalir:
                 //Toast.makeText(this,"Salir", Toast.LENGTH_LONG).show();
                CloseSession();
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
