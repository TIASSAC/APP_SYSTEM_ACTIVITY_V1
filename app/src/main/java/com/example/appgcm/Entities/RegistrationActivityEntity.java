package com.example.appgcm.Entities;

import java.io.Serializable;
import java.util.Date;

public class RegistrationActivityEntity implements Serializable {

    private int idSqlLite;
    private int IdRegistrationActivity;
    private String IdMovilDevice;
    private int IdPerson;
    private String PersonName;
    private String FirstLastName;
    private String SecondLastName;
    private String Photocheck;
    private String DocumentNumber;
    private String RegistrationActivityDate;
    private String RegistrationActivityType;
    private int RegistrationUser;
    private String RegistrationStatus;
    private String MigrationStatus;

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    private String Comment;

    public String PathFileBase64;
    public String ValorConsulta;
    public String MensajeConsulta;

    public RegistrationActivityEntity() {
    }

    public RegistrationActivityEntity(String idMovilDevice, int idPerson, String personName, String firstLastName, String secondLastName, String photocheck, String documentNumber, String registrationActivityDate, String registrationActivityType, int registrationUser, String registrationStatus, String migrationStatus, String comment) {
        IdMovilDevice = idMovilDevice;
        IdPerson = idPerson;
        PersonName = personName;
        FirstLastName = firstLastName;
        SecondLastName = secondLastName;
        Photocheck = photocheck;
        DocumentNumber = documentNumber;
        RegistrationActivityDate = registrationActivityDate;
        RegistrationActivityType = registrationActivityType;
        RegistrationUser = registrationUser;
        RegistrationStatus = registrationStatus;
        MigrationStatus = migrationStatus;
        Comment = comment;
    }

    public RegistrationActivityEntity(int idRegistrationActivity, String idMovilDevice, int idPerson, String personName, String firstLastName, String secondLastName, String photocheck, String documentNumber, String registrationActivityDate, String registrationActivityType, int registrationUser, String registrationStatus, String migrationStatus, String comment) {
        IdRegistrationActivity = idRegistrationActivity;
        IdMovilDevice = idMovilDevice;
        IdPerson = idPerson;
        PersonName = personName;
        FirstLastName = firstLastName;
        SecondLastName = secondLastName;
        Photocheck = photocheck;
        DocumentNumber = documentNumber;
        RegistrationActivityDate = registrationActivityDate;
        RegistrationActivityType = registrationActivityType;
        RegistrationUser = registrationUser;
        RegistrationStatus = registrationStatus;
        MigrationStatus = migrationStatus;
        Comment = comment;
    }

    public RegistrationActivityEntity(int idSqlLite, int idRegistrationActivity, String idMovilDevice, int idPerson, String personName, String firstLastName, String secondLastName, String photocheck, String documentNumber, String registrationActivityDate, String registrationActivityType, int registrationUser, String registrationStatus, String migrationStatus, String comment) {
        this.idSqlLite = idSqlLite;
        IdRegistrationActivity = idRegistrationActivity;
        IdMovilDevice = idMovilDevice;
        IdPerson = idPerson;
        PersonName = personName;
        FirstLastName = firstLastName;
        SecondLastName = secondLastName;
        Photocheck = photocheck;
        DocumentNumber = documentNumber;
        RegistrationActivityDate = registrationActivityDate;
        RegistrationActivityType = registrationActivityType;
        RegistrationUser = registrationUser;
        RegistrationStatus = registrationStatus;
        MigrationStatus = migrationStatus;
        Comment = comment;
    }

    public int getIdSqlLite() {
        return idSqlLite;
    }

    public void setIdSqlLite(int idSqlLite) {
        this.idSqlLite = idSqlLite;
    }

    public int getIdRegistrationActivity() {
        return IdRegistrationActivity;
    }

    public void setIdRegistrationActivity(int idRegistrationActivity) {
        IdRegistrationActivity = idRegistrationActivity;
    }

    public String getIdMovilDevice() {
        return IdMovilDevice;
    }

    public void setIdMovilDevice(String idMovilDevice) {
        IdMovilDevice = idMovilDevice;
    }

    public int getIdPerson() {
        return IdPerson;
    }

    public void setIdPerson(int idPerson) {
        IdPerson = idPerson;
    }

    public String getPersonName() {
        return PersonName;
    }

    public void setPersonName(String personName) {
        PersonName = personName;
    }

    public String getFirstLastName() {
        return FirstLastName;
    }

    public void setFirstLastName(String firstLastName) {
        FirstLastName = firstLastName;
    }

    public String getSecondLastName() {
        return SecondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        SecondLastName = secondLastName;
    }

    public String getPhotocheck() {
        return Photocheck;
    }

    public void setPhotocheck(String photocheck) {
        Photocheck = photocheck;
    }

    public String getDocumentNumber() {
        return DocumentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        DocumentNumber = documentNumber;
    }

    public String getRegistrationActivityDate() {
        return RegistrationActivityDate;
    }

    public void setRegistrationActivityDate(String registrationActivityDate) {
        RegistrationActivityDate = registrationActivityDate;
    }

    public String getRegistrationActivityType() {
        return RegistrationActivityType;
    }

    public void setRegistrationActivityType(String registrationActivityType) {
        RegistrationActivityType = registrationActivityType;
    }

    public int getRegistrationUser() {
        return RegistrationUser;
    }

    public void setRegistrationUser(int registrationUser) {
        RegistrationUser = registrationUser;
    }

    public String getRegistrationStatus() {
        return RegistrationStatus;
    }

    public void setRegistrationStatus(String registrationStatus) {
        RegistrationStatus = registrationStatus;
    }

    public String getMigrationStatus() {
        return MigrationStatus;
    }

    public void setMigrationStatus(String migrationStatus) {
        MigrationStatus = migrationStatus;
    }

    public String getPathFileBase64() {
        return PathFileBase64;
    }

    public void setPathFileBase64(String pathFileBase64) {
        PathFileBase64 = pathFileBase64;
    }

    public String getValorConsulta() {
        return ValorConsulta;
    }

    public void setValorConsulta(String valorConsulta) {
        ValorConsulta = valorConsulta;
    }

    public String getMensajeConsulta() {
        return MensajeConsulta;
    }

    public void setMensajeConsulta(String mensajeConsulta) {
        MensajeConsulta = mensajeConsulta;
    }
}
