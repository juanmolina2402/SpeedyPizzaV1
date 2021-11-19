package com.restaurant_bd.speedypizza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.restaurant_bd.speedypizza.Adapters.MenuDialog;
import com.restaurant_bd.speedypizza.Models.Categoria;
import com.restaurant_bd.speedypizza.Services.APIServices;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryActivity extends AppCompatActivity {
    private final String baseUrl = "https://restaurant-bd.herokuapp.com/";
    private List<Categoria> listaCategoria;
    private Categoria cSelected;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIServices categoriaService = retrofit.create(APIServices.class);
        Call<List<Categoria>> lista = categoriaService.getCategoria();
        lista.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if(response.isSuccessful()){
                    listaCategoria = response.body();
                    ArrayAdapter<Categoria> adapter = new ArrayAdapter<>(CategoryActivity.this, R.layout.custom_category, listaCategoria);
                    listView = findViewById(R.id.lv_categoria);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                            cSelected = (Categoria) arg0.getAdapter().getItem(arg2);
                            Intent i= new Intent(CategoryActivity.this, MenuActivity.class);
                            i.putExtra("Id", cSelected.getId().toString());
                            startActivity(i);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void finish() {
        boolean b = false;
        Intent returnIntent = new Intent();
        try {
            if(MenuDialog.listaTemporal.size() > 0){
                setResult(RESULT_OK, returnIntent);
            }
        }catch (Exception e){
            setResult(RESULT_CANCELED, returnIntent);
        }
        super.finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}