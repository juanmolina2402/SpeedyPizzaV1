package com.restaurant_bd.speedypizza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.restaurant_bd.speedypizza.Models.Empleado;
import com.restaurant_bd.speedypizza.Models.Usuario;
import com.restaurant_bd.speedypizza.Services.EmpleadoAdapter;
import com.restaurant_bd.speedypizza.Services.UsuarioAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    public static final String ID_EMPLOYEE = "employeeId";
    private EditText ed_username;
    private EditText ed_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ed_username = findViewById(R.id.edt_user);
        ed_password = findViewById(R.id.edt_password);
        Button btnlogin = findViewById(R.id.btn_login);



        btnlogin.setOnClickListener(v -> {

            /////////////////////LOGIN USUARIO//////////////////////////////////////
            Usuario usuario = new Usuario();
            usuario.setUsername(ed_username.getText().toString());
            usuario.setPassword(ed_password.getText().toString());

            Call<Usuario> getUsuarioForLogin = UsuarioAdapter.getApiServiceUsuario().getUserLogin(usuario);
            getUsuarioForLogin.enqueue(getUsuarioPorLogin);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ed_username.setText("");
        ed_password.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isDestroyed();
    }

    ///Variable que se le asigna la respuesta de la solicitud
    private final Callback<Usuario> getUsuarioPorLogin = new Callback<Usuario>() {
        @Override
        public void onResponse(@NonNull Call<Usuario> call, Response<Usuario> response) {
            if (response.isSuccessful()) {
                Usuario usuarioAux = response.body();
                long id_usuario = usuarioAux.getId();
                Toast.makeText(LoginActivity.this, "Usuario correcto", Toast.LENGTH_LONG).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));

                ///Obteniendo el empleado según el id del usuario
                Call<Empleado> getEmpleadoForId = EmpleadoAdapter.getApiServiceEmpleados().getEmpleadoForLogin(id_usuario);
                getEmpleadoForId.enqueue(getEmpleadoPorLogin);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Error: " + response.code(), Toast.LENGTH_LONG).show();
            }
        }
        @Override
        public void onFailure(@NonNull Call<Usuario> call, Throwable t) {
            Toast.makeText(LoginActivity.this, "Hubo un error: " + t.getMessage(), Toast.LENGTH_LONG).show();
        }
    };


    ///Variable que se le asigna la llamada del endpoint de obtener un empleado según el id de usuario
    private final Callback<Empleado> getEmpleadoPorLogin = new Callback<Empleado>() {
        @Override
        public void onResponse(@NonNull Call<Empleado> call, Response<Empleado> response) {
            if(response.isSuccessful()){
                Empleado empleadoAux = response.body();
                SharedPreferences sharedPreferences = getSharedPreferences(ID_EMPLOYEE, MODE_PRIVATE);
                SharedPreferences.Editor editorConfig = sharedPreferences.edit();

                    editorConfig.putString("id_employee", String.valueOf(empleadoAux.getId()));
                    editorConfig.apply();
                    Toast.makeText(LoginActivity.this, "Se agregó el empleado", Toast.LENGTH_LONG).show();

            }
            else{
                Toast.makeText(LoginActivity.this, "Ocurrió un error", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(@NonNull Call<Empleado> call, Throwable t) {
            Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
        }
    };
}

