package com.restaurant_bd.speedypizza.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.restaurant_bd.speedypizza.Models.Menu;
import com.restaurant_bd.speedypizza.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MenuDialog {
    public static ArrayList<Menu> listaTemporal;
    public static double total = 0;

    public MenuDialog(Context context, long id, String nombre, String precio){
        if(listaTemporal == null){
            listaTemporal = new ArrayList<>();
        }

        final Dialog dialog = new Dialog(context);
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.setContentView(R.layout.dialog_menu);

        final TextView tvProducto = dialog.findViewById(R.id.tvProducto);
        tvProducto.setText(nombre);
        final EditText edtCantidad = dialog.findViewById(R.id.edtCantidad);
        Button btnCancelar = dialog.findViewById(R.id.btnCancelar);
        Button btnAceptar = dialog.findViewById(R.id.btnAceptar);

        btnCancelar.setOnClickListener(view -> dialog.dismiss());
        btnAceptar.setOnClickListener(view -> {
            if(!edtCantidad.getText().toString().isEmpty()){
                double p = Double.parseDouble(precio);
                int c = Integer.parseInt(edtCantidad.getText().toString());
                DecimalFormat f = new DecimalFormat("#.00");
                String t = f.format(p * c);
                total += Double.parseDouble(t);
                listaTemporal.add(new Menu(id, nombre, precio, edtCantidad.getText().toString()));
                Toast.makeText(context, "Agregado", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }else{
                Toast.makeText(context, "Cantidad inválida", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }
}
