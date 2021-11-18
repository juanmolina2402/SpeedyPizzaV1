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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.restaurant_bd.speedypizza.Adapters.MenuDialog;
import com.restaurant_bd.speedypizza.Models.Menu;
import com.restaurant_bd.speedypizza.Models.Mesa;
import com.restaurant_bd.speedypizza.Services.MesaAdapter;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddOrderActivity extends AppCompatActivity {
    private List<Mesa> listaMesa;
    private Mesa mSelected;
    private AutoCompleteTextView textView;
    private ListView lvPedidos;
    private ImageView bt_aceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        lvPedidos = (ListView)findViewById(R.id.lv_pedidos);
        ArrayList<String> Lst= new ArrayList<String>();
        boolean b = false;
        try {
            if(MenuDialog.listaTemporal.size() > 0){
                b = true;
            }
        }catch (Exception e){
            b = false;
        }

        if(b){

            for(int i=0;i<MenuDialog.listaTemporal.size();i++)
            {
                String a=MenuDialog.listaTemporal.get(i).getNombre()+" / "+MenuDialog.listaTemporal.get(i).getPrecio() +" / "+MenuDialog.listaTemporal.get(i).getImagen();
                Lst.add(a);
            }

            ArrayAdapter<Menu> adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, Lst);
            lvPedidos.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else{
            Toast.makeText(this, "La lista está vacía", Toast.LENGTH_LONG).show();
        }

        ///POST
        Mesa m = new Mesa();
        m.setCodigo("Mesa6");
        bt_aceptar = (ImageView) findViewById(R.id.btn_aceptar);
        Call<Mesa> callPostMesa = MesaAdapter.getApiServiceMesa().setMesa(m);
        callPostMesa.enqueue(new Callback<Mesa>() {
            @Override
            public void onResponse(Call<Mesa> call, Response<Mesa> response) {
                if(response.isSuccessful()){
                    Mesa m2 = response.body();
                    bt_aceptar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(AddOrderActivity.this, "Se agrego una nueva mesa", Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Toast.makeText(AddOrderActivity.this, "Error: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Mesa> call, Throwable t) {
                Toast.makeText(AddOrderActivity.this, "" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


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
            startActivity(new Intent(AddOrderActivity.this, CategoryActivity.class));
        });

       /* ImageView btnAceptar  = findViewById(R.id.btn_aceptar);
        btnAceptar.setOnClickListener(v -> {
            finish();
        });*/
    }
}