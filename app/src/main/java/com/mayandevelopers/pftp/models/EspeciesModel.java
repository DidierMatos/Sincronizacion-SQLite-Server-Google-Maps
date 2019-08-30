package com.mayandevelopers.pftp.models;

public class EspeciesModel {

    int idEspecie;
    private String nombreEspecie ;

    public EspeciesModel(int idEspecie, String nombreEspecie) {
        this.idEspecie = idEspecie;
        this.nombreEspecie = nombreEspecie;
    }

    public EspeciesModel(){

    }

    public int getIdEspecie() {
        return idEspecie;
    }

    public void setIdEspecie(int idEspecie) {
        this.idEspecie = idEspecie;
    }

    public String getNombreEspecie() {
        return nombreEspecie;
    }

    public void setNombreEspecie(String nombreEspecie) {
        this.nombreEspecie = nombreEspecie;
    }
}
