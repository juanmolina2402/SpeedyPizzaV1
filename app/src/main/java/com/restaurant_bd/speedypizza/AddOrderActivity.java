package com.restaurant_bd.speedypizza;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

public class AddOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        String[] option = {"Mesa 1", "Mesa 2", "Mesa 3", "Mesa 4", "Mesa 5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, option);
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.menu);
        textView.setAdapter(adapter);
    }

}