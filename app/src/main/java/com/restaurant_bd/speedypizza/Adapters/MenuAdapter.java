package com.restaurant_bd.speedypizza.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.restaurant_bd.speedypizza.R;
import com.restaurant_bd.speedypizza.Models.Menu;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder>{
    private final List<Menu> menuList;
    private final Context context;

    public MenuAdapter(List<Menu> menuList, Context context) {
        this.menuList = menuList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Menu m = menuList.get(position);

        String base64String = "data:image/png;base64," + m.getImagen();
        String base64Image = base64String.split(",")[1];
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.ivImagen.setImageBitmap(decodedByte);
        holder.tvNombre.setText(m.getNombre());
        holder.tvDescripcion.setText(m.getDescripcion());
        holder.tvPrecio.setText(m.getPrecio());
        holder.llmenu.setOnClickListener(view -> new MenuDialog(context, m.getId(), m.getNombre(), m.getPrecio()));
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivImagen;
        TextView tvNombre,tvDescripcion, tvPrecio;
        LinearLayout llmenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImagen = itemView.findViewById(R.id.ivImagen);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            llmenu = itemView.findViewById(R.id.llmenu);
        }
    }
}
