package com.restaurant_bd.speedypizza.Services;

import com.restaurant_bd.speedypizza.Models.Menu;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MenuService {
    @GET("api/menu/categoria/{id}")
    Call<List<Menu>> getMenu(@Path("id") long id);
}
