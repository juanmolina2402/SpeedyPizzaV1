package com.restaurant_bd.speedypizza.Services;

import com.restaurant_bd.speedypizza.Models.Categoria;
import com.restaurant_bd.speedypizza.Models.Menu;
import com.restaurant_bd.speedypizza.Models.Mesa;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIServices {
    @GET("api/categoria")
    Call<List<Categoria>> getCategoria();

    @GET("api/menu/categoria/{id}")
    Call<List<Menu>> getMenu(@Path("id") long id);

    @GET("api/mesa")
    Call<List<Mesa>> getMesa();
}
