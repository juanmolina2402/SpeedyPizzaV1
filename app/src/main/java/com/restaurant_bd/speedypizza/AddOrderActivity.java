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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        String[] option = {"Mesa 1", "Mesa 2", "Mesa 3", "Mesa 4", "Mesa 5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, option);
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.menu);
        textView.setAdapter(adapter);

        Button btnAgregar  = findViewById(R.id.btn_agregar);
        btnAgregar.setOnClickListener(v -> {
            startActivity(new Intent(AddOrderActivity.this, MenuActivity.class));
        });

        Button btnAceptar  = findViewById(R.id.btn_aceptar);
        btnAceptar.setOnClickListener(v -> {
            finish();
        });
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