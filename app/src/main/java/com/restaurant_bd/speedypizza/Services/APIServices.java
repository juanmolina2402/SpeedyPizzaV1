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

    //////////////SERIVE CATEGORIAS////////////////////////
    interface ServiceCategorias{
        @GET("api/categoria")
        Call<List<Categoria>> getCategoria();
    }
    //////////////SERIVE MENUS////////////////////////
    interface ServiceMenus{
        @GET("api/menu/categoria/{id}")
        Call<List<Menu>> getMenu(@Path("id") long id);
    }

    //////////////SERIVE MESAS////////////////////////
    interface ServiceMesas{

        @GET("api/mesa")
        Call<List<Mesa>> getMesa();

        @POST("api/mesa")
        Call<Mesa> setMesa(@Body Mesa mesa);
    }
    //////////////SERIVE USUARIOS////////////////////////
    interface ServiceUsuarios{
        @GET("api/usuario")
        Call<List<Usuario>> getUsuario();

        @POST("api/usuario/login")
        Call<Usuario> getUserLogin(@Body Usuario usuario);
    }

    //////////////SERIVE EMPLEADOS////////////////////////
    interface ServiceEmpleados{
        @GET("api/empleado")
        Call<List<Empleado>> getEmpleado();

        @GET("api/empleado/{id}")
        Call<Empleado> getEmpleadoForLogin(@Path("id") Long id);
    }
    //////////////SERIVE PEDIDO////////////////////////
    interface ServicePedidos{
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
