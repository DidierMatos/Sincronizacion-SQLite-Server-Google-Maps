package com.mayandevelopers.pftp.models;

public class ArbolesModel {

    private int id;
    private String folio;
    private String num_serie;
    private String latitud;
    private String longitud;
    private int id_e;
    private int id_c;

    public ArbolesModel(int id, String folio, String num_serie, String latitud, String longitud, int id_e, int id_c) {
        this.id = id;
        this.folio = folio;
        this.num_serie = num_serie;
        this.latitud = latitud;
        this.longitud = longitud;
        this.id_e = id_e;
        this.id_c = id_c;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getNum_serie() {
        return num_serie;
    }

    public void setNum_serie(String num_serie) {
        this.num_serie = num_serie;
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

    public int getId_e() {
        return id_e;
    }

    public void setId_e(int id_e) {
        this.id_e = id_e;
    }

    public int getId_c() {
        return id_c;
    }

    public void setId_c(int id_c) {
        this.id_c = id_c;
    }
}
