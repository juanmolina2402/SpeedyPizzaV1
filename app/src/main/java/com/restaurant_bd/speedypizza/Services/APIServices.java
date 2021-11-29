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
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIServices {
    @GET("api/categoria")
    Call<List<Categoria>> getCategoria();

    @GET("api/menu/categoria/{id}")
    Call<List<Menu>> getMenu(@Path("id") long id);

    APIServices.ServiceMesas serviceMesas();

    APIServices.ServicePedidos servicePedidos();

    APIServices.ServiceEmpleados serviceEmpleados();

    APIServices.ServiceUsuarios serviceUsuarios();

    ///APIServices.ServiceDetallesPedidos serviceDetallesPedidos();

    //////////////SERIVE MESAS////////////////////////
    public interface ServiceMesas{

        @GET("api/mesa")
        Call<List<Mesa>> getMesa();

        @POST("api/mesa")
        Call<Mesa> setMesa(@Body Mesa mesa);
    }
    //////////////SERIVE USUARIOS////////////////////////
    public interface ServiceUsuarios{
        @GET("api/usuario")
        Call<List<Usuario>> getUsuario();
    }

    //////////////SERIVE USUARIOS////////////////////////
    public interface ServiceEmpleados{
        @GET("api/empleado")
        Call<List<Empleado>> getEmpleado();
    }

    //////////////SERIVE PEDIDO////////////////////////
    public interface ServicePedidos{
        @GET("api/pedido")
        Call<List<Pedido>> getPedido();

        @POST("api/pedido")
        Call<Pedido> setPedido(@Body Pedido pedido);
    }

    @GET("api/pedido/Listos")
    Call<List<Pedido>> getListPedidos();

    @PUT("api/pedido/{id}")
    Call<Pedido> putPedido(@Path("id") long id, @Body Pedido e);
}
