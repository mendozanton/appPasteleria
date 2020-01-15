package com.pasteleria.app.apppasteleria.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.pasteleria.app.apppasteleria.modelo.Producto;
import com.pasteleria.app.apppasteleria.services.ConnectApi;
import com.pasteleria.app.apppasteleria.services.Mensaje;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class ProductoOpcActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView textView1,textView2,textView3;
    private ImageButton imageButton1,imageButton2;
    private Button btnContinuar;
    private RequestQueue mQueue;
    private int opc, cant, idProducto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_opc);

        imageView = findViewById(R.id.ivProd_opc);
        textView1 = findViewById(R.id.tvProdDesc_opc);
        textView2 = findViewById(R.id.tvProdPrec_opc);
        textView3 = findViewById(R.id.tvCantidad);
        cant = 1;
        imageButton1 = findViewById(R.id.imgbtMinus);
        imageButton2 = findViewById(R.id.imgbtPlus);
        btnContinuar = findViewById(R.id.btnContinuar);
        Intent intent = getIntent();
        mQueue = Volley.newRequestQueue(this);
        final Producto producto = (Producto) intent.getExtras().getSerializable("producto");
        idProducto = producto.getIdProducto();
        opc = intent.getExtras().getInt("op");

        Picasso.with(this)
                .load(producto.getImagenes().get(0).getSource())
                .fit()
                .centerCrop()
                .into(imageView);
        DecimalFormat formato = new DecimalFormat("#,###.00");

        textView2.setText("PEN S/. " + formato.format(producto.getPrecio()));
        textView1.setText(producto.getDescripcion());
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cant =  Integer.parseInt(textView3.getText().toString());
                if (cant > 1) cant = cant - 1;
                textView3.setText(String.valueOf(cant));
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cant =  Integer.parseInt(textView3.getText().toString());
                cant = cant + 1;
                textView3.setText(String.valueOf(cant));
            }
        });
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (opc) {
                    case 0:
                        agregarALaCesta(idProducto,cant,1);
                        break;
                    case 1:

                        break;
                }
            }
        });
    }
    public void agregarALaCesta(Integer idProducto, int cantidad, Integer idEstado) {
        String url = ConnectApi.url_local + ConnectApi.url_cesta;

        SharedPreferences preferences = getSharedPreferences(
                "users", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        int idUsuario = 0;
        if(preferences.contains("idUsuario")){
            idUsuario = Integer.parseInt(preferences.getString("idUsuario",""));
        } else {

        }

        String jsonString =
                "{" +
                    "idUsuario:" + idUsuario + "," +
                     "productos:" +
                        "{" +
                            "idProducto:" + idProducto + "," +
                            "cantidad:" + cantidad + "," +
                            "idEstado:" + idEstado +
                        "}" +
                "}";

        JSONObject jsonObject = null;

        try { jsonObject = new JSONObject(jsonString); } catch (Exception e){ e.printStackTrace(); }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject content = response.getJSONObject("content");

                            if (content.getInt("codigo") == Mensaje.prod_add_cesta) {
                                String mensaje = content.getString("descripcion");
                                Toast.makeText(getApplicationContext(),mensaje,
                                        Toast.LENGTH_LONG).show();
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
    }
}
