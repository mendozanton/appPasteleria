package com.pasteleria.app.apppasteleria.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pasteleria.app.apppasteleria.R;
import com.pasteleria.app.apppasteleria.adapter.PedidosAdapter;
import com.pasteleria.app.apppasteleria.adapter.ProductoAdapter;
import com.pasteleria.app.apppasteleria.modelo.Compra;
import com.pasteleria.app.apppasteleria.modelo.Imagen;
import com.pasteleria.app.apppasteleria.modelo.Pedido;
import com.pasteleria.app.apppasteleria.modelo.Producto;
import com.pasteleria.app.apppasteleria.services.ConnectApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActividadFragment extends Fragment {
    private RecyclerView rvDatos;
    private PedidosAdapter adapter;
    private ArrayList<Pedido> vDatos;
    private RequestQueue mQueue;
    int idUsuario;

    public ActividadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_actividad, container, false);;
        vDatos = new ArrayList<>();
        adapter = new PedidosAdapter(getContext());
        rvDatos = view.findViewById(R.id.rvCompras);
        rvDatos.setLayoutManager(new LinearLayoutManager(getContext()));
        rvDatos.setAdapter(adapter);
        rvDatos.setHasFixedSize(true);
        mQueue = Volley.newRequestQueue(getContext());

        SharedPreferences preferences = getContext().getSharedPreferences(
                "users", getContext().MODE_PRIVATE);
        idUsuario = 0;
        if(preferences.contains("idUsuario")){
            idUsuario = Integer.parseInt(preferences.getString("idUsuario",""));

            String url = ConnectApi.url_local + ConnectApi.url_pedido + "usuario="+idUsuario;

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray content = response.getJSONArray("content");

                                for(int i = 0; i < content.length(); i++){

                                    JSONObject ped = content.getJSONObject(i);

                                    Pedido p = new Pedido();
                                    List<Compra> c = new ArrayList<>();
                                    p.setCodigo(ped.getString("codigo"));
                                    p.setEnvio(ped.getString("envio"));
                                    p.setFecha(ped.getString("fecha"));
                                    JSONArray compras = ped.getJSONArray("compras");
                                    /*
                                    for(int y = 0; y < compras.length(); y++){
                                        Compra com = new Compra();
                                        com.setIdCompra();
                                        c.add(com);
                                    }*/


                                    vDatos.add(p);
                                }
                                adapter.agregarElementos(vDatos);

                            }catch (JSONException ex){
                                ex.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) { error.printStackTrace();}
                    }
            );
            mQueue.add(request);
        }

        return view;
    }

}
