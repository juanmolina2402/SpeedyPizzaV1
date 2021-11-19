package com.restaurant_bd.speedypizza.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.restaurant_bd.speedypizza.Models.Menu;
import com.restaurant_bd.speedypizza.R;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{
    private final List<Menu> menuList;
    private final Context context;

    public OrderAdapter(List<Menu> menuList, Context context) {
        this.menuList = menuList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        Menu m = menuList.get(position);

        holder.tvNombre.setText(m.getNombre());
        holder.tvCantidad.setText(m.getCantidad());
        holder.tvPrecio.setText("$ "+m.getPrecio());
        holder.llModificar.setOnClickListener(view -> {
            new OrderDialog(context, m.getId(), m.getNombre(), m.getPrecio(), m.getCantidad(), position, false);
        });
        holder.ivEliminar.setOnClickListener(view -> {
            new OrderDialog(context, m.getId(), m.getNombre(), m.getPrecio(), m.getCantidad(), position, true);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivEliminar;
        TextView tvNombre, tvPrecio, tvCantidad;
        LinearLayout llModificar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivEliminar = itemView.findViewById(R.id.ivEliminar);
            tvNombre = itemView.findViewById(R.id.tvNombreP);
            tvPrecio = itemView.findViewById(R.id.tvPrecioP);
            tvCantidad = itemView.findViewById(R.id.tvCantidadP);
            llModificar = itemView.findViewById(R.id.ll_Modificar);
        }
    }
}
