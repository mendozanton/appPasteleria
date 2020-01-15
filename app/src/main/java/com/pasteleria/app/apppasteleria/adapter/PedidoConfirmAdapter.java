package com.pasteleria.app.apppasteleria.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.pasteleria.app.apppasteleria.modelo.CestaProducto;

import java.util.ArrayList;

public class PedidoConfirmAdapter  extends RecyclerView.Adapter<PedidoConfirmAdapter.PedidoConfirmAdapterViewHolder> {
    private Context context;
    private ArrayList<CestaProducto> lista;

    @NonNull
    @Override
    public PedidoConfirmAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoConfirmAdapterViewHolder pedidoConfirmAdapterViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class PedidoConfirmAdapterViewHolder extends RecyclerView.ViewHolder {
        public PedidoConfirmAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
