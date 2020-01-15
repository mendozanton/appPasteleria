package com.pasteleria.app.apppasteleria.adapter;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.pasteleria.app.apppasteleria.R;
import com.pasteleria.app.apppasteleria.modelo.CestaProducto;
import com.pasteleria.app.apppasteleria.modelo.Producto;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductoCestaAdapter extends RecyclerView.Adapter<ProductoCestaAdapter.ProductoCestaViewHolder>{
    private Context context;
    private ArrayList<CestaProducto> lista;

    public ProductoCestaAdapter(Context context) {
        this.context = context;
        lista = new ArrayList<>();
    }

    @NonNull
    @Override
    public ProductoCestaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_prod_cesta, viewGroup,false);
        return new ProductoCestaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductoCestaViewHolder productoCestaViewHolder, int i) {
        final CestaProducto item = lista.get(i);

        productoCestaViewHolder.tvNom.setText("De " + item.getNombre());
        final DecimalFormat formato = new DecimalFormat("#,###.00");

        productoCestaViewHolder.tvPrec.setText("S/." + " " + formato.format(item.getPrecio()));
        productoCestaViewHolder.tvCant.setText("Cantidad:" + " " + item.getCantidad().toString());
        productoCestaViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                productoCestaViewHolder.checkbox.setChecked(!productoCestaViewHolder.checkbox.isChecked());
                if (productoCestaViewHolder.checkbox.isChecked()) {
                    item.setEstado(1);
                    
                } else {
                    item.setEstado(2);
                }
            }
        });
        productoCestaViewHolder.checkbox.setChecked(false);
        Picasso.with(context)
                .load(item.getImagenes().get(0).getSource())
                .fit()
                .centerInside()
                .into(productoCestaViewHolder.ivProd);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ProductoCestaViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkbox;
        TextView tvNom,tvPrec,tvCant;
        ImageView ivProd;
        CardView cardView;

        public ProductoCestaViewHolder(@NonNull View itemView) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.checkbox_itemCesta);
            tvNom = itemView.findViewById(R.id.tvNomProdCesta_itemCesta);
            tvPrec = itemView.findViewById(R.id.tvPrecProdCesta_itemCesta);
            tvCant = itemView.findViewById(R.id.tvCantProdCesta_itemCesta);
            ivProd = itemView.findViewById(R.id.ivProdCesta_itemCesta);
            cardView = itemView.findViewById(R.id.cardview_itemCesta);

        }
    }

    public void agregarElementos(ArrayList<CestaProducto> data){
        lista.clear();
        lista.addAll(data);
        notifyDataSetChanged();
    }
    public void BorrarElementos(){
        lista.clear();

        notifyDataSetChanged();
    }

    public ArrayList<CestaProducto> getListaProductos() {
        return lista;
    }
}
