package com.example.appgcm.Interfaces;

import com.example.appgcm.Entities.ActivityEntity;
import com.example.appgcm.Util.Const;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ActivityInterface {

    String SERVICE_NAME_USER= Const.SERVICE_NAME + "Activity";

    @Headers({
            "Content-Type: application/json",
            "application-id: B9D12B47-6B88-8471-FFAD-2B4FFD1EA100",
            "secret-key: 46C1AEC7-6BA7-D1C7-FF6A-FD9EA95C0C00",
            "application-type: REST"
    })
    @GET(SERVICE_NAME_USER + "/GetlistarActividadParaDispositivoMovil?")
    Call<List<ActivityEntity>> GetlistarActividadParaDispositivoMovil(
            @Query("idDispositivo") String idDispositivo,
            @Query("acceso") String acceso
    );

    @Headers({
            "Content-Type: application/json",
            "application-id: B9D12B47-6B88-8471-FFAD-2B4FFD1EA100",
            "secret-key: 46C1AEC7-6BA7-D1C7-FF6A-FD9EA95C0C00",
            "application-type: REST"
    })
    @GET(SERVICE_NAME_USER + "/GetactualizarEstadoMigracionDispositivoMovilParaActividad?")
    Call<String> GetactualizarEstadoMigracionDispositivoMovilParaActividad(
            @Query("idDispositivo") String idDispositivo,
            @Query("idActividad") int idActividad,
            @Query("idUsuarioRegistro") int idUsuarioRegistro,
            @Query("acceso") String acceso
    );
}
