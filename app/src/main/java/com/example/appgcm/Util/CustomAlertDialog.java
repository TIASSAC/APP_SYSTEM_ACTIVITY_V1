package com.example.appgcm.Util;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.appgcm.Entities.RegistrationActivityEntity;
import com.example.appgcm.Listeners.MainListener;
import com.example.appgcm.R;
import com.example.appgcm.Storage.DB.CRUDOperations;
import com.example.appgcm.Storage.DB.MyDatabase;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomAlertDialog extends DialogFragment {

    TextView txtNombresCustomAlertDialog;
    TextView txtFotocheckCustomAlertDialog;
    TextView txtPuntoAccesoCustomAlertDialog;
    TextView txtFechaRegistroCustomAlertDialog;
    TextView txtTipoRegistroCustomAlertDialog;
    TextView txtNroDocumentoCustomAlertDialog;
    ImageView imgFotoCustomAlertDialog;
    MaterialButton btnCancelarCustomAlertDialog;
    MaterialButton btnAceptarCustomAlertDialog;
    Context ctx;
    MainListener mListener;

    RegistrationActivityEntity registrationActivityEntityX;
    public CustomAlertDialog(RegistrationActivityEntity registrationActivityEntity, Context context) {
        registrationActivityEntityX = registrationActivityEntity;
        ctx = context;
   }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.vw_custom_alert_dialog, container, false);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(ctx, R.style.AppThemeAlertDialog);
        //Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //dialog.getWindow().setLayout(200,200);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        return dialog;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        txtNombresCustomAlertDialog = (TextView) getView().findViewById(R.id.txtNombresCustomAlertDialog);
        txtFotocheckCustomAlertDialog = (TextView) getView().findViewById(R.id.txtFotocheckCustomAlertDialog);
        txtPuntoAccesoCustomAlertDialog = (TextView) getView().findViewById(R.id.txtPuntoAccesoCustomAlertDialog);
        txtFechaRegistroCustomAlertDialog = (TextView) getView().findViewById(R.id.txtFechaRegistroCustomAlertDialog);
        txtTipoRegistroCustomAlertDialog = (TextView) getView().findViewById(R.id.txtTipoRegistroCustomAlertDialog);
        imgFotoCustomAlertDialog = (ImageView) getView().findViewById(R.id.imgFotoCustomAlertDialog);
        btnCancelarCustomAlertDialog = (MaterialButton) getView().findViewById(R.id.btnCancelarCustomAlertDialog);
        btnAceptarCustomAlertDialog = (MaterialButton) getView().findViewById(R.id.btnAceptarCustomAlertDialog);
        txtNroDocumentoCustomAlertDialog = (TextView) getView().findViewById(R.id.txtNroDocumentoCustomAlertDialog);

        txtNombresCustomAlertDialog.setText(registrationActivityEntityX.getPersonName() + " " + registrationActivityEntityX.getFirstLastName() + " " + registrationActivityEntityX.getSecondLastName());
        txtFotocheckCustomAlertDialog.setText(registrationActivityEntityX.getPhotocheck());
        //txtPuntoAccesoCustomAlertDialog.setText(registrationActivityEntityX.getAccessPoint());
        txtNroDocumentoCustomAlertDialog.setText("DNI: " + registrationActivityEntityX.getDocumentNumber());
       // txtFechaRegistroCustomAlertDialog.setText(registrationActivityEntityX.getRegistrationAccessDate());
        //txtTipoRegistroCustomAlertDialog.setText("Datos de Lectura - " + registrationActivityEntityX.getRegistrationAccessType());

        String rutaBase64 = registrationActivityEntityX.getPathFileBase64();
        byte[] decodedString = Base64.decode(rutaBase64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imgFotoCustomAlertDialog.setImageBitmap(decodedByte);

        btnCancelarCustomAlertDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
                mListener.dismissProgressDialog();
            }
        });

//        btnAceptarCustomAlertDialog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int responseAddRegistrationAccess = 0;
//                CRUDOperations crudOperations = new CRUDOperations(new MyDatabase(ctx));
//                //responseAddRegistrationAccess = crudOperations.addRegistrationAccess(registrationActivityEntityX);
//
//                if(responseAddRegistrationAccess > 0){
//                    Toast.makeText(ctx, "Registro exitoso", Toast.LENGTH_LONG).show();
//                }else{
//                    Toast.makeText(ctx, "Error al registrar", Toast.LENGTH_LONG).show();
//                    Const.saveErrorData(ctx,new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()),
//                            "método: NdefReaderTask / Mensaje: error al registrar el acesso del usuario: " +
//                                    registrationActivityEntityX.getPersonName() + " " + registrationActivityEntityX.getFirstLastName() + " " +
//                                    registrationActivityEntityX.getSecondLastName() + " " + registrationActivityEntityX.getPhotocheck() + " " + registrationActivityEntityX.getDocumentNumber() + " " +
//                                    registrationActivityEntityX.getRegistrationAccessDate() + " " + registrationActivityEntityX.getRegistrationAccessType(),"1");
//                }
//                getDialog().cancel();
//                mListener.dismissProgressDialog();
//            }
//        });

    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        mListener = (MainListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}
