package com.restaurant_bd.speedypizza.Services;

import com.restaurant_bd.speedypizza.Models.Categoria;
import com.restaurant_bd.speedypizza.Models.Empleado;
import com.restaurant_bd.speedypizza.Models.Menu;
import com.restaurant_bd.speedypizza.Models.Mesa;
import com.restaurant_bd.speedypizza.Models.Pedido;
import com.restaurant_bd.speedypizza.Models.Usuario;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIServices {
    @GET("api/categoria")
    Call<List<Categoria>> getCategoria();

    @GET("api/menu/categoria/{id}")
    Call<List<Menu>> getMenu(@Path("id") long id);

    APIServices.ServiceMesas serviceMesas();

    //////////////MESAS////////////////////////
    interface ServiceMesas{

        @GET("api/mesa")
        Call<List<Mesa>> getMesa();

        @POST("api/mesa")
        Call<Mesa> setMesa(@Body Mesa mesa);
    }
    //////////////USUARIOS////////////////////////
    interface ServiceUsuarios{
        @GET
        Call<List<Usuario>> getUsuario();
    }

    //////////////USUARIOS////////////////////////
    interface ServiceEmpleados{
        @GET
        Call<List<Empleado>> getEmpleado();
    }

    //////////////PEDIDO////////////////////////
    interface ServicePedidos{
        @GET
        Call<List<Pedido>> getPedido();

        @POST
        Call<Pedido> setPedido(@Body Pedido pedido);
    }
}
