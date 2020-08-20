package com.example.appgcm.Storage.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabase extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;

    public static final String DATABASE_NAME = "BDSYSTEMACTIVITY2";

    public static final String TB_USER = "tb_user";
    public static final String TB_REGISTRATION_ACTIVITY = "tb_registration_activity";
    public static final String TB_MIGRATION_ERROR = "tb_migration_error";
    public static final String TB_CONFIGURATION = "tb_configuration";
    public static final String TB_PERSON = "tb_person";
    public static final String TB_ACTIVITY = "tb_activity";

    //Columnas de la Tabla USUARIO
    public static final String KEY_ID_SQLLITE_TB_USER = "idSqlLiteUser";
    public static final String KEY_ID_TB_USER = "IdUser";
    public static final String KEY_ID_PERSON_TB_USER = "IdPerson";
    public static final String KEY_PERSON_NAME_TB_USER = "PersonName";
    public static final String KEY_FIRST_LAST_NAME_TB_USER = "FirstLastName";
    public static final String KEY_SECOND_LAST_NAME_TB_USER = "SecondLastName";
    public static final String KEY_PHOTOCHECK_TB_USER = "Photocheck";
    public static final String KEY_USER_TB_USER = "UUser";
    public static final String KEY_PASSWORD_TB_USER = "UPassword";
    public static final String KEY_REGISTRATION_STATUS_TB_USER = "RegistrationStatus";

    //Columnas de la Tabla PERSONA
    public static final String KEY_ID_SQLLITE_TB_PERSON = "idSqlLitePerson";
    public static final String KEY_ID_TB_PERSON = "IdPerson";
    public static final String KEY_ID_AREA_TB_PERSON = "IdArea";
    public static final String KEY_AREA_NAME_TB_PERSON = "AreaName";
    public static final String KEY_ID_ROLE_TB_PERSON = "IdRole";
    public static final String KEY_ROLE_NAME_TB_PERSON = "RoleName";
    public static final String KEY_PERSON_NAME_TB_PERSON = "PersonName";
    public static final String KEY_FIRST_LAST_NAME_TB_PERSON = "FirstLastName";
    public static final String KEY_SECOND_LAST_NAME_TB_PERSON = "SecondLastName";
    public static final String KEY_PHOTOCHECK_TB_PERSON = "Photocheck";
    public static final String KEY_DOCUMENT_NUMBER_TB_PERSON = "DocumentNumber";
    public static final String KEY_DOCUMENT_PATHBASE64_TB_PERSON = "PathFileBase64";
    public static final String KEY_REGISTRATION_STATUS_TB_PERSON = "RegistrationStatus";

    //Columnas de la Tabla ACTIVIDAD
    public static final String KEY_ID_SQLLITE_TB_ACTIVITY = "idSqlLiteActivity";
    public static final String KEY_ID_TB_ACTIVITY = "IdActivity";
    public static final String KEY_ACTIVITY_NAME_TB_ACTIVITY = "ActivityName";
    public static final String KEY_ID_ROLE_TB_ACTIVITY = "IdRole";
    public static final String KEY_ROLE_NAME_TB_ACTIVITY = "RoleName";
    public static final String KEY_REGISTRATION_STATUS_TB_ACTIVITY = "RegistrationStatus";


    //Columnas de la Tabla REGISTRO DE actividad
    public static final String KEY_ID_SQLLITE_TB_REGISTRATION_ACTIVITY = "idSqlLiteRegistrationActivity";
    public static final String KEY_ID_TB_REGISTRATION_ACTIVITY = "IdRegistrationActivity";
    public static final String KEY_ID_MOVIL_DEVICE_TB_REGISTRATION_ACTIVITY = "IdMovilDevice";
    public static final String KEY_ID_PERSON_TB_REGISTRATION_ACTIVITY = "IdPerson";
    public static final String KEY_PERSON_NAME_TB_REGISTRATION_ACTIVITY = "PersonName";
    public static final String KEY_FIRST_LAST_NAME_TB_REGISTRATION_ACTIVITY = "FirstLastName";
    public static final String KEY_SECOND_LAST_NAME_TB_REGISTRATION_ACTIVITY = "SecondLastName";
    public static final String KEY_PHOTOCHECK_TB_REGISTRATION_ACTIVITY = "Photocheck";
    public static final String KEY_DOCUMENT_NUMBER_TB_REGISTRATION_ACTIVITY = "DocumentNumber";
    public static final String KEY_REGISTRATION_ACTIVITY_DATE_TB_REGISTRATION_ACTIVITY = "RegistrationActivityDate";
    public static final String KEY_REGISTRATION_ACTIVITY_TYPE_TB_REGISTRATION_ACTIVITY = "RegistrationActivityType";
    public static final String KEY_COMMENT_TB_REGISTRATION_ACTIVITY = "Comment";
    public static final String KEY_REGISTRATION_USER_TB_REGISTRATION_ACTIVITY = "RegistrationUser";
    public static final String KEY_REGISTRATION_STATUS_TB_REGISTRATION_ACTIVITY= "RegistrationStatus";
    public static final String KEY_MIGRATION_STATUS_TB_REGISTRATION_ACTIVITY = "MigrationStatus";

    //Columnas de la Tabla ERROR MIGRACIÓN
    public static final String KEY_ID_SQLLITE_TB_ERROR = "IdSqlLiteErrorMigration";
    public static final String KEY_MIGRATION_START_DATE_TB_ERROR = "MigrationStartDate";
    public static final String KEY_ERROR_DESCRIPTION_TB_ERROR = "ErrorDescription";
    public static final String KEY_ERROR_DATE_TB_ERROR = "ErrorDate";
    public static final String KEY_REGISTRATION_USER_TB_ERROR = "RegistrationUser";

    //Columnas de la Tabla CONFIGURACIÓN
    public static final String KEY_ID_SQLLITE_TB_CONFIGURATION = "IdSqlLiteErrorMigration";
    public static final String KEY_ACCESS_PONT_TB_CONFIGURATION = "accessPointConfiguration";
    public static final String KEY_ID_ACCESS_PONT_TB_CONFIGURATION = "accessPointConfigurationID";
    public static final String KEY_REGISTRATION_DATE_TB_CONFIGURATION = "ErrorDate";
    public static final String KEY_REGISTRATION_TIMEOUT_TB_CONFIGURATION = "TimeOut";
    public static final String KEY_REGISTRATION_MIGRATION_LAPSE_TB_CONFIGURATION = "MigrationLapse";
    public static final String KEY_UPDATE_DATE_TB_CONFIGURATION = "UpdateDate";
    public static final String KEY_REGISTRATION_USER_TB_CONFIGURATION = "RegistrationUser";
    public static final String KEY_UPDATE_USER_TB_CONFIGURATION = "UpdateUser";


    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    // TODO Auto-generated method stub
        String sql= "CREATE TABLE " + TB_USER + "("
                + KEY_ID_SQLLITE_TB_USER + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
                + KEY_ID_TB_USER + " INTEGER,"
                + KEY_ID_PERSON_TB_USER + " INTEGER,"
                + KEY_PERSON_NAME_TB_USER + " TEXT,"
                + KEY_FIRST_LAST_NAME_TB_USER + " TEXT,"
                + KEY_SECOND_LAST_NAME_TB_USER + " TEXT,"
                + KEY_PHOTOCHECK_TB_USER + " TEXT,"
                + KEY_USER_TB_USER + " TEXT,"
                + KEY_PASSWORD_TB_USER + " TEXT,"
                + KEY_REGISTRATION_STATUS_TB_USER + " TEXT" + ")";
        db.execSQL(sql);

        String sqlE= "CREATE TABLE " + TB_PERSON + "("
                + KEY_ID_SQLLITE_TB_PERSON + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
                + KEY_ID_TB_PERSON + " INTEGER,"
                + KEY_ID_AREA_TB_PERSON + " INTEGER,"
                + KEY_AREA_NAME_TB_PERSON + " TEXT,"
                + KEY_ID_ROLE_TB_PERSON + " INTEGER,"
                + KEY_ROLE_NAME_TB_PERSON + " TEXT,"
                + KEY_PERSON_NAME_TB_PERSON + " TEXT,"
                + KEY_FIRST_LAST_NAME_TB_PERSON + " TEXT,"
                + KEY_SECOND_LAST_NAME_TB_PERSON + " TEXT,"
                + KEY_PHOTOCHECK_TB_PERSON + " TEXT,"
                + KEY_DOCUMENT_NUMBER_TB_PERSON + " TEXT,"
                + KEY_DOCUMENT_PATHBASE64_TB_PERSON + " TEXT,"
                + KEY_REGISTRATION_STATUS_TB_PERSON + " TEXT" + ")";
        db.execSQL(sqlE);

        String sqlB= "CREATE TABLE " + TB_REGISTRATION_ACTIVITY + "("
                + KEY_ID_SQLLITE_TB_REGISTRATION_ACTIVITY + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
                + KEY_ID_TB_REGISTRATION_ACTIVITY + " INTEGER,"
                + KEY_ID_MOVIL_DEVICE_TB_REGISTRATION_ACTIVITY + " TEXT,"
                + KEY_ID_PERSON_TB_REGISTRATION_ACTIVITY + " INTEGER,"
                + KEY_PERSON_NAME_TB_REGISTRATION_ACTIVITY + " TEXT,"
                + KEY_FIRST_LAST_NAME_TB_REGISTRATION_ACTIVITY + " TEXT,"
                + KEY_SECOND_LAST_NAME_TB_REGISTRATION_ACTIVITY + " TEXT,"
                + KEY_PHOTOCHECK_TB_REGISTRATION_ACTIVITY + " TEXT,"
                + KEY_DOCUMENT_NUMBER_TB_REGISTRATION_ACTIVITY + " TEXT,"
                + KEY_REGISTRATION_ACTIVITY_DATE_TB_REGISTRATION_ACTIVITY + " TEXT,"
                + KEY_REGISTRATION_ACTIVITY_TYPE_TB_REGISTRATION_ACTIVITY + " TEXT,"
                + KEY_COMMENT_TB_REGISTRATION_ACTIVITY + " TEXT,"
                + KEY_REGISTRATION_USER_TB_REGISTRATION_ACTIVITY + " TEXT,"
                + KEY_REGISTRATION_STATUS_TB_REGISTRATION_ACTIVITY + " TEXT,"
                + KEY_MIGRATION_STATUS_TB_REGISTRATION_ACTIVITY + " TEXT" + ")";
        db.execSQL(sqlB);

        String sqlC= "CREATE TABLE " + TB_MIGRATION_ERROR + "("
                + KEY_ID_SQLLITE_TB_ERROR+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
                + KEY_MIGRATION_START_DATE_TB_ERROR + " TEXT,"
                + KEY_ERROR_DESCRIPTION_TB_ERROR + " TEXT,"
                + KEY_ERROR_DATE_TB_ERROR + " TEXT,"
                + KEY_REGISTRATION_USER_TB_ERROR + " TEXT" + ")";
        db.execSQL(sqlC);

        String sqlD= "CREATE TABLE " + TB_CONFIGURATION + "("
                + KEY_ID_SQLLITE_TB_CONFIGURATION + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
                + KEY_ID_ACCESS_PONT_TB_CONFIGURATION + " TEXT,"
                + KEY_ACCESS_PONT_TB_CONFIGURATION + " TEXT,"
                + KEY_REGISTRATION_TIMEOUT_TB_CONFIGURATION + " TEXT,"
                + KEY_REGISTRATION_MIGRATION_LAPSE_TB_CONFIGURATION + " TEXT,"
                + KEY_REGISTRATION_DATE_TB_CONFIGURATION + " TEXT,"
                + KEY_UPDATE_DATE_TB_CONFIGURATION + " TEXT,"
                + KEY_REGISTRATION_USER_TB_CONFIGURATION + " TEXT,"
                + KEY_UPDATE_USER_TB_CONFIGURATION + " TEXT" + ")";
        db.execSQL(sqlD);

        String sqlF= "CREATE TABLE " + TB_ACTIVITY + "("
                + KEY_ID_SQLLITE_TB_ACTIVITY + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
                + KEY_ID_TB_ACTIVITY + " INTEGER,"
                + KEY_ACTIVITY_NAME_TB_ACTIVITY + " TEXT,"
                + KEY_ID_ROLE_TB_ACTIVITY + " INTEGER,"
                + KEY_ROLE_NAME_TB_ACTIVITY + " TEXT,"
                + KEY_REGISTRATION_STATUS_TB_ACTIVITY + " TEXT" + ")";
        db.execSQL(sqlF);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        String sql= "DROP TABLE IF EXISTS " + TB_USER;
        db.execSQL(sql);
        String sqlB= "DROP TABLE IF EXISTS " + TB_REGISTRATION_ACTIVITY;
        db.execSQL(sqlB);
        String sqlC= "DROP TABLE IF EXISTS " + TB_MIGRATION_ERROR;
        db.execSQL(sqlC);
        String sqlD= "DROP TABLE IF EXISTS " + TB_CONFIGURATION;
        db.execSQL(sqlD);
        String sqlE= "DROP TABLE IF EXISTS " + TB_PERSON;
        db.execSQL(sqlE);
        String sqlF= "DROP TABLE IF EXISTS " + TB_ACTIVITY;
        db.execSQL(sqlF);
    }
}
