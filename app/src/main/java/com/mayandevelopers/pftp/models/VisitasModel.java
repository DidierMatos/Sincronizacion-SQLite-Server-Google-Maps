package com.mayandevelopers.pftp.models;

public class VisitasModel {

    private int id_visita;
    private String fecha_visita, altura, diametro, observaciones, vigor, condicion, sanidad, id_arbol, fecha_registro, fecha_actualizacion, latitud, longitud;

    public VisitasModel(int id_visita, String fecha_visita, String altura, String diametro, String observaciones, String vigor, String condicion, String sanidad, String id_arbol, String fecha_registro, String fecha_actualizacion, String latitud, String longitud) {
        this.id_visita = id_visita;
        this.fecha_visita = fecha_visita;
        this.altura = altura;
        this.diametro = diametro;
        this.observaciones = observaciones;
        this.vigor = vigor;
        this.condicion = condicion;
        this.sanidad = sanidad;
        this.id_arbol = id_arbol;
        this.fecha_registro = fecha_registro;
        this.fecha_actualizacion = fecha_actualizacion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public int getId_visita() {
        return id_visita;
    }

    public void setId_visita(int id_visita) {
        this.id_visita = id_visita;
    }

    public String getFecha_visita() {
        return fecha_visita;
    }

    public void setFecha_visita(String fecha_visita) {
        this.fecha_visita = fecha_visita;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getDiametro() {
        return diametro;
    }

    public void setDiametro(String diametro) {
        this.diametro = diametro;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getVigor() {
        return vigor;
    }

    public void setVigor(String vigor) {
        this.vigor = vigor;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public String getSanidad() {
        return sanidad;
    }

    public void setSanidad(String sanidad) {
        this.sanidad = sanidad;
    }

    public String getId_arbol() {
        return id_arbol;
    }

    public void setId_arbol(String id_arbol) {
        this.id_arbol = id_arbol;
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

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
