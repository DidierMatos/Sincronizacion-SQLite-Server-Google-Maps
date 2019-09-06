package com.mayandevelopers.pftp.models;

public class MuestrasModel {

    private String idMuestra;
    private String nombreMuestra;
    private String folioMuestra;
    private String latitudMuestra;
    private String longitudMuestra;
    private String radioMuestra;
    private String fechaMuestra;
    private String fechaActualizacion;
    private String idCentro;

    public MuestrasModel(String idMuestra, String nombreMuestra, String folioMuestra, String latitudMuestra, String longitudMuestra, String radioMuestra, String fechaMuestra, String fechaActualizacion, String idCentro) {
        this.idMuestra = idMuestra;
        this.nombreMuestra = nombreMuestra;
        this.folioMuestra = folioMuestra;
        this.latitudMuestra = latitudMuestra;
        this.longitudMuestra = longitudMuestra;
        this.radioMuestra = radioMuestra;
        this.fechaMuestra = fechaMuestra;
        this.fechaActualizacion = fechaActualizacion;
        this.idCentro = idCentro;
    }

    public String getIdMuestra() {
        return idMuestra;
    }

    public void setIdMuestra(String idMuestra) {
        this.idMuestra = idMuestra;
    }

    public String getNombreMuestra() {
        return nombreMuestra;
    }

    public void setNombreMuestra(String nombreMuestra) {
        this.nombreMuestra = nombreMuestra;
    }

    public String getFolioMuestra() {
        return folioMuestra;
    }

    public void setFolioMuestra(String folioMuestra) {
        this.folioMuestra = folioMuestra;
    }

    public String getLatitudMuestra() {
        return latitudMuestra;
    }

    public void setLatitudMuestra(String latitudMuestra) {
        this.latitudMuestra = latitudMuestra;
    }

    public String getLongitudMuestra() {
        return longitudMuestra;
    }

    public void setLongitudMuestra(String longitudMuestra) {
        this.longitudMuestra = longitudMuestra;
    }

    public String getRadioMuestra() {
        return radioMuestra;
    }

    public void setRadioMuestra(String radioMuestra) {
        this.radioMuestra = radioMuestra;
    }

    public String getFechaMuestra() {
        return fechaMuestra;
    }

    public void setFechaMuestra(String fechaMuestra) {
        this.fechaMuestra = fechaMuestra;
    }

    public String getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(String fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(String idCentro) {
        this.idCentro = idCentro;
    }
}
