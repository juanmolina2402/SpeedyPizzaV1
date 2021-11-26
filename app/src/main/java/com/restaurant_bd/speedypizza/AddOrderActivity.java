package com.restaurant_bd.speedypizza;

import static com.restaurant_bd.speedypizza.Adapters.MenuAdapter.listaTemporal;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.restaurant_bd.speedypizza.Adapters.MenuAdapter;
import com.restaurant_bd.speedypizza.Adapters.OrderAdapter;
import com.restaurant_bd.speedypizza.Adapters.OrderDialog;
import com.restaurant_bd.speedypizza.Models.Detalle_Pedido;
import com.restaurant_bd.speedypizza.Models.Empleado;
import com.restaurant_bd.speedypizza.Models.Menu;
import com.restaurant_bd.speedypizza.Models.Mesa;
import com.restaurant_bd.speedypizza.Models.Pedido;
import com.restaurant_bd.speedypizza.Services.MesaAdapter;
import com.restaurant_bd.speedypizza.Services.PedidoAdapter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddOrderActivity extends AppCompatActivity implements OrderDialog.RefreshList{
    private List<Mesa> listaMesa;
    private List<Menu> listaMenu;
    private Mesa mSelected;
    private AutoCompleteTextView tvMesa;
    private ImageView bt_aceptar;
    private RecyclerView rvPedidos;
    private TextView tvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        tvTotal = findViewById(R.id.tv_total);
        rvPedidos = findViewById(R.id.rvPedidos);
        FloatingActionButton btnAgregar  = findViewById(R.id.fab2);
        BottomAppBar bar = findViewById(R.id.bottomAppBar);

        btnAgregar.setOnClickListener(v -> startActivityForResult(new Intent(AddOrderActivity.this, CategoryActivity.class), 1));
        setSupportActionBar(bar);

        Call<List<Mesa>> callMesa = MesaAdapter.getApiServiceMesa().getMesa();
        callMesa.enqueue(getMesasCallback);


        ///POST Pedidos
        Pedido pedido = new Pedido();
        Detalle_Pedido detalle_pedido = new Detalle_Pedido();
        ////Usuario usuario = new Usuario("user" , "user");
        Empleado empleado = new Empleado(1);
        pedido.setEmpleado(empleado);
        pedido.setMesa(new Mesa(1));
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String salida = df.format(new Date());
        pedido.setFecha_pedido(salida);
        pedido.setObservacion("Pedido pendiente");
        pedido.setEstado("PENDIENTE");
        pedido.setCliente("Chepe");

        ///LISTA DETALLE PEDIDO
        ///detalle_pedido.setIDPEDIDO(pedido);
        detalle_pedido.setMenu(new Menu(5));
        detalle_pedido.setCantidad(5);

        List<Detalle_Pedido> lstDetallePedidos = new ArrayList<Detalle_Pedido>();
        lstDetallePedidos.add(detalle_pedido);

        pedido.setDetalle_pedido(lstDetallePedidos);

        Call<Pedido> postPedidos = PedidoAdapter.getApiServicePedido().setPedido(pedido);
        postPedidos.enqueue(new Callback<Pedido>() {
            @Override
            public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                if(response.isSuccessful()){
                    Pedido pedido2 = response.body();

                    bt_aceptar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(AddOrderActivity.this, "Se agrego un nuevo pedido", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else{
                    Toast.makeText(AddOrderActivity.this, "Error: " + response.code(), Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(Call<Pedido> call, Throwable t) {
                Toast.makeText(AddOrderActivity.this, "" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_app_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btn_clear) {
            clear();
            updateList();
        }else{
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private final Callback<List<Mesa>> getMesasCallback = new Callback<List<Mesa>>() {
        @Override
        public void onResponse(@NonNull Call<List<Mesa>> call, Response<List<Mesa>> response) {
            if(response.isSuccessful()){
                listaMesa = response.body();
                ArrayAdapter<Mesa> adapter = new ArrayAdapter<>(AddOrderActivity.this, android.R.layout.simple_dropdown_item_1line, listaMesa);
                tvMesa = findViewById(R.id.menu);
                tvMesa.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                tvMesa.setOnItemClickListener((arg0, arg1, arg2, arg3) -> {
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
    };

    private void loadData(){ //ASIGNAR DATOS DE LA LISTA TEMPORAL
        listaMenu = listaTemporal;
        tvTotal.setText(String.valueOf(MenuAdapter.total));
    }

    private void updateList(){ //ACTUALIZAR RECYCLERVIEW
        loadData();
        OrderAdapter adapter = new OrderAdapter(this.listaMenu, AddOrderActivity.this);
        rvPedidos.setLayoutManager(new LinearLayoutManager(this));
        rvPedidos.hasFixedSize();
        rvPedidos.setAdapter(adapter);
    }

    @Override //ESPERAMOS RESPUESTA DEL ACTIVITY CATEGORIA PARA ACTUALIZAR
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            updateList();
        }
    }

    @Override //ACTUALIZAR RECYCLERVIEW SI SE HIZO UNA MODIFICACION O ELIMINACION DE LA LISTA TEMPORAL
    public void result(boolean r) {
        if(r){
            updateList();
        }
    }

    private void clear(){ //RESTABLECER VALORES
        listaTemporal = new ArrayList<>();
        MenuAdapter.total = 0;
    }

    @Override
    public void finish() { //AL SALIR, ME RESETEE TODOS LOS VALORES
        clear();
        super.finish();
    }
}