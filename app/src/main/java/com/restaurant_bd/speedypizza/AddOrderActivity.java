package com.restaurant_bd.speedypizza;

import static com.restaurant_bd.speedypizza.Adapters.MenuAdapter.listaTemporal;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
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
    private List<Menu> listaMenu;
    private Mesa mSelected;
    private RecyclerView rvPedidos;
    private TextView tvTotal;
    private EditText edtCliente;
    private int mesa;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        edtCliente = findViewById(R.id.edtCliente);
        tvTotal = findViewById(R.id.tv_total);
        rvPedidos = findViewById(R.id.rvPedidos);
        FloatingActionButton btnAgregar  = findViewById(R.id.fab2);
        BottomAppBar bar = findViewById(R.id.bottomAppBar);

        btnAgregar.setOnClickListener(v -> startActivityForResult(new Intent(AddOrderActivity.this, CategoryActivity.class), 1));
        setSupportActionBar(bar);

        Call<List<Mesa>> callMesa = MesaAdapter.getApiServiceMesa().getMesa();
        callMesa.enqueue(getMesasCallback);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_app_bar, menu);
        return true;
    }

    private void alertDialog(boolean b){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddOrderActivity.this);
        builder.setTitle("Confirmación");

        if(b){
            builder.setMessage("¿Desea eliminar toda la lista de menus?");
            builder.setPositiveButton("Aceptar", (dialog, which) -> {
                clear();
                updateList();
            });
        }else{
            builder.setMessage("¿Desea enviar el pedido?");
            builder.setPositiveButton("Aceptar", (dialog, which) -> {
                Aceptar();
                finish();
            });
        }
        builder.setNegativeButton("Cancelar",null );
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btn_clear) {
            if(listaTemporal != null && !listaTemporal.isEmpty()){
                alertDialog(true);
            }
        }else{
            if(!edtCliente.getText().toString().isEmpty() && listaTemporal != null && !listaTemporal.isEmpty()){
                alertDialog(false);
            }else{
                Toast.makeText(AddOrderActivity.this, "Campos vacìos", Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private final Callback<List<Mesa>> getMesasCallback = new Callback<List<Mesa>>() {
        @Override
        public void onResponse(@NonNull Call<List<Mesa>> call, Response<List<Mesa>> response) {
            if(response.isSuccessful()){
                List<Mesa> listaMesa = response.body();
                ArrayAdapter<Mesa> adapter = new ArrayAdapter<>(AddOrderActivity.this, android.R.layout.simple_dropdown_item_1line, listaMesa);
                AutoCompleteTextView tvMesa = findViewById(R.id.menu);
                tvMesa.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                tvMesa.setOnItemClickListener((arg0, arg1, arg2, arg3) -> {
                    mSelected = (Mesa) arg0.getAdapter().getItem(arg2);
                    mesa = mSelected.getId();
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

    public void Aceptar(){
        ///POST Pedidos
        Pedido pedido = new Pedido();

        ////Usuario usuario = new Usuario("user" , "user");
        this.sharedPreferences = getSharedPreferences(LoginActivity.ID_EMPLOYEE, MODE_PRIVATE);

        Empleado empleado = new Empleado(Long.parseLong(sharedPreferences.getString("id_employee", "0")));
        pedido.setEmpleado(empleado);
        pedido.setMesa(new Mesa(mesa));
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String salida = df.format(new Date());
        pedido.setFecha_pedido(salida);
        pedido.setObservacion("");
        pedido.setEstado("PENDIENTE");
        pedido.setCliente(edtCliente.getText().toString());

        List<Detalle_Pedido> lstDetallePedidos = new ArrayList<>();
        for (Menu m : listaTemporal) {
            Detalle_Pedido detalle_pedido = new Detalle_Pedido();
            detalle_pedido.setMenu(new Menu(m.getId()));
            detalle_pedido.setCantidad(Long.parseLong(m.getCantidad()));
            lstDetallePedidos.add(detalle_pedido);
        }

        pedido.setDetalle_pedido(lstDetallePedidos);

        Call<Pedido> postPedidos = PedidoAdapter.getApiServicePedido().setPedido(pedido);
        postPedidos.enqueue(new Callback<Pedido>() {
            @Override
            public void onResponse(@NonNull Call<Pedido> call, @NonNull Response<Pedido> response) {
                if(response.isSuccessful()){
                    Toast.makeText(AddOrderActivity.this, "Se agregó un nuevo pedido", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(AddOrderActivity.this, "Error: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<Pedido> call, @NonNull Throwable t) {
                //Toast.makeText(AddOrderActivity.this, "Error, por favor comuniquese con el soporte", Toast.LENGTH_LONG).show();
            }
        });
    }

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
    public void finish() { //ENVIAR RESPUESTA A MAIN ACTIVITY
        Intent returnIntent = new Intent();
        try {
            if(listaTemporal.size() > 0){
                setResult(RESULT_OK, returnIntent);
            }
        }catch (Exception e){
            setResult(RESULT_CANCELED, returnIntent);
        }
        clear();
        super.finish();
    }
}