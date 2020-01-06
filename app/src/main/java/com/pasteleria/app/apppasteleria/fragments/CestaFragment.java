package com.pasteleria.app.apppasteleria.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pasteleria.app.apppasteleria.R;
import com.pasteleria.app.apppasteleria.adapter.ProductoAdapter;
import com.pasteleria.app.apppasteleria.adapter.ProductoCestaAdapter;
import com.pasteleria.app.apppasteleria.modelo.CestaProducto;
import com.pasteleria.app.apppasteleria.modelo.Imagen;
import com.pasteleria.app.apppasteleria.modelo.Producto;
import com.pasteleria.app.apppasteleria.services.ConnectApi;
import com.pasteleria.app.apppasteleria.services.Mensaje;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CestaFragment extends Fragment {
    private RecyclerView rvDatos;
    private ProductoCestaAdapter adapter;
    private ArrayList<CestaProducto> vDatos;
    private RequestQueue mQueue;
    private TextView title;
    private ImageButton deleteItem;
    private Button comprar;

    public CestaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cesta, container, false);
        title = view.findViewById(R.id.tvTitle_cestaFragment);
        deleteItem = view.findViewById(R.id.ibDeleteItem_cestaFragment);
        comprar = view.findViewById(R.id.btnComprar_cestaFragment);
        vDatos = new ArrayList<>();
        adapter = new ProductoCestaAdapter(getContext());
        rvDatos = view.findViewById(R.id.rvProductoCesta);
        rvDatos.setLayoutManager(new LinearLayoutManager(getContext()));
        rvDatos.setAdapter(adapter);
        rvDatos.setHasFixedSize(true);
        mQueue = Volley.newRequestQueue(getContext());

        SharedPreferences preferences = getContext().getSharedPreferences(
                "users", getContext().MODE_PRIVATE);
        int idUsuario = 0;
        if(preferences.contains("idUsuario")){
            idUsuario = Integer.parseInt(preferences.getString("idUsuario",""));

            String url = ConnectApi.url_local + ConnectApi.url_cesta + "/usuario=" + idUsuario;

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject content = response.getJSONObject("content");
                                if (content.getInt("idCesta") >= 1) {
                                    JSONArray prodJsonArray = content.getJSONArray("productos");

                                    for (int i = 0; i < prodJsonArray.length(); i++) {

                                        JSONObject projs = prodJsonArray.getJSONObject(i);
                                        CestaProducto pr = new CestaProducto();
                                        pr.setIdCestaProducto(projs.getInt("idCestaProducto"));
                                        pr.setIdProducto(projs.getInt("idProducto"));
                                        pr.setNombre(projs.getString("nombre"));
                                        pr.setDescripcion(projs.getString("descripcion"));
                                        pr.setDescripcion2(projs.getString("descripcion2"));
                                        pr.setStock(projs.getInt("stock"));
                                        List<Imagen> imgs = new ArrayList<>();
                                        JSONArray jsonArrImagenes = projs.getJSONArray("imagenes");

                                        for (int y = 0; y < jsonArrImagenes.length(); y++) {
                                            Imagen img = new Imagen();
                                            img.setIdImagen(jsonArrImagenes.getJSONObject(y).getInt("idImagen"));
                                            img.setSource(jsonArrImagenes.getJSONObject(y).getString("source"));
                                            img.setNombre(jsonArrImagenes.getJSONObject(y).getString("nombre"));
                                            img.setClasificacion(jsonArrImagenes.getJSONObject(y).getString("clasificacion"));

                                            imgs.add(img);
                                        }
                                        pr.setImagenes(imgs);
                                        pr.setPrecio(projs.getDouble("precio"));
                                        pr.setEstado(2);
                                        vDatos.add(pr);

                                    }
                                    title.setText(title.getText().toString() + " (" + prodJsonArray.length() + ")");
                                    adapter.agregarElementos(vDatos);
                                }
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

            deleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<CestaProducto> prods= new ArrayList<>();
                    for (CestaProducto c : adapter.getListaProductos()) {
                        if (c.getEstado() == 1) {
                            prods.add(c);
                        }
                    }
                    {
                        String url = ConnectApi.url_local + ConnectApi.url_cesta;


                        JSONObject jsonObject = null;
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray();
                            for (int i = 0; i < prods.size(); i++) {
                                jsonArray.put(prods.get(i).getIdCestaProducto());
                            }

                        } catch (Exception e){ e.printStackTrace(); }
                        try {
                            jsonObject = new JSONObject();
                            jsonObject.put("idCestaProducto", jsonArray);
                        } catch (Exception e){ e.printStackTrace(); }

                        System.out.println(jsonObject);

                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            JSONObject content = response.getJSONObject("content");


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
                }
            });
        } else { idUsuario = -1; }

        return view;
    }



}
