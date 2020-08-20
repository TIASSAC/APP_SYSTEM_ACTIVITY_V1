package com.example.appgcm.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.appgcm.Entities.*;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appgcm.R;

import java.util.List;

public class RVAdapterForReading extends RecyclerView.Adapter<RVAdapterForReading.PersonViewHolder> {

    private List<RegistrationActivityEntity> lecturasx;
    public  RVAdapterForReading(List<RegistrationActivityEntity> lecturas){
        this.lecturasx = lecturas;
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_for_reading, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        holder.txtFotcheckCVR.setText(lecturasx.get(position).getPhotocheck());
        holder.txtNombresCVR.setText(lecturasx.get(position).getPersonName() + " " + lecturasx.get(position).getFirstLastName() + " " + lecturasx.get(position).getSecondLastName());
        //holder.txtEstadoCVR.setText(lecturasx.get(""position).getRegistrationAccessType()"");
        //holder.txtFechaCVR.setText(lecturasx.get(position).getRegistrationAccessDate());
    }

    @Override
    public int getItemCount() {
        return lecturasx.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView txtFotcheckCVR;
        TextView txtNombresCVR;
        TextView txtEstadoCVR;
        TextView txtFechaCVR;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cvResumen);
            txtFotcheckCVR = (TextView)itemView.findViewById(R.id.txtFotcheckCVR);
            txtNombresCVR = (TextView)itemView.findViewById(R.id.txtNombresCVR);
            txtEstadoCVR = (TextView)itemView.findViewById(R.id.txtEstadoCVR);
            txtFechaCVR = (TextView)itemView.findViewById(R.id.txtFechaCVR);
        }
    }
}
