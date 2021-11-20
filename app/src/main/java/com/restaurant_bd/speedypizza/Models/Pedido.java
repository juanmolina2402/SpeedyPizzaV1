package com.restaurant_bd.speedypizza.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Pedido {
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
    private Date fecha_pedido;

    @SerializedName("observacion")
    @Expose
    private String observacion;

    @SerializedName("estado")
    @Expose
    private String estado;

    @SerializedName("cliente")
    @Expose
    private String cliente;
}
