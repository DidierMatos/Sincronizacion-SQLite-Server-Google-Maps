package com.mayandevelopers.pftp.models;

public class RanchosModel {

    private int idRancho;
    private String nombreRancho ;


    public RanchosModel(int idRancho, String nombreRancho) {
        this.idRancho = idRancho;
        this.nombreRancho = nombreRancho;
    }

    public int getIdRancho() {
        return idRancho;
    }

    public void setIdRancho(int idRancho) {
        this.idRancho = idRancho;
    }

    public String getNombreRancho() {
        return nombreRancho;
    }

    public void setNombreRancho(String nombreRancho) {
        this.nombreRancho = nombreRancho;
    }
}
