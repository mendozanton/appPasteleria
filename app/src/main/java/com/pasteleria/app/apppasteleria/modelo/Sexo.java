package com.pasteleria.app.apppasteleria.modelo;

public class Sexo {
    private Integer idUsuarioSexo;
    private String nombre;

    public Sexo() {
    }

    @Override
    public String toString() {
        return "Sexo{" +
                "idUsuarioSexo=" + idUsuarioSexo +
                ", nombre='" + nombre + '\'' +
                '}';
    }

    public Sexo(Integer idUsuarioSexo, String nombre) {
        this.idUsuarioSexo = idUsuarioSexo;
        this.nombre = nombre;
    }

    public Integer getIdUsuarioSexo() {
        return idUsuarioSexo;
    }

    public void setIdUsuarioSexo(Integer idUsuarioSexo) {
        this.idUsuarioSexo = idUsuarioSexo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
