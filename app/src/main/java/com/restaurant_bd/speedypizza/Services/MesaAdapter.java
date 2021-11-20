package com.restaurant_bd.speedypizza.Services;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MesaAdapter {
    private static APIServices.ServiceMesas API_SERVICE;

    ///Método que aplica patrón de diseño singleton, ya que se asegura que se use una sola instancia
    public static APIServices.ServiceMesas getApiServiceMesa() {

        // Creamos un interceptor y le indicamos el log level a usar
        ///Devolverá logs y el código de repuesta (200, 400 o 500) el cual se ve en la consola de Android
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Asociamos el interceptor a las peticiones
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        String baseUrl = "https://restaurant-bd.herokuapp.com/";

        if (API_SERVICE == null) {
            ///Retrofit Hace las peticiones HTTP
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    ///Gson convierte las respuestas JSON a objetos Java y veceversa
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build()) // <-- usamos el log level
                    .build();
            API_SERVICE = retrofit.create(APIServices.ServiceMesas.class);
        }

        return API_SERVICE;
    }
}
