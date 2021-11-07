package com.restaurant_bd.speedypizza.Services;

import com.restaurant_bd.speedypizza.Models.Mesa;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MesaService {
    @GET("api/mesa")
    Call<List<Mesa>> getMesa();
}
