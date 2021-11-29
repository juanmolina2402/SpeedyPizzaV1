package com.restaurant_bd.speedypizza.Adapters;

import android.annotation.SuppressLint;
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

public class PedidoDialog {
    private UpdateOrder interfaz;

    public interface UpdateOrder{
        void result(boolean r, long id);
    }

    @SuppressLint("SetTextI18n")
    public PedidoDialog(Context context, long id, String cliente, String estado){
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

            edtCantidad.setVisibility(View.GONE);
            tvProducto.setText("Cliente: " + cliente);
            if(estado.equals("LISTO")){
                tvTitulo.setText(R.string.itm_dialog_title4);
                btnAceptar.setText(R.string.btn_entregar);
            }else{
                tvTitulo.setText(R.string.itm_dialog_title5);
                btnAceptar.setText(R.string.btn_anular);
            }

            btnCancelar.setOnClickListener(view -> dialog.dismiss());
            btnAceptar.setOnClickListener(view -> {
                interfaz = (UpdateOrder) context;
                if(estado.equals("LISTO")){
                    interfaz.result(true, id);
                }else{
                    interfaz.result(false, id);
                }
                dialog.dismiss();
            });
            dialog.show();
        }catch (Exception e){
            Toast.makeText(context, "Lo sentimos, error inesperado", Toast.LENGTH_SHORT).show();
        }
    }
}
