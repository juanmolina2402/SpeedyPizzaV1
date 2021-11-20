package com.restaurant_bd.speedypizza.Models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Mesa {
    /*
    @SerializedName
    Con esta anotación le indicamos que este campo va cambiar su nombre que el que
    viene en el objeto serializable
    */
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("numero")
    @Expose
    private String numero;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return numero;
    }

    public void setCodigo(String codigo) {
        this.numero = codigo;
    }

    @NonNull
    @Override
    public String toString(){
        return numero;
    }
}
