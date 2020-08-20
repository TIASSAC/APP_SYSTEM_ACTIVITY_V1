package com.example.appgcm.Listeners;

import android.app.ProgressDialog;

public interface MainListener {
    void goToMainFragment();
    void goToButtonFragment();
    void CloseSession();

    void showProgressDialog();
    void dismissProgressDialog();
    void updateActionBar(String texto);

}
