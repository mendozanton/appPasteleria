package com.pasteleria.app.apppasteleria.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pasteleria.app.apppasteleria.R;
import com.pasteleria.app.apppasteleria.adapter.ProductoCestaAdapter;
import com.pasteleria.app.apppasteleria.modelo.CestaProducto;
import com.pasteleria.app.apppasteleria.modelo.Imagen;
import com.pasteleria.app.apppasteleria.services.ConnectApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PedidoConfirmActivity extends AppCompatActivity {
    private TextView nomU, distU, dirU, telU, fecEnvio, precTotal2, precSubTotal, precEnvio;
    private Button pedir;
    private LinearLayout fecEnvioLy;
    private SharedPreferences preferences;
    private int idUsuario,idDistrito;
    private RequestQueue mQueue;
    private ProductoCestaAdapter adapter;
    private ArrayList<CestaProducto> vDatos;
    private RecyclerView rvDatos;
    private Double precio_envio;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    private int[] envioFecArr = new int[3];
    private int[] envioTimeArr = new int[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_confirm);
        precio_envio = 0.0;
        nomU = findViewById(R.id.tvNombre_pedConfitmActivity);
        distU = findViewById(R.id.tvDist_pedConfitmActivity);
        dirU = findViewById(R.id.tvDirecc_pedConfitmActivity);
        telU = findViewById(R.id.tvTel_pedConfitmActivity);
        fecEnvioLy = findViewById(R.id.lyFecEnvio_pedConfitmActivity);
        fecEnvio = findViewById(R.id.tvFecEnvio_pedConfitmActivity);
        precTotal2 = findViewById(R.id.tvPrecTotal2_pedConfitmActivity);
        precSubTotal = findViewById(R.id.tvPrecSubTotal_pedConfitmActivity);
        precEnvio = findViewById(R.id.tvPrecEnvio_pedConfitmActivity);
        pedir = findViewById(R.id.btnPedir_pedConfitmActivity);

        vDatos = new ArrayList<>();
        adapter = new ProductoCestaAdapter(getApplicationContext());
        rvDatos = findViewById(R.id.rvProductoConfirm);
        rvDatos.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvDatos.setAdapter(adapter);
        rvDatos.setHasFixedSize(true);
        mQueue = Volley.newRequestQueue(getApplicationContext());
        preferences = getSharedPreferences(
                "users", MODE_PRIVATE);
        if(preferences.contains("idUsuario")) {
            idUsuario = Integer.parseInt(preferences.getString("idUsuario", ""));
            System.out.println(idUsuario);

            {
                String url = ConnectApi.url_local + ConnectApi.url_user + idUsuario;
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject content = response.getJSONObject("content");
                                    nomU.setText(content.getString("nombre") + content.getString("apellido"));
                                    Integer tel = content.getInt("telefono");
                                    telU.setText(tel.toString());
                                    JSONObject direccion = content.getJSONObject("direccion");
                                    String dir = direccion.getString("avenida") + " " +
                                            direccion.getString("urbanizacion") + " " +
                                            direccion.getString("calle") + " " +
                                            direccion.getString("referencia") ;
                                    idDistrito = direccion.getInt("idDistrito");
                                    dirU.setText(dir);
                                    {
                                        String url = ConnectApi.url_local + ConnectApi.url_distrito + idDistrito;
                                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                                                new Response.Listener<JSONObject>() {
                                                    @Override
                                                    public void onResponse(JSONObject response) {
                                                        try {
                                                            JSONObject content = response.getJSONObject("content");
                                                            distU.setText(content.getString("nombre"));
                                                        } catch (JSONException ex) {
                                                            ex.printStackTrace();
                                                        }
                                                    }
                                                },
                                                new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        error.printStackTrace();
                                                    }
                                                }
                                        );
                                        mQueue.add(request);
                                    }
                                } catch (JSONException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        }
                );
                mQueue.add(request);
            }

            ArrayList<CestaProducto> prods= (ArrayList<CestaProducto>) getIntent().getSerializableExtra("listProds");
            vDatos = prods;
            adapter.agregarElementos(vDatos);
            Double subtotal= 0.0;

            for (CestaProducto c : prods) {
                subtotal += c.getPrecio();
            }
            final DecimalFormat formato = new DecimalFormat("#,###.00");
            precSubTotal.setText("S/. " + formato.format(subtotal));

            {
                String url = ConnectApi.url_local + ConnectApi.url_product + "/100";
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject content = response.getJSONObject("content");
                                    precio_envio = content.getDouble("precio");
                                    precEnvio.setText("S/. " + formato.format(precio_envio));

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
                /*
                Double total = subtotal + precio_envio;
                precTotal2.setText(formato.format(total));
                */

            }

            fecEnvioLy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    calendar = Calendar.getInstance();
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int month = calendar.get(Calendar.MONTH);
                    int year = calendar.get(Calendar.YEAR);
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    int minute = calendar.get(Calendar.MINUTE);

                    timePickerDialog = new TimePickerDialog(PedidoConfirmActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            envioTimeArr[0] = hourOfDay;
                            envioTimeArr[1] = minute;
                            envioTimeArr[2] = 0;

                            fecEnvio.setText(fecEnvio.getText().toString() + " "+
                                            hourOfDay + ":" +
                                            minute
                                    );
                        }
                    }, hour, minute, true);
                    //timePickerDialog.setTitle("Select Time");
                    timePickerDialog.show();

                    datePickerDialog = new DatePickerDialog(PedidoConfirmActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int mYear, int mMonth, int mDayOfMonth) {
                            envioFecArr[0] = mYear;
                            envioFecArr[1] = mMonth+1;
                            envioFecArr[2] = mDayOfMonth;
                            fecEnvio.setText(mDayOfMonth + "/"+ (mMonth+1) + "/" + mYear);
                        }
                    }, year,month,day);
                    datePickerDialog.show();



                }
            });

            pedir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<CestaProducto> prods= (ArrayList<CestaProducto>) getIntent().getSerializableExtra("listProds");
                    String url = ConnectApi.url_local + "/compra_pedido";
                    JSONObject jsonObject = null;
                    JSONObject jsonObject2;
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray();
                        for (int i = 0; i < prods.size(); i++) {
                            String obj = "{" +
                                    "idProducto:" + prods.get(i).getIdProducto() + ","+
                                    "cantidad:" + prods.get(i).getCantidad() +
                                    "}";
                            jsonObject2 = new JSONObject(obj);
                            jsonArray.put(jsonObject2);
                        }

                    } catch (Exception e){ e.printStackTrace(); }
                    try {
                        jsonObject = new JSONObject();
                        jsonObject.put("producto", jsonArray);
                        String envio = envioFecArr[0] + "-" + envioFecArr[1] + "-" + envioFecArr[2];
                        envio += " " + envioTimeArr[0] +":"+ envioTimeArr[1] +":"+ "00";
                        jsonObject.put("envio", envio);
                        jsonObject.put("prioridad",3);
                        jsonObject.put("usuario",idUsuario);
                    } catch (Exception e){ e.printStackTrace(); }

                    System.out.println(jsonObject);
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        JSONObject content = response.getJSONObject("content");
                                        int cod = content.getInt("codigo");

                                        if(cod == 7) {
                                            {
                                                ArrayList<CestaProducto> prods= (ArrayList<CestaProducto>) getIntent().getSerializableExtra("listProds");
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

                                                //System.out.println(jsonObject);

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
                                            Toast.makeText(getApplicationContext(),content.getString("descripcion"),
                                                    Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(PedidoConfirmActivity.this, MainActivity.class));
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

            });

        } else  {

        }
    }
}
