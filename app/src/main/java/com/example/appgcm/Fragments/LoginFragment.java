package com.example.appgcm.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.example.appgcm.Entities.RegistrationActivityEntity;
import com.example.appgcm.Entities.UserEntity;
import com.example.appgcm.Storage.DB.CRUDOperations;
import com.example.appgcm.Storage.DB.MyDatabase;
import com.example.appgcm.Util.Const;
import com.example.appgcm.Util.Internet.NetworkUtil;
import com.example.appgcm.Util.Internet.ValidateConn;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appgcm.Listeners.LoginListener;
import com.example.appgcm.R;
import com.example.appgcm.Storage.PreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import static com.example.appgcm.Util.Const.GLOBAL_USER_ID_SESSION;

public class LoginFragment extends Fragment {

    /***********************VARIABLES******************************/
    /***VARIABLES POR DEFECTO***/
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /***VARIABLES PERSONALIZADAS***/
    private TextInputLayout textInputLayout;
    private TextInputEditText txtUsuario;
    private TextInputEditText txtClave;
    private MaterialButton btnIngresar, btnSync;

    private LoginListener mListener;
    private Button btnCrearUsuario;
    private Button btnListarUsuario;
    private Button btnBuscarUsuario;
    private Button btnActualizarUsuario;
    private Button btnConsumirServicio;
    private NetworkUtil networkUtil;
    private CRUDOperations crudOperations;
    private TextView txtIdDispositivoFL;

    //ADDITIONAL CODE
    private ProgressDialog progressDialog;
    Handler handler = new Handler();
    /*****************************************************************/

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init(){
        txtUsuario = (TextInputEditText) getActivity().findViewById(R.id.txtUsuario);
        txtClave = (TextInputEditText) getActivity().findViewById(R.id.txtPassword);
        btnIngresar = (MaterialButton) getActivity().findViewById(R.id.btnIngresar);
        btnSync = (MaterialButton) getActivity().findViewById(R.id.btnSync);
        btnIngresarClic ingresarLogin = new btnIngresarClic();
        btnIngresar.setOnClickListener(ingresarLogin);

        btnCrearUsuario = (Button) getActivity().findViewById(R.id.btnCrearUsuario);
        btnListarUsuario = (Button) getActivity().findViewById(R.id.btnListarUsuario);
        btnBuscarUsuario = (Button) getActivity().findViewById(R.id.btnBuscarUsuario);
        btnActualizarUsuario = (Button) getActivity().findViewById(R.id.btnActualizarUsuario);
        btnConsumirServicio = (Button) getActivity().findViewById(R.id.btnConsumirServicio);
        txtIdDispositivoFL = (TextView) getActivity().findViewById(R.id.txtIdDispositivoFL);
        networkUtil = new NetworkUtil(getActivity());
        crudOperations = new CRUDOperations(new MyDatabase(getActivity()));
        txtIdDispositivoFL.setText("ID-Dispositivo: " + Const.getAndroidDeviceId(getActivity()));

        btnCrearUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        btnListarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //List<UserEntity> lst =new ArrayList<UserEntity>();
                List<RegistrationActivityEntity> lst =new ArrayList<RegistrationActivityEntity>();
                String lista = "";
                //lst= crudOperations.getPendingRegistrationAccess();
                //lst= crudOperations.getAllUsers();
//                for(int i = 0; i<lst.size(); i++){
//                    lista += lst.get(i).getIdSqlLite()+ "\n" +
//                            lst.get(i).getIdUser()+ "\n" +
//                            lst.get(i).getPersonName()+ "\n" +
//                            lst.get(i).getPhotocheck()+ "\n" +
//                            lst.get(i).getFirstLastName()+ "\n" +
//                            lst.get(i).getSecondLastName()+ "\n" +
//                            lst.get(i).getUUser()+ "\n" +
//                            lst.get(i).getUPassword()+ "\n" +
//                            lst.get(i).getRegistrationStatus()+ "\n";
//                }

                for(int i = 0; i<lst.size(); i++){

                    lista += "idsql: " + lst.get(i).getIdSqlLite() + "\n" +
                            "idmovil: " + lst.get(i).getIdMovilDevice() + "\n" +
                            "nombre : " + lst.get(i).getPersonName() + "\n" +
                             "apellido 1: " + lst.get(i).getFirstLastName() + "\n" +
                            "apellido 2: " + lst.get(i).getSecondLastName() + "\n" +
                            "fotocheck: " + lst.get(i).getPhotocheck() + "\n" +
                           // "fecha: " + lst.get(i).getRegistrationAccessDate() + "\n" +
                           // "tipo: " + lst.get(i).getRegistrationAccessType() + "\n" +
                           // "access point: " + lst.get(i).getAccessPoint() + "\n" +
                            "estado: " + lst.get(i).getRegistrationStatus() + "\n" +
                            "usuario registro: " + lst.get(i).getRegistrationUser() + "\n" +
                            "migracion: " + lst.get(i).getMigrationStatus() + "\n";

                }
                Toast.makeText(getActivity(), lista, Toast.LENGTH_LONG).show();
            }
        });

        btnBuscarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               UserEntity userEntity = new UserEntity();
