package com.pasteleria.app.apppasteleria.modelo;

import java.io.Serializable;

public class Compra implements Serializable {
    private Integer idCompra;
    private Integer cantidad;
    private Double monto;
    private Integer producto;

    public Compra() {

    }

    public Compra(Integer idCompra, Integer cantidad, Double monto, Integer producto) {
        this.idCompra = idCompra;
        this.cantidad = cantidad;
        this.monto = monto;
        this.producto = producto;
    }

    public Integer getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Integer idCompra) {
        this.idCompra = idCompra;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Integer getProducto() {
        return producto;
    }

    public void setProducto(Integer producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return "Compra{" +
                "idCompra=" + idCompra +
                ", cantidad=" + cantidad +
                ", monto=" + monto +
                ", producto=" + producto +
                '}';
    }
}

