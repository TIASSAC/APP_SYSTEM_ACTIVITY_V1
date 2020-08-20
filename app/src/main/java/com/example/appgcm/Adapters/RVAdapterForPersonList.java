package com.example.appgcm.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appgcm.Entities.PersonEntity;
import com.example.appgcm.R;

import java.util.List;

public class RVAdapterForPersonList extends RecyclerView.Adapter<RVAdapterForPersonList.PersonListViewHolder> {

    private List<PersonEntity> lecturasx;
    public  RVAdapterForPersonList(List<PersonEntity> lecturas){
        this.lecturasx = lecturas;
    }
    @NonNull
    @Override
    public RVAdapterForPersonList.PersonListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_for_person_list, parent, false);
        RVAdapterForPersonList.PersonListViewHolder pvh = new RVAdapterForPersonList.PersonListViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RVAdapterForPersonList.PersonListViewHolder holder, int position) {
        String nombres = lecturasx.get(position).getPersonName() + " " + lecturasx.get(position).getFirstLastName() + " " + lecturasx.get(position).getSecondLastName();

        if(!(lecturasx.get(position).getPathFileBase64() == null) ){
            if(!(lecturasx.get(position).getPathFileBase64().equals(""))){
                String rutaBase64 = lecturasx.get(position).getPathFileBase64();
                byte[] decodedString = Base64.decode(rutaBase64, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                holder.imgFotoListadoPersonas.setImageBitmap(decodedByte);
            }
        }
        holder.txtNombresListadoPersonas.setText(nombres);
        holder.txtNroDocumentoListadoPersonas.setText("DNI: " + lecturasx.get(position).getDocumentNumber());
        holder.txtFotocheckListadoPersonas.setText(lecturasx.get(position).getPhotocheck());
    }

    @Override
    public int getItemCount() {
        return lecturasx.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    public static class PersonListViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView txtNombresListadoPersonas ;
        TextView txtNroDocumentoListadoPersonas;
        TextView txtFotocheckListadoPersonas;
        ImageView imgFotoListadoPersonas;

        PersonListViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cvListaPersonas);
            txtNombresListadoPersonas = (TextView)itemView.findViewById(R.id.txtNombresListadoPersonas);
            txtNroDocumentoListadoPersonas = (TextView)itemView.findViewById(R.id.txtNroDocumentoListadoPersonas);
            txtFotocheckListadoPersonas = (TextView)itemView.findViewById(R.id.txtFotocheckListadoPersonas);
            imgFotoListadoPersonas = (ImageView) itemView.findViewById(R.id.imgFotoListadoPersonas);

        }
    }
}
