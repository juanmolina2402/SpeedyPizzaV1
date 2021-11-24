package com.restaurant_bd.speedypizza.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detalle_Pedido {
    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("IDPEDIDO")
    @Expose
    private Pedido IDPEDIDO;

    @SerializedName("menu")
    @Expose
    private Menu menu;

    @SerializedName("cantidad")
    @Expose
    private long cantidad;

    public Detalle_Pedido() {
        super();
    }

    public Detalle_Pedido(long id, Pedido IDPEDIDO, Menu menu, long cantidad) {
        this.id = id;
        this.IDPEDIDO = IDPEDIDO;
        this.menu = menu;
        this.cantidad = cantidad;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Pedido getIDPEDIDO() {
        return IDPEDIDO;
    }

    public void setIDPEDIDO(Pedido IDPEDIDO) {
        this.IDPEDIDO = IDPEDIDO;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public long getCantidad() {
        return cantidad;
    }

    public void setCantidad(long cantidad) {
        this.cantidad = cantidad;
    }
}
