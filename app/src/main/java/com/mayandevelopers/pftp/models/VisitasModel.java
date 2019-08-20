package com.mayandevelopers.pftp.models;

public class VisitasModel {

    private int id_visita;
    private String fecha_visita;

    public VisitasModel(int id_visita, String fecha_visita) {
        this.id_visita = id_visita;
        this.fecha_visita = fecha_visita;
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
}
