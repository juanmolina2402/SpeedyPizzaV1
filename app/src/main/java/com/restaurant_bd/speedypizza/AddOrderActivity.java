package com.restaurant_bd.speedypizza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.restaurant_bd.speedypizza.Adapters.MenuDialog;
import com.restaurant_bd.speedypizza.Adapters.OrderAdapter;
import com.restaurant_bd.speedypizza.Adapters.OrderDialog;
import com.restaurant_bd.speedypizza.Models.Menu;
import com.restaurant_bd.speedypizza.Models.Mesa;
import com.restaurant_bd.speedypizza.Services.MesaAdapter;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddOrderActivity extends AppCompatActivity implements OrderDialog.RefreshList{
    private List<Mesa> listaMesa;
    private List<Menu> listaMenu;
    private Mesa mSelected;
    private AutoCompleteTextView textView;
    private ImageView bt_aceptar;
    private RecyclerView recyclerView;
    private TextView tvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        tvTotal = findViewById(R.id.tv_total);
        recyclerView = findViewById(R.id.rvPedidos);

        ///GET
        Call<List<Mesa>> callMesa = MesaAdapter.getApiServiceMesa().getMesa();
        callMesa.enqueue(new Callback<List<Mesa>>() {
            @Override
            public void onResponse(@NonNull Call<List<Mesa>> call, @NonNull Response<List<Mesa>> response) {
                if(response.isSuccessful()){
                    ///Obtenemos la respuesta del servidor
                    listaMesa = response.body();
                    ArrayAdapter<Mesa> adapter = new ArrayAdapter<>(AddOrderActivity.this, android.R.layout.simple_dropdown_item_1line, listaMesa);
                    textView = findViewById(R.id.menu);
                    textView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    textView.setOnItemClickListener((arg0, arg1, arg2, arg3) -> {
                        mSelected = (Mesa) arg0.getAdapter().getItem(arg2);
                        Toast.makeText(AddOrderActivity.this, "Clicked " + arg2 + " codigo: " + mSelected.getId(), Toast.LENGTH_SHORT).show();

                    });
                }else{
                    Toast.makeText(AddOrderActivity.this, "Error: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Mesa>> call, @NonNull Throwable t) {
                Toast.makeText(AddOrderActivity.this, "" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        ImageView btnAgregar  = findViewById(R.id.btn_agregar);
        btnAgregar.setOnClickListener(v ->
                startActivityForResult(new Intent(AddOrderActivity.this, CategoryActivity.class), 1)
        );

        ImageView btnLimpiar  = findViewById(R.id.btn_clear);
        btnLimpiar.setOnClickListener(v -> {
            MenuDialog.listaTemporal = new ArrayList<>();
            MenuDialog.total = 0;
            updateList();
        });

       /* ImageView btnAceptar  = findViewById(R.id.btn_aceptar);
        btnAceptar.setOnClickListener(v -> {
            finish();
        });*/
    }

    public void loadData(){
        listaMenu = MenuDialog.listaTemporal;
        String total = String.valueOf(MenuDialog.total);
        tvTotal.setText(total);
    }

    public void updateList(){
        loadData();
        OrderAdapter adapter = new OrderAdapter(this.listaMenu, AddOrderActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            updateList();
        }
    }

    @Override
    public void finish() {
        MenuDialog.listaTemporal = new ArrayList<>();
        MenuDialog.total = 0;
        super.finish();
    }

    @Override
    public void result(boolean r) {
        if(r){
            updateList();
        }
    }
}