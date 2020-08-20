package com.example.appgcm.Storage;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.example.appgcm.Entities.ActivityEntity;
import com.example.appgcm.Entities.PersonEntity;
import com.example.appgcm.Entities.RegistrationActivityEntity;
import com.example.appgcm.Entities.UserEntity;
import com.example.appgcm.Interfaces.ActivityInterface;
import com.example.appgcm.Interfaces.PersonInterface;
import com.example.appgcm.Interfaces.RegistrationActivityInterface;
import com.example.appgcm.Interfaces.UserInterface;
import com.example.appgcm.Storage.DB.CRUDOperations;
import com.example.appgcm.Storage.DB.MyDatabase;
import com.example.appgcm.Util.Const;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Rest {

    private static final String TAG = "Rest";
    //  API Service
    HttpLoggingInterceptor interceptor;
    OkHttpClient client;
    Gson gson;
    Retrofit retrofit;
    //Interfaces
    UserInterface userInterface;
    //Variables
    ProgressDialog mprogressDialog;
    String ANDROID_DEVICE;
    CRUDOperations crudOperations;

    private Context ctx;
    public Rest(Context context){
        ctx = context;
        //  Instantiate Response Api
        interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder().addInterceptor(interceptor)
                .connectTimeout(1,TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();

        //  Instantiate Retrofit2
        gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Const.HTTP_SITE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        ANDROID_DEVICE = Const.getAndroidDeviceId(ctx);
        crudOperations = new CRUDOperations(new MyDatabase(ctx));
    }

    /*FOR USER*/
    public void receiveUserData(final String mmigrationStartDate){

        UserInterface apiService = retrofit.create(UserInterface.class);
        Call<List<UserEntity>> call = apiService.GetlistarUsuarioParaDispositivoMovil(ANDROID_DEVICE, Const.HTTP_SITE_KEY);
        call.enqueue(new Callback<List<UserEntity>>() {

            @Override
            public void onResponse(Call<List<UserEntity>> call, Response<List<UserEntity>> response) {

                if (response.isSuccessful()){
                    if (response.code() == 200){

                        List<UserEntity> userEntities = response.body();
                        if(userEntities.size()>0 && userEntities.get(0).getIdUser() > 0){

                            for(int i=0; i<userEntities.size(); i++){
                                UserEntity userEntity = new UserEntity();
                                UserEntity userEntity1 = new UserEntity(userEntities.get(i).getIdUser(), userEntities.get(i).getIdPerson(),
                                        userEntities.get(i).getPersonName(),userEntities.get(i).getPhotocheck(),
                                        userEntities.get(i).getFirstLastName(),userEntities.get(i).getSecondLastName(),
                                        userEntities.get(i).getUUser(), userEntities.get(i).getUPassword(),userEntities.get(i).getRegistrationStatus());
                                //VALIDATE USER
                                userEntity =  crudOperations.getUser(userEntities.get(i).getIdUser());

                                if(userEntity.getIdSqlLite() == 0){
                                    //INSERT ON DB
                                    int responseAddUser = crudOperations.addUser(userEntity1);
                                    if(responseAddUser > 0){
                                        updateStatusUserOnServer(userEntity1.getIdUser(), 1, mmigrationStartDate);
                                    }else{
                                        Const.saveErrorData(ctx,mmigrationStartDate,"método: receiveUserData / código: "+response.code() + " / Mensaje: Error al insertar el usuario con ID " + userEntities.get(i).getIdUser() + " Respuesta de la inserción: "+ responseAddUser,"1");
                                    }
                                }else{
                                    //UPDATE ON DB
                                    int responseUpdateUser = crudOperations.updateUser(userEntity1);
                                    Log.v(TAG,"actualización exitosa" + responseUpdateUser);
                                    if(responseUpdateUser > 0){
                                        Log.v(TAG,"actualización exitosa");
                                        updateStatusUserOnServer(userEntity1.getIdUser(), 1, mmigrationStartDate);
                                    }else{
                                        Const.saveErrorData(ctx,mmigrationStartDate,"método: receiveUserData / código: "+response.code() + " / Mensaje: Error al actualizar el usuario con ID " + userEntities.get(i).getIdUser() + " Respuesta de la actualización: "+ responseUpdateUser,"1");
                                    }
                                }
                            }

                        }else{
                            if(userEntities.size()>0 && userEntities.get(0).getValorConsulta().equals("0")){
                                Const.saveErrorData(ctx,mmigrationStartDate,"método: receiveUserData / código: "+response.code() + " Mensaje:" + userEntities.get(0).getMensajeConsulta(),"1");
                            }
                            //Const.saveErrorData(ctx,mmigrationStartDate,"método: receiveUserData / código: "+response.code() + " El servicio retornó 0 registros","1");
                            //Log.v(TAG,"el servicio retornó 0 registros");
                        }

                    } else {
                        Const.saveErrorData(ctx,mmigrationStartDate,"método: receiveUserData / código: "+response.code(),"1");
                        //Log.v(TAG,"error 404");

                    }
                } else{
                    Const.saveErrorData(ctx,mmigrationStartDate,"método: receiveUserData / código: "+response.code(),"1");
                    //Log.v(TAG,"no exito");

                }

            }

            @Override
            public void onFailure(Call<List<UserEntity>> call, Throwable t) {

                if (t instanceof IOException){
                    //SERVICE_MENSAJE += "Android: Fallo en la red. \n";
                    //Log.v(TAG,"error" + t.getMessage());
                    Const.saveErrorData(ctx,mmigrationStartDate,"método: receiveUserData / Mensaje: OnFailure "+t.getMessage(),"1");
                }else{
                    //Log.v(TAG,"error" + t.getMessage());
                   // SERVICE_MENSAJE += "Android: Problema en la conversión. \n";
                    Const.saveErrorData(ctx,mmigrationStartDate,"método: receiveUserData / Mensaje: OnFailure "+t.getMessage(),"1");
                }

                call.cancel();
                //progressDialog.dismiss();
            }

        });
    }

    public void updateStatusUserOnServer(int idUsuario, int idUsuarioRegistro, final String mmigrationStartDate){

        UserInterface apiService = retrofit.create(UserInterface.class);
        Call<String> call = apiService.GetactualizarEstadoMigracionDispositivoMovil(ANDROID_DEVICE, idUsuario, idUsuarioRegistro, Const.HTTP_SITE_KEY);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){

                    if (response.code() == 200){
                        String rx = response.body();
                        if(!rx.equals("1")){
                            Const.saveErrorData(ctx,mmigrationStartDate,"método: updateStatusUserOnServer / código: "+response.code() + " / Mensaje: "+ rx,"1");
                        }

                    } else {
                        //Log.v(TAG,"error 404");
                        //progressDialog.dismiss();
                        Const.saveErrorData(ctx,mmigrationStartDate,"método: updateStatusUserOnServer / código: "+response.code(),"1");
                    }
                } else{
                    //Log.v(TAG,"no exito");
                    Const.saveErrorData(ctx,mmigrationStartDate,"método: updateStatusUserOnServer / código: "+response.code(),"1");

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (t instanceof IOException){
                    //SERVICE_MENSAJE += "Android: Fallo en la red. \n";
                    //Log.v(TAG,"error" + t.getMessage());
                    Const.saveErrorData(ctx,mmigrationStartDate,"método: updateStatusUserOnServer / Mensaje: OnFailure "+t.getMessage(),"1");
                }else{
                    //Log.v(TAG,"error" + t.getMessage());
                    // SERVICE_MENSAJE += "Android: Problema en la conversión. \n";
                    Const.saveErrorData(ctx,mmigrationStartDate,"método: updateStatusUserOnServer / Mensaje: OnFailure "+t.getMessage(),"1");
                }

                call.cancel();
                //progressDialog.dismiss();
            }
        });
    }

    /*FOR PERSON*/

    public void receivePersonData(final String mmigrationStartDate){

        PersonInterface apiService = retrofit.create(PersonInterface.class);
        Call<List<PersonEntity>> call = apiService.GetlistarPersonaParaDispositivoMovil(ANDROID_DEVICE, Const.HTTP_SITE_KEY);
        call.enqueue(new Callback<List<PersonEntity>>() {

            @Override
            public void onResponse(Call<List<PersonEntity>> call, Response<List<PersonEntity>> response) {

                if (response.isSuccessful()){
                    if (response.code() == 200){

                        List<PersonEntity> personEntities = response.body();

                        if(personEntities.size()>0 && personEntities.get(0).getIdPerson() > 0){

                            for(int i=0; i<personEntities.size(); i++){
                                PersonEntity personEntity = new PersonEntity();
                                PersonEntity personEntity1 = new PersonEntity(personEntities.get(i).getIdPerson(),
                                        personEntities.get(i).getIdArea(),personEntities.get(i).getAreaName(),
                                        personEntities.get(i).getIdRole(),personEntities.get(i).getRoleName(),
                                        personEntities.get(i).getPersonName(), personEntities.get(i).getFirstLastName(),
                                        personEntities.get(i).getSecondLastName(),personEntities.get(i).getPhotocheck(),
                                        personEntities.get(i).getDocumentNumber(),personEntities.get(i).getRegistrationStatus(), personEntities.get(i).getPathFileBase64());
                                //VALIDATE USER
                                personEntity =  crudOperations.getPerson(personEntities.get(i).getIdPerson());

                                if(personEntity.getIdPersonSqlLite() == 0){
                                    //INSERT ON DB
                                    int responseAddPerson = crudOperations.addPerson(personEntity1);
                                    if(responseAddPerson > 0){
                                        updateStatusPersonOnServer(personEntity1.getIdPerson(), 1, mmigrationStartDate);
                                    }else{
                                        Const.saveErrorData(ctx,mmigrationStartDate,"método: receivePersonData / código: "+response.code() + " / Mensaje: Error al insertar la persona con ID " + personEntities.get(i).getIdPerson() + " Respuesta de la inserción: "+ responseAddPerson,"1");
                                    }
                                }else{
                                    //UPDATE ON DB
                                    int responseUpdatePerson = crudOperations.updatePerson(personEntity1);
                                    Log.v(TAG,"actualización exitosa" + responseUpdatePerson);
                                    if(responseUpdatePerson > 0){
                                        Log.v(TAG,"actualización exitosa");
                                        updateStatusPersonOnServer(personEntity1.getIdPerson(), 1, mmigrationStartDate);
                                    }else{
                                        Const.saveErrorData(ctx,mmigrationStartDate,"método: receivePersonData / código: "+response.code() + " / Mensaje: Error al actualizar la persona con ID " + personEntities.get(i).getIdPerson() + " Respuesta de la actualización: "+ responseUpdatePerson,"1");
                                    }
                                }
                            }

                        }else{
                            if(personEntities.size()>0 && personEntities.get(0).getValorConsulta().equals("0")){
                                Const.saveErrorData(ctx,mmigrationStartDate,"método: receivePersonData / código: "+response.code() + " Mensaje:" + personEntities.get(0).getMensajeConsulta(),"1");
                            }
                            //Const.saveErrorData(ctx,mmigrationStartDate,"método: receiveUserData / código: "+response.code() + " El servicio retornó 0 registros","1");
                            //Log.v(TAG,"el servicio retornó 0 registros");
                        }

                    } else {
                        Const.saveErrorData(ctx,mmigrationStartDate,"método: receivePersonData / código: "+response.code(),"1");
                        //Log.v(TAG,"error 404");

                    }
                } else{
                    Const.saveErrorData(ctx,mmigrationStartDate,"método: receivePersonData / código: "+response.code(),"1");
                    //Log.v(TAG,"no exito");

                }

            }

            @Override
            public void onFailure(Call<List<PersonEntity>> call, Throwable t) {

                if (t instanceof IOException){
                    //SERVICE_MENSAJE += "Android: Fallo en la red. \n";
                    //Log.v(TAG,"error" + t.getMessage());
                    Const.saveErrorData(ctx,mmigrationStartDate,"método: receivePersonData / Mensaje: OnFailure "+t.getMessage(),"1");
                }else{
                    //Log.v(TAG,"error" + t.getMessage());
                    // SERVICE_MENSAJE += "Android: Problema en la conversión. \n";
                    Const.saveErrorData(ctx,mmigrationStartDate,"método: receivePersonData / Mensaje: OnFailure "+t.getMessage(),"1");
                }

                call.cancel();
                //progressDialog.dismiss();
            }

        });
    }

    public void updateStatusPersonOnServer(int idPersona, int idUsuarioRegistro, final String mmigrationStartDate){

        PersonInterface apiService = retrofit.create(PersonInterface.class);
        Call<String> call = apiService.GetactualizarEstadoMigracionDispositivoMovilParaPersona(ANDROID_DEVICE, idPersona, idUsuarioRegistro, Const.HTTP_SITE_KEY);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){

                    if (response.code() == 200){
                        String rx = response.body();
                        if(!rx.equals("1")){
                            Const.saveErrorData(ctx,mmigrationStartDate,"método: updateStatusPersonOnServer / código: "+response.code() + " / Mensaje: "+ rx,"1");
                        }

                    } else {
                        //Log.v(TAG,"error 404");
                        //progressDialog.dismiss();
                        Const.saveErrorData(ctx,mmigrationStartDate,"método: updateStatusPersonOnServer / código: "+response.code(),"1");
                    }
                } else{
                    //Log.v(TAG,"no exito");
                    Const.saveErrorData(ctx,mmigrationStartDate,"método: updateStatusPersonOnServer / código: "+response.code(),"1");

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (t instanceof IOException){
                    //SERVICE_MENSAJE += "Android: Fallo en la red. \n";
                    //Log.v(TAG,"error" + t.getMessage());
                    Const.saveErrorData(ctx,mmigrationStartDate,"método: updateStatusPersonOnServer / Mensaje: OnFailure "+t.getMessage(),"1");
                }else{
                    //Log.v(TAG,"error" + t.getMessage());
                    // SERVICE_MENSAJE += "Android: Problema en la conversión. \n";
                    Const.saveErrorData(ctx,mmigrationStartDate,"método: updateStatusPersonOnServer / Mensaje: OnFailure "+t.getMessage(),"1");
                }

                call.cancel();
                //progressDialog.dismiss();
            }
        });
    }

    /*FOR ACTIVITY*/
    public void receiveActivityData(final String mmigrationStartDate){

        ActivityInterface apiService = retrofit.create(ActivityInterface.class);
        Call<List<ActivityEntity>> call = apiService.GetlistarActividadParaDispositivoMovil(ANDROID_DEVICE, Const.HTTP_SITE_KEY);
        call.enqueue(new Callback<List<ActivityEntity>>() {

            @Override
            public void onResponse(Call<List<ActivityEntity>> call, Response<List<ActivityEntity>> response) {

                if (response.isSuccessful()){
                    if (response.code() == 200){

                        List<ActivityEntity> activityEntities = response.body();

                        if(activityEntities.size()>0 && activityEntities.get(0).getIdActivity() > 0){

                            for(int i=0; i<activityEntities.size(); i++){
                                ActivityEntity activityEntity = new ActivityEntity();
                                ActivityEntity activityEntity1 = new ActivityEntity(activityEntities.get(i).getIdActivity(),
                                        activityEntities.get(i).getActivityName(),
                                        activityEntities.get(i).getIdRole(),activityEntities.get(i).getRoleName(),
                                        activityEntities.get(i).getRegistrationStatus());
                                //VALIDATE USER
                                activityEntity =  crudOperations.getActivity(activityEntities.get(i).getIdActivity());

                                if(activityEntity.getIdSqlLiteActivity() == 0){
                                    //INSERT ON DB
                                    int responseAddPerson = crudOperations.addActivity(activityEntity1);
                                    if(responseAddPerson > 0){
                                        updateStatusActivityOnServer(activityEntity1.getIdActivity(), 1, mmigrationStartDate);
                                    }else{
                                        Const.saveErrorData(ctx,mmigrationStartDate,"método: receiveActivityData / código: "+response.code() + " / Mensaje: Error al insertar la actividad con ID " + activityEntities.get(i).getIdActivity() + " Respuesta de la inserción: "+ responseAddPerson,"1");
                                    }
                                }else{
                                    //UPDATE ON DB
                                    int responseUpdatePerson = crudOperations.updateActivity(activityEntity1);
                                    Log.v(TAG,"actualización exitosa" + responseUpdatePerson);
                                    if(responseUpdatePerson > 0){
                                        Log.v(TAG,"actualización exitosa");
                                        updateStatusPersonOnServer(activityEntity1.getIdActivity(), 1, mmigrationStartDate);
                                    }else{
                                        Const.saveErrorData(ctx,mmigrationStartDate,"método: receiveActivityData / código: "+response.code() + " / Mensaje: Error al actualizar la actividad con ID " + activityEntities.get(i).getIdActivity() + " Respuesta de la actualización: "+ responseUpdatePerson,"1");
                                    }
                                }
                            }

                        }else{
                            if(activityEntities.size()>0 && activityEntities.get(0).getValorConsulta().equals("0")){
                                Const.saveErrorData(ctx,mmigrationStartDate,"método: receiveActivityData / código: "+response.code() + " Mensaje:" + activityEntities.get(0).getMensajeConsulta(),"1");
                            }
                            //Const.saveErrorData(ctx,mmigrationStartDate,"método: receiveUserData / código: "+response.code() + " El servicio retornó 0 registros","1");
                            //Log.v(TAG,"el servicio retornó 0 registros");
                        }

                    } else {
                        Const.saveErrorData(ctx,mmigrationStartDate,"método: receiveActivityData / código: "+response.code(),"1");
                        //Log.v(TAG,"error 404");

                    }
                } else{
                    Const.saveErrorData(ctx,mmigrationStartDate,"método: receiveActivityData / código: "+response.code(),"1");
                    //Log.v(TAG,"no exito");

                }

            }

            @Override
            public void onFailure(Call<List<ActivityEntity>> call, Throwable t) {

                if (t instanceof IOException){
                    //SERVICE_MENSAJE += "Android: Fallo en la red. \n";
                    //Log.v(TAG,"error" + t.getMessage());
                    Const.saveErrorData(ctx,mmigrationStartDate,"método: receiveActivityData / Mensaje: OnFailure "+t.getMessage(),"1");
                }else{
                    //Log.v(TAG,"error" + t.getMessage());
                    // SERVICE_MENSAJE += "Android: Problema en la conversión. \n";
                    Const.saveErrorData(ctx,mmigrationStartDate,"método: receiveActivityData / Mensaje: OnFailure "+t.getMessage(),"1");
                }

                call.cancel();
                //progressDialog.dismiss();
            }

        });
    }

    public void updateStatusActivityOnServer(int idPersona, int idUsuarioRegistro, final String mmigrationStartDate){

        ActivityInterface apiService = retrofit.create(ActivityInterface.class);
        Call<String> call = apiService.GetactualizarEstadoMigracionDispositivoMovilParaActividad(ANDROID_DEVICE, idPersona, idUsuarioRegistro, Const.HTTP_SITE_KEY);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){

                    if (response.code() == 200){
                        String rx = response.body();
                        if(!rx.equals("1")){
                            Const.saveErrorData(ctx,mmigrationStartDate,"método: updateStatusActivityOnServer / código: "+response.code() + " / Mensaje: "+ rx,"1");
                        }

                    } else {
                        //Log.v(TAG,"error 404");
                        //progressDialog.dismiss();
                        Const.saveErrorData(ctx,mmigrationStartDate,"método: updateStatusActivityOnServer / código: "+response.code(),"1");
                    }
                } else{
                    //Log.v(TAG,"no exito");
                    Const.saveErrorData(ctx,mmigrationStartDate,"método: updateStatusActivityOnServer / código: "+response.code(),"1");

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (t instanceof IOException){
                    //SERVICE_MENSAJE += "Android: Fallo en la red. \n";
                    //Log.v(TAG,"error" + t.getMessage());
                    Const.saveErrorData(ctx,mmigrationStartDate,"método: updateStatusActivityOnServer / Mensaje: OnFailure "+t.getMessage(),"1");
                }else{
                    //Log.v(TAG,"error" + t.getMessage());
                    // SERVICE_MENSAJE += "Android: Problema en la conversión. \n";
                    Const.saveErrorData(ctx,mmigrationStartDate,"método: updateStatusActivityOnServer / Mensaje: OnFailure "+t.getMessage(),"1");
                }

                call.cancel();
                //progressDialog.dismiss();
            }
        });
    }

    /*FOR REGISTRATION ACCESS*/
    public void sendRegistrationActivityData(final String mmigrationStartDate){

        List<RegistrationActivityEntity> lst = new ArrayList<>();
        lst= crudOperations.getPendingRegistrationActivity();
        for(int i = 0; i<lst.size(); i++){
            Log.v("xxxxxx",""+lst.get(i).getComment());
            RegistrationActivityInterface apiService = retrofit.create(RegistrationActivityInterface.class);
            Call<String> call = apiService.GetobtenerRegistroDeDispositivoMovil(lst.get(i).getIdSqlLite(), ANDROID_DEVICE,  lst.get(i).getPersonName(),
                    lst.get(i).getFirstLastName(), lst.get(i).getSecondLastName(), lst.get(i).getPhotocheck(),
                    lst.get(i).getRegistrationActivityDate(), lst.get(i).getRegistrationActivityType(), lst.get(i).getRegistrationUser(), Const.HTTP_SITE_KEY, lst.get(i).getDocumentNumber(), lst.get(i).getComment());
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()){

                        if (response.code() == 200){
                           String rx = response.body();
                           int rxI;
                           try{
                               rxI = Integer.parseInt(rx);
                           }catch (Exception e){
                               rxI = 0;
                           }
                           if(rxI > 0){
                               int responseUpdateRegistrationAccess  = crudOperations.updateRegistrationActivityStatus(rxI);
                               if(responseUpdateRegistrationAccess > 0){
                                   Log.v(TAG,"actualización estado exito 200");
                               }else{
                                   Const.saveErrorData(ctx,mmigrationStartDate,"método: sendRegistrationActivityData / código: "+response.code() + " / Mensaje: Error al actualizar el registro de actividad. / Respuesta de la actualización: "+ responseUpdateRegistrationAccess,"1");
                               }
                           }else {
                               Const.saveErrorData(ctx,mmigrationStartDate,"método: sendRegistrationActivityData / código: "+response.code() + " / Mensaje: "+ rx,"1");
                           }

                        } else{
                            //Log.v(TAG,"error 404");
                            //progressDialog.dismiss();
                            Const.saveErrorData(ctx,mmigrationStartDate,"método: sendRegistrationActivityData / código: "+response.code(),"1");
                        }
                    } else{
                        //Log.v(TAG,"no exito");
                        Const.saveErrorData(ctx,mmigrationStartDate,"método: sendRegistrationActivityData / código: "+response.code(),"1");
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    if (t instanceof IOException){
                        //SERVICE_MENSAJE += "Android: Fallo en la red. \n";
                        //Log.v(TAG,"error" + t.getMessage());
                        Const.saveErrorData(ctx,mmigrationStartDate,"método: sendRegistrationActivityData / Mensaje: OnFailure "+t.getMessage(),"1");
                    }else{
                        //Log.v(TAG,"error" + t.getMessage());
                        // SERVICE_MENSAJE += "Android: Problema en la conversión. \n";
                        Const.saveErrorData(ctx,mmigrationStartDate,"método: sendRegistrationActivityData / Mensaje: OnFailure "+t.getMessage(),"1");
                    }

                    call.cancel();
                    //progressDialog.dismiss();
                }
            });
        }


    }
}
