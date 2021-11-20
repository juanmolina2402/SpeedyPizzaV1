package com.restaurant_bd.speedypizza.Models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Categoria {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("categoria")
    @Expose
    private String categoria;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @NonNull
    @Override
    public String toString(){
        return categoria;
    }
}
