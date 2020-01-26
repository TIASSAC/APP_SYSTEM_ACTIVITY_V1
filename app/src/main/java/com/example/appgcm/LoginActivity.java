package com.example.appgcm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.appgcm.Fragments.LoginFragment;
import com.example.appgcm.Listeners.LoginListener;
import com.example.appgcm.Util.CustomAnimation;
import com.example.appgcm.Util.NavigationFragment;

public class LoginActivity extends AppCompatActivity implements LoginListener {

    private LoginFragment loginFragment =new LoginFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        goToLogin();
    }

    private void goToLogin(){
        NavigationFragment.addFragment(null, loginFragment, "loginFragment", this,
                R.id.Login_linear_container, false, CustomAnimation.LEFT_RIGHT);
    }

    @Override
    public void goToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}
