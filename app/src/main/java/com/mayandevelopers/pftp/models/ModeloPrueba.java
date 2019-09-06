package com.mayandevelopers.pftp.models;

public class ModeloPrueba {

    String id_muestra, id_arbol;

    public ModeloPrueba(String id_muestra, String id_arbol) {
        this.id_muestra = id_muestra;
        this.id_arbol = id_arbol;
    }


    public String getId_muestra() {
        return id_muestra;
    }

    public void setId_muestra(String id_muestra) {
        this.id_muestra = id_muestra;
    }

    public String getId_arbol() {
        return id_arbol;
    }

    public void setId_arbol(String id_arbol) {
        this.id_arbol = id_arbol;
    }
}
