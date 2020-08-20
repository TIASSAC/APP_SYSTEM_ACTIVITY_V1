package com.example.appgcm.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.appgcm.Listeners.MainListener;
import com.example.appgcm.R;
import com.example.appgcm.Util.Const;
import com.example.appgcm.Util.DatePickerFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActivedPersonListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivedPersonListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MainListener mListener;
    private RecyclerView rvListaPersonasActivas;
    private TextView txtMensajeListadoPersonasActivas;
    private SwipeRefreshLayout SwipeActivedPersonFragment;

    private ImageView btnBuscarAPFragment;
    private ImageView btnCalendarACFragment;
    private TextView txtFechaACFragment;
    private TextInputLayout LayoutSearchACFragment  ;
    private TextInputEditText txtSearchACFragment;

    private int tipoConsulta = 1; //1: AMBOS, 2: SOLO INGRESOS // 3: SOLO SALIDAS

    public ActivedPersonListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActivedPersonListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActivedPersonListFragment newInstance(String param1, String param2) {
        ActivedPersonListFragment fragment = new ActivedPersonListFragment();
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
        return inflater.inflate(R.layout.fragment_actived_person_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tipoConsulta = 1;
        mListener.updateActionBar("Registros");
        rvListaPersonasActivas = (RecyclerView) getActivity().findViewById(R.id.rvListaPersonasActivas);
        txtMensajeListadoPersonasActivas = (TextView)getActivity().findViewById(R.id.txtMensajeListadoPersonasActivas);
        SwipeActivedPersonFragment = (SwipeRefreshLayout)getActivity().findViewById(R.id.SwipeActivedPersonFragment);
        rvListaPersonasActivas.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rvListaPersonasActivas.setLayoutManager(llm);

        btnBuscarAPFragment = (ImageView)getActivity().findViewById(R.id.btnBuscarAPFragment);
        btnCalendarACFragment = (ImageView)getActivity().findViewById(R.id.btnCalendarACFragment);
        txtFechaACFragment = (TextView) getActivity().findViewById(R.id.txtFechaACFragment);
        LayoutSearchACFragment = (TextInputLayout)getActivity().findViewById(R.id.LayoutSearchACFragment);
        txtSearchACFragment = (TextInputEditText)getActivity().findViewById(R.id.txtSearchACFragment);





        txtSearchACFragment.setFilters(new InputFilter[] { inputfilter });

        txtFechaACFragment.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        btnCalendarACFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        txtFechaACFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        btnBuscarAPFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loadData();
            }
        });

        //loadData();
        SwipeActivedPersonFragment.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //loadData();

            }
        });
    }


    private InputFilter inputfilter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source != null && Const.CARACTERES_NO_PERMITIDOS .contains(("" + source))) {
                return "";
            }
            return null;
        }
    };

//    private void loadData(){
//        if(!validarDatos()) {
//            List<RegistrationActivityEntity> lst = new ArrayList<RegistrationActivityEntity>();
//            List<RegistrationActivityEntity> lstX = new ArrayList<RegistrationActivityEntity>();
//            CRUDOperations crudOperations = new CRUDOperations(new MyDatabase(getActivity()));
//            String fecha = txtFechaACFragment.getText().toString().trim();
//            String busqueda = txtSearchACFragment.getText().toString().trim();
//
//            lst = crudOperations.getRegistrationAccessForToday(fecha,busqueda);
//            if (lst.size() > 0) {
//                if(tipoConsulta == 1){
//                    lstX = lst;
//                }else if (tipoConsulta == 2){
//
//                    for(int i = 0; i< lst.size(); i++){
//
//                        if((lst.get(i).getRegistrationAccessDate() != "-" && lst.get(i).getRegistrationAccessDateOut() != "-")){
//
//                            try{
//                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//                                Date accessDate = sdf.parse(lst.get(i).getRegistrationAccessDate());
//                                Date accessDateOut = sdf.parse(lst.get(i).getRegistrationAccessDateOut());
//                                if(accessDate.after(accessDateOut)){
//                                    lst.get(i).setRegistrationAccessDateOut("-");
//                                    lst.get(i).setAccessPointOut("-");
//                                    lst.get(i).setIdMovilDeviceOut("-");
//                                    lstX.add(lst.get(i));
//                                }
//                            }catch (Exception ex){
//                                Const.saveErrorData(getActivity(),new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()),"método: loadData on ActivedPersonFragment " + ex.getMessage(),"1");
//                            }
//
//                        }else if((lst.get(i).getRegistrationAccessDate() != "-" && lst.get(i).getRegistrationAccessDateOut() == "-")){
//                            lstX.add(lst.get(i));
//                        }
//                    }
//                    if(!(lstX.size()> 0)){
//                        txtMensajeListadoPersonasActivas.setVisibility(View.VISIBLE);
//                        rvListaPersonasActivas.setVisibility(View.GONE);
//                    }
//                }
//
//                txtMensajeListadoPersonasActivas.setVisibility(View.GONE);
//                rvListaPersonasActivas.setVisibility(View.VISIBLE);
//                RVAdapterForActivePersonList adapter = new RVAdapterForActivePersonList(lstX);
//                rvListaPersonasActivas.setAdapter(adapter);
//            } else {
//                txtMensajeListadoPersonasActivas.setVisibility(View.VISIBLE);
//                rvListaPersonasActivas.setVisibility(View.GONE);
//            }
//
//        }
//        SwipeActivedPersonFragment.setRefreshing(false);
//    }

    private Boolean validarDatos(){
        Boolean response = false;
        LayoutSearchACFragment.setError(null);

        int  lengthSearch = txtFechaACFragment.getText().length();

        if(lengthSearch > 50){
            LayoutSearchACFragment.setError("Valor permitido entre 1 y 50 caracteres");
            Toast.makeText(getActivity(), "Valor permitido de búsqueda entre 1 y 50 caracteres", Toast.LENGTH_LONG).show();
            response = true;
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
            txtFechaACFragment.setText(dia + "/" + mes + "/" + String.valueOf(year));
        }
    };
}
