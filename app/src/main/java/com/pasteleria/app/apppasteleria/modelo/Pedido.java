package com.pasteleria.app.apppasteleria.modelo;

import java.io.Serializable;
import java.util.List;

public class Pedido implements Serializable {

    private Integer idPedido;
    private String fecha;
    private String envio;
    private String codigo;
    private Integer usuario;
    private List<Compra> compras;
    private String prioridad;
    private String estado;

    public Pedido() {
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEnvio() {
        return envio;
    }

    public void setEnvio(String envio) {
        this.envio = envio;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    public List<Compra> getCompras() {
        return compras;
    }

    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido +
                ", fecha='" + fecha + '\'' +
                ", envio='" + envio + '\'' +
                ", codigo='" + codigo + '\'' +
                ", usuario=" + usuario +
                ", compras=" + compras +
                ", prioridad='" + prioridad + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
