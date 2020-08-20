package com.example.appgcm.Interfaces;

import com.example.appgcm.Entities.RegistrationActivityEntity;
import com.example.appgcm.Util.Const;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RegistrationActivityInterface {

    String SERVICE_NAME_USER= Const.SERVICE_NAME + "RegistrationActivity";

    @Headers({
            "Content-Type: application/json",
            "application-id: B9D12B47-6B88-8471-FFAD-2B4FFD1EA100",
            "secret-key: 46C1AEC7-6BA7-D1C7-FF6A-FD9EA95C0C00",
            "application-type: REST"
    })
    @GET(SERVICE_NAME_USER + "/GetobtenerRegistroDeDispositivoMovil?")
    Call<String> GetobtenerRegistroDeDispositivoMovil(
            @Query("idSQLLite") int idSQLLite,
            @Query("idDispositivo") String idDispositivo,
            @Query("nombre") String nombre,
            @Query("apellidoPaterno") String apellidoPaterno,
            @Query("apellidoMaterno") String apellidoMaterno,
            @Query("fotocheck") String fotocheck,
            @Query("fechaRegistro") String fechaRegistro,
            @Query("tipoRegistro") String tipoRegistro,
            @Query("idUsuarioRegistro") int idUsuarioRegistro,
            @Query("acceso") String acceso,
            @Query("nroDocumento") String nroDocumento,
            @Query("comentario") String comentario
    );



}
