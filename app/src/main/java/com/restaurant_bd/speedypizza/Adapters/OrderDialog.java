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

import com.restaurant_bd.speedypizza.AddOrderActivity;
import com.restaurant_bd.speedypizza.R;

public class OrderDialog {

    public OrderDialog(Context context, long id, String nombre, String precio, String cantidad, int position, boolean b){

        final Dialog dialog = new Dialog(context);
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.setContentView(R.layout.dialog_menu);

        final TextView tvProducto = dialog.findViewById(R.id.tvProducto);
        final EditText edtCantidad = dialog.findViewById(R.id.edtCantidad);
        final TextView tvTitulo = dialog.findViewById(R.id.tvTitleDialog);
        Button btnCancelar = dialog.findViewById(R.id.btnCancelar);
        Button btnAceptar = dialog.findViewById(R.id.btnAceptar);
        Double total = MenuDialog.total;

        if(b){
            tvTitulo.setText(R.string.itm_dialog_title2);
            edtCantidad.setVisibility(View.GONE);
            btnAceptar.setText(R.string.btn_eliminar);
        }else{
            tvTitulo.setText(R.string.itm_dialog_title3);
            edtCantidad.setText(cantidad);
            btnAceptar.setText(R.string.btn_modificar);
        }
        tvProducto.setText(nombre);

        btnCancelar.setOnClickListener(view -> dialog.dismiss());
        btnAceptar.setOnClickListener(view -> {
            if(b){
                MenuDialog.total = total - (Double.parseDouble(precio) * Integer.parseInt(cantidad));;
                MenuDialog.listaTemporal.remove(position);
                Toast.makeText(context, "Se eliminó", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }else{
                if(!edtCantidad.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Se modificó", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }else{
                    Toast.makeText(context, "Cantidad inválida", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }
}
