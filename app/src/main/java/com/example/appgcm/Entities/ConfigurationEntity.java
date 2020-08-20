package com.example.appgcm.Entities;

import java.io.Serializable;

public class ConfigurationEntity implements Serializable {

    private int IdConfiguration;
    private int IdAccessPoint;
    private String AccessPoint;
    private String RegistrationDate;
    private String UpdateDate;
    private String RegistrationUser;
    private String UpdateUser;

    public int getTimeout() {
        return Timeout;
    }

    public void setTimeout(int timeout) {
        Timeout = timeout;
    }

    public int getMigrationLapse() {
        return MigrationLapse;
    }

    public void setMigrationLapse(int migrationLapse) {
        MigrationLapse = migrationLapse;
    }

    private int Timeout; /*utilizado para definir el Tiempo de espera de conexión con el servidor*/
    private int MigrationLapse; /*Utilizado para definir el intervalo de tiempo de consulta a los servicios del servidor*/

    public ConfigurationEntity() {
    }

    public ConfigurationEntity(int idAccessPoint, String accessPoint, String registrationDate, String updateDate, String registrationUser, String updateUser, int timeout, int migrationLapse) {
        IdAccessPoint = idAccessPoint;
        AccessPoint = accessPoint;
        RegistrationDate = registrationDate;
        UpdateDate = updateDate;
        RegistrationUser = registrationUser;
        UpdateUser = updateUser;
        Timeout = timeout;
        MigrationLapse = migrationLapse;
    }

    public ConfigurationEntity(int idConfiguration,int idAccessPoint, String accessPoint, String registrationDate, String updateDate, String registrationUser, String updateUser, int timeout, int migrationLapse) {
        IdConfiguration = idConfiguration;
        IdAccessPoint = idAccessPoint;
        AccessPoint = accessPoint;
        RegistrationDate = registrationDate;
        UpdateDate = updateDate;
        RegistrationUser = registrationUser;
        UpdateUser = updateUser;
        Timeout = timeout;
        MigrationLapse = migrationLapse;
    }

    public int getIdAccessPoint() {
        return IdAccessPoint;
    }

    public void setIdAccessPoint(int idAccessPoint) {
        IdAccessPoint = idAccessPoint;
    }

    public int getIdConfiguration() {
        return IdConfiguration;
    }

    public void setIdConfiguration(int idConfiguration) {
        IdConfiguration = idConfiguration;
    }

    public String getAccessPoint() {
        return AccessPoint;
    }

    public void setAccessPoint(String accessPoint) {
        AccessPoint = accessPoint;
    }

    public String getRegistrationDate() {
        return RegistrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        RegistrationDate = registrationDate;
    }

    public String getUpdateDate() {
        return UpdateDate;
    }

    public void setUpdateDate(String updateDate) {
        UpdateDate = updateDate;
    }

    public String getRegistrationUser() {
        return RegistrationUser;
    }

    public void setRegistrationUser(String registrationUser) {
        RegistrationUser = registrationUser;
    }

    public String getUpdateUser() {
        return UpdateUser;
    }

    public void setUpdateUser(String updateUser) {
        UpdateUser = updateUser;
    }
}
