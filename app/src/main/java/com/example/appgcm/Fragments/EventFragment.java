package com.example.appgcm.Fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appgcm.Adapters.RVAdapterForError;
import com.example.appgcm.Entities.MigrationErrorEntity;
import com.example.appgcm.Listeners.MainListener;
import com.example.appgcm.R;
import com.example.appgcm.Storage.DB.CRUDOperations;
import com.example.appgcm.Storage.DB.MyDatabase;
import com.example.appgcm.Util.DatePickerFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MainListener mListener;
    private RecyclerView rv;
    private TextView txtMensajeLecturasError;
    private SwipeRefreshLayout SwipeEventFragment;

    private ImageView btnCalendarREFragment;
    private ImageView btnBuscarREFragment;
    private TextView txtFechaREFragment;

    public EventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReadingGroupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventFragment newInstance(String param1, String param2) {
        EventFragment fragment = new EventFragment();
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
        return inflater.inflate(R.layout.fragment_reading_group, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListener.updateActionBar("Reporte de Eventos");
        rv = (RecyclerView) getActivity().findViewById(R.id.rvListaResumenAgrupado);
        txtMensajeLecturasError = (TextView)getActivity().findViewById(R.id.txtMensajeLecturasError);
        SwipeEventFragment = (SwipeRefreshLayout) getActivity().findViewById(R.id.SwipeEventFragment);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        btnCalendarREFragment = (ImageView)getActivity().findViewById(R.id.btnCalendarREFragment);
        txtFechaREFragment = (TextView) getActivity().findViewById(R.id.txtFechaREFragment);
        btnBuscarREFragment = (ImageView)getActivity().findViewById(R.id.btnBuscarREFragment);

        txtFechaREFragment.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        btnCalendarREFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DialogFragment dialogFragment = new DatePickerFragment();
//                dialogFragment.show(getFragmentManager(), "Date Picker");
                showDatePicker();
            }
        });
        txtFechaREFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        btnBuscarREFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadErrorData();
            }
        });

        //String fotocheck, String nombres, String estado, String fecha
//        ReadingGroupEntity readingEntity = new ReadingGroupEntity("27/03/2020","15","15");
//        ReadingGroupEntity readingEntityB = new ReadingGroupEntity("28/03/2020","15","15");
//        ReadingGroupEntity readingEntityC = new ReadingGroupEntity("29/03/2020","16","16");
//        ReadingGroupEntity readingEntityD = new ReadingGroupEntity("30/03/2020","16","16");
//        ArrayList<ReadingGroupEntity> persons = new ArrayList<>();
//        persons.add(readingEntity);
//        persons.add(readingEntityB);
//        persons.add(readingEntityC);
//        persons.add(readingEntityD);

        loadErrorData();
        SwipeEventFragment.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadErrorData();

            }
        });
    }

    private void loadErrorData(){
        List<MigrationErrorEntity> lst =new ArrayList<MigrationErrorEntity>();
        CRUDOperations crudOperations = new CRUDOperations(new MyDatabase(getActivity()));
        String fecha = txtFechaREFragment.getText().toString().trim();
        lst = crudOperations.getAllMigrationError(fecha);
        if(lst.size()> 0){
            txtMensajeLecturasError.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
            RVAdapterForError adapter = new RVAdapterForError(lst);
            rv.setAdapter(adapter);
        }else{
            txtMensajeLecturasError.setVisibility(View.VISIBLE);
            rv.setVisibility(View.GONE);
        }
        SwipeEventFragment.setRefreshing(false);
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
            txtFechaREFragment.setText(dia + "/" + mes + "/" + String.valueOf(year));
        }
    };
}
