package com.restaurant_bd.speedypizza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import com.restaurant_bd.speedypizza.interfaces.MesaService;
import com.restaurant_bd.speedypizza.model.Mesa;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddOrderActivity extends AppCompatActivity {
    private final String baseUrl = "https://restaurant-bd.herokuapp.com/";
    private List<Mesa> listaMesa;
    private Mesa mSelected;
    private AutoCompleteTextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MesaService mesaService = retrofit.create(MesaService.class);
        Call<List<Mesa>> lista = mesaService.getMesa();
        lista.enqueue(new Callback<List<Mesa>>() {
            @Override
            public void onResponse(Call<List<Mesa>> call, Response<List<Mesa>> response) {
                if(response.isSuccessful()){
                    listaMesa = response.body();
                    ArrayAdapter<Mesa> adapter = new ArrayAdapter<Mesa>(AddOrderActivity.this, android.R.layout.simple_dropdown_item_1line, listaMesa);
                    textView = (AutoCompleteTextView) findViewById(R.id.menu);
                    textView.setAdapter(adapter);
                    textView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                            mSelected = (Mesa) arg0.getAdapter().getItem(arg2);
                            Toast.makeText(AddOrderActivity.this, "Clicked " + arg2 + " codigo: " + mSelected.getCodigo(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Mesa>> call, @NonNull Throwable t) {

            }
        });

        Button btnAgregar  = findViewById(R.id.btn_agregar);
        btnAgregar.setOnClickListener(v -> {
            startActivity(new Intent(AddOrderActivity.this, CategoryActivity.class));
        });

        Button btnAceptar  = findViewById(R.id.btn_aceptar);
        btnAceptar.setOnClickListener(v -> {
            finish();
        });
    }
}