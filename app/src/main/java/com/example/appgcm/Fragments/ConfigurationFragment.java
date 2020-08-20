package com.example.appgcm.Fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appgcm.Entities.ConfigurationEntity;
import com.example.appgcm.Listeners.MainListener;
import com.example.appgcm.R;
import com.example.appgcm.Storage.DB.CRUDOperations;
import com.example.appgcm.Storage.DB.MyDatabase;
import com.example.appgcm.Util.Const;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ConfigurationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MainListener mListener;
    private MaterialButton btnGuardarConfiguracion;
    private CRUDOperations crudOperations;
    private TextInputLayout LayoutTimeOut;
    private TextInputEditText txtTimeOut;
    private TextInputLayout LayouIntervaloMigracion;
    private TextInputEditText txtIntervaloMigracion;
    private TextView txtIdDispositivoMF;
    //ADDITIONAL CODE

    List<ConfigurationEntity> lst =new ArrayList<ConfigurationEntity>();
    public ConfigurationFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ConfigurationFragment newInstance(String param1, String param2) {
        ConfigurationFragment fragment = new ConfigurationFragment();
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
        return inflater.inflate(R.layout.fragment_configuration, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListener.updateActionBar("Configuración");

        LayoutTimeOut = getActivity().findViewById(R.id.LayoutTimeOut);
        txtTimeOut = (TextInputEditText) getActivity().findViewById(R.id.txtTimeOut);
        LayouIntervaloMigracion = getActivity().findViewById(R.id.LayouIntervaloMigracion);
        txtIntervaloMigracion = (TextInputEditText) getActivity().findViewById(R.id.txtIntervaloMigracion);
        txtIdDispositivoMF = (TextView) getActivity().findViewById(R.id.txtIdDispositivoMF);
        txtIdDispositivoMF.setText(Const.getAndroidDeviceId(getActivity()));

        btnGuardarConfiguracion = (MaterialButton) getActivity().findViewById(R.id.btnGuardarConfiguracion);
        ArrayList<String> strings = new ArrayList<>();
        crudOperations = new CRUDOperations(new MyDatabase(getActivity()));
        strings.add("BOLÍVAR");
        strings.add("TÚNEL SUR");


        ArrayAdapter<String> stringArrayAdapter  = new ArrayAdapter<String>(getContext(),R.layout.spinner_theme_1,strings);
        stringArrayAdapter.setDropDownViewResource(R.layout.spinner_theme_1);
    ;

        crudOperations = new CRUDOperations(new MyDatabase(getActivity()));
        lst = crudOperations.getAllConfiguration();
        if(lst.size() > 0){

            txtTimeOut.setText(""+lst.get(0).getTimeout());
            txtIntervaloMigracion.setText(""+lst.get(0).getMigrationLapse());
        }else{
            txtTimeOut.setText(""+15);
            txtIntervaloMigracion.setText(""+15);
        }


        btnGuardarConfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!validarDatos()){
                    ConfigurationEntity configurationEntity;
                    int responseAddConfiguration;
                    int responseUpdateConfiguration;
                    if(lst.size()>0){
                        //UPDATE CONFIGURATION
                        configurationEntity = new ConfigurationEntity(lst.get(0).getIdConfiguration(), 0,"",
                                lst.get(0).getRegistrationDate(),
                                new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()),"1","1", Integer.parseInt(txtTimeOut.getText().toString().trim()), Integer.parseInt(txtIntervaloMigracion.getText().toString().trim()));
                        responseUpdateConfiguration =  crudOperations.updateConfiguration(configurationEntity);
                        if(responseUpdateConfiguration > 0){
                            Toast.makeText(getActivity(), "Configuración guardada con éxito", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getActivity(), "Error al actualizar configuración", Toast.LENGTH_LONG).show();
                            Const.saveErrorData(getActivity(),new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()),"método: updateConfiguration / Error al actualizar configuración Respuesta de la actualización: "+ responseUpdateConfiguration,"1");
                        }
                    }else{
                        configurationEntity = new ConfigurationEntity(0, "",
                                new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()),
                                new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()),"1","1",Integer.parseInt(txtTimeOut.getText().toString().trim()), Integer.parseInt(txtIntervaloMigracion.getText().toString().trim()));
                        //ADD CONFIGURATION
                        responseAddConfiguration =  crudOperations.addConfiguration(configurationEntity);
                        if(responseAddConfiguration > 0){
                            Toast.makeText(getActivity(), "Configuración guardada con éxito", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getActivity(), "Error al guardar configuración", Toast.LENGTH_LONG).show();
                            Const.saveErrorData(getActivity(),new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()),"método: guardarConfiguración / Error al guardar configuración Respuesta de la actualización: "+ responseAddConfiguration,"1");
                        }
                    }
                }


            }
        });

    }

    private Boolean validarDatos(){
        Boolean response = false;
        LayoutTimeOut.setError(null);
        LayouIntervaloMigracion.setError(null);

        int  lengthTimeOut = txtTimeOut.getText().length();
        Log.v("txtTimeOut",""+lengthTimeOut);
        if(lengthTimeOut == 0 || lengthTimeOut > 3){
            LayoutTimeOut.setError("Valor permitido entre 15 y 120 segundos");
            response = true;
        }else{
            int timeOut = Integer.parseInt(txtTimeOut.getText().toString());
            if(timeOut < 15 ||  timeOut > 120){
                LayoutTimeOut.setError("Valor permitido entre 15 y 120 segundos");
                response = true;
            }else{
                LayoutTimeOut.setError(null);
            }
        }

        int  lengthIntervaloMigracion = txtIntervaloMigracion.getText().length();
        Log.v("txtIntervaloMigracion",""+lengthIntervaloMigracion);
        if(lengthIntervaloMigracion == 0 || lengthIntervaloMigracion > 3){
            LayouIntervaloMigracion.setError("Valor permitido entre 15 y 120 segundos");
            response = true;
        }else{

            int intervaloMigracion = Integer.parseInt(txtIntervaloMigracion.getText().toString());

            if(intervaloMigracion < 15 ||  intervaloMigracion > 120){
                LayouIntervaloMigracion.setError("Valor permitido entre 15 y 120 segundos");

                response = true;
            }else{
                LayouIntervaloMigracion.setError(null);
            }
        }
        return response;
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
