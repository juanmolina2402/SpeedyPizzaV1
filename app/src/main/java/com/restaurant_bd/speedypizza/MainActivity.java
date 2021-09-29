package com.restaurant_bd.speedypizza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton btnAcceder  = findViewById(R.id.fab);
        btnAcceder.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddOrderActivity.class));
        });
    }
}