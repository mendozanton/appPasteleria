package com.pasteleria.app.apppasteleria.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pasteleria.app.apppasteleria.R;
import com.pasteleria.app.apppasteleria.modelo.Imagen;
import com.pasteleria.app.apppasteleria.modelo.Producto;
import com.pasteleria.app.apppasteleria.services.ConnectApi;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductoInfoActivity extends AppCompatActivity {
    private ImageView imagen;
    private TextView precio,descripcion,descripcion2,precioEnvio;
    private Context context = this;
    private Producto producto;
    private Double precio_envio;
    private RequestQueue mQueue;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navi_cesta:
                    llamarProductoOpcActivity(0, producto);
                    break;
                case R.id.navi_comp:
                    llamarProductoOpcActivity(1, producto);
                    break;
            }
            return true;

        }
    };

    private void llamarProductoOpcActivity(int op, Producto producto) {
        Intent intent = new Intent(context, ProductoOpcActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("producto",producto);
        intent.putExtras(bundle);
        intent.putExtra("op",op);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_info);

        imagen = findViewById(R.id.ivProdDetalle);
        precio = findViewById(R.id.tvProDeta_precio);
        descripcion = findViewById(R.id.tvProDeta_descrip);
        precioEnvio = findViewById(R.id.tvEnvPrec_ProdInf);
        mQueue = Volley.newRequestQueue(getApplicationContext());
        precio_envio = 0.0;

        String url = ConnectApi.url_local + ConnectApi.url_product + "/100";



        Intent intent = getIntent();
        producto = (Producto) intent.getExtras().getSerializable("producto");

        Picasso.with(context)
                .load(producto.getImagenes().get(0).getSource())
                .fit()
                .centerCrop()
                .into(imagen);

        final DecimalFormat formato = new DecimalFormat("#,###.00");

        //System.out.println("despues del request : " + precio_envio);

        precio.setText("PEN S/. " + formato.format(producto.getPrecio()));
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject content = response.getJSONObject("content");

                            precio_envio = content.getDouble("precio");
                            precioEnvio.setText("PEN S/. " + formato.format(precio_envio));

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
        descripcion.setText(producto.getDescripcion());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_compra);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}
