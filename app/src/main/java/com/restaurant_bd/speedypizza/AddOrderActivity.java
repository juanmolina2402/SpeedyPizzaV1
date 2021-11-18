package com.restaurant_bd.speedypizza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;
import com.restaurant_bd.speedypizza.Adapters.MenuDialog;
import com.restaurant_bd.speedypizza.Adapters.OrderAdapter;
import com.restaurant_bd.speedypizza.Models.Menu;
import com.restaurant_bd.speedypizza.Models.Mesa;
import com.restaurant_bd.speedypizza.Services.MesaAdapter;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddOrderActivity extends AppCompatActivity {
    private List<Mesa> listaMesa;
    private List<Menu> listaMenu;
    private Mesa mSelected;
    private AutoCompleteTextView textView;
    private ImageView bt_aceptar;
    private RecyclerView recyclerView;
    private int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        recyclerView = findViewById(R.id.rvPedidos);

        ///GET
        Call<List<Mesa>> callMesa = MesaAdapter.getApiServiceMesa().getMesa();
        callMesa.enqueue(new Callback<List<Mesa>>() {
            @Override
            public void onResponse(Call<List<Mesa>> call, Response<List<Mesa>> response) {
                if(response.isSuccessful()){
                    ///Obtenemos la respuesta del servidor
                    listaMesa = response.body();
                    ArrayAdapter<Mesa> adapter = new ArrayAdapter<Mesa>(AddOrderActivity.this, android.R.layout.simple_dropdown_item_1line, listaMesa);
                    textView = (AutoCompleteTextView) findViewById(R.id.menu);
                    textView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    textView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                            mSelected = (Mesa) arg0.getAdapter().getItem(arg2);
                            Toast.makeText(AddOrderActivity.this, "Clicked " + arg2 + " codigo: " + mSelected.getId(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }else{
                    Toast.makeText(AddOrderActivity.this, "Error: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Mesa>> call, @NonNull Throwable t) {
                Toast.makeText(AddOrderActivity.this, "" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        ImageView btnAgregar  = findViewById(R.id.btn_agregar);
        btnAgregar.setOnClickListener(v -> {
            startActivityForResult(new Intent(AddOrderActivity.this, CategoryActivity.class), REQUEST_CODE);
        });

        ImageView btnLimpiar  = findViewById(R.id.btn_clear);
        btnLimpiar.setOnClickListener(v -> {

        });

       /* ImageView btnAceptar  = findViewById(R.id.btn_aceptar);
        btnAceptar.setOnClickListener(v -> {
            finish();
        });*/
    }

    public void updateList(){
        listaMenu = MenuDialog.listaTemporal;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AddOrderActivity.this));
        recyclerView.setAdapter(new OrderAdapter(listaMenu, AddOrderActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            updateList();
        }
    }
}