package com.example.appgcm.Util.Internet;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.appgcm.Entities.ConfigurationEntity;
import com.example.appgcm.Entities.MigrationErrorEntity;
import com.example.appgcm.R;
import com.example.appgcm.Storage.DB.CRUDOperations;
import com.example.appgcm.Storage.DB.MyDatabase;
import com.example.appgcm.Storage.Rest;
import com.example.appgcm.Util.Const;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.appgcm.Util.Const.STR_ENABLE_WIFI;

public class ValidateConn  {

    public Context ctx;
    public String qType = ""; //1: muestra un progressDialog duante la migración, 2: no muestra ningún progressDialog
    public  ValidateConn(Context context, String queryType){
        ctx = context;
        qType = queryType;
    }

    //DECLARACIÓN DE VARIABLES
    private static final String TAG = "ValidateConn";
    private ProgressDialog progressDialog;
    private String SERVICE_WEB = Const.HTTP_SITE;
    private String mmigrationStartDate;


    public void mstartConn(){
        new validateWifiConn().execute();
    }
    public class validateWifiConn extends AsyncTask<String, Long, String> {
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            if(qType.equals("1")){
                progressDialog = new ProgressDialog(ctx, R.style.AppThemeAssacDialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setTitle("Información");
                progressDialog.setMessage(STR_ENABLE_WIFI);
                progressDialog.show();
            }

        }

        @Override
        protected String doInBackground(String... urls) {
            String response = "0";
            WifiManager wifiManager = (WifiManager) ctx.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();

            if (!wifiManager.isWifiEnabled()){

                wifiManager.setWifiEnabled(true);
                try{
                    Thread.sleep(8000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                info = cm.getActiveNetworkInfo();
                if(info != null && info.isAvailable() && info.isConnected()){
                    response = "1";
                }

            }else{
                if(info != null && info.isAvailable() && info.isConnected()){
                    response = "1";
                }

            }

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            if(qType.equals("1")){
                progressDialog.dismiss();
            }

            if(response == "1"){
                new validateServerConn().execute();
            }else if(response == "0"){

                if(qType.equals("1")){
                    Toast.makeText(ctx, "No se tiene salida a INTERNET. Verifique su conexión y vuelva a intentarlo", Toast.LENGTH_LONG).show();
                }
                Const.saveErrorData(ctx,mmigrationStartDate,"método: validateWifiConn /No se tiene salida a INTERNET. Verifique su conexión y vuelva a intentarlo","1");

            }
        }

}

    public class validateServerConn extends AsyncTask<Boolean,Long, Boolean>{
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(qType.equals("1")){
            progressDialog = new ProgressDialog(ctx, R.style.AppThemeAssacDialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setTitle("Información");
            progressDialog.setMessage("Conectando con el servidor");
            progressDialog.show();
        }
        mmigrationStartDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        Const.LAST_MIGRATION_DATE = mmigrationStartDate;

    }

    @Override
    protected Boolean doInBackground(Boolean... booleans) {
        int timeout = 15000;
        CRUDOperations crudOperations = new CRUDOperations(new MyDatabase(ctx));
        List<ConfigurationEntity> lst = new ArrayList<ConfigurationEntity>();
        lst = crudOperations.getAllConfiguration();
        if(lst.size()>0){
            timeout = lst.get(0).getTimeout() * 1000;
        }

        boolean result = false;
        HttpURLConnection httpUrl = null;
        try {
            httpUrl = (HttpURLConnection) new URL(SERVICE_WEB)
                    .openConnection();
            httpUrl.setConnectTimeout(timeout);
            httpUrl.connect();
            result = true;
        } catch (IOException e) {
            Log.e(TAG, "ERROR DE CONEXIÓN AL SERVIDOR: "+ e.getMessage());
            result = false;
        } finally {
            if (null != httpUrl) {
                httpUrl.disconnect();
            }
            httpUrl = null;
        }

        return result;
    }

    @Override
    protected void onPostExecute(Boolean s) {
        if(qType.equals("1")){
            progressDialog.dismiss();
        }

        if(s){
            Rest rest = new Rest(ctx);
            //Receive User's from Server
            rest.receiveUserData(mmigrationStartDate);
            //Receive Person's from Server
            rest.receivePersonData(mmigrationStartDate);
            //Receive Activity's from Server
            rest.receiveActivityData(mmigrationStartDate);
            //Send Transction's to Server
            rest.sendRegistrationActivityData(mmigrationStartDate);
            //Receive RegistrationAccess's from Server
            //rest.receiveRegistrationAccessData(mmigrationStartDate);
        }else{
            if(qType.equals("1")){
                Toast.makeText(ctx, "No se tiene conexión con el servidor", Toast.LENGTH_LONG).show();
            }
            Const.saveErrorData(ctx,mmigrationStartDate,"método: validateServerConn / No se tiene conexión con el servidor","1");
        }
    }


}




}