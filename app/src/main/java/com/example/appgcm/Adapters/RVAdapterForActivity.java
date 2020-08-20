package com.example.appgcm.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appgcm.Entities.ActivityEntity;
import com.example.appgcm.Entities.RegistrationActivityEntity;
import com.example.appgcm.R;
import com.example.appgcm.Util.Const;

import java.util.List;

public class RVAdapterForActivity extends RecyclerView.Adapter<RVAdapterForActivity.ActivityViewHolder> {
    private List<ActivityEntity> lecturasx;

    public RVAdapterForActivity(List<ActivityEntity> lecturas){
        this.lecturasx = lecturas;
    }
    @NonNull
    @Override
    public RVAdapterForActivity.ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_for_activity, parent, false);
        RVAdapterForActivity.ActivityViewHolder pvh = new RVAdapterForActivity.ActivityViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RVAdapterForActivity.ActivityViewHolder holder, final int position) {

        String nombreActividad = lecturasx.get(position).getActivityName();
        holder.txtNombreActividadCV.setText(nombreActividad);



        holder.chkActividadCV.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int idActividad = lecturasx.get(position).getIdActivity();
                if(isChecked){
                        if(!(Const.SELECTED_ACTIVITY_LIST.contains(idActividad))){
                            Const.SELECTED_ACTIVITY_LIST.add(idActividad);
                        }
                }else{

                    for(int i = 0; i<Const.SELECTED_ACTIVITY_LIST.size(); i++){
                        if(Const.SELECTED_ACTIVITY_LIST.get(i) == idActividad){
                            Const.SELECTED_ACTIVITY_LIST.remove(i);
                        }
                    }
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

    public static class ActivityViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView txtNombreActividadCV ;
        CheckBox chkActividadCV;

        ActivityViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cvActividadCV);
            txtNombreActividadCV = (TextView)itemView.findViewById(R.id.txtNombreActividadCV);
            chkActividadCV = (CheckBox) itemView.findViewById(R.id.chkActividadCV);



        }
    }


}
