package com.example.appgcm.Util;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.appgcm.R;

public class CustomProgressDialog {

    Context ctx;
    ProgressDialog mProgressDialog;

    public CustomProgressDialog(Context context) {
        ctx = context;
    }

    public void showProgressDialog(){

        mProgressDialog = new ProgressDialog(ctx, R.style.AppThemeAssacDialog);
        mProgressDialog.setMessage("Buscando dispositivo ...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
       // mProgressDialog.setContentView(R.layout.progress_dialog);

       // mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
           // @Override
           // public void onCancel(DialogInterface dialogInterface) {
           //     finCarga();
           // }
       // });

    }

    public void dismissProgressDialog(){
        mProgressDialog.dismiss();
        //mProgressDialog = null;
    }
}
