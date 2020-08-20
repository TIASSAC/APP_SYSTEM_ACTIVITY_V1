package com.example.appgcm.Fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appgcm.Adapters.RVAdapterForPersonList;
import com.example.appgcm.Entities.PersonEntity;
import com.example.appgcm.Listeners.MainListener;
import com.example.appgcm.R;
import com.example.appgcm.Storage.DB.CRUDOperations;
import com.example.appgcm.Storage.DB.MyDatabase;
import com.example.appgcm.Util.Const;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MainListener mListener;
    private RecyclerView rvListaPersonas;
    private TextView txtMensajeListadoPersonas;
    private SwipeRefreshLayout SwipePersonListFragment;
    private ImageView btnBuscarPLFragment;
    private TextInputLayout LayoutSearchPLFragment  ;
    private TextInputEditText txtSearchPLFragment;
    public PersonListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonListFragment newInstance(String param1, String param2) {
        PersonListFragment fragment = new PersonListFragment();
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
        return inflater.inflate(R.layout.fragment_person_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListener.updateActionBar("Consulta de Personal Activo");
        rvListaPersonas = (RecyclerView) getActivity().findViewById(R.id.rvListaPersonas);
        txtMensajeListadoPersonas = (TextView)getActivity().findViewById(R.id.txtMensajeListadoPersonas);
        SwipePersonListFragment = (SwipeRefreshLayout)getActivity().findViewById(R.id.SwipePersonListFragment);
        LayoutSearchPLFragment = (TextInputLayout)getActivity().findViewById(R.id.LayoutSearchPLFragment);
        txtSearchPLFragment = (TextInputEditText)getActivity().findViewById(R.id.txtSearchPLFragment);
        btnBuscarPLFragment = (ImageView)getActivity().findViewById(R.id.btnBuscarPLFragment);
        txtSearchPLFragment.setFilters(new InputFilter[] { inputfilter });
        rvListaPersonas.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rvListaPersonas.setLayoutManager(llm);


        btnBuscarPLFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
        loadData();
        SwipePersonListFragment.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();

            }
        });

    }

    private void loadData(){
        if(!validarDatos()){
            List<PersonEntity> lst =new ArrayList<PersonEntity>();
            CRUDOperations crudOperations = new CRUDOperations(new MyDatabase(getActivity()));
            String busqueda = txtSearchPLFragment.getText().toString().trim();

            lst = crudOperations.getAllActivePerson(busqueda);
            if(lst.size()> 0){
                txtMensajeListadoPersonas.setVisibility(View.GONE);
                rvListaPersonas.setVisibility(View.VISIBLE);
                RVAdapterForPersonList adapter = new RVAdapterForPersonList(lst);
                rvListaPersonas.setAdapter(adapter);
            }else{
                txtMensajeListadoPersonas.setVisibility(View.VISIBLE);
                rvListaPersonas.setVisibility(View.GONE);
            }

        }
        SwipePersonListFragment.setRefreshing(false);
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
    private Boolean validarDatos(){
        Boolean response = false;
        LayoutSearchPLFragment.setError(null);

        int  lengthSearch = txtSearchPLFragment.getText().length();

        if(lengthSearch > 50){
            //Toast.makeText(getActivity(), "Valor permitido de búsqueda entre 1 y 50 caracteres", Toast.LENGTH_LONG).show();
            LayoutSearchPLFragment.setError("Valor permitido entre 1 y 50 caracteres");
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



}
