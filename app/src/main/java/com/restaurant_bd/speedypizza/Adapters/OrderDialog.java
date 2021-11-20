package com.restaurant_bd.speedypizza.Adapters;

import static com.restaurant_bd.speedypizza.Adapters.MenuAdapter.listaTemporal;
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
import com.restaurant_bd.speedypizza.Models.Menu;
import com.restaurant_bd.speedypizza.R;
import java.text.DecimalFormat;

public class OrderDialog {
    private RefreshList interfaz;

    public interface RefreshList{
        void result(boolean r);
    }

    public OrderDialog(Context context, long id, String nombre, String precio, String cantidad, int position, boolean b){
        try {
            //PROPIEDADES DEL DIALOGO
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

            if(b){ //SI ES VERDADERO ENTONCES SE VA A ELIMINAR
                tvTitulo.setText(R.string.itm_dialog_title2);
                edtCantidad.setVisibility(View.GONE);
                btnAceptar.setText(R.string.btn_eliminar);
            }else{ //SE VA A MODIFICAR
                tvTitulo.setText(R.string.itm_dialog_title3);
                edtCantidad.setText(cantidad);
                btnAceptar.setText(R.string.btn_modificar);
            }

            tvProducto.setText(nombre);
            btnCancelar.setOnClickListener(view -> dialog.dismiss());
            btnAceptar.setOnClickListener(view -> {
                interfaz = (RefreshList) context;
                if(b){
                    listaTemporal.remove(position);
                    calculate(precio, cantidad, null, b);
                    Toast.makeText(context, "Eliminado", Toast.LENGTH_SHORT).show();
                    interfaz.result(true);
                    dialog.dismiss();
                }else{
                    if(!edtCantidad.getText().toString().isEmpty()) {
                        listaTemporal.set(position, new Menu(id, nombre, precio, edtCantidad.getText().toString()));
                        calculate(precio, cantidad, edtCantidad.getText().toString(), b);
                        Toast.makeText(context, "Modificado", Toast.LENGTH_SHORT).show();
                        interfaz.result(true);
                        dialog.dismiss();
                    }else{
                        Toast.makeText(context, "Cantidad inválida", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            dialog.show();
        }catch (Exception e){
            Toast.makeText(context, "Lo sentimos, error inesperado", Toast.LENGTH_SHORT).show();
        }
    }

    private void calculate(String precio, String cantidad, String c, boolean b){
        Double total = MenuAdapter.total;
        total = total - (Double.parseDouble(precio) * Integer.parseInt(cantidad));
        DecimalFormat f = new DecimalFormat("#.00");
        String finalTotal = f.format(total);

        if(b){
            MenuAdapter.total = Double.parseDouble(finalTotal);
        }else{
            String t = f.format(Double.parseDouble(finalTotal) + (Double.parseDouble(precio) * Integer.parseInt(c)));
            MenuAdapter.total = Double.parseDouble(t);
        }
    }
}
