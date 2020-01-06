package com.pasteleria.app.apppasteleria.modelo;

import java.io.Serializable;

public class Direccion implements Serializable {
    private Integer idDireccion;
    private String avenida;
    private String urbanizacion;
    private String calle;
    private String referencia;
    private Integer idDistrito;

    public Direccion() {
        this.avenida = "";
        this.urbanizacion = "";
        this.calle = "";
        this.referencia = "";

    }

    public Direccion(String avenida, String urbanizacion, String calle, String referencia, Integer idDistrito) {
        this.avenida = avenida;
        this.urbanizacion = urbanizacion;
        this.calle = calle;
        this.referencia = referencia;
        this.idDistrito = idDistrito;
    }

    @Override
    public String toString() {
        return "Direccion{" +
                "idDireccion=" + idDireccion +
                ", avenida='" + avenida + '\'' +
                ", urbanizacion='" + urbanizacion + '\'' +
                ", calle='" + calle + '\'' +
                ", referencia='" + referencia + '\'' +
                ", IdDistrito=" + idDistrito +
                '}';
    }

    public Integer getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(Integer idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getAvenida() {
        return avenida;
    }

    public void setAvenida(String avenida) {
        this.avenida = avenida;
    }

    public String getUrbanizacion() {
        return urbanizacion;
    }

    public void setUrbanizacion(String urbanizacion) {
        this.urbanizacion = urbanizacion;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Integer getIdDistrito() {
        return idDistrito;
    }

    public void setIdDistrito(Integer idDistrito) {
        this.idDistrito = idDistrito;
    }
}
