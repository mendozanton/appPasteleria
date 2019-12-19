package com.pasteleria.app.apppasteleria.modelo;

public class Imagen {
    private Integer idImagen;
    private String source;
    private String nombre;
    private String clasificacion;

    public Imagen() {
    }

    public Imagen(Integer idImagen, String source, String nombre, String clasificacion) {
        this.idImagen = idImagen;
        this.source = source;
        this.nombre = nombre;
        this.clasificacion = clasificacion;
    }

    @Override
    public String toString() {
        return "Imagen{" +
                "idImagen=" + idImagen +
                ", source='" + source + '\'' +
                ", nombre='" + nombre + '\'' +
                ", clasificacion='" + clasificacion + '\'' +
                '}';
    }

    public Integer getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(Integer idImagen) {
        this.idImagen = idImagen;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }
}
