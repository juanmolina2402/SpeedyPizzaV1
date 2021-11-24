package com.restaurant_bd.speedypizza.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Pedido implements Serializable {
    private static final long serialversionuid = 1L;

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("empleado")
    @Expose
    private Empleado empleado;

    @SerializedName("mesa")
    @Expose
    private Mesa mesa;

    @SerializedName("fecha_pedido")
    @Expose
    private String fecha_pedido;

    @SerializedName("observacion")
    @Expose
    private String observacion;

    @SerializedName("estado")
    @Expose
    private String estado;

    @SerializedName("cliente")
    @Expose
    private String cliente;

    @SerializedName("detalle_pedido")
    @Expose
    private List<Detalle_Pedido> detalle_pedido;

    public Pedido() {
        super();
    }

    public Pedido(long id, Empleado empleado, Mesa mesa, String fecha_pedido, String observacion, String estado, String cliente) {
        this.id = id;
        this.empleado = empleado;
        this.mesa = mesa;
        this.fecha_pedido = fecha_pedido;
        this.observacion = observacion;
        this.estado = estado;
        this.cliente = cliente;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public String getFecha_pedido() {
        return fecha_pedido;
    }

    public void setFecha_pedido(String fecha_pedido) {
        this.fecha_pedido = fecha_pedido;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public List<Detalle_Pedido> getDetalle_pedido() {
        return detalle_pedido;
    }

    public void setDetalle_pedido(List<Detalle_Pedido> detalle_pedido) {
        this.detalle_pedido = detalle_pedido;
    }
}
