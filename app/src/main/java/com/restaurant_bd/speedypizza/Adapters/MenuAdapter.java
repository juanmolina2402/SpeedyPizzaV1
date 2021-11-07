package com.restaurant_bd.speedypizza.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.restaurant_bd.speedypizza.R;
import com.restaurant_bd.speedypizza.Models.Menu;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder>{
    private List<Menu> menu;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        private TextView tvId, tvNombre, tvDescripcion, tvPrecio;
        private ImageView ivImagen;
        private Context context;

        public ViewHolder(View view, Context context){
            super(view);
            //tvId = view.findViewById(R.id.tvId);
            tvNombre = view.findViewById(R.id.tvNombre);
            tvDescripcion = view.findViewById(R.id.tvDescripcion);
            tvPrecio = view.findViewById(R.id.tvPrecio);
            ivImagen = view.findViewById(R.id.ivImagen);
            this.context=context;
        }
    }

    public MenuAdapter(List<Menu> menu, Context context) {
        this.context = context;
        this.menu = menu;
    }

    @NonNull
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu,parent,false);
        ViewHolder vh = new ViewHolder(v, context);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.ViewHolder holder, int position) {
        Menu p = menu.get(position);
        holder.tvId.setText(""+p.getId());
        holder.tvNombre.setText(p.getNombre());
        holder.tvDescripcion.setText(p.getDescripcion());
        holder.tvPrecio.setText(p.getPrecio());

        //String base64String = "data:image/png;base64," + m.getImagen();
        //String base64Image = base64String.split(",")[1];

        //byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        // decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        //holder.ivImagen.setImageBitmap(decodedByte);
    }

    @Override
    public int getItemCount() {
        return menu.size();
    }
}
