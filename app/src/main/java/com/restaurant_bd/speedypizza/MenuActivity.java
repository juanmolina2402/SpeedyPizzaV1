package com.restaurant_bd.speedypizza;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.restaurant_bd.speedypizza.Adapters.MenuAdapter;
import com.restaurant_bd.speedypizza.Models.Menu;
import com.restaurant_bd.speedypizza.Services.APIServices;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuActivity extends AppCompatActivity {
    private final String baseUrl = "https://restaurant-bd.herokuapp.com/";
    private List<Menu> listaMenu;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        id = getIntent().getStringExtra("Id");
        buscar();
    }

    public void buscar ()
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIServices menuService = retrofit.create(APIServices.class);
        Call<List<Menu>> lista = menuService.getMenu(Integer.parseInt(id));
        lista.enqueue(new Callback<List<Menu>>() {
            @Override
            public void onResponse(Call<List<Menu>> call, Response<List<Menu>> response) {
                try {
                    if(response.isSuccessful())
                    {
                        listaMenu = response.body();
                        RecyclerView recyclerView = findViewById(R.id.rvMenu);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MenuActivity.this));
                        recyclerView.setAdapter(new MenuAdapter(listaMenu, MenuActivity.this));
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}