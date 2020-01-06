package com.pasteleria.app.apppasteleria.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pasteleria.app.apppasteleria.R;
import com.pasteleria.app.apppasteleria.modelo.Direccion;
import com.pasteleria.app.apppasteleria.modelo.Distrito;
import com.pasteleria.app.apppasteleria.modelo.Sexo;
import com.pasteleria.app.apppasteleria.services.ConnectApi;
import com.pasteleria.app.apppasteleria.services.Mensaje;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {
    private RequestQueue mQueue;
    private Spinner spinnerSexo, spineerdistrito;
    private TextView FecNac, Direcc, avenida, calle, urbanizacion, referencia ;
    private Button Continuar;
    private EditText  Nomb, Apel, Cont, Emai, Tele;
    private String Msg = "";
    private ArrayList<Distrito> distritoObj;
    private ArrayList<Sexo> sexoObj;
    private int[] nacArr = new int[3];
    int sexId;
    private Direccion direccion;

    Calendar calendar;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        spinnerSexo = findViewById(R.id.spSex_Aregister);
        FecNac = findViewById(R.id.tvFecNac_Aregister);
        Direcc = findViewById(R.id.tvDirecc_Aregister);
        Nomb = findViewById(R.id.etNom_Aregister);
        Apel = findViewById(R.id.etApe_Aregister);
        Cont = findViewById(R.id.etPasswd_Aregister);
        Emai = findViewById(R.id.etEmail_Aregister);
        Tele = findViewById(R.id.etTelef_Aregister);
        distritoObj = new ArrayList<>();
        sexoObj = new ArrayList<>();
        Continuar = findViewById(R.id.btnContinuar_Aregister);
        mQueue = Volley.newRequestQueue(this);

        {
            String url = ConnectApi.url_local + ConnectApi.url_sexo;
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray reponseContent = response.getJSONArray("content");

                                for (int i = 0; i < reponseContent.length(); i++){
                                    Integer id = reponseContent.getJSONObject(i).getInt("idUsuarioSexo");
                                    String nom = reponseContent.getJSONObject(i).getString("nombre");
                                    sexoObj.add(new Sexo(id,nom));
                                   // sexos.add();
                                }
                                ArrayList<String> listaDeSexos = new ArrayList<>();
                                for(Sexo s : sexoObj) {
                                    listaDeSexos.add(s.getNombre());
                                }
                                ArrayAdapter<String> adapter =
                                        new ArrayAdapter<>(
                                                getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listaDeSexos);
                                spinnerSexo.setAdapter(adapter);
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            mQueue.add(request);
        }
        FecNac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR) - 18;

                datePickerDialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDayOfMonth) {
                        nacArr[0] = mYear;
                        nacArr[1] = mMonth;
                        nacArr[2] = mDayOfMonth;
                        FecNac.setText("F. Nacimiento: " + mDayOfMonth + "/"+ mMonth + "/" + mYear);
                    }
                }, year,month,day);
                datePickerDialog.show();
            }
        });

        Direcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                View view = getLayoutInflater().inflate(R.layout.modal_direccion, null);
                avenida = view.findViewById(R.id.etAv_modalDirecc);
                calle = view.findViewById(R.id.etCalle_modalDirecc);
                urbanizacion = view.findViewById(R.id.etUrb_modalDirecc);
                referencia = view.findViewById(R.id.etRef_modalDirecc);
                spineerdistrito = view.findViewById(R.id.spDist_modalDirecc);

                Button guardar = view.findViewById(R.id.btnGuardar_modalDirecc);
                {
                    String url = ConnectApi.url_local + ConnectApi.url_distrito;
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        JSONArray reponseContent = response.getJSONArray("content");

                                        for (int i = 0; i < reponseContent.length(); i++){
                                            Integer id = reponseContent.getJSONObject(i).getInt("idDistrito");
                                            String nom = reponseContent.getJSONObject(i).getString("nombre");
                                            distritoObj.add(new Distrito(id,nom));
                                        }
                                        ArrayList<String> listaDeDistritos = new ArrayList<>();
                                        for(Distrito d : distritoObj) {
                                            listaDeDistritos.add(d.getNombre());
                                        }
                                        System.out.println("lista de distritos: " + distritoObj.get(1).getNombre());
                                        ArrayAdapter<String> adapter =
                                                new ArrayAdapter<>(getApplicationContext(),
                                                        android.R.layout.simple_spinner_dropdown_item, listaDeDistritos);
                                        spineerdistrito.setAdapter(adapter);
                                    } catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });
                    mQueue.add(request);
                }

                builder.setView(view);
                final  AlertDialog  dialog = builder.create();
                dialog.show();

                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (referencia.getText().length() < 5) {
                            Toast.makeText(getApplicationContext(),"Ingrese una referencia correcta",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            String di="",ca="",ur="",re="",av="";
                            direccion = new Direccion();
                            if (spineerdistrito.getSelectedItem().toString().length() > 0) {
                                di = "Distrito. " + spineerdistrito.getSelectedItem().toString() + ", ";
                            }
                            if (calle.getText().toString().length() > 0) {
                                ca = "calle. " + calle.getText().toString() + ", ";
                                direccion.setCalle(calle.getText().toString());
                            }
                            if (urbanizacion.getText().toString().length() > 0) {
                                ur = "Urb. " + urbanizacion.getText().toString() + ", ";
                                direccion.setUrbanizacion(urbanizacion.getText().toString());
                            }
                            if (referencia.getText().toString().length() > 0) {
                                re = referencia.getText().toString() + ", ";
                                direccion.setReferencia(referencia.getText().toString());
                            }
                            if (avenida.getText().toString().length() > 0) {
                                av = "Av. " + avenida.getText().toString() + ", ";
                                direccion.setAvenida(avenida.getText().toString());
                            }

                            String dis = spineerdistrito.getSelectedItem().toString();
                            for(Distrito d : distritoObj) {
                                if (d.getNombre().equalsIgnoreCase(dis)) {
                                    direccion.setIdDistrito(d.getIdDistrito());
                                }
                            }

                            Direcc.setText(di + av + ca + ur + re);
                            dialog.hide();
                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        Continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validar()){
                    String url = ConnectApi.url_local + ConnectApi.url_user + "clientedireccion";
                    String fechaJson = nacArr[0] + "-" + nacArr[1] + "-" + nacArr[2];
                    int idsex = 0;
                    for (Sexo s : sexoObj) {
                        if (spinnerSexo.getSelectedItem().toString().equalsIgnoreCase(s.getNombre())) {
                            idsex = s.getIdUsuarioSexo();
                        }
                    }
                    String jsonString =
                            "{" +
                                    "nombre:" + "\"" + Nomb.getText().toString() + "\"" + "," +
                                    "apellido:"  + "\"" + Apel.getText().toString() + "\"" + "," +
                                    "nacimiento:"  + "\"" + fechaJson + "\"" + "," +
                                    "telefono:"  + Tele.getText().toString()+ "," +
                                    "sexo:"  + idsex + "," +
                                    "email:"  + "\"" + Emai.getText().toString() + "\"" + "," +
                                    "direccion:"  + "{" +
                                        "avenida:"  + "\"" + direccion.getAvenida() + "\"" + "," +
                                        "urbanizacion:" + "\"" + direccion.getUrbanizacion() + "\"" + "," +
                                        "calle:" + "\"" + direccion.getCalle() + "\"" + "," +
                                        "referencia:" + "\"" + direccion.getReferencia() + "\"" + "," +
                                        "idDistrito:" + direccion.getIdDistrito()  +
                                    "}" + "," +
                                    "password:"  + "\"" + Cont.getText().toString() + "\"" +
                            "}";
                    JSONObject jsonObject = null;
                    try { jsonObject = new JSONObject(jsonString); } catch (Exception e){ e.printStackTrace(); }
                    //System.out.println(jsonObject);
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {

                                        JSONObject content = response.getJSONObject("content");
                                        String mensaje = content.getString("descripcion");
                                        if (content.getInt("codigo") != Mensaje.user_passw_no_valid) {

                                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                        }
                                        Toast.makeText(getApplicationContext(),mensaje,
                                                Toast.LENGTH_LONG).show();
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
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    View view = getLayoutInflater().inflate(R.layout.modal_mensaje, null);
                    final TextView mensaje = view.findViewById(R.id.tvTitle_modalMensaje);
                    Button ok = view.findViewById(R.id.btnOk_modalMensaje);
                    builder.setView(view);
                    final  AlertDialog  dialog = builder.create();
                    dialog.show();
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.hide();
                            dialog.dismiss();
                        }
                    });
                    mensaje.setText(Msg);
                }
            }
        });
    }

    public boolean validar() {
        boolean camposValido = false;

        if (Emai.length() == 0) {
            Msg= "Complete todos los campos";
        } else if (Emai.length() < 6) {
            Msg = "Email no valido";
        } else if (Cont.length() == 0) {
            Msg= "Complete todos los campos";
        } else if (Cont.length() < 6) {
            Msg = "Contraseña mayor a 6 caracteres";
        } else if (Nomb.length() == 0) {
            Msg= "Complete todos los campos";
        } else if (Nomb.length() < 3) {
            Msg = "Nombre no valido";
        } else if(Apel.length() == 0) {
            Msg= "Complete todos los campos";
        } else if (Apel.length() < 3) {
            Msg = "Apellido no valido";
        } else if (Tele.length() == 0) {
            Msg= "Complete todos los campos";
        } else if (Tele.length() < 9) {
            Msg= "Telefono no valido";
        } else if (
            spinnerSexo.getSelectedItem().toString().equalsIgnoreCase("Seleccione su genero") ||
                    spinnerSexo.getSelectedItem().toString().equalsIgnoreCase("Seleccione su sexo")
        ) {
            Msg="Sexo no valido";
        } else if (
                FecNac.getText().toString().equalsIgnoreCase("Fecha de nacimiento")
        ) {
            Msg="Fecha no valida";
        }else if (
                Direcc.getText().toString().equalsIgnoreCase("Direccion")
        ) {
            Msg="Direccion no valida";
        } else {
            camposValido = true;
        }

        return camposValido;
    }


}
