package com.example.appgcm.Entities;

import java.io.Serializable;

public class ActivityEntity implements Serializable {

    public int idSqlLiteActivity;
    public int IdActivity;
    public String ActivityName;
    public int IdRole;
    public String RoleName;
    public String RegistrationStatus;
    public String ValorConsulta;

    public String getMensajeConsulta() {
        return MensajeConsulta;
    }

    public void setMensajeConsulta(String mensajeConsulta) {
        MensajeConsulta = mensajeConsulta;
    }

    public String MensajeConsulta;

    public String getValorConsulta() {
        return ValorConsulta;
    }

    public void setValorConsulta(String valorConsulta) {
        ValorConsulta = valorConsulta;
    }

    public ActivityEntity() {
    }

    public ActivityEntity(int idSqlLiteActivity, int idActivity, String activityName,int idRole,String roleName, String registrationStatus) {
        this.idSqlLiteActivity = idSqlLiteActivity;
        IdActivity = idActivity;
        ActivityName = activityName;
        IdRole = idRole;
        RoleName = roleName;
        RegistrationStatus = registrationStatus;
    }

    public ActivityEntity(int idActivity, String activityName,int idRole,String roleName, String registrationStatus) {
        IdActivity = idActivity;
        ActivityName = activityName;
        IdRole = idRole;
        RoleName = roleName;
        RegistrationStatus = registrationStatus;
    }

    public int getIdSqlLiteActivity() {
        return idSqlLiteActivity;
    }

    public void setIdSqlLiteActivity(int idSqlLiteActivity) {
        this.idSqlLiteActivity = idSqlLiteActivity;
    }

    public int getIdActivity() {
        return IdActivity;
    }

    public void setIdActivity(int idActivity) {
        IdActivity = idActivity;
    }

    public String getActivityName() {
        return ActivityName;
    }

    public void setActivityName(String activityName) {
        ActivityName = activityName;
    }

    public String getRegistrationStatus() {
        return RegistrationStatus;
    }

    public void setRegistrationStatus(String registrationStatus) {
        RegistrationStatus = registrationStatus;
    }

    public int getIdRole() {
        return IdRole;
    }

    public void setIdRole(int idRole) {
        IdRole = idRole;
    }

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String roleName) {
        RoleName = roleName;
    }
}
