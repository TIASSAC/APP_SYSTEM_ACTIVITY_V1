package com.example.appgcm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appgcm.Adapters.RVAdapterForAdjunto;
import com.example.appgcm.Entities.ArchivoAdjuntoEntity;
import com.example.appgcm.Util.OperacionesCache;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.Util;
import com.vincent.filepicker.activity.AudioPickActivity;
import com.vincent.filepicker.activity.ImagePickActivity;
import com.vincent.filepicker.activity.NormalFilePickActivity;
import com.vincent.filepicker.activity.VideoPickActivity;
import com.vincent.filepicker.filter.entity.AudioFile;
import com.vincent.filepicker.filter.entity.ImageFile;
import com.vincent.filepicker.filter.entity.NormalFile;
import com.vincent.filepicker.filter.entity.VideoFile;

import java.util.ArrayList;

import static com.vincent.filepicker.activity.AudioPickActivity.IS_NEED_RECORDER;
import static com.vincent.filepicker.activity.BaseActivity.IS_NEED_FOLDER_LIST;
import static com.vincent.filepicker.activity.ImagePickActivity.IS_NEED_CAMERA;

public class AdjuntaArchivoActivity extends AppCompatActivity {
    ImageView btnCargarImagen, btnCargarDocumento, btnCargarVideo, btnCargarAudio;
    Button btnContinuar;
    private TextView mTvResult;
    RecyclerView rvListaAdjuntos;
    ArrayList<ArchivoAdjuntoEntity> listArchivos;

    void cargarAdjuntos(){
        OperacionesCache operacion = new OperacionesCache();
        listArchivos = operacion.getListaAdjuntosCache("lista_adjuntos", this);
        if(listArchivos.size() == 0){
            mTvResult.setText("No ha seleccionado ningún archivo.");
        }
        else{
            String nombre = "";
            for (int i = 0; i < listArchivos.size(); i++){
               nombre += "\n" + listArchivos.get(i).getNombre();
            }
            if(listArchivos.size() == 1){
                mTvResult.setText(String.valueOf(listArchivos.size()) + " archivo seleccionado");
            }
            else{
                mTvResult.setText(String.valueOf(listArchivos.size()) + " archivos seleccionados");
            }
            //mTvResult.setText(nombre);
            RVAdapterForAdjunto adapter = new RVAdapterForAdjunto(listArchivos, AdjuntaArchivoActivity.this, mTvResult);
            rvListaAdjuntos.setAdapter(adapter);
            rvListaAdjuntos.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjunta_archivo);


        btnCargarImagen = (ImageView) findViewById(R.id.btnCargarImagen);
        btnCargarDocumento = (ImageView) findViewById(R.id.btnCargarDocumento);
        btnCargarVideo = (ImageView) findViewById(R.id.btnCargarVideo);
        btnCargarAudio = (ImageView) findViewById(R.id.btnCargarAudio);
        mTvResult = (TextView) findViewById(R.id.tv_result);
        btnContinuar = (Button) findViewById(R.id.btnContinuar);
        rvListaAdjuntos = (RecyclerView) findViewById(R.id.rvListaAdjuntos);
        rvListaAdjuntos.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getBaseContext());
        rvListaAdjuntos.setLayoutManager(llm);
        cargarAdjuntos();

        btnCargarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AdjuntaArchivoActivity.this, ImagePickActivity.class);
                intent1.putExtra(IS_NEED_CAMERA, true);
                intent1.putExtra(Constant.MAX_NUMBER, 9);
                intent1.putExtra(IS_NEED_FOLDER_LIST, true);
                startActivityForResult(intent1, Constant.REQUEST_CODE_PICK_IMAGE);
            }
        });

        btnCargarDocumento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(AdjuntaArchivoActivity.this, NormalFilePickActivity.class);
                intent4.putExtra(Constant.MAX_NUMBER, 9);
                intent4.putExtra(IS_NEED_FOLDER_LIST, true);
                intent4.putExtra(NormalFilePickActivity.SUFFIX,
                        new String[] {"xlsx", "xls", "doc", "dOcX", "ppt", ".pptx", "pdf", "txt"});
                startActivityForResult(intent4, Constant.REQUEST_CODE_PICK_FILE);
            }
        });
        btnCargarAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(AdjuntaArchivoActivity.this, AudioPickActivity.class);
                intent3.putExtra(IS_NEED_RECORDER, true);
                intent3.putExtra(Constant.MAX_NUMBER, 9);
                intent3.putExtra(IS_NEED_FOLDER_LIST, true);
                startActivityForResult(intent3, Constant.REQUEST_CODE_PICK_AUDIO);
            }
        });
        btnCargarVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(AdjuntaArchivoActivity.this, VideoPickActivity.class);
                intent2.putExtra(IS_NEED_CAMERA, true);
                intent2.putExtra(Constant.MAX_NUMBER, 9);
                intent2.putExtra(IS_NEED_FOLDER_LIST, true);
                startActivityForResult(intent2, Constant.REQUEST_CODE_PICK_VIDEO);
            }
        });

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        btnContinuar.setVisibility(View.GONE);
        OperacionesCache operacion = new OperacionesCache();
        switch (requestCode) {
            case Constant.REQUEST_CODE_PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    ArrayList<ImageFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
                    StringBuilder builder = new StringBuilder();
                    for (ImageFile file : list) {
                        String path = file.getPath();
                        builder.append(path + "\n");
                        ArchivoAdjuntoEntity archivo = new ArchivoAdjuntoEntity();
                        archivo.setTipo("Imagen");
                        archivo.setNombre(file.getName());
                        archivo.setPath(file.getPath());
                        archivo.setSize(file.getSize());
                        operacion.AddArchivoAdjunto(AdjuntaArchivoActivity.this, archivo);
                    }
                    //mTvResult.setText(builder.toString());
                    btnContinuar.setVisibility(View.VISIBLE);
                    cargarAdjuntos();
                }
                break;
            case Constant.REQUEST_CODE_PICK_VIDEO:
                if (resultCode == RESULT_OK) {
                    ArrayList<VideoFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_VIDEO);
                    StringBuilder builder = new StringBuilder();
                    for (VideoFile file : list) {
                        String path = file.getPath();
                        builder.append(path + "\n");
                        ArchivoAdjuntoEntity archivo = new ArchivoAdjuntoEntity();
                        archivo.setTipo("Video");
                        archivo.setNombre(file.getName());
                        archivo.setPath(file.getPath());
                        archivo.setSize(file.getSize());
                        operacion.AddArchivoAdjunto(AdjuntaArchivoActivity.this, archivo);
                    }
                    cargarAdjuntos();
                    btnContinuar.setVisibility(View.VISIBLE);
                }
                break;
            case Constant.REQUEST_CODE_PICK_AUDIO:
                if (resultCode == RESULT_OK) {
                    ArrayList<AudioFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_AUDIO);
                    StringBuilder builder = new StringBuilder();
                    for (AudioFile file : list) {
                        String path = file.getPath();
                        builder.append(path + "\n");
                        ArchivoAdjuntoEntity archivo = new ArchivoAdjuntoEntity();
                        archivo.setTipo("Audio");
                        archivo.setNombre(file.getName());
                        archivo.setPath(file.getPath());
                        archivo.setSize(file.getSize());
                        operacion.AddArchivoAdjunto(AdjuntaArchivoActivity.this, archivo);
                    }
                    cargarAdjuntos();
                    btnContinuar.setVisibility(View.VISIBLE);
                }
                break;
            case Constant.REQUEST_CODE_PICK_FILE:
                if (resultCode == RESULT_OK) {
                    ArrayList<NormalFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE);
                    StringBuilder builder = new StringBuilder();
                    for (NormalFile file : list) {
                        String path = file.getPath();
                        builder.append(path + "\n");
                        ArchivoAdjuntoEntity archivo = new ArchivoAdjuntoEntity();
                        archivo.setTipo("Documento");
                        archivo.setNombre(file.getName());
                        archivo.setPath(file.getPath());
                        archivo.setSize(file.getSize());
                        operacion.AddArchivoAdjunto(AdjuntaArchivoActivity.this, archivo);
                    }
                    cargarAdjuntos();
                    btnContinuar.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

}
