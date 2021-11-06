package com.restaurant_bd.speedypizza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.restaurant_bd.speedypizza.interfaces.MenuService;
import com.restaurant_bd.speedypizza.model.Menu;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuActivity extends AppCompatActivity {
    private final String baseUrl = "https://restaurant-bd.herokuapp.com/";
    EditText id;
    TextView firtname;
    TextView lastname;
    TextView email;
    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        id= (EditText) findViewById(R.id.id);
        firtname=(TextView)findViewById(R.id.firstname);
        lastname=(TextView)findViewById(R.id.lastname);
        email=(TextView)findViewById(R.id.email);
        imagen = findViewById(R.id.ivImagen);
    }

    public void buscar (View view)
    {
        Retrofit t = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MenuService api = t.create(MenuService.class);
        Call<Menu> call= api.find(Integer.parseInt(id.getText().toString()));//QUEMADO
        call.enqueue(new Callback<Menu>() {
            @Override
            public void onResponse(Call<Menu> call, Response<Menu> response) {

                try {
                    if(response.isSuccessful())
                    {
                        Menu m = response.body();
                        firtname.setText(m.getNombre());
                        lastname.setText(m.getDescripcion());
                        email.setText(m.getPrecio());

                        String base64String = "data:image/png;base64," + m.getImagen();
                        String base64Image = base64String.split(",")[1];

                        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                        imagen.setImageBitmap(decodedByte);
                    }

                }catch (Exception e)
                {
                    Toast.makeText(MenuActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Menu> call, Throwable t) {

            }
        });
    }
}