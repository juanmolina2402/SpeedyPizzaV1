package com.restaurant_bd.speedypizza.interfaces;

import com.restaurant_bd.speedypizza.model.Categoria;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoriaService {
    @GET("api/categoria")
    Call<List<Categoria>> getCategoria();
}
