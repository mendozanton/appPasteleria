package com.pasteleria.app.apppasteleria.modelo;

import java.io.Serializable;
import java.util.List;

public class Producto implements Serializable {
    private Integer idProducto;
    private String nombre;
    private String descripcion;
    private String descripcion2;
    private Double precio;
    private Integer stock;
    private List<Imagen> Imagenes;
    private String detalle;
    private String estado;

    public Producto() {

    }


    public String getDescripcion2() {
        return descripcion2;
    }

    public void setDescripcion2(String descripcio2) {
        this.descripcion2 = descripcio2;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public List<Imagen> getImagenes() {
        return Imagenes;
    }

    public void setImagenes(List<Imagen> imagenes) {
        Imagenes = imagenes;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "idProducto=" + idProducto +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", descripcio2='" + descripcion2 + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                ", Imagenes=" + Imagenes +
                ", detalle='" + detalle + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
