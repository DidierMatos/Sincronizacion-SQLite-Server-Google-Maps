package com.mayandevelopers.pftp.models;

public class Usuario {

   String id;
   String nombres;
   String apellidos;
   String correo;
   String pass;
   String telefono;
   String tipo_usuario;
   String privilegios_acceso;

    public Usuario() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    public String getPrivilegios_acceso() {
        return privilegios_acceso;
    }

    public void setPrivilegios_acceso(String privilegios_acceso) {
        this.privilegios_acceso = privilegios_acceso;
    }
}
