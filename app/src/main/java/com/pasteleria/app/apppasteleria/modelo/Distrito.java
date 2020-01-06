package com.pasteleria.app.apppasteleria.modelo;

public class Distrito {
    private Integer idDistrito;
    private String nombre;

    public Distrito() {
    }

    public Distrito(Integer idDistrito, String nombre) {
        this.idDistrito = idDistrito;
        this.nombre = nombre;
    }

    public Integer getIdDistrito() {
        return idDistrito;
    }

    public void setIdDistrito(Integer idDistrito) {
        this.idDistrito = idDistrito;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Distrito{" +
                "idDistrito=" + idDistrito +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
