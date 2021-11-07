package com.restaurant_bd.speedypizza;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.restaurant_bd.speedypizza.Adapters.MenuAdapter;
import com.restaurant_bd.speedypizza.Models.Categoria;
import com.restaurant_bd.speedypizza.Models.Mesa;
import com.restaurant_bd.speedypizza.Services.MenuService;
import com.restaurant_bd.speedypizza.Models.Menu;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuActivity extends AppCompatActivity {
    private final String baseUrl = "https://restaurant-bd.herokuapp.com/";
    private List<Menu> listaMenu;
    EditText id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        buscar();
    }

    public void buscar ()
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MenuService menuService = retrofit.create(MenuService.class);
        Call<List<Menu>> lista = menuService.getMenu(1);
        lista.enqueue(new Callback<List<Menu>>() {
            @Override
            public void onResponse(Call<List<Menu>> call, Response<List<Menu>> response) {
                try {
                    if(response.isSuccessful())
                    {
                        listaMenu = response.body();
                        RecyclerView recyclerView = findViewById(R.id.rvMenu);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MenuActivity.this));
                        recyclerView.setAdapter(new MenuAdapter(listaMenu));
                    }

                }catch (Exception e)
                {
                    Toast.makeText(MenuActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Menu>> call, Throwable t) {

            }
        });
    }
}