//                userEntity =  crudOperations.getUser(1);
//                if(userEntity == null){
//                    Toast.makeText(getActivity(), "null", Toast.LENGTH_LONG).show();
//                }else{
//                    Toast.makeText(getActivity(), "\nid: "+userEntity.getIdUser() + "\nNombre: " + userEntity.getPersonName(), Toast.LENGTH_LONG).show();
//                }

            }
        });

        btnActualizarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int resultado = 0;
                UserEntity userEntity = new UserEntity();
                userEntity.setPersonName("gianfranco");
                userEntity.setFirstLastName("cha");
                userEntity.setSecondLastName("mon");
                userEntity.setPhotocheck("111");
                userEntity.setUPassword("123");
                userEntity.setUUser("user");
                userEntity.setRegistrationStatus("E");
                userEntity.setIdUser(1);
                resultado =  crudOperations.updateUser(userEntity);
                Toast.makeText(getActivity(), "resultado" + resultado, Toast.LENGTH_LONG).show();
            }
        });

        btnConsumirServicio.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                 Boolean b1 = false;
                 Boolean b2 = false;
                Boolean b3 = false;
                String x = "";
                ValidateConn validateConn = new ValidateConn(getActivity(),"1");
                validateConn.mstartConn();


            }
        });

        btnSincronizarClic SincronizarDatos = new btnSincronizarClic();
        btnSync.setOnClickListener(SincronizarDatos);
    }

    private class btnIngresarClic implements View.OnClickListener{

        @Override
        public void onClick(View view) {

            String usuario, clave;
            usuario = txtUsuario.getText().toString().trim();
            clave = txtClave.getText().toString().trim();
            //ADDITIONAL CODE
            CRUDOperations crudOperations = new CRUDOperations(new MyDatabase(getActivity()));
            UserEntity userEntity = new UserEntity();
            int totalUsers = crudOperations.getUserCount();
            userEntity = crudOperations.getUserForLogin(usuario.toUpperCase(),clave);
            if(totalUsers > 0){
                if(userEntity.getIdUser() > 0){
                    savePreferences(userEntity.getIdUser(), userEntity.getIdPerson(), userEntity.getPersonName(),userEntity.getFirstLastName() + " " + userEntity.getSecondLastName(),userEntity.getUUser(),userEntity.getUPassword(),"", "CATALINA HUANCA", "");
                    GLOBAL_USER_ID_SESSION = userEntity.getIdPerson();
                    mListener.goToMain();
                }else{
                    Toast.makeText(getActivity(),"Credenciales incorrectas", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(getActivity(),"No existen usuarios registrados.", Toast.LENGTH_LONG).show();
            }

        }
    }

    private class btnSincronizarClic implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            ValidateConn validateConn = new ValidateConn(getActivity(),"1");
            validateConn.mstartConn();
           // mListener.loadService();

//            //ADDITIONAL CODE
//            progressDialog = new ProgressDialog(getActivity(), R.style.AppThemeAssacDialog);
//            progressDialog.setMessage("Sincronizando Datos");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
//
//            handler.postDelayed(new Runnable() {
//                public void run() {
//
//                    // función a ejecutar
//                    progressDialog.dismiss();
//                    Toast.makeText(getActivity(),"Datos sincronizados correctamente", Toast.LENGTH_LONG).show();
//                    //Log.v("TIMER", "MENSAJE");
//
//                    //handler.postDelayed(this, 500);
//                }
//
//            }, 4000);

        }
    }

    //ADDITIONAL CODE
    private void savePreferences(int idUser, int idPerson, String name, String lastname, String user, String pass, String date, String operationName, String accessPoint) {
        PreferencesHelper.saveSession(getActivity(),idUser,idPerson, user,pass, name,lastname,date, operationName, accessPoint);

    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
            mListener = (LoginListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
