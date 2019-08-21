package com.mayandevelopers.pftp.models;

public class MuestrasModel {

    private int idMuestra;
    private String numMuestra;
    private String fechaMuestra;

    public MuestrasModel(int idMuestra, String numMuestra, String fechaMuestra) {
        this.idMuestra = idMuestra;
        this.numMuestra = numMuestra;
        this.fechaMuestra = fechaMuestra;
    }

    public int getIdMuestra() {
        return idMuestra;
    }

    public void setIdMuestra(int idMuestra) {
        this.idMuestra = idMuestra;
    }

    public String getNumMuestra() {
        return numMuestra;
    }

    public void setNumMuestra(String numMuestra) {
        this.numMuestra = numMuestra;
    }

    public String getFechaMuestra() {
        return fechaMuestra;
    }

    public void setFechaMuestra(String fechaMuestra) {
        this.fechaMuestra = fechaMuestra;
    }
}
