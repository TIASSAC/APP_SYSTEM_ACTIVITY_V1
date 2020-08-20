package com.example.appgcm.Fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appgcm.Adapters.RVAdapterForActivity;
import com.example.appgcm.Entities.ActivityEntity;
import com.example.appgcm.Entities.ConfigurationEntity;
import com.example.appgcm.Entities.PersonEntity;
import com.example.appgcm.Entities.RegistrationActivityEntity;
import com.example.appgcm.MainActivity;
import com.example.appgcm.Storage.DB.CRUDOperations;
import com.example.appgcm.Storage.DB.MyDatabase;
import com.example.appgcm.Storage.PreferencesHelper;
import com.example.appgcm.Util.Const;
import com.example.appgcm.Util.CustomAnimation;
import com.example.appgcm.Util.DatePickerFragment;
import com.example.appgcm.Util.NavigationFragment;
import com.google.android.material.button.MaterialButton;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appgcm.Listeners.MainListener;
import com.example.appgcm.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import static com.example.appgcm.Util.Const.GLOBAL_USER_ID_SESSION;


public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change typetxtFechaMainFragments of parameters
    private String mParam1;
    private String mParam2;

    private MainListener mListener;
    MaterialButton btnIrActivity2;
    MaterialButton btnIrFragmento2;

    //ADDITIONAL CODE
    private String txtUsuario;
    private String txtPuntoAcceso;
    CRUDOperations crudOperations;
    private String Fecha;
    Thread thread;
    Handler handler = new Handler();
    Handler handlerDialog = new Handler();
    RecyclerView rvListaActividadesMainFragment;
    TextView txtMensajeSinDatosMainFragment;
    MaterialButton btnGenerarRegistroActividadMainFragment;
    TextView txtFechaMainFragment,txtNombreFechaMainFragment;
    ImageView btnFechaMainFragment;
    TextInputLayout LayoutComentariosMainFragment;
    TextInputEditText txtComentariosMainFragment;
    TextView txtUltimaMigracionMainFragment;
    List<ActivityEntity> activityEntities;

    public MainFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListener.updateActionBar("Registro de Actividad");
        Const.SELECTED_ACTIVITY = "";
        Const.SELECTED_ACTIVITY_LIST.clear();
        //ADDITIONAL CODE
        crudOperations = new CRUDOperations(new MyDatabase(getActivity()));
        txtUsuario =  PreferencesHelper.getNameUserSession(getActivity());
        txtMensajeSinDatosMainFragment = (TextView) getActivity().findViewById(R.id.txtMensajeSinDatosMainFragment);
        btnGenerarRegistroActividadMainFragment = (MaterialButton) getActivity().findViewById(R.id.btnGenerarRegistroActividadMainFragment);
        txtFechaMainFragment = (TextView)getActivity().findViewById(R.id.txtFechaMainFragment);
        txtNombreFechaMainFragment = (TextView)getActivity().findViewById(R.id.txtNombreFechaMainFragment);

        btnFechaMainFragment = (ImageView)getActivity().findViewById(R.id.btnFechaMainFragment);
        LayoutComentariosMainFragment = (TextInputLayout)getActivity().findViewById(R.id.LayoutComentariosMainFragment);
        txtComentariosMainFragment = (TextInputEditText)getActivity().findViewById(R.id.txtComentariosMainFragment);
        txtUltimaMigracionMainFragment = (TextView)getActivity().findViewById(R.id.txtUltimaMigracionMainFragment);
        txtComentariosMainFragment.setFilters(new InputFilter[] { inputfilter });
        txtComentariosMainFragment.setText("");
        rvListaActividadesMainFragment = (RecyclerView) getActivity().findViewById(R.id.rvListaActividadesMainFragment);
        rvListaActividadesMainFragment.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rvListaActividadesMainFragment.setLayoutManager(llm);

        /**Para el nombre del día de la semana***/
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String nombreDiaSemana = sdf.format(d);
        txtNombreFechaMainFragment.setText(nombreDiaSemana +"  ");
        txtFechaMainFragment.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

        GLOBAL_USER_ID_SESSION = PreferencesHelper.getIdPersonSession(getContext());
        Log.v("idUsuario","idUsuario" + GLOBAL_USER_ID_SESSION);
        List<ConfigurationEntity> lst = new ArrayList<ConfigurationEntity>();
        lst = crudOperations.getAllConfiguration();
        actualizarTiempo();



        activityEntities = new ArrayList<>();

        activityEntities = crudOperations.getAllActiveActivity("");


        if(activityEntities.size()>0){
            txtMensajeSinDatosMainFragment.setVisibility(View.GONE);
            rvListaActividadesMainFragment.setVisibility(View.VISIBLE);
            RVAdapterForActivity adapter = new RVAdapterForActivity(activityEntities);

            final RVAdapterForActivity  rvAdapterForActivity = new RVAdapterForActivity(activityEntities);

            ItemTouchHelper.SimpleCallback itemTouchSimpleCallback =  new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    //activityEntities.remove(viewHolder.getAdapterPosition());
                    //rvAdapterForActivity.notifyDataSetChanged();
                    Toast.makeText(getActivity(),"Se dará una acción",Toast.LENGTH_LONG).show();
                }
            };


            rvListaActividadesMainFragment.setAdapter(adapter);
            new ItemTouchHelper(itemTouchSimpleCallback).attachToRecyclerView(rvListaActividadesMainFragment);


        }else{
            txtMensajeSinDatosMainFragment.setVisibility(View.VISIBLE);
            rvListaActividadesMainFragment.setVisibility(View.GONE);
        }

        /******************************/

        /******************************/

        btnGenerarRegistroActividadMainFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Const.SELECTED_ACTIVITY = "";
                if(!(validar())){
                    for(int i = 0; i < Const.SELECTED_ACTIVITY_LIST.size(); i++){
                        Const.SELECTED_ACTIVITY =  Const.SELECTED_ACTIVITY + Const.SELECTED_ACTIVITY_LIST.get(i) + ",";
                    }
                    Const.SELECTED_ACTIVITY = Const.SELECTED_ACTIVITY.substring(0,(Const.SELECTED_ACTIVITY.length()-1));

                    Log.v("ARRAY",Const.SELECTED_ACTIVITY);
                    PersonEntity personEntities = new PersonEntity();
                    personEntities = crudOperations.getPerson(PreferencesHelper.getIdPersonSession(getActivity()));
                    if(personEntities.getIdPerson()>0){
                        int addRegistrationActivityResponse = 0;
                        RegistrationActivityEntity registrationActivityEntity = new RegistrationActivityEntity(Const.getAndroidDeviceId(getActivity()),
                                personEntities.getIdPerson(), personEntities.getPersonName(), personEntities.getFirstLastName(),
                                personEntities.getSecondLastName(),personEntities.getPhotocheck(), personEntities.getDocumentNumber(),
                                txtFechaMainFragment.getText().toString(), Const.SELECTED_ACTIVITY,
                                PreferencesHelper.getIdUserSession(getActivity()),"A","P", txtComentariosMainFragment.getText().toString());
                        addRegistrationActivityResponse = crudOperations.addRegistrationActivity(registrationActivityEntity);
                        if(addRegistrationActivityResponse > 0){

                            Toast.makeText(getActivity(), "Registro generado con éxito", Toast.LENGTH_LONG).show();
                            Const.SELECTED_ACTIVITY_LIST.clear();

                            List<ActivityEntity> activityEntities = new ArrayList<>();
                            activityEntities = crudOperations.getAllActiveActivity("");
                            RVAdapterForActivity adapter = new RVAdapterForActivity(activityEntities);
                            rvListaActividadesMainFragment.setAdapter(adapter);
                            txtComentariosMainFragment.setText("");

                        }else{
                            Toast.makeText(getActivity(), "Ocurrió un error al tratar de generar el registro de actividad.", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getActivity(), "Ocurrió un error al tratar de obtener los datos del usuario.", Toast.LENGTH_LONG).show();
                    }
                }


            }
        });

        btnFechaMainFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

    }

    private void actualizarTiempo(){
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        Thread.sleep(1000);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            txtUltimaMigracionMainFragment.setText(Const.LAST_MIGRATION_DATE);
                        }
                    });
                }
            }
        });
        thread.start();
    }

    private InputFilter inputfilter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source != null && Const.CARACTERES_NO_PERMITIDOS.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };

    private Boolean validar(){
        boolean response = false;
        if(Const.SELECTED_ACTIVITY_LIST.size() == 0){
            Toast.makeText(getActivity(), "Debe seleccionar al menos una actividad", Toast.LENGTH_LONG).show();
            response = true;
        }
        LayoutComentariosMainFragment.setError(null);

        int  lengthSearch = txtComentariosMainFragment.getText().length();

        if(lengthSearch > 150){
            LayoutComentariosMainFragment.setError("Valor permitido entre 1 y 150 caracteres");
            Toast.makeText(getActivity(), "Valor permitido de búsqueda entre 1 y 150 caracteres", Toast.LENGTH_LONG).show();
            response = true;
        }
        return  response;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }
    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            int tamanoDia, tamanoMes;
            String dia, mes;
            tamanoDia = String.valueOf(dayOfMonth).length();
            tamanoMes = String.valueOf(monthOfYear).length();
            if(tamanoDia == 1){
                dia = "0"+dayOfMonth;
            }else{
                dia = String.valueOf(dayOfMonth);
            }
            if(tamanoMes == 1){
                mes = "0"+(monthOfYear+1);
            }else{
                mes = String.valueOf((monthOfYear+1));
            }

            String nombreDiaSemana = "";
            try{
                SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                Date myDate = inFormat.parse(dia+"-"+mes+"-"+year);
                 nombreDiaSemana = sdf.format(myDate);

            }catch (Exception e){
                nombreDiaSemana= "";
            }
            txtNombreFechaMainFragment.setText(nombreDiaSemana +"  ");
            txtFechaMainFragment.setText(dia + "/" + mes + "/" + String.valueOf(year));
        }
    };

}


