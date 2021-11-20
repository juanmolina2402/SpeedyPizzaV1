package com.restaurant_bd.speedypizza.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Empleado {
    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("nombres")
    @Expose
    private String nombres;

    @SerializedName("apellidos")
    @Expose
    private String apellidos;

    @SerializedName("direccion")
    @Expose
    private String direccion;

    @SerializedName("telefono")
    @Expose
    private String telefono;

    @SerializedName("DUI")
    @Expose
    private String DUI;

    @SerializedName("usuario")
    @Expose
    private Usuario usuario;
}
