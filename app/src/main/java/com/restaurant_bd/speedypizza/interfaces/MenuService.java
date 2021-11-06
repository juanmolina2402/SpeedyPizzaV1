package com.restaurant_bd.speedypizza.interfaces;

import com.restaurant_bd.speedypizza.model.Menu;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MenuService {
    @GET("api/menu/{id}")
    public Call<Menu> find(@Path("id") long id );
}
