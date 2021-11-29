package com.restaurant_bd.speedypizza;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.restaurant_bd.speedypizza.Adapters.PedidoAdapter;
import com.restaurant_bd.speedypizza.Adapters.PedidoDialog;
import com.restaurant_bd.speedypizza.Models.Pedido;
import com.restaurant_bd.speedypizza.Services.APIServices;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements PedidoDialog.UpdateOrder {
    private final String baseUrl = "https://restaurant-bd.herokuapp.com/";
    private List<Pedido> listaPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = findViewById(R.id.toolbar);
        FloatingActionButton btnAcceder  = findViewById(R.id.fab);

        setSupportActionBar(toolbar);
        btnAcceder.setOnClickListener(v -> startActivityForResult(new Intent(MainActivity.this, AddOrderActivity.class), 1));
        llamarPedidos();
    }

    public void llamarPedidos(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIServices pedidoService = retrofit.create(APIServices.class);
        Call<List<Pedido>> lista = pedidoService.getListPedidos();
        lista.enqueue(new Callback<List<Pedido>>() {
            @Override
            public void onResponse(@NonNull Call<List<Pedido>> call, @NonNull Response<List<Pedido>> response) {
                try {
                    if(response.isSuccessful())
                    {
                        listaPedido = response.body();
                        RecyclerView recyclerView = findViewById(R.id.rvListaPedidos);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        recyclerView.setAdapter(new PedidoAdapter(listaPedido, MainActivity.this));
                        recyclerView.getViewTreeObserver().addOnPreDrawListener(
                                new ViewTreeObserver.OnPreDrawListener() {
                                    @Override
                                    public boolean onPreDraw() {
                                        recyclerView.getViewTreeObserver().removeOnPreDrawListener(this);

                                        for (int i = 0; i < recyclerView.getChildCount(); i++) {
                                            View v = recyclerView.getChildAt(i);
                                            v.setAlpha(0.0f);
                                            v.animate().alpha(1.0f)
                                                    .setDuration(300)
                                                    .setStartDelay(i * 50L)
                                                    .start();
                                        }
                                        return true;
                                    }
                                });
                    }

                }catch (Exception e)
                {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Pedido>> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.itm_update) {
            llamarPedidos();
        }else if (id == R.id.itm_about) {
            startActivity(new Intent(MainActivity.this, AboutOfActivity.class));
        }else{
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void modificarPedido(long id, String e){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Pedido p = new Pedido();
        p.setEstado(e);
        APIServices pedidoService = retrofit.create(APIServices.class);
        Call<Pedido> putPedido = pedidoService.putPedido(id, p);
        putPedido.enqueue(new Callback<Pedido>() {
            @Override
            public void onResponse(@NonNull Call<Pedido> call, @NonNull Response<Pedido> response) {
                try {
                    if(response.isSuccessful())
                    {
                        Toast.makeText(MainActivity.this, "Se realizo con exito", Toast.LENGTH_SHORT).show();
                        llamarPedidos();
                    }

                }catch (Exception e)
                {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<Pedido> call, @NonNull Throwable t) {
            }
        });
    }

    @Override //ESPERAMOS RESPUESTA DEL ACTIVITY ADD ORDER PARA ACTUALIZAR
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            llamarPedidos();
        }
    }

    @Override
    public void result(boolean r, long id) {
        String e;
        if(r){
            e = "ENTREGADO";
        }else{
            e = "ANULADO";
        }
        modificarPedido(id, e);
    }
}