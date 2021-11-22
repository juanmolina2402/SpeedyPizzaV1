package com.restaurant_bd.speedypizza.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.restaurant_bd.speedypizza.Models.Menu;
import com.restaurant_bd.speedypizza.R;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder>{
    private final List<Menu> menuList;
    private final Context context;
    public static ArrayList<Menu> listaTemporal;
    public static double total = 0;

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

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvNombre,tvDescripcion, tvPrecio;
        LinearLayout llmenu, ivImagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImagen = itemView.findViewById(R.id.ivImagen);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            llmenu = itemView.findViewById(R.id.llmenu);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Menu m = menuList.get(position);
        String p = "$" + m.getPrecio();

        holder.ivImagen.setBackground(convertImage(m.getImagen()));
        holder.tvNombre.setText(m.getNombre());
        holder.tvDescripcion.setText(m.getDescripcion());
        holder.tvPrecio.setText(p);
        holder.llmenu.setOnClickListener(view -> new MenuDialog(context, m.getId(), m.getNombre(), m.getPrecio()));
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    private Drawable convertImage(String imagen){
        String base64String = "data:image/png;base64," + imagen;
        String base64Image = base64String.split(",")[1];
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        Bitmap b = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return new BitmapDrawable(context.getResources(), b);
    }
}
