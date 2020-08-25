package com.example.appgcm.Util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.appgcm.Entities.ArchivoAdjuntoEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class OperacionesCache {

    public <T> void setList(String key, ArrayList<T> list, Context context) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        SharedPreferences sharedPref = context.getSharedPreferences("SharedPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEditor;
        myEditor = sharedPref.edit();
        myEditor.putString(key, json);
        myEditor.commit();
    }

    public ArrayList<ArchivoAdjuntoEntity> getListaAdjuntosCache(String DETALLE_TAG,  Context context){
        Gson gson = new Gson();
        ArrayList<ArchivoAdjuntoEntity> productFromShared = new ArrayList<>();
        SharedPreferences sharedPref = context.getSharedPreferences("SharedPrefs", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString(DETALLE_TAG, new ArrayList<ArchivoAdjuntoEntity>().toString());

        Type type = new TypeToken<ArrayList<ArchivoAdjuntoEntity>>() {}.getType();
        productFromShared = gson.fromJson(jsonPreferences, type);

        return productFromShared;
    }

    public <T> void AddArchivoAdjunto(Context context, ArchivoAdjuntoEntity adjunto) {
        ArrayList<ArchivoAdjuntoEntity> lista = getListaAdjuntosCache("lista_adjuntos", context);
        boolean existe = false;
        for (int i = 0; i < lista.size(); i++){
            if(adjunto.getNombre().equals(lista.get(i).getNombre()) && adjunto.getTipo().equals(lista.get(i).getTipo())){
                existe = true;
            }
        }
        if(!existe)
            lista.add(adjunto);
        setList("lista_adjuntos", lista, context);
    }

}
