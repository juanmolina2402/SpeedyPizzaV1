package com.restaurant_bd.speedypizza.Services;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PedidoAdapter {
    private static APIServices.ServicePedidos API_SERVICE;

    public static APIServices.ServicePedidos getApiServicePedido(){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        String baseURL = "https://restaurant-bd.herokuapp.com/";

        if(API_SERVICE == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
            API_SERVICE = retrofit.create(APIServices.ServicePedidos.class);
        }
        return API_SERVICE;
    }
}
