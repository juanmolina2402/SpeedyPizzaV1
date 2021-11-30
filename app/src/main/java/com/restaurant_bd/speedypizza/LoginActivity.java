package com.restaurant_bd.speedypizza;

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
    private SharedPreferences sharedPreferences;
    public static final String ID_EMPLOYEE = "employeeId";
    private EditText ed_username;
    private EditText ed_password;
    private Button btnlogin;

    private Usuario usuarioAux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ed_username = (EditText) findViewById(R.id.edt_user);
        ed_password = (EditText) findViewById(R.id.edt_password);
        btnlogin = (Button) findViewById(R.id.btn_login);



        btnlogin.setOnClickListener(v -> {

            /////////////////////LOGIN USUARIO//////////////////////////////////////
            Usuario usuario = new Usuario();
            usuario.setUsername(ed_username.getText().toString());
            usuario.setPassword(ed_password.getText().toString());

            Call<Usuario> getUsuarioForLogin = UsuarioAdapter.getApiServiceUsuario().getUserLogin(usuario);
            getUsuarioForLogin.enqueue(getUsuarioPorLogin);
        });
    }

    ///Variable que se le asigna la respuesta de la solicitud
    private final Callback<Usuario> getUsuarioPorLogin = new Callback<Usuario>() {
        @Override
        public void onResponse(Call<Usuario> call, Response<Usuario> response) {
            if (response.isSuccessful()) {
                usuarioAux = response.body();
                long id_usuario = usuarioAux.getId();
                Toast.makeText(LoginActivity.this, "Usuario correcto", Toast.LENGTH_LONG).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));

                Call<Empleado> getEmpleadoForId = EmpleadoAdapter.getApiServiceEmpleados().getEmpleadoForLogin(id_usuario);
                getEmpleadoForId.enqueue(new Callback<Empleado>() {
                    @Override
                    public void onResponse(Call<Empleado> call, Response<Empleado> response) {
                        if(response.isSuccessful()){
                            Empleado empleadoAux = response.body();
                            sharedPreferences = getSharedPreferences(ID_EMPLOYEE, MODE_PRIVATE);
                            SharedPreferences.Editor editorConfig = sharedPreferences.edit();

                            editorConfig.putString("id_employee", String.valueOf(empleadoAux.getId()));
                            editorConfig.commit();
                            Toast.makeText(LoginActivity.this, "Se agregó el empleado", Toast.LENGTH_LONG).show();

                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Ocurrió un error", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Empleado> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Toast.makeText(LoginActivity.this, "Error: " + response.code(), Toast.LENGTH_LONG).show();
            }
        }
        @Override
        public void onFailure(Call<Usuario> call, Throwable t) {
            Toast.makeText(LoginActivity.this, "Hubo un error: " + t.getMessage(), Toast.LENGTH_LONG).show();
        }
    };
}
