package com.pasteleria.app.apppasteleria.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pasteleria.app.apppasteleria.R;
import com.pasteleria.app.apppasteleria.modelo.Pedido;
import com.pasteleria.app.apppasteleria.modelo.Producto;

import java.util.ArrayList;

public class PedidosAdapter  extends RecyclerView.Adapter<PedidosAdapter.PedidosAdapterViewHolder>{
    private Context context;
    private ArrayList<Pedido> lista;

    public PedidosAdapter(Context context) {
        this.context = context;
        lista = new ArrayList<>();
    }

    @NonNull
    @Override
    public PedidosAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_compras, viewGroup,false);
        return new PedidosAdapter.PedidosAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidosAdapterViewHolder pedidosAdapterViewHolder, int i) {
        final Pedido item = lista.get(i);
        pedidosAdapterViewHolder.tvIdPed.setText(item.getCodigo());
        pedidosAdapterViewHolder.tvFecEnvPed.setText(item.getEnvio());
        pedidosAdapterViewHolder.tvFecRegist.setText(item.getFecha());
        //pedidosAdapterViewHolder.tvMontoPed.setText(item.ge());


    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class PedidosAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdPed,tvFecEnvPed,tvFecRegist,tvMontoPed;
        CardView cardView;
        public PedidosAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdPed = itemView.findViewById(R.id.tvIdped_itemComp);
            tvFecEnvPed = itemView.findViewById(R.id.tvEnvped_itemComp);
            tvFecRegist = itemView.findViewById(R.id.tvFechped_itemComp);
            tvMontoPed = itemView.findViewById(R.id.tvMonto_itemComp);
            cardView = itemView.findViewById(R.id.cardview_itemCompras);

        }
    }

    public void agregarElementos(ArrayList<Pedido> data){
        lista.clear();
        lista.addAll(data);
        notifyDataSetChanged();
    }
}
