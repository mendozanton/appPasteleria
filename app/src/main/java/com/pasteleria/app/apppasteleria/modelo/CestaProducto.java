package com.pasteleria.app.apppasteleria.modelo;

import java.util.List;

public class CestaProducto {
    private Integer idCestaProducto;
    private Integer idProducto;
    private String nombre;
    private String descripcion;
    private String descripcion2;
    private Integer cantidad;
    private Double precio;
    private Integer stock;
    private List<Imagen> Imagenes;
    private String detalle;
    private Integer estado;

    public CestaProducto() {
    }

    public CestaProducto(Integer idCestaProducto, Integer idProducto, String nombre, String descripcion, String descripcion2, Integer cantidad, Double precio, Integer stock, List<Imagen> imagenes, String detalle, Integer estado) {
        this.idCestaProducto = idCestaProducto;
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.descripcion2 = descripcion2;
        this.cantidad = cantidad;
        this.precio = precio;
        this.stock = stock;
        Imagenes = imagenes;
        this.detalle = detalle;
        this.estado = estado;
    }

    public Integer getIdCestaProducto() {
        return idCestaProducto;
    }

    public void setIdCestaProducto(Integer idCestaProducto) {
        this.idCestaProducto = idCestaProducto;
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

    public String getDescripcion2() {
        return descripcion2;
    }

    public void setDescripcion2(String descripcion2) {
        this.descripcion2 = descripcion2;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
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

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "CestaProducto{" +
                "idCestaProducto=" + idCestaProducto +
                ", idProducto=" + idProducto +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", descripcion2='" + descripcion2 + '\'' +
                ", cantidad=" + cantidad +
                ", precio=" + precio +
                ", stock=" + stock +
                ", Imagenes=" + Imagenes +
                ", detalle='" + detalle + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
