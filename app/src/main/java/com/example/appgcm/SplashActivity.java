package com.example.appgcm;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.appgcm.Storage.PreferencesHelper;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_SCREEN_DELAY = 3000;

    private static final String TAG ="SplashActivity" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TimerTask task = new TimerTask()
        {
            @Override
            public void run() {
                Intent intent;
                boolean session= PreferencesHelper.isSignedIn(SplashActivity.this);
                if(session)
                {
                    Log.d(TAG,"En sesión");
                    intent=new Intent(SplashActivity.this, MainActivity.class);
                }else {
                    Log.d(TAG,"Fuera de sesión");
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }
}
