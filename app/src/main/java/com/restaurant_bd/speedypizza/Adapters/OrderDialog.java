package com.restaurant_bd.speedypizza.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.restaurant_bd.speedypizza.R;

public class OrderDialog {

    public OrderDialog(Context context, long id, String nombre, String precio, int cantidad, boolean b){

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.setContentView(R.layout.dialog_menu);

        final TextView tvProducto = dialog.findViewById(R.id.tvProducto);
        final EditText edtCantidad = dialog.findViewById(R.id.edtCantidad);
        final TextView tvTitulo = dialog.findViewById(R.id.tvTitleDialog);
        Button btnCancelar = dialog.findViewById(R.id.btnCancelar);
        Button btnAceptar = dialog.findViewById(R.id.btnAceptar);

        if(b){
            tvTitulo.setText("¿Eliminar menu?");
            edtCantidad.setVisibility(View.INVISIBLE);
            btnAceptar.setText("Eliminar");
        }else{
            tvTitulo.setText("¿Modificar cantidad?");
            edtCantidad.setText(cantidad);
            btnAceptar.setText("Modificar");
        }
        tvProducto.setText(nombre);

        btnCancelar.setOnClickListener(view -> dialog.dismiss());
        btnAceptar.setOnClickListener(view -> {
            if(b){
                //listaTemporal.add(new Menu(id, nombre, null, precio, edtCantidad.getText().toString()));
                Toast.makeText(context, "Se eliminó", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }else{
                Toast.makeText(context, "Cantidad inválida", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }
}
