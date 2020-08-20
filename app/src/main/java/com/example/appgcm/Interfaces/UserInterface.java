package com.example.appgcm.Interfaces;

import com.example.appgcm.Entities.UserEntity;
import com.example.appgcm.Util.Const;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserInterface {

    String SERVICE_NAME_USER= Const.SERVICE_NAME + "User";

    @Headers({
            "Content-Type: application/json",
            "application-id: B9D12B47-6B88-8471-FFAD-2B4FFD1EA100",
            "secret-key: 46C1AEC7-6BA7-D1C7-FF6A-FD9EA95C0C00",
            "application-type: REST"
    })
    @GET(SERVICE_NAME_USER + "/GetlistarUsuarioParaDispositivoMovil?")
    Call<List<UserEntity>> GetlistarUsuarioParaDispositivoMovil(
            @Query("idDispositivo") String idDispositivo,
            @Query("acceso") String acceso
    );

    @Headers({
            "Content-Type: application/json",
            "application-id: B9D12B47-6B88-8471-FFAD-2B4FFD1EA100",
            "secret-key: 46C1AEC7-6BA7-D1C7-FF6A-FD9EA95C0C00",
            "application-type: REST"
    })
    @GET(SERVICE_NAME_USER + "/GetactualizarEstadoMigracionDispositivoMovil?")
    Call<String> GetactualizarEstadoMigracionDispositivoMovil(
            @Query("idDispositivo") String idDispositivo,
            @Query("idUsuario") int idUsuario,
            @Query("idUsuarioRegistro") int idUsuarioRegistro,
            @Query("acceso") String acceso
    );

//    @POST(SERVICE_NAME_USER + "/UpdUserSend")
//    Call<Void> doUpdUserSend(
//            @Body ReplyServiceEntity replyServiceEntity);

}
