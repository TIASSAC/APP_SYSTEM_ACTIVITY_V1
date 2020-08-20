package com.example.appgcm.Util.Internet;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import java.lang.reflect.Method;

import androidx.annotation.RequiresApi;

import com.example.appgcm.R;
import com.example.appgcm.Util.Const;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.example.appgcm.Util.Const.STR_ENABLE_WIFI;

public class NetworkUtil {

    /************VARIABLES************/
    private static final String TAG = "NetworkUtil";
    private ProgressDialog progressDialog;

    public Context ctx;
    public  NetworkUtil(Context context){
        ctx = context;
    }

    public static int NET_CNNT_SERVICE_WEB_OK = 1; // NetworkAvailable
    public static int NET_CNNT_SERVICE_WEB_TIMEOUT = 2; // no NetworkAvailable
    public static int NET_NOT_PREPARE = 3; // Net no ready
    public static int NET_ERROR = 4; //net error
    private static int TIMEOUT = 15000; // TIMEOUT
    public Handler handler = new Handler();

    private String SERVICE_WEB_URL = Const.HTTP_SITE;

    /**************METODOS**************/

    /***METODO PARA HABILITAR EL WIFI ***/
    public boolean turnOnWifi(){
        Log.v("NETWORK UTIL","X1");
        Boolean resultado = false;
        WifiManager wifiManager = (WifiManager) ctx.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()){

            wifiManager.setWifiEnabled(true);

            progressDialog = new ProgressDialog(ctx, R.style.AppThemeAssacDialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setTitle("Información");
            progressDialog.setMessage(STR_ENABLE_WIFI);
            progressDialog.show();

            handler.postDelayed(new Runnable() {
                public void run() {

                    // función a ejecutar
                    progressDialog.dismiss();
                    turnOnWifi();
                    //Log.v("TIMER", "MENSAJE");

                    //handler.postDelayed(this, 500);
                }

            }, 4000);
        }else{
            Log.v("NETWORK UTIL","X3");
            resultado = true;
        }
        return resultado;
    }

    public boolean isOnlineNet() {
        Boolean PingServer;
        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 2 "+SERVICE_WEB_URL);
            int val = p.waitFor();
            if(val == 0){PingServer = true;}else{ PingServer = false;}

        } catch (Exception e) {
            e.printStackTrace();
            PingServer = false;
        }
        return PingServer;
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isAvailable() && info.isConnected();
    }

    /**VALIDA SI TIENE CONEXION POR LOS 3 CASOS**/
    public  boolean hasConnect(Context context){
        boolean wifiConnected = isWIFIConnected(context); /*CONEXIÓN POR WIFI*/
        boolean mobileConnected = isMOBILEConnected(context); /*CONEXIÓN POR DATOS*/
        boolean wiredConnected = isWiredConnected(context); /*CONEXIÓN POR ETHERNET*/
        if(!wifiConnected && !mobileConnected && !wiredConnected){
            // If there is no connection, the user is informed that there is no network currently.
            return false;
        }
        return true;
    }

    /**VALIDA SI TIENE CONEXION POR ETHERNET**/
    public static boolean isWiredConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    /**VALIDA SI TIENE CONEXION POR PAQUETE DE DATOS**/
    public static boolean isMOBILEConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(networkInfo !=null && networkInfo.isConnected()){
            return true;
        }
        return false;
    }

    /**VALIDA SI TIENE CONEXION POR WIFI**/
    public static boolean isWIFIConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if(networkInfo !=null && networkInfo.isConnected()){
            return true;
        }
        return false;
    }

    /**  **/
    public  boolean connectionNetwork(String addresURL, Context context) {

//        AndroidConfigurationRepository repository = new AndroidConfigurationRepository(context);
//        List<AndroidConfigurationORMEntity> lista = repository.getAAll();
        int timeout = 15000;

//        if (lista!=null){
//            if (lista.size() > 0){
//                timeout = (lista.get(0).getTimeoutNetwork() * 1000);
//            }
//        }

        boolean result = false;
        HttpURLConnection httpUrl = null;
        try {
            httpUrl = (HttpURLConnection) new URL(addresURL)
                    .openConnection();
            httpUrl.setConnectTimeout(timeout);
            httpUrl.connect();
            result = true;
        } catch (IOException e) {
            Log.e("CONECTION", e.getMessage());
            result = false;
        } finally {
            if (null != httpUrl) {
                httpUrl.disconnect();
            }
            httpUrl = null;
        }
        return result;
    }

    /**
     *  wifi is enable
     */
    public static boolean isWifiEnabled(Context context) {
        ConnectivityManager mgrConn = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager mgrTel = (TelephonyManager) context.
                getSystemService(Context.TELEPHONY_SERVICE);

        return ((mgrConn.getActiveNetworkInfo() != null && mgrConn
                .getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) ||
                (mgrTel.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS));
    }

    /**
     * <p>
     * getLocalIpAddress(never used)
     * </p>
     */
    public static String getLocalIpAddress() {
        String ret = "";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        ret = inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return ret;
    }

    /**
     * Get the IP address of the local WiFi, tv and littleServer used by me
     *
     * @param context
     * @return
     */
    public InetAddress getLocalIpAddress(Context  context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        InetAddress address = null;
        try {
            address = InetAddress.getByName(String.format(Locale.ENGLISH,
                    "%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                    (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff)));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return address;
    }

    /*
     * Return the current state of the network
     * */
    /*public static int getNetState(Context context, String addresURL) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo networkinfo = connectivity.getActiveNetworkInfo();
                if (networkinfo != null) {
                    if (networkinfo.isAvailable() && networkinfo.isConnected()) {
                        if (!connectionNetwork(addresURL)) {
                            return NET_CNNT_SERVICE_WEB_TIMEOUT;
                        } else {
                            return NET_CNNT_SERVICE_WEB_OK;
                        }
                    } else {
                        return NET_NOT_PREPARE;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NET_ERROR;
    }*/

}




