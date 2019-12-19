package com.pasteleria.app.apppasteleria.adapter;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SymbolTable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pasteleria.app.apppasteleria.R;
import com.pasteleria.app.apppasteleria.activity.ProductoInfoActivity;
import com.pasteleria.app.apppasteleria.modelo.Producto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {

    private Context context;
    private ArrayList<Producto> lista;

    public ProductoAdapter(Context context) {
        this.context = context;
        lista = new ArrayList<>();
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_prod, viewGroup,false);

        /***   Incluye el contexto      ***/
        //View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_prod, viewGroup,false);
        return new ProductoViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder productoViewHolder, int i) {
        final Producto item = lista.get(i);
        productoViewHolder.tvNombre.setText("De " + item.getNombre());
        productoViewHolder.tvDescrip.setText(item.getDescripcion());

        Picasso.with(context)
                .load(item.getImagenes().get(0).getSource())
                .fit()
                .centerInside()
                .into(productoViewHolder.ivImagen);



        productoViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductoInfoActivity.class);

                ArrayList<String> srcImages = new ArrayList<>();

                for(int i = 0; i < item.getImagenes().size(); i++){
                    srcImages.add(item.getImagenes().get(i).getSource());
                }
                intent.putStringArrayListExtra("imagesProd",srcImages);
                intent.putExtra("precioProd",item.getPrecio());
                intent.putExtra("descripProd",item.getDescripcion());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ProductoViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImagen;
        TextView tvNombre,tvDescrip;
        CardView cardView;
        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImagen = itemView.findViewById(R.id.ivImagen);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvDescrip = itemView.findViewById(R.id.tvDescrip);
            cardView = itemView.findViewById(R.id.cardview_product);
        }
    }

    public void agregarElementos(ArrayList<Producto> data){
        lista.clear();
        lista.addAll(data);
        notifyDataSetChanged();
    }
}
