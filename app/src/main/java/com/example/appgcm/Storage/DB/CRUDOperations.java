package com.example.appgcm.Storage.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.appgcm.Entities.ActivityEntity;
import com.example.appgcm.Entities.ConfigurationEntity;
import com.example.appgcm.Entities.MigrationErrorEntity;
import com.example.appgcm.Entities.PersonEntity;
import com.example.appgcm.Entities.RegistrationActivityEntity;
import com.example.appgcm.Entities.UserEntity;
import com.example.appgcm.Storage.PreferencesHelper;
import static com.example.appgcm.Util.Const.GLOBAL_USER_ID_SESSION;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CRUDOperations {

    private MyDatabase helper;
    public CRUDOperations(SQLiteOpenHelper _helper) {
        super();
        // TODO Auto-generated constructor stub
        helper =(MyDatabase)_helper;
    }

    //TB_USER
    public int addUser(UserEntity userEntity)
    {
        SQLiteDatabase db = helper.getWritableDatabase(); //modo escritura
        ContentValues values = new ContentValues();
        values.put(MyDatabase.KEY_ID_TB_USER, userEntity.getIdUser());
        values.put(MyDatabase.KEY_ID_PERSON_TB_USER, userEntity.getIdPerson());
        values.put(MyDatabase.KEY_PERSON_NAME_TB_USER, userEntity.getPersonName());
        values.put(MyDatabase.KEY_FIRST_LAST_NAME_TB_USER, userEntity.getFirstLastName());
        values.put(MyDatabase.KEY_SECOND_LAST_NAME_TB_USER, userEntity.getSecondLastName());
        values.put(MyDatabase.KEY_PHOTOCHECK_TB_USER, userEntity.getPhotocheck());
        values.put(MyDatabase.KEY_USER_TB_USER, userEntity.getUUser().toUpperCase());
        values.put(MyDatabase.KEY_PASSWORD_TB_USER, userEntity.getUPassword());
        values.put(MyDatabase.KEY_REGISTRATION_STATUS_TB_USER, userEntity.getRegistrationStatus());

        int row = (int) db.insert(MyDatabase.TB_USER, null, values);
        db.close();
        return row;
    }

    public int updateUser(UserEntity userEntity)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MyDatabase.KEY_ID_PERSON_TB_USER, userEntity.getIdPerson());
        values.put(MyDatabase.KEY_PERSON_NAME_TB_USER, userEntity.getPersonName());
        values.put(MyDatabase.KEY_FIRST_LAST_NAME_TB_USER, userEntity.getFirstLastName());
        values.put(MyDatabase.KEY_SECOND_LAST_NAME_TB_USER, userEntity.getSecondLastName());
        values.put(MyDatabase.KEY_PHOTOCHECK_TB_USER, userEntity.getPhotocheck());
        values.put(MyDatabase.KEY_USER_TB_USER, userEntity.getUUser().toUpperCase());
        values.put(MyDatabase.KEY_PASSWORD_TB_USER, userEntity.getUPassword());
        values.put(MyDatabase.KEY_REGISTRATION_STATUS_TB_USER, userEntity.getRegistrationStatus());

        int row =db.update(MyDatabase.TB_USER,
                values,
                MyDatabase.KEY_ID_TB_USER+"=?",
                new String[]{String.valueOf(userEntity.getIdUser())});
        db.close();

        return row;
    }

    public int deleteUser(UserEntity userEntity)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        int row= db.delete(MyDatabase.TB_USER,
                MyDatabase.KEY_ID_TB_USER+"=?",
                new String[]{String.valueOf(userEntity.getIdUser())});
        db.close();
        return row;
    }

    public UserEntity getUser(int id)
    {
        UserEntity userEntity= new UserEntity();
        SQLiteDatabase db = helper.getReadableDatabase(); //modo lectura
        Cursor cursor = db.query(MyDatabase.TB_USER,
                new String[]{
                        MyDatabase.KEY_ID_SQLLITE_TB_USER,
                        MyDatabase.KEY_ID_TB_USER,
                        MyDatabase.KEY_ID_PERSON_TB_USER,
                        MyDatabase.KEY_PERSON_NAME_TB_USER,
                        MyDatabase.KEY_FIRST_LAST_NAME_TB_USER,
                        MyDatabase.KEY_SECOND_LAST_NAME_TB_USER,
                        MyDatabase.KEY_PHOTOCHECK_TB_USER,
                        MyDatabase.KEY_USER_TB_USER,
                        MyDatabase.KEY_PASSWORD_TB_USER,
                        MyDatabase.KEY_REGISTRATION_STATUS_TB_USER},
                MyDatabase.KEY_ID_TB_USER + "=?",
                new String[]{String.valueOf(id)}, null, null, null);
        if(cursor!=null && cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            int idSqlLite = Integer.parseInt(cursor.getString(0));
            int IdUser = Integer.parseInt(cursor.getString(1));
            int IdPerson = Integer.parseInt(cursor.getString(2));
            String PersonName = cursor.getString(3);
            String FirstLastName = cursor.getString(4);
            String SecondLastName = cursor.getString(5);
            String Photocheck = cursor.getString(6);
            String UUser = cursor.getString(7);
            String UPassword = cursor.getString(8);
            String RegistrationStatus = cursor.getString(9);

            userEntity= new UserEntity(idSqlLite, IdUser,IdPerson, PersonName,Photocheck,FirstLastName,SecondLastName,UUser,UPassword,RegistrationStatus);
        }

        return userEntity;
    }

    public UserEntity getUserForLogin(String user, String password)
    {
        UserEntity userEntity= new UserEntity();
        SQLiteDatabase db = helper.getReadableDatabase(); //modo lectura
        Cursor cursor = db.query(MyDatabase.TB_USER,
                new String[]{
                        MyDatabase.KEY_ID_SQLLITE_TB_USER,
                        MyDatabase.KEY_ID_TB_USER,
                        MyDatabase.KEY_ID_PERSON_TB_USER,
                        MyDatabase.KEY_PERSON_NAME_TB_USER,
                        MyDatabase.KEY_FIRST_LAST_NAME_TB_USER,
                        MyDatabase.KEY_SECOND_LAST_NAME_TB_USER,
                        MyDatabase.KEY_PHOTOCHECK_TB_USER,
                        MyDatabase.KEY_USER_TB_USER,
                        MyDatabase.KEY_PASSWORD_TB_USER,
                        MyDatabase.KEY_REGISTRATION_STATUS_TB_USER},
                MyDatabase.KEY_USER_TB_USER + "=? AND "+ MyDatabase.KEY_PASSWORD_TB_USER + "=?",
                new String[]{user,password}, null, null, null);
        if(cursor!=null && cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            int idSqlLite = Integer.parseInt(cursor.getString(0));
            int IdUser = Integer.parseInt(cursor.getString(1));
            int IdPerson = Integer.parseInt(cursor.getString(2));
            String PersonName = cursor.getString(3);
            String FirstLastName = cursor.getString(4);
            String SecondLastName = cursor.getString(5);
            String Photocheck = cursor.getString(6);
            String UUser = cursor.getString(7);
            String UPassword = cursor.getString(8);
            String RegistrationStatus = cursor.getString(9);

            userEntity= new UserEntity(idSqlLite, IdUser, IdPerson, PersonName,Photocheck,FirstLastName,SecondLastName,UUser,UPassword,RegistrationStatus);
        }

        return userEntity;
    }


    public List<UserEntity> getAllUsers()
    {
        List<UserEntity> lst =new ArrayList<UserEntity>();
        String sql= "SELECT  * FROM " + MyDatabase.TB_USER;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do
            {
                UserEntity userEntity =new UserEntity();
                userEntity.setIdSqlLite(Integer.parseInt(cursor.getString(0)));
                userEntity.setIdUser(Integer.parseInt(cursor.getString(1)));
                userEntity.setPersonName(cursor.getString(2));
                userEntity.setFirstLastName(cursor.getString(3));
                userEntity.setSecondLastName(cursor.getString(4));
                userEntity.setPhotocheck(cursor.getString(5));
                userEntity.setUUser(cursor.getString(6));
                userEntity.setUPassword(cursor.getString(7));
                userEntity.setRegistrationStatus(cursor.getString(8));

                lst.add(userEntity);
            }while(cursor.moveToNext());
        }
        return lst;
    }

    public int getUserCount()
    {
        int totalCount = 0;
        String sql= "SELECT * FROM "+MyDatabase.TB_USER;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        totalCount = cursor.getCount();
        cursor.close();

        return totalCount;
    }

    //ACTIVIDAD
    public int addActivity(ActivityEntity activityEntity)
    {
        SQLiteDatabase db = helper.getWritableDatabase(); //modo escritura
        ContentValues values = new ContentValues();
        values.put(MyDatabase.KEY_ID_TB_ACTIVITY, activityEntity.getIdActivity());
        values.put(MyDatabase.KEY_ACTIVITY_NAME_TB_ACTIVITY, activityEntity.getActivityName());
        values.put(MyDatabase.KEY_ID_ROLE_TB_ACTIVITY, activityEntity.getIdRole());
        values.put(MyDatabase.KEY_ROLE_NAME_TB_ACTIVITY, activityEntity.getRoleName());
        values.put(MyDatabase.KEY_REGISTRATION_STATUS_TB_ACTIVITY, activityEntity.getRegistrationStatus());

        int row = (int) db.insert(MyDatabase.TB_ACTIVITY, null, values);
        db.close();
        return row;
    }

    public int updateActivity(ActivityEntity activityEntity)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MyDatabase.KEY_ID_TB_ACTIVITY, activityEntity.getIdActivity());
        values.put(MyDatabase.KEY_ACTIVITY_NAME_TB_ACTIVITY, activityEntity.getActivityName());
        values.put(MyDatabase.KEY_ID_ROLE_TB_ACTIVITY, activityEntity.getIdRole());
        values.put(MyDatabase.KEY_ROLE_NAME_TB_ACTIVITY, activityEntity.getRoleName());
        values.put(MyDatabase.KEY_REGISTRATION_STATUS_TB_ACTIVITY, activityEntity.getRegistrationStatus());

        int row =db.update(MyDatabase.TB_ACTIVITY,
                values,
                MyDatabase.KEY_ID_TB_ACTIVITY+"=?",
                new String[]{String.valueOf(activityEntity.getIdActivity())});
        db.close();

        return row;
    }

    public ActivityEntity getActivity(int id)
    {
        ActivityEntity activityEntity= new ActivityEntity();
        SQLiteDatabase db = helper.getReadableDatabase(); //modo lectura
        Cursor cursor = db.query(MyDatabase.TB_ACTIVITY,
                new String[]{
                        MyDatabase.KEY_ID_SQLLITE_TB_ACTIVITY,
                        MyDatabase.KEY_ID_TB_ACTIVITY,
                        MyDatabase.KEY_ACTIVITY_NAME_TB_ACTIVITY,
                        MyDatabase.KEY_ID_ROLE_TB_ACTIVITY,
                        MyDatabase.KEY_ROLE_NAME_TB_ACTIVITY,
                        MyDatabase.KEY_REGISTRATION_STATUS_TB_ACTIVITY,},
                MyDatabase.KEY_ID_TB_ACTIVITY + "=?",
                new String[]{String.valueOf(id)}, null, null, null);
        if(cursor!=null && cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            int idSqlLite = Integer.parseInt(cursor.getString(0));
            int idActividad = Integer.parseInt(cursor.getString(1));
            String nombreActividad = cursor.getString(2);
            int idRole = Integer.parseInt(cursor.getString(3));
            String nombreRole = cursor.getString(4);
            String RegistrationStatus = cursor.getString(5);

            activityEntity= new ActivityEntity(idSqlLite, idActividad, nombreActividad,idRole,nombreRole,RegistrationStatus);
        }

        return activityEntity;
    }

    public int IdCargoUsuario(){

        int ID_ROLE = 0;
        String sql= "SELECT  " + MyDatabase.KEY_ID_ROLE_TB_PERSON + " FROM " + MyDatabase.TB_PERSON + " WHERE " + MyDatabase.KEY_REGISTRATION_STATUS_TB_PERSON + " = 'A'" +
                "AND "+MyDatabase.KEY_ID_TB_PERSON+" = "+ GLOBAL_USER_ID_SESSION;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do
            {
                ID_ROLE = Integer.parseInt(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        Log.v("idRole","idrole"+ ID_ROLE);
        return ID_ROLE;
    }

    public List<ActivityEntity> getAllActiveActivity(String busqueda)
    {
        int Id_role = IdCargoUsuario();
        Log.v("IdCargo","IdCargo" + Id_role);
        List<ActivityEntity> lst =new ArrayList<ActivityEntity>();
        String sql= "SELECT  * FROM " + MyDatabase.TB_ACTIVITY + " WHERE " + MyDatabase.KEY_REGISTRATION_STATUS_TB_ACTIVITY + " = 'A'" +
                "AND "+ MyDatabase.KEY_ID_ROLE_TB_ACTIVITY +" = "+Id_role  +" AND (" + MyDatabase.KEY_ACTIVITY_NAME_TB_ACTIVITY + " LIKE '%" + busqueda + "%' )";
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do
            {
                ActivityEntity activityEntity =new ActivityEntity();
                activityEntity.setIdSqlLiteActivity(Integer.parseInt(cursor.getString(0)));
                activityEntity.setIdActivity(Integer.parseInt(cursor.getString(1)));
                activityEntity.setActivityName(cursor.getString(2));
                activityEntity.setIdRole(Integer.parseInt(cursor.getString(3)));
                activityEntity.setRoleName(cursor.getString(4));
                activityEntity.setRegistrationStatus(cursor.getString(5));

                lst.add(activityEntity);
            }while(cursor.moveToNext());
        }
        return lst;
    }

    //TB_PERSON
    public int addPerson(PersonEntity personEntity)
    {
        SQLiteDatabase db = helper.getWritableDatabase(); //modo escritura
        ContentValues values = new ContentValues();
        values.put(MyDatabase.KEY_ID_TB_PERSON, personEntity.getIdPerson());
        values.put(MyDatabase.KEY_ID_AREA_TB_PERSON, personEntity.getIdArea());
        values.put(MyDatabase.KEY_AREA_NAME_TB_PERSON, personEntity.getAreaName());
        values.put(MyDatabase.KEY_ID_ROLE_TB_PERSON, personEntity.getIdRole());
        values.put(MyDatabase.KEY_ROLE_NAME_TB_PERSON, personEntity.getRoleName());
        values.put(MyDatabase.KEY_PERSON_NAME_TB_PERSON, personEntity.getPersonName());
        values.put(MyDatabase.KEY_FIRST_LAST_NAME_TB_PERSON, personEntity.getFirstLastName());
        values.put(MyDatabase.KEY_SECOND_LAST_NAME_TB_PERSON, personEntity.getSecondLastName());
        values.put(MyDatabase.KEY_PHOTOCHECK_TB_PERSON, personEntity.getPhotocheck());
        values.put(MyDatabase.KEY_DOCUMENT_NUMBER_TB_PERSON, personEntity.getDocumentNumber());
        values.put(MyDatabase.KEY_DOCUMENT_PATHBASE64_TB_PERSON, personEntity.getPathFileBase64());
        values.put(MyDatabase.KEY_REGISTRATION_STATUS_TB_PERSON, personEntity.getRegistrationStatus());

        int row = (int) db.insert(MyDatabase.TB_PERSON, null, values);
        db.close();
        return row;
    }

    public int updatePerson(PersonEntity personEntity)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MyDatabase.KEY_ID_AREA_TB_PERSON, personEntity.getIdArea());
        values.put(MyDatabase.KEY_AREA_NAME_TB_PERSON, personEntity.getAreaName());
        values.put(MyDatabase.KEY_ID_ROLE_TB_PERSON, personEntity.getIdRole());
        values.put(MyDatabase.KEY_ROLE_NAME_TB_PERSON, personEntity.getRoleName());
        values.put(MyDatabase.KEY_PERSON_NAME_TB_PERSON, personEntity.getPersonName());
        values.put(MyDatabase.KEY_FIRST_LAST_NAME_TB_PERSON, personEntity.getFirstLastName());
        values.put(MyDatabase.KEY_SECOND_LAST_NAME_TB_PERSON, personEntity.getSecondLastName());
        values.put(MyDatabase.KEY_PHOTOCHECK_TB_PERSON, personEntity.getPhotocheck());
        values.put(MyDatabase.KEY_DOCUMENT_NUMBER_TB_PERSON, personEntity.getDocumentNumber());
        values.put(MyDatabase.KEY_DOCUMENT_PATHBASE64_TB_PERSON, personEntity.getPathFileBase64());
        values.put(MyDatabase.KEY_REGISTRATION_STATUS_TB_PERSON, personEntity.getRegistrationStatus());

        int row =db.update(MyDatabase.TB_PERSON,
                values,
                MyDatabase.KEY_ID_TB_PERSON+"=?",
                new String[]{String.valueOf(personEntity.getIdPerson())});
        db.close();

        return row;
    }

    public int deletePerson(PersonEntity personEntity)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        int row= db.delete(MyDatabase.TB_PERSON,
                MyDatabase.KEY_ID_TB_PERSON+"=?",
                new String[]{String.valueOf(personEntity.getIdPerson())});
        db.close();
        return row;
    }

    public PersonEntity getPerson(int id)
    {
        PersonEntity personEntity= new PersonEntity();
        SQLiteDatabase db = helper.getReadableDatabase(); //modo lectura
        Cursor cursor = db.query(MyDatabase.TB_PERSON,
                new String[]{
                        MyDatabase.KEY_ID_SQLLITE_TB_PERSON,
                        MyDatabase.KEY_ID_TB_PERSON,
                        MyDatabase.KEY_ID_AREA_TB_PERSON,
                        MyDatabase.KEY_AREA_NAME_TB_PERSON,
                        MyDatabase.KEY_ID_ROLE_TB_PERSON,
                        MyDatabase.KEY_ROLE_NAME_TB_PERSON,
                        MyDatabase.KEY_PERSON_NAME_TB_PERSON,
                        MyDatabase.KEY_FIRST_LAST_NAME_TB_PERSON,
                        MyDatabase.KEY_SECOND_LAST_NAME_TB_PERSON,
                        MyDatabase.KEY_PHOTOCHECK_TB_PERSON,
                        MyDatabase.KEY_DOCUMENT_NUMBER_TB_PERSON,
                        MyDatabase.KEY_DOCUMENT_PATHBASE64_TB_PERSON,
                        MyDatabase.KEY_REGISTRATION_STATUS_TB_PERSON},
                MyDatabase.KEY_ID_TB_PERSON + "=?",
                new String[]{String.valueOf(id)}, null, null, null);
        if(cursor!=null && cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            int idSqlLite = Integer.parseInt(cursor.getString(0));
            int IdPerson = Integer.parseInt(cursor.getString(1));
            int IdArea = Integer.parseInt(cursor.getString(2));
            String AreaName = cursor.getString(3);
            int IdRole = Integer.parseInt(cursor.getString(4));
            String RoleName = cursor.getString(5);
            String PersonName = cursor.getString(6);
            String FirstLastName = cursor.getString(7);
            String SecondLastName = cursor.getString(8);
            String Photocheck = cursor.getString(8);
            String DocumentNumber = cursor.getString(10);
            String PathBase64 = cursor.getString(11);
            String RegistrationStatus = cursor.getString(12);

            personEntity= new PersonEntity(idSqlLite, IdPerson,IdArea,AreaName,IdRole,RoleName, PersonName,FirstLastName,SecondLastName,Photocheck,DocumentNumber,RegistrationStatus, PathBase64);
        }

        return personEntity;
    }



    public PersonEntity getPersonByPhotocheck(String photocheck)
    {
        PersonEntity personEntity= new PersonEntity();
        SQLiteDatabase db = helper.getReadableDatabase(); //modo lectura
        Cursor cursor = db.query(MyDatabase.TB_PERSON,
                new String[]{
                        MyDatabase.KEY_ID_SQLLITE_TB_PERSON,
                        MyDatabase.KEY_ID_TB_PERSON,
                        MyDatabase.KEY_ID_AREA_TB_PERSON,
                        MyDatabase.KEY_AREA_NAME_TB_PERSON,
                        MyDatabase.KEY_ID_ROLE_TB_PERSON,
                        MyDatabase.KEY_ROLE_NAME_TB_PERSON,
                        MyDatabase.KEY_PERSON_NAME_TB_PERSON,
                        MyDatabase.KEY_FIRST_LAST_NAME_TB_PERSON,
                        MyDatabase.KEY_SECOND_LAST_NAME_TB_PERSON,
                        MyDatabase.KEY_PHOTOCHECK_TB_PERSON,
                        MyDatabase.KEY_DOCUMENT_NUMBER_TB_PERSON,
                        MyDatabase.KEY_DOCUMENT_PATHBASE64_TB_PERSON,
                        MyDatabase.KEY_REGISTRATION_STATUS_TB_PERSON},
                MyDatabase.KEY_PHOTOCHECK_TB_PERSON + "=?",
                new String[]{String.valueOf(photocheck)}, null, null, null);
        if(cursor!=null && cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            int idSqlLite = Integer.parseInt(cursor.getString(0));
            int IdPerson = Integer.parseInt(cursor.getString(1));
            int IdArea = Integer.parseInt(cursor.getString(2));
            String AreaName = cursor.getString(3);
            int IdRole = Integer.parseInt(cursor.getString(4));
            String RoleName = cursor.getString(5);
            String PersonName = cursor.getString(6);
            String FirstLastName = cursor.getString(7);
            String SecondLastName = cursor.getString(8);
            String Photocheck = cursor.getString(9);
            String DocumentNumber = cursor.getString(10);
            String PathBase64 = cursor.getString(11);
            String RegistrationStatus = cursor.getString(12);

            personEntity= new PersonEntity(idSqlLite, IdPerson,IdArea,AreaName,IdRole,RoleName, PersonName,FirstLastName,SecondLastName,Photocheck,DocumentNumber,RegistrationStatus, PathBase64);
        }

        return personEntity;
    }

    public List<PersonEntity> getAllPerson()
    {
        List<PersonEntity> lst =new ArrayList<PersonEntity>();
        String sql= "SELECT  * FROM " + MyDatabase.TB_PERSON;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do
            {
                PersonEntity personEntity =new PersonEntity();
                personEntity.setIdPersonSqlLite(Integer.parseInt(cursor.getString(0)));
                personEntity.setIdPerson(Integer.parseInt(cursor.getString(1)));
                personEntity.setIdArea(Integer.parseInt(cursor.getString(2)));
                personEntity.setAreaName(cursor.getString(3));
                personEntity.setIdRole(Integer.parseInt(cursor.getString(4)));
                personEntity.setRoleName(cursor.getString(5));
                personEntity.setPersonName(cursor.getString(6));
                personEntity.setFirstLastName(cursor.getString(7));
                personEntity.setSecondLastName(cursor.getString(8));
                personEntity.setPhotocheck(cursor.getString(9));
                personEntity.setDocumentNumber(cursor.getString(10));
                personEntity.setPathFileBase64(cursor.getString(11));
                personEntity.setRegistrationStatus(cursor.getString(12));

                lst.add(personEntity);
            }while(cursor.moveToNext());
        }
        return lst;
    }

    public List<PersonEntity> getAllActivePerson(String busqueda)
    {
        List<PersonEntity> lst =new ArrayList<PersonEntity>();
        String sql= "SELECT  * FROM " + MyDatabase.TB_PERSON + " WHERE " + MyDatabase.KEY_REGISTRATION_STATUS_TB_PERSON + " = 'A'" +
                " AND (" + MyDatabase.KEY_PERSON_NAME_TB_PERSON + " LIKE '%" + busqueda + "%' OR " +
                MyDatabase.KEY_FIRST_LAST_NAME_TB_PERSON + " LIKE '%" + busqueda + "%' OR " +
                MyDatabase.KEY_SECOND_LAST_NAME_TB_PERSON + " LIKE '%" + busqueda + "%' OR " +
                MyDatabase.KEY_PHOTOCHECK_TB_PERSON + " LIKE '%" + busqueda + "%' OR " +
                MyDatabase.KEY_DOCUMENT_NUMBER_TB_PERSON + " LIKE '%" + busqueda + "%' )";
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do
            {
                PersonEntity personEntity =new PersonEntity();
                personEntity.setIdPersonSqlLite(Integer.parseInt(cursor.getString(0)));
                personEntity.setIdPerson(Integer.parseInt(cursor.getString(1)));
                personEntity.setIdArea(Integer.parseInt(cursor.getString(2)));
                personEntity.setAreaName(cursor.getString(3));
                personEntity.setIdRole(Integer.parseInt(cursor.getString(4)));
                personEntity.setRoleName(cursor.getString(5));
                personEntity.setPersonName(cursor.getString(6));
                personEntity.setFirstLastName(cursor.getString(7));
                personEntity.setSecondLastName(cursor.getString(8));
                personEntity.setPhotocheck(cursor.getString(9));
                personEntity.setDocumentNumber(cursor.getString(10));
                personEntity.setPathFileBase64(cursor.getString(11));
                personEntity.setRegistrationStatus(cursor.getString(12));

                lst.add(personEntity);
            }while(cursor.moveToNext());
        }
        return lst;
    }

    public int getPersonCount()
    {
        int totalCount = 0;
        String sql= "SELECT * FROM "+MyDatabase.TB_PERSON;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        totalCount = cursor.getCount();
        cursor.close();

        return totalCount;
    }


    //TB_REGISTRATION_ACTIVITY
    public int addRegistrationActivity(RegistrationActivityEntity RegistrationActivityEntity)
    {
        SQLiteDatabase db = helper.getWritableDatabase(); //modo escritura
        ContentValues values = new ContentValues();
        values.put(MyDatabase.KEY_ID_TB_REGISTRATION_ACTIVITY, RegistrationActivityEntity.getIdRegistrationActivity());
        values.put(MyDatabase.KEY_ID_MOVIL_DEVICE_TB_REGISTRATION_ACTIVITY, RegistrationActivityEntity.getIdMovilDevice());
        values.put(MyDatabase.KEY_ID_PERSON_TB_REGISTRATION_ACTIVITY, RegistrationActivityEntity.getIdPerson());
        values.put(MyDatabase.KEY_PERSON_NAME_TB_REGISTRATION_ACTIVITY, RegistrationActivityEntity.getPersonName());
        values.put(MyDatabase.KEY_FIRST_LAST_NAME_TB_REGISTRATION_ACTIVITY, RegistrationActivityEntity.getFirstLastName());
        values.put(MyDatabase.KEY_SECOND_LAST_NAME_TB_REGISTRATION_ACTIVITY, RegistrationActivityEntity.getSecondLastName());
        values.put(MyDatabase.KEY_PHOTOCHECK_TB_REGISTRATION_ACTIVITY, RegistrationActivityEntity.getPhotocheck());
        values.put(MyDatabase.KEY_DOCUMENT_NUMBER_TB_REGISTRATION_ACTIVITY, RegistrationActivityEntity.getDocumentNumber());
        values.put(MyDatabase.KEY_REGISTRATION_ACTIVITY_DATE_TB_REGISTRATION_ACTIVITY, RegistrationActivityEntity.getRegistrationActivityDate());
        values.put(MyDatabase.KEY_REGISTRATION_ACTIVITY_TYPE_TB_REGISTRATION_ACTIVITY, RegistrationActivityEntity.getRegistrationActivityType());
        values.put(MyDatabase.KEY_COMMENT_TB_REGISTRATION_ACTIVITY, RegistrationActivityEntity.getComment());
        values.put(MyDatabase.KEY_REGISTRATION_USER_TB_REGISTRATION_ACTIVITY, RegistrationActivityEntity.getRegistrationUser());
        values.put(MyDatabase.KEY_REGISTRATION_STATUS_TB_REGISTRATION_ACTIVITY, RegistrationActivityEntity.getRegistrationStatus());
        values.put(MyDatabase.KEY_MIGRATION_STATUS_TB_REGISTRATION_ACTIVITY, RegistrationActivityEntity.getMigrationStatus());

        int row = (int) db.insert(MyDatabase.TB_REGISTRATION_ACTIVITY, null, values);
        db.close();
        return row;
    }

    public int updateRegistrationActivity(RegistrationActivityEntity RegistrationActivityEntity)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MyDatabase.KEY_ID_TB_REGISTRATION_ACTIVITY, RegistrationActivityEntity.getIdRegistrationActivity());
        values.put(MyDatabase.KEY_ID_MOVIL_DEVICE_TB_REGISTRATION_ACTIVITY, RegistrationActivityEntity.getIdMovilDevice());
        values.put(MyDatabase.KEY_ID_PERSON_TB_REGISTRATION_ACTIVITY, RegistrationActivityEntity.getIdPerson());
        values.put(MyDatabase.KEY_PERSON_NAME_TB_REGISTRATION_ACTIVITY, RegistrationActivityEntity.getPersonName());
        values.put(MyDatabase.KEY_FIRST_LAST_NAME_TB_REGISTRATION_ACTIVITY, RegistrationActivityEntity.getFirstLastName());
        values.put(MyDatabase.KEY_SECOND_LAST_NAME_TB_REGISTRATION_ACTIVITY, RegistrationActivityEntity.getSecondLastName());
        values.put(MyDatabase.KEY_PHOTOCHECK_TB_REGISTRATION_ACTIVITY, RegistrationActivityEntity.getPhotocheck());
        values.put(MyDatabase.KEY_DOCUMENT_NUMBER_TB_REGISTRATION_ACTIVITY, RegistrationActivityEntity.getDocumentNumber());
        values.put(MyDatabase.KEY_REGISTRATION_ACTIVITY_DATE_TB_REGISTRATION_ACTIVITY, RegistrationActivityEntity.getRegistrationActivityDate());
        values.put(MyDatabase.KEY_REGISTRATION_ACTIVITY_TYPE_TB_REGISTRATION_ACTIVITY, RegistrationActivityEntity.getRegistrationActivityType());
        values.put(MyDatabase.KEY_COMMENT_TB_REGISTRATION_ACTIVITY, RegistrationActivityEntity.getComment());
        values.put(MyDatabase.KEY_REGISTRATION_USER_TB_REGISTRATION_ACTIVITY, RegistrationActivityEntity.getRegistrationUser());
        values.put(MyDatabase.KEY_REGISTRATION_STATUS_TB_REGISTRATION_ACTIVITY, RegistrationActivityEntity.getRegistrationStatus());
        values.put(MyDatabase.KEY_MIGRATION_STATUS_TB_REGISTRATION_ACTIVITY, RegistrationActivityEntity.getMigrationStatus());

        int row =db.update(MyDatabase.TB_REGISTRATION_ACTIVITY,
                values,
                MyDatabase.KEY_ID_TB_REGISTRATION_ACTIVITY+"=?",
                new String[]{String.valueOf(RegistrationActivityEntity.getIdRegistrationActivity())});
        db.close();

        return row;
    }

    public int updateRegistrationActivityStatus(int id)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MyDatabase.KEY_MIGRATION_STATUS_TB_REGISTRATION_ACTIVITY, "E");

        int row =db.update(MyDatabase.TB_REGISTRATION_ACTIVITY,
                values,
                MyDatabase.KEY_ID_SQLLITE_TB_REGISTRATION_ACTIVITY+"=?",
                new String[]{String.valueOf(id)});
        db.close();

        return row;
    }

    public RegistrationActivityEntity getRegistrationActivity(int id)
    {
        RegistrationActivityEntity RegistrationActivityEntity= new RegistrationActivityEntity();
        SQLiteDatabase db = helper.getReadableDatabase(); //modo lectura
        Cursor cursor = db.query(MyDatabase.TB_REGISTRATION_ACTIVITY,
                new String[]{
                        MyDatabase.KEY_ID_SQLLITE_TB_REGISTRATION_ACTIVITY,
                        MyDatabase.KEY_ID_TB_REGISTRATION_ACTIVITY,
                        MyDatabase.KEY_ID_MOVIL_DEVICE_TB_REGISTRATION_ACTIVITY,
                        MyDatabase.KEY_ID_PERSON_TB_REGISTRATION_ACTIVITY,
                        MyDatabase.KEY_PERSON_NAME_TB_REGISTRATION_ACTIVITY,
                        MyDatabase.KEY_FIRST_LAST_NAME_TB_REGISTRATION_ACTIVITY,
                        MyDatabase.KEY_SECOND_LAST_NAME_TB_REGISTRATION_ACTIVITY,
                        MyDatabase.KEY_PHOTOCHECK_TB_REGISTRATION_ACTIVITY,
                        MyDatabase.KEY_DOCUMENT_NUMBER_TB_REGISTRATION_ACTIVITY,
                        MyDatabase.KEY_REGISTRATION_ACTIVITY_DATE_TB_REGISTRATION_ACTIVITY,
                        MyDatabase.KEY_REGISTRATION_ACTIVITY_TYPE_TB_REGISTRATION_ACTIVITY,
                        MyDatabase.KEY_COMMENT_TB_REGISTRATION_ACTIVITY,
                        MyDatabase.KEY_REGISTRATION_USER_TB_REGISTRATION_ACTIVITY,
                        MyDatabase.KEY_REGISTRATION_STATUS_TB_REGISTRATION_ACTIVITY,
                        MyDatabase.KEY_MIGRATION_STATUS_TB_REGISTRATION_ACTIVITY},
                MyDatabase.KEY_ID_TB_REGISTRATION_ACTIVITY + "=?",
                new String[]{String.valueOf(id)}, null, null, null);
        if(cursor!=null && cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            int idSqlLite = Integer.parseInt(cursor.getString(0));
            int IdRegistrationActivity = Integer.parseInt(cursor.getString(1));
            String idMovilDevice = cursor.getString(2);
            int idPerson = Integer.parseInt(cursor.getString(3));
            String PersonName = cursor.getString(4);
            String FirstLastName = cursor.getString(5);
            String SecondLastName = cursor.getString(6);
            String Photocheck = cursor.getString(7);
            String DocumentNumber = cursor.getString(8);
            String RegistrationActivityDate = cursor.getString(9);
            String RegistrationActivityType = cursor.getString(10);
            String comentario = cursor.getString(11);
            int RegistrationUser = Integer.parseInt(cursor.getString(12));
            String RegistrationStatus = cursor.getString(13);
            String MigrationStatus = cursor.getString(14);

            RegistrationActivityEntity= new RegistrationActivityEntity(idSqlLite, IdRegistrationActivity,idMovilDevice, idPerson, PersonName,FirstLastName,SecondLastName,Photocheck, DocumentNumber, RegistrationActivityDate, RegistrationActivityType,RegistrationUser, RegistrationStatus, MigrationStatus, comentario);
        }

        return RegistrationActivityEntity;
    }

    public List<RegistrationActivityEntity> getPendingRegistrationActivity()
    {
        List<RegistrationActivityEntity> lst =new ArrayList<RegistrationActivityEntity>();
        //String sql= "SELECT  * FROM " + MyDatabase.TB_REGISTRATION_ACTIVITY;
        SQLiteDatabase db = helper.getReadableDatabase();
        //Cursor cursor = db.rawQuery(sql, null);
        Cursor cursor = db.query(MyDatabase.TB_REGISTRATION_ACTIVITY,
                new String[]{
                        MyDatabase.KEY_ID_SQLLITE_TB_REGISTRATION_ACTIVITY,
                        MyDatabase.KEY_ID_TB_REGISTRATION_ACTIVITY,
                        MyDatabase.KEY_ID_MOVIL_DEVICE_TB_REGISTRATION_ACTIVITY,
                        MyDatabase.KEY_ID_PERSON_TB_REGISTRATION_ACTIVITY,
                        MyDatabase.KEY_PERSON_NAME_TB_REGISTRATION_ACTIVITY,
                        MyDatabase.KEY_FIRST_LAST_NAME_TB_REGISTRATION_ACTIVITY,
                        MyDatabase.KEY_SECOND_LAST_NAME_TB_REGISTRATION_ACTIVITY,
                        MyDatabase.KEY_PHOTOCHECK_TB_REGISTRATION_ACTIVITY,
                        MyDatabase.KEY_DOCUMENT_NUMBER_TB_REGISTRATION_ACTIVITY,
                        MyDatabase.KEY_REGISTRATION_ACTIVITY_DATE_TB_REGISTRATION_ACTIVITY,
                        MyDatabase.KEY_REGISTRATION_ACTIVITY_TYPE_TB_REGISTRATION_ACTIVITY,
                        MyDatabase.KEY_COMMENT_TB_REGISTRATION_ACTIVITY,
                        MyDatabase.KEY_REGISTRATION_USER_TB_REGISTRATION_ACTIVITY,
                        MyDatabase.KEY_REGISTRATION_STATUS_TB_REGISTRATION_ACTIVITY,
                        MyDatabase.KEY_MIGRATION_STATUS_TB_REGISTRATION_ACTIVITY},
                MyDatabase.KEY_MIGRATION_STATUS_TB_REGISTRATION_ACTIVITY + "=?",
                new String[]{String.valueOf("P")}, null, null, null);
        if(cursor.moveToFirst())
        {
            do
            {
                RegistrationActivityEntity RegistrationActivityEntity =new RegistrationActivityEntity();
                RegistrationActivityEntity.setIdSqlLite(Integer.parseInt(cursor.getString(0)));
                RegistrationActivityEntity.setIdRegistrationActivity(Integer.parseInt(cursor.getString(1)));
                RegistrationActivityEntity.setIdMovilDevice(cursor.getString(2));
                RegistrationActivityEntity.setIdPerson(Integer.parseInt(cursor.getString(3)));
                RegistrationActivityEntity.setPersonName(cursor.getString(4));
                RegistrationActivityEntity.setFirstLastName(cursor.getString(5));
                RegistrationActivityEntity.setSecondLastName(cursor.getString(6));
                RegistrationActivityEntity.setPhotocheck(cursor.getString(7));
                RegistrationActivityEntity.setDocumentNumber(cursor.getString(8));
                RegistrationActivityEntity.setRegistrationActivityDate(cursor.getString(9));
                RegistrationActivityEntity.setRegistrationActivityType(cursor.getString(10));
                RegistrationActivityEntity.setComment(cursor.getString(11));
                RegistrationActivityEntity.setRegistrationUser(cursor.getInt(12));
                RegistrationActivityEntity.setRegistrationStatus(cursor.getString(13));
                RegistrationActivityEntity.setMigrationStatus(cursor.getString(14));

                lst.add(RegistrationActivityEntity);
            }while(cursor.moveToNext());
        }
        return lst;
    }



    public List<RegistrationActivityEntity> getAllRegistrationActivity()
    {
        //String[] selectionArgs = new String[]{user, pass};
        List<RegistrationActivityEntity> lst =new ArrayList<RegistrationActivityEntity>();
        //String fecha = "'07/05/2020'";
        String sql= "SELECT  * FROM " + MyDatabase.TB_REGISTRATION_ACTIVITY +
                //" WHERE " + MyDatabase.KEY_ACTIVITY_DATE + " <= " + fecha + " ORDER BY "
                " ORDER BY " + MyDatabase.KEY_REGISTRATION_ACTIVITY_DATE_TB_REGISTRATION_ACTIVITY + " DESC";
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.moveToFirst())
        {
            do
            {
                RegistrationActivityEntity RegistrationActivityEntity =new RegistrationActivityEntity();
                RegistrationActivityEntity.setIdSqlLite(Integer.parseInt(cursor.getString(0)));
                RegistrationActivityEntity.setIdRegistrationActivity(Integer.parseInt(cursor.getString(1)));
                RegistrationActivityEntity.setIdMovilDevice(cursor.getString(2));
                RegistrationActivityEntity.setIdPerson(Integer.parseInt(cursor.getString(3)));
                RegistrationActivityEntity.setPersonName(cursor.getString(4));
                RegistrationActivityEntity.setFirstLastName(cursor.getString(5));
                RegistrationActivityEntity.setSecondLastName(cursor.getString(6));
                RegistrationActivityEntity.setPhotocheck(cursor.getString(7));
                RegistrationActivityEntity.setDocumentNumber(cursor.getString(8));
                RegistrationActivityEntity.setRegistrationActivityDate(cursor.getString(9));
                RegistrationActivityEntity.setRegistrationActivityType(cursor.getString(10));
                RegistrationActivityEntity.setRegistrationUser(cursor.getInt(11));
                RegistrationActivityEntity.setRegistrationStatus(cursor.getString(12));
                RegistrationActivityEntity.setMigrationStatus(cursor.getString(13));

                lst.add(RegistrationActivityEntity);
            }while(cursor.moveToNext());
        }
        return lst;
    }

