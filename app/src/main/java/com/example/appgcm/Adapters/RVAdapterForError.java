package com.example.appgcm.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appgcm.Entities.MigrationErrorEntity;
import com.example.appgcm.R;

import java.util.List;

public class RVAdapterForError extends RecyclerView.Adapter<RVAdapterForError.ErrorViewHolder> {

    private List<MigrationErrorEntity> lecturasx;
    public  RVAdapterForError(List<MigrationErrorEntity> lecturas){
        this.lecturasx = lecturas;
    }

    @NonNull
    @Override
    public ErrorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_for_error, parent, false);
        ErrorViewHolder pvh = new ErrorViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ErrorViewHolder holder, int position) {
        holder.txtFechaEventoError.setText(lecturasx.get(position).getErrorDate());
        holder.txtDescripciónEvento.setText(lecturasx.get(position).getErrorDescription());
    }

    @Override
    public int getItemCount() {
        return lecturasx.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    public static class ErrorViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView txtFechaEventoError ;
        TextView txtDescripciónEvento;

        ErrorViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cvError);
            txtFechaEventoError = (TextView)itemView.findViewById(R.id.txtFechaEventoError);
            txtDescripciónEvento = (TextView)itemView.findViewById(R.id.txtDescripciónEvento);
        }
    }
}
