package com.restaurant_bd.speedypizza;

import static com.restaurant_bd.speedypizza.Adapters.MenuAdapter.listaTemporal;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.restaurant_bd.speedypizza.Models.Categoria;
import com.restaurant_bd.speedypizza.Services.CategoriaAdapter;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {
    private Categoria cSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ///Haciendo la llamada del endpoint getCategoria
        Call<List<Categoria>> lista = CategoriaAdapter.getApiServiceCategorias().getCategoria();
        lista.enqueue(getLstCategorias);
    }

    ///Variable asignada a la lista de categorias
    private final Callback<List<Categoria>> getLstCategorias = new Callback<List<Categoria>>() {
        @Override
        public void onResponse(@NonNull Call<List<Categoria>> call, Response<List<Categoria>> response) {
            if(response.isSuccessful()){
                List<Categoria> listaCategoria = response.body();
                ArrayAdapter<Categoria> adapter = new ArrayAdapter<>(CategoryActivity.this, R.layout.custom_category, listaCategoria);
                ListView listView = findViewById(R.id.lv_categoria);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener((arg0, arg1, arg2, arg3) -> {
                    cSelected = (Categoria) arg0.getAdapter().getItem(arg2);
                    Intent i= new Intent(CategoryActivity.this, MenuActivity.class);
                    i.putExtra("Id", cSelected.getId().toString());
                    startActivity(i);
                });
            }
        }

        @Override
        public void onFailure(@NonNull Call<List<Categoria>> call, @NonNull Throwable t) {

        }
    };

    @Override
    public void finish() { //ENVIAR RESPUESTA A ORDER ACTIVITY
        Intent returnIntent = new Intent();
        try {
            if(listaTemporal.size() > 0){
                setResult(RESULT_OK, returnIntent);
            }
        }catch (Exception e){
            setResult(RESULT_CANCELED, returnIntent);
        }
        super.finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}