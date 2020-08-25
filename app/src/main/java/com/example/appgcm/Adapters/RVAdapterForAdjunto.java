package com.example.appgcm.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appgcm.Entities.ArchivoAdjuntoEntity;
import com.example.appgcm.R;
import com.example.appgcm.Util.OperacionesCache;

import java.util.ArrayList;

public class RVAdapterForAdjunto extends RecyclerView.Adapter<RVAdapterForAdjunto.AdjuntoListViewHolder> {
    private ArrayList<ArchivoAdjuntoEntity> lecturasx;
    private Context context;
    private TextView cuentaitems;
    public  RVAdapterForAdjunto(ArrayList<ArchivoAdjuntoEntity> lecturas, Context context, TextView cuentaitems){
        this.lecturasx = lecturas;
        this.context = context;
        this.cuentaitems = cuentaitems;
    }

    @NonNull
    @Override
    public RVAdapterForAdjunto.AdjuntoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_for_adjuntos, parent, false);
        RVAdapterForAdjunto.AdjuntoListViewHolder pvh = new RVAdapterForAdjunto.AdjuntoListViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RVAdapterForAdjunto.AdjuntoListViewHolder holder, final int position) {
        String nombre = lecturasx.get(position).getNombre();
        String tam = (lecturasx.get(position).getSize() / 1024) + " KB";
        String tipo = lecturasx.get(position).getTipo();
        holder.txtNombreArchivo.setText(nombre);
        holder.txtTamArchivo.setText(tam);
        if(tipo.equals("Imagen")){
            holder.icono.setImageResource(R.drawable.ic_imagen_icon);
        }
        if(tipo.equals("Video")) {
            holder.icono.setImageResource(R.drawable.ic_video_icon);
        }
        if(tipo.equals("Audio")) {
            holder.icono.setImageResource(R.drawable.ic_audio_icon);;
        }
        if(tipo.equals("Documento")) {
            holder.icono.setImageResource(R.drawable.ic_documento_icon);
        }
        holder.btnEliminarArchivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lecturasx.remove(position);
                //Actualiza el Adapter
                notifyDataSetChanged();
                OperacionesCache op =  new OperacionesCache();
                op.setList("lista_adjuntos",  lecturasx, context);
                if(lecturasx.size() == 0){
                    cuentaitems.setText("No ha seleccionado ningún archivo.");
                }
                if(lecturasx.size() == 1){
                    cuentaitems.setText(String.valueOf(lecturasx.size()) + " archivo seleccionado");
                }
                if(lecturasx.size() > 1){
                    cuentaitems.setText(String.valueOf(lecturasx.size()) + " archivos seleccionados");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lecturasx.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class AdjuntoListViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombreArchivo ;
        TextView txtTamArchivo;
        ImageButton btnEliminarArchivo;
        ImageView icono;

        AdjuntoListViewHolder(View itemView) {
            super(itemView);
            txtNombreArchivo = (TextView)itemView.findViewById(R.id.txtNombreArchivo);
            txtTamArchivo = (TextView)itemView.findViewById(R.id.txtTamArchivo);
            btnEliminarArchivo = (ImageButton) itemView.findViewById(R.id.btnEliminarArchivo);
            icono = (ImageView) itemView.findViewById(R.id.ivicono);
        }

    }
}
