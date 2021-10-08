package com.restaurant_bd.speedypizza.interfaces;

import com.restaurant_bd.speedypizza.model.Mesa;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MesaService {
    @GET("api/mesa")
    Call<List<Mesa>> getMesa();
}
