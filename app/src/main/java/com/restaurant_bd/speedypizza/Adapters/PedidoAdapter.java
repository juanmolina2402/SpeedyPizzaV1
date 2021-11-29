package com.restaurant_bd.speedypizza.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.restaurant_bd.speedypizza.Models.Pedido;
import com.restaurant_bd.speedypizza.R;
import java.util.List;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.ViewHolder>{
    private final List<Pedido> pedidoList;
    private final Context context;

    public PedidoAdapter(List<Pedido> pedidoList, Context context) {
        this.pedidoList = pedidoList;
        this.context = context;
    }

    @NonNull
    @Override
    public PedidoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedidos, parent, false);
        return new PedidoAdapter.ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvEmpleado,tvCliente, tvMesa, tvEstado;
        LinearLayout llpedidos;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmpleado = itemView.findViewById(R.id.tvMesero);
            tvCliente = itemView.findViewById(R.id.tvCliente);
            tvMesa = itemView.findViewById(R.id.tvMesa);
            tvEstado = itemView.findViewById(R.id.tvEstado);
            llpedidos = itemView.findViewById(R.id.llpedidos);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoAdapter.ViewHolder holder, int position) {
        Pedido p = pedidoList.get(position);
        String e = p.getEmpleado().getNombres() + " " + p.getEmpleado().getApellidos();

        holder.tvEmpleado.setText(e);
        holder.tvCliente.setText(p.getCliente());
        holder.tvMesa.setText(p.getMesa().getCodigo());
        holder.tvEstado.setText(p.getEstado());
        holder.llpedidos.setOnClickListener(view -> new PedidoDialog(context, p.getId(), p.getCliente(), p.getEstado()));
    }

    @Override
    public int getItemCount() {
        return pedidoList.size();
    }
}