//    public List<RegistrationActivityEntity> getRegistrationActivityForToday(String fecha, String busqueda)
//    {
//        //String[] selectionArgs = new String[]{user, pass};
//        List<RegistrationActivityEntity> lst =new ArrayList<RegistrationActivityEntity>();
//        //String fecha = "'07/05/2020'";
//        String today = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
//        String todayX = "'"+fecha + "'";
//        String sql = "";
//            sql = "SELECT * FROM (SELECT " +
//                    "(SELECT "+MyDatabase.KEY_ACTIVITY_DATE_TB_REGISTRATION_ACTIVITY +" FROM " + MyDatabase.TB_REGISTRATION_ACTIVITY + " RAX WHERE RAX." + MyDatabase.KEY_ID_PERSON_TB_REGISTRATION_ACTIVITY + " = RA." +  MyDatabase.KEY_ID_PERSON_TB_REGISTRATION_ACTIVITY + " AND RAX." +MyDatabase.KEY_ACTIVITY_TYPE_TB_REGISTRATION_ACTIVITY+ " = 'INGRESO' AND substr(RAX."+ MyDatabase.KEY_ACTIVITY_DATE_TB_REGISTRATION_ACTIVITY + ",1,10) = " + todayX+" ORDER BY RAX." + MyDatabase.KEY_ID_SQLLITE_TB_REGISTRATION_ACTIVITY +" DESC LIMIT 1) AS 'FechaIngreso', " +
//                    "(SELECT "+MyDatabase.KEY_ACCESS_POINT_TB_REGISTRATION_ACTIVITY +" FROM " + MyDatabase.TB_REGISTRATION_ACTIVITY + " RAX WHERE RAX." + MyDatabase.KEY_ID_PERSON_TB_REGISTRATION_ACTIVITY + " = RA." +  MyDatabase.KEY_ID_PERSON_TB_REGISTRATION_ACTIVITY + " AND RAX." +MyDatabase.KEY_ACTIVITY_TYPE_TB_REGISTRATION_ACTIVITY+ " = 'INGRESO' AND substr(RAX."+ MyDatabase.KEY_ACTIVITY_DATE_TB_REGISTRATION_ACTIVITY + ",1,10) = " + todayX+" ORDER BY RAX." + MyDatabase.KEY_ID_SQLLITE_TB_REGISTRATION_ACTIVITY +" DESC LIMIT 1) AS 'PuntoAccesoIngreso', " +
//                    "(SELECT "+MyDatabase.KEY_ACTIVITY_DATE_TB_REGISTRATION_ACTIVITY +" FROM " + MyDatabase.TB_REGISTRATION_ACTIVITY + " RAX WHERE RAX." + MyDatabase.KEY_ID_PERSON_TB_REGISTRATION_ACTIVITY + " = RA." +  MyDatabase.KEY_ID_PERSON_TB_REGISTRATION_ACTIVITY + " AND RAX." +MyDatabase.KEY_ACTIVITY_TYPE_TB_REGISTRATION_ACTIVITY+ " = 'SALIDA' AND substr(RAX." + MyDatabase.KEY_ACTIVITY_DATE_TB_REGISTRATION_ACTIVITY + ",1,10) = " + todayX +" ORDER BY RAX." + MyDatabase.KEY_ID_SQLLITE_TB_REGISTRATION_ACTIVITY +" DESC LIMIT 1) AS 'FechaSalida', " +
//                    "(SELECT "+MyDatabase.KEY_ACCESS_POINT_TB_REGISTRATION_ACTIVITY +" FROM " + MyDatabase.TB_REGISTRATION_ACTIVITY + " RAX WHERE RAX." + MyDatabase.KEY_ID_PERSON_TB_REGISTRATION_ACTIVITY + " = RA." +  MyDatabase.KEY_ID_PERSON_TB_REGISTRATION_ACTIVITY + " AND RAX." +MyDatabase.KEY_ACTIVITY_TYPE_TB_REGISTRATION_ACTIVITY+ " = 'SALIDA' AND substr(RAX."+ MyDatabase.KEY_ACTIVITY_DATE_TB_REGISTRATION_ACTIVITY + ",1,10) = " + todayX+" ORDER BY RAX." + MyDatabase.KEY_ID_SQLLITE_TB_REGISTRATION_ACTIVITY +" DESC LIMIT 1) AS 'PuntoAccesoSalida', " +
//                    "(SELECT "+MyDatabase.KEY_ID_MOVIL_DEVICE_TB_REGISTRATION_ACTIVITY +" FROM " + MyDatabase.TB_REGISTRATION_ACTIVITY + " RAX WHERE RAX." + MyDatabase.KEY_ID_PERSON_TB_REGISTRATION_ACTIVITY + " = RA." +  MyDatabase.KEY_ID_PERSON_TB_REGISTRATION_ACTIVITY + " AND RAX." +MyDatabase.KEY_ACTIVITY_TYPE_TB_REGISTRATION_ACTIVITY+ " = 'INGRESO' AND substr(RAX." + MyDatabase.KEY_ACTIVITY_DATE_TB_REGISTRATION_ACTIVITY + ",1,10) = " + todayX +" ORDER BY RAX." + MyDatabase.KEY_ID_SQLLITE_TB_REGISTRATION_ACTIVITY +" DESC LIMIT 1) AS 'IdDispositivoIngreso', " +
//                    "(SELECT "+MyDatabase.KEY_ID_MOVIL_DEVICE_TB_REGISTRATION_ACTIVITY +" FROM " + MyDatabase.TB_REGISTRATION_ACTIVITY + " RAX WHERE RAX." + MyDatabase.KEY_ID_PERSON_TB_REGISTRATION_ACTIVITY + " = RA." +  MyDatabase.KEY_ID_PERSON_TB_REGISTRATION_ACTIVITY + " AND RAX." +MyDatabase.KEY_ACTIVITY_TYPE_TB_REGISTRATION_ACTIVITY+ " = 'SALIDA' AND substr(RAX."+ MyDatabase.KEY_ACTIVITY_DATE_TB_REGISTRATION_ACTIVITY + ",1,10) = " + todayX+" ORDER BY RAX." + MyDatabase.KEY_ID_SQLLITE_TB_REGISTRATION_ACTIVITY +" DESC LIMIT 1) AS 'IdDispositivoSalida', " +
//                    "(SELECT "+MyDatabase.KEY_ID_PERSON_TB_REGISTRATION_ACTIVITY +" FROM " + MyDatabase.TB_REGISTRATION_ACTIVITY + " RAX WHERE RAX." + MyDatabase.KEY_ID_PERSON_TB_REGISTRATION_ACTIVITY + " = RA." +  MyDatabase.KEY_ID_PERSON_TB_REGISTRATION_ACTIVITY + " ORDER BY RAX." + MyDatabase.KEY_ID_SQLLITE_TB_REGISTRATION_ACTIVITY +" DESC LIMIT 1) AS " + MyDatabase.KEY_ID_PERSON_TB_REGISTRATION_ACTIVITY  + ", " +
//                    "(SELECT "+MyDatabase.KEY_PERSON_NAME_TB_REGISTRATION_ACTIVITY +" FROM " + MyDatabase.TB_REGISTRATION_ACTIVITY + " RAX WHERE RAX." + MyDatabase.KEY_ID_PERSON_TB_REGISTRATION_ACTIVITY + " = RA." +  MyDatabase.KEY_ID_PERSON_TB_REGISTRATION_ACTIVITY + " ORDER BY RAX." + MyDatabase.KEY_ID_SQLLITE_TB_REGISTRATION_ACTIVITY +" DESC LIMIT 1) AS " + MyDatabase.KEY_PERSON_NAME_TB_REGISTRATION_ACTIVITY  + ", " +
//                    "(SELECT "+MyDatabase.KEY_FIRST_LAST_NAME_TB_REGISTRATION_ACTIVITY +" FROM " + MyDatabase.TB_REGISTRATION_ACTIVITY + " RAX WHERE RAX." + MyDatabase.KEY_ID_PERSON_TB_REGISTRATION_ACTIVITY + " = RA." +  MyDatabase.KEY_ID_PERSON_TB_REGISTRATION_ACTIVITY + " ORDER BY RAX." + MyDatabase.KEY_ID_SQLLITE_TB_REGISTRATION_ACTIVITY +" DESC LIMIT 1) AS " + MyDatabase.KEY_FIRST_LAST_NAME_TB_REGISTRATION_ACTIVITY  + ", " +
//                    "(SELECT "+MyDatabase.KEY_SECOND_LAST_NAME_TB_REGISTRATION_ACTIVITY +" FROM " + MyDatabase.TB_REGISTRATION_ACTIVITY + " RAX WHERE RAX." + MyDatabase.KEY_ID_PERSON_TB_REGISTRATION_ACTIVITY + " = RA." +  MyDatabase.KEY_ID_PERSON_TB_REGISTRATION_ACTIVITY + " ORDER BY RAX." + MyDatabase.KEY_ID_SQLLITE_TB_REGISTRATION_ACTIVITY +" DESC LIMIT 1) AS " + MyDatabase.KEY_SECOND_LAST_NAME_TB_REGISTRATION_ACTIVITY  + ", " +
//                    "(SELECT "+MyDatabase.KEY_PHOTOCHECK_TB_REGISTRATION_ACTIVITY +" FROM " + MyDatabase.TB_REGISTRATION_ACTIVITY + " RAX WHERE RAX." + MyDatabase.KEY_ID_PERSON_TB_REGISTRATION_ACTIVITY + " = RA." +  MyDatabase.KEY_ID_PERSON_TB_REGISTRATION_ACTIVITY + " ORDER BY RAX." + MyDatabase.KEY_ID_SQLLITE_TB_REGISTRATION_ACTIVITY +" DESC LIMIT 1) AS " + MyDatabase.KEY_PHOTOCHECK_TB_REGISTRATION_ACTIVITY  + ", " +
//                    "(SELECT "+MyDatabase.KEY_DOCUMENT_NUMBER_TB_REGISTRATION_ACTIVITY +" FROM " + MyDatabase.TB_REGISTRATION_ACTIVITY + " RAX WHERE RAX." + MyDatabase.KEY_ID_PERSON_TB_REGISTRATION_ACTIVITY + " = RA." +  MyDatabase.KEY_ID_PERSON_TB_REGISTRATION_ACTIVITY + " ORDER BY RAX." + MyDatabase.KEY_ID_SQLLITE_TB_REGISTRATION_ACTIVITY +" DESC LIMIT 1) AS " + MyDatabase.KEY_DOCUMENT_NUMBER_TB_REGISTRATION_ACTIVITY  + ", " +
//                    "(SELECT "+MyDatabase.KEY_DOCUMENT_PATHBASE64_TB_PERSON +" FROM " + MyDatabase.TB_PERSON + " RAX WHERE RAX." + MyDatabase.KEY_ID_TB_PERSON + " = RA." +  MyDatabase.KEY_ID_PERSON_TB_REGISTRATION_ACTIVITY + " ORDER BY RAX." + MyDatabase.KEY_ID_SQLLITE_TB_PERSON +" DESC LIMIT 1) AS " + MyDatabase.KEY_DOCUMENT_PATHBASE64_TB_PERSON  +
//                    " FROM " + MyDatabase.TB_REGISTRATION_ACTIVITY + " RA " +
//                    " WHERE substr(RA." + MyDatabase.KEY_ACTIVITY_DATE_TB_REGISTRATION_ACTIVITY + ",1,10) = " + todayX +
//                    " GROUP BY RA." + MyDatabase.KEY_ID_PERSON_TB_REGISTRATION_ACTIVITY + ") AS PV" +
//                    " WHERE ( PV." + MyDatabase.KEY_PERSON_NAME_TB_REGISTRATION_ACTIVITY + " LIKE '%" + busqueda + "%' OR PV." +
//                    MyDatabase.KEY_FIRST_LAST_NAME_TB_REGISTRATION_ACTIVITY + " LIKE '%" + busqueda + "%' OR PV." +
//                    MyDatabase.KEY_SECOND_LAST_NAME_TB_REGISTRATION_ACTIVITY + " LIKE '%" + busqueda + "%' OR PV." +
//                    MyDatabase.KEY_PHOTOCHECK_TB_REGISTRATION_ACTIVITY + " LIKE '%" + busqueda + "%' OR PV." +
//                    MyDatabase.KEY_DOCUMENT_NUMBER_TB_REGISTRATION_ACTIVITY + " LIKE '%" + busqueda + "%' )";
//
//
//
//        SQLiteDatabase db = helper.getReadableDatabase();
//        Cursor cursor = db.rawQuery(sql, null);
//        //Log.v("CRUDOPERATIONS",sql2);
//        if(cursor.moveToFirst())
//        {
//            do
//            {
//                RegistrationActivityEntity RegistrationActivityEntity =new RegistrationActivityEntity();
//                String fechaIngreso = ""+cursor.getString(0);
//                if(fechaIngreso.equals("null")){
//                    RegistrationActivityEntity.setRegistrationActivityDate("-");
//                }else {RegistrationActivityEntity.setRegistrationActivityDate(cursor.getString(0));}
//                String puntoAccesoIngreso = ""+cursor.getString(1);
//                if(puntoAccesoIngreso.equals("null")){
//                    RegistrationActivityEntity.setAccessPoint("-");
//                }else {RegistrationActivityEntity.setAccessPoint(cursor.getString(1));}
//
//                String fechaSalida = ""+cursor.getString(2);
//                if(fechaSalida.equals("null")){
//                    RegistrationActivityEntity.setRegistrationActivityDateOut("-");
//                }else {RegistrationActivityEntity.setRegistrationActivityDateOut(cursor.getString(2));}
//                String puntoAccesoSalida = ""+cursor.getString(3);
//                if(puntoAccesoSalida.equals("null")){
//                    RegistrationActivityEntity.setAccessPointOut("-");
//                }else {RegistrationActivityEntity.setAccessPointOut(cursor.getString(3));}
//
//                String IdMoviIngreso = ""+cursor.getString(4);
//                if(IdMoviIngreso.equals("null")){
//                    RegistrationActivityEntity.setIdMovilDevice("-");
//                }else {RegistrationActivityEntity.setIdMovilDevice(cursor.getString(4));}
//
//                String IdMoviSalida = ""+cursor.getString(5);
//                if(IdMoviSalida.equals("null")){
//                    RegistrationActivityEntity.setIdMovilDeviceOut("-");
//                }else {RegistrationActivityEntity.setIdMovilDeviceOut(cursor.getString(5));}
//
//                RegistrationActivityEntity.setIdPerson(Integer.parseInt(cursor.getString(6)));
//                RegistrationActivityEntity.setPersonName(cursor.getString(7));
//                RegistrationActivityEntity.setFirstLastName(cursor.getString(8));
//                RegistrationActivityEntity.setSecondLastName(cursor.getString(9));
//                RegistrationActivityEntity.setPhotocheck(cursor.getString(10));
//                RegistrationActivityEntity.setDocumentNumber(cursor.getString(11));
//                RegistrationActivityEntity.setPathFileBase64(cursor.getString(12));
//
//
//                lst.add(RegistrationActivityEntity);
//            }while(cursor.moveToNext());
//        }
//        return lst;
//    }

    public int getRegistrationActivityCount()
    {
        int totalCount = 0;
        String sql= "SELECT * FROM "+MyDatabase.TB_REGISTRATION_ACTIVITY;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        totalCount = cursor.getCount();
        cursor.close();

        return totalCount;
    }


    //TB_CONFIGURATION
    public int addConfiguration(ConfigurationEntity configurationEntity)
    {
        SQLiteDatabase db = helper.getWritableDatabase(); //modo escritura
        ContentValues values = new ContentValues();
        values.put(MyDatabase.KEY_ID_ACCESS_PONT_TB_CONFIGURATION, configurationEntity.getIdAccessPoint());
        values.put(MyDatabase.KEY_ACCESS_PONT_TB_CONFIGURATION, configurationEntity.getAccessPoint());
        values.put(MyDatabase.KEY_REGISTRATION_TIMEOUT_TB_CONFIGURATION, configurationEntity.getTimeout());
        values.put(MyDatabase.KEY_REGISTRATION_MIGRATION_LAPSE_TB_CONFIGURATION, configurationEntity.getMigrationLapse());
        values.put(MyDatabase.KEY_REGISTRATION_DATE_TB_CONFIGURATION, configurationEntity.getRegistrationDate());
        values.put(MyDatabase.KEY_UPDATE_DATE_TB_CONFIGURATION, configurationEntity.getUpdateDate());
        values.put(MyDatabase.KEY_REGISTRATION_USER_TB_CONFIGURATION, configurationEntity.getRegistrationUser());
        values.put(MyDatabase.KEY_UPDATE_USER_TB_CONFIGURATION, configurationEntity.getUpdateUser());

        int row = (int) db.insert(MyDatabase.TB_CONFIGURATION, null, values);
        db.close();
        return row;
    }

    public int updateConfiguration(ConfigurationEntity configurationEntity)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MyDatabase.KEY_ID_ACCESS_PONT_TB_CONFIGURATION, configurationEntity.getIdAccessPoint());
        values.put(MyDatabase.KEY_ACCESS_PONT_TB_CONFIGURATION, configurationEntity.getAccessPoint());
        values.put(MyDatabase.KEY_REGISTRATION_TIMEOUT_TB_CONFIGURATION, configurationEntity.getTimeout());
        values.put(MyDatabase.KEY_REGISTRATION_MIGRATION_LAPSE_TB_CONFIGURATION, configurationEntity.getMigrationLapse());
        values.put(MyDatabase.KEY_REGISTRATION_DATE_TB_CONFIGURATION, configurationEntity.getRegistrationDate());
        values.put(MyDatabase.KEY_UPDATE_DATE_TB_CONFIGURATION, configurationEntity.getUpdateDate());
        values.put(MyDatabase.KEY_REGISTRATION_USER_TB_CONFIGURATION, configurationEntity.getRegistrationUser());
        values.put(MyDatabase.KEY_UPDATE_USER_TB_CONFIGURATION, configurationEntity.getUpdateUser());

        int row =db.update(MyDatabase.TB_CONFIGURATION,
                values,
                MyDatabase.KEY_ID_SQLLITE_TB_CONFIGURATION+"=?",
                new String[]{String.valueOf(configurationEntity.getIdConfiguration())});
        db.close();

        return row;
    }

    public List<ConfigurationEntity> getAllConfiguration()
    {
        List<ConfigurationEntity> lst =new ArrayList<ConfigurationEntity>();
        String sql= "SELECT  * FROM " + MyDatabase.TB_CONFIGURATION;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do
            {
                ConfigurationEntity configurationEntity =new ConfigurationEntity();
                configurationEntity.setIdConfiguration(Integer.parseInt(cursor.getString(0)));
                configurationEntity.setIdAccessPoint(Integer.parseInt(cursor.getString(1)));
                configurationEntity.setAccessPoint(cursor.getString(2));
                configurationEntity.setTimeout(Integer.parseInt(cursor.getString(3)));
                configurationEntity.setMigrationLapse(Integer.parseInt(cursor.getString(4)));
                configurationEntity.setRegistrationDate(cursor.getString(5));
                configurationEntity.setUpdateDate(cursor.getString(6));
                configurationEntity.setRegistrationUser(cursor.getString(7));
                configurationEntity.setUpdateUser(cursor.getString(8));
                lst.add(configurationEntity);
            }while(cursor.moveToNext());
        }
        return lst;
    }

    //TB_MIGRATION_ERROR
    public int addMigrationError(MigrationErrorEntity migrationErrorEntity)
    {
        SQLiteDatabase db = helper.getWritableDatabase(); //modo escritura
        ContentValues values = new ContentValues();
        values.put(MyDatabase.KEY_MIGRATION_START_DATE_TB_ERROR, migrationErrorEntity.getMigrationStartDate());
        values.put(MyDatabase.KEY_ERROR_DESCRIPTION_TB_ERROR, migrationErrorEntity.getErrorDescription());
        values.put(MyDatabase.KEY_ERROR_DATE_TB_ERROR, migrationErrorEntity.getErrorDate());
        values.put(MyDatabase.KEY_REGISTRATION_USER_TB_ERROR, migrationErrorEntity.getRegistrationUser());

        int row = (int) db.insert(MyDatabase.TB_MIGRATION_ERROR, null, values);
        db.close();
        return row;
    }

    public List<MigrationErrorEntity> getAllMigrationError(String fecha)
    {
        List<MigrationErrorEntity> lst =new ArrayList<MigrationErrorEntity>();
        String todayX = "'"+fecha + "'";
        String sql= "SELECT  * FROM " + MyDatabase.TB_MIGRATION_ERROR + " WHERE substr("+ MyDatabase.KEY_ERROR_DATE_TB_ERROR + ",1,10) = " + todayX +" ORDER BY " + MyDatabase.KEY_ID_SQLLITE_TB_ERROR + " DESC";
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do
            {
                MigrationErrorEntity migrationErrorEntity =new MigrationErrorEntity();
                migrationErrorEntity.setIdMigrationError(Integer.parseInt(cursor.getString(0)));
                migrationErrorEntity.setMigrationStartDate(cursor.getString(1));
                migrationErrorEntity.setErrorDescription(cursor.getString(2));
                migrationErrorEntity.setErrorDate(cursor.getString(3));
                migrationErrorEntity.setRegistrationUser(cursor.getString(4));
                lst.add(migrationErrorEntity);
            }while(cursor.moveToNext());
        }
        return lst;
    }

}
