package com.pasteleria.app.apppasteleria.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import com.pasteleria.app.apppasteleria.modelo.Imagen;
import com.pasteleria.app.apppasteleria.services.ConnectApi;
import com.pasteleria.app.apppasteleria.R;
import com.pasteleria.app.apppasteleria.adapter.ProductoAdapter;
import com.pasteleria.app.apppasteleria.modelo.Producto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView rvDatos;
    private ProductoAdapter adapter;
    private ArrayList<Producto> vDatos;
    private RequestQueue mQueue;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        vDatos = new ArrayList<>();
        adapter = new ProductoAdapter(getContext());
        rvDatos = view.findViewById(R.id.rvProducto);
        rvDatos.setLayoutManager(new GridLayoutManager(getContext(),2));
        rvDatos.setAdapter(adapter);
        rvDatos.setHasFixedSize(true);
        mQueue = Volley.newRequestQueue(getContext());

        WsGetProductos();
        return view;
    }

    public void WsGetProductos() {
        String url = ConnectApi.url_local + ConnectApi.url_product;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("content");

                            for(int i = 0; i < jsonArray.length(); i++){

                                JSONObject projs = jsonArray.getJSONObject(i);
                                Producto pr = new Producto();

                                pr.setNombre(projs.getString("nombre"));
                                pr.setDescripcion(projs.getString("descripcion"));

                                List<Imagen> imgs = new ArrayList<>();
                                JSONArray jsonArrImagenes =  projs.getJSONArray("imagenes");

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
                                vDatos.add(pr);
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

}
