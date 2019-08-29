package com.mayandevelopers.pftp.models;

public class RanchosModel {

    private String idRancho;
    private String nombreRancho;
    private String ubicacion;
    private String codigo_postal;
    private String fecha_registro;
    private String fecha_actualizacion;

    public RanchosModel() {
    }

    public RanchosModel(String idRancho, String nombreRancho, String ubicacion, String codigo_postal, String fecha_registro, String fecha_actualizacion) {
        this.idRancho = idRancho;
        this.nombreRancho = nombreRancho;
        this.ubicacion = ubicacion;
        this.codigo_postal = codigo_postal;
        this.fecha_registro = fecha_registro;
        this.fecha_actualizacion = fecha_actualizacion;
    }

    public String getIdRancho() {
        return idRancho;
    }

    public void setIdRancho(String idRancho) {
        this.idRancho = idRancho;
    }

    public String getNombreRancho() {
        return nombreRancho;
    }

    public void setNombreRancho(String nombreRancho) {
        this.nombreRancho = nombreRancho;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(String codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public String getFecha_actualizacion() {
        return fecha_actualizacion;
    }

    public void setFecha_actualizacion(String fecha_actualizacion) {
        this.fecha_actualizacion = fecha_actualizacion;
    }
}