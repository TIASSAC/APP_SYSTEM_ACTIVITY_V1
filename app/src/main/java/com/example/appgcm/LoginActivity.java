package com.example.appgcm;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.appgcm.Fragments.LoginFragment;
import com.example.appgcm.Listeners.LoginListener;
import com.example.appgcm.Util.Const;
import com.example.appgcm.Util.CustomAnimation;
import com.example.appgcm.Util.Internet.NetworkUtil;
import com.example.appgcm.Util.Internet.VerificarInternet;
import com.example.appgcm.Util.NavigationFragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import okhttp3.internal.Util;

import static com.example.appgcm.Util.Const.HTTP_SITE;
import static com.example.appgcm.Util.Const.STR_ADVERTENCIA;
import static com.example.appgcm.Util.Const.STR_CANCEL;
import static com.example.appgcm.Util.Const.STR_NOT_FOUND_ERROR;
import static com.example.appgcm.Util.Const.STR_OFF_WIFI;
import static com.example.appgcm.Util.Const.STR_OK;



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

    @Override
    public void loadService() {

//        VerificarInternet procesoVerificar = new VerificarInternet(LoginActivity.this, new VerificarInternet.Verificar() {
//            @Override
//            public void ConexionExitosa() {
//
//                Toast.makeText(getApplication(),"Si hay conexion a internet",Toast.LENGTH_SHORT).show();
////                new SendReciveData(LoginActivity.this, 2).loadDataAndroid(LoginActivity.this);
//            }
//
//            @Override
//            public void ConexionFallida() {
//
//                if(new NetworkUtil().isWIFIConnected(LoginActivity.this)){
//                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(LoginActivity.this,R.style.AppThemeAlertDialog);
//                    builder.setTitle(STR_ADVERTENCIA);
//                    builder.setMessage(STR_NOT_FOUND_ERROR + " - " + HTTP_SITE);
//                    builder.setCancelable(false);
//                    builder.setNegativeButton(STR_OK, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            dialogInterface.cancel();
//                        }
//                    });
//
//                    //AlertDialog alertDialog = builder.create();
//                    builder.show();
//                }else{
//                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(LoginActivity.this,R.style.AppThemeAlertDialog);
//                    builder.setTitle(STR_ADVERTENCIA);
//                    builder.setMessage(STR_OFF_WIFI);
//                    builder.setCancelable(false);
//                    builder.setPositiveButton(STR_OK, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            //  Habilitar WIFI
////                            NetworkUtil networkUtil = new NetworkUtil();
//////                            networkUtil.loadNetwork(LoginActivity.this);
//////                            (new Handler()).postDelayed(new Runnable() {
//////                                @Override
//////                                public void run() {
//////                                    loadService();
//////                                }
//////                            }, 8000);
//                        }
//                    });
//                    builder.setNegativeButton(STR_CANCEL, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            dialogInterface.cancel();
//                        }
//                    });
//
//                    //AlertDialog alertDialog = builder.create();
//                    builder.show();
//                }
//            }
//        });
//
//        procesoVerificar.execute();
    }
}
