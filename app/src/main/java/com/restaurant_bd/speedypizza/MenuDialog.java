package com.restaurant_bd.speedypizza;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class MenuDialog {

    public interface ProcesarCantidad{
        void ResultadoCantidad(String cantidad);
    }

    private ProcesarCantidad interfaz;

    public MenuDialog(Context context, ProcesarCantidad actividad){
        interfaz = actividad;
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.setContentView(R.layout.dialog_menu);

        final EditText edtCantidad = dialog.findViewById(R.id.edtCantidad);
        Button btnCancelar = dialog.findViewById(R.id.btnCancelar);
        Button btnAceptar = dialog.findViewById(R.id.btnAceptar);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaz.ResultadoCantidad(edtCantidad.getText().toString());
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
