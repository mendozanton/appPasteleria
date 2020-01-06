package com.pasteleria.app.apppasteleria.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pasteleria.app.apppasteleria.R;
import com.pasteleria.app.apppasteleria.services.ConnectApi;
import com.pasteleria.app.apppasteleria.services.Mensaje;

import org.json.JSONException;
import org.json.JSONObject;

public class SettingsActivity extends AppCompatActivity {
    private Button logout;
    private SharedPreferences preferences;
    private RequestQueue mQueue;
    private TextView nombre, apellido, email, telefono, sexo;
    private LinearLayout linearL_email,linearL_telefon,linearL_password;
    private int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mQueue = Volley.newRequestQueue(getApplicationContext());
        logout = findViewById(R.id.btnLogout_Asettings);
        email = findViewById(R.id.tvEmailUsu_Asettings);
        nombre = findViewById(R.id.tvNomUsu_Asettings);
        apellido = findViewById(R.id.tvApeUsu_Asettings);
        telefono = findViewById(R.id.tvTelUsu_Asettings);
        sexo = findViewById(R.id.tvSexUsu_Asettings);
        linearL_email = findViewById(R.id.LinearL_email_Asettings);
        linearL_telefon = findViewById(R.id.LinearL_telefono_Asettings);
        linearL_password = findViewById(R.id.LinearL_password_Asettings);

        preferences = getSharedPreferences(
                "users", MODE_PRIVATE);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("idUsuario");
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                getApplicationContext().startActivity(intent);
            }
        });

        if(preferences.contains("idUsuario")){
            id = Integer.parseInt(preferences.getString("idUsuario",""));
            String url = ConnectApi.url_local + ConnectApi.url_user + id;
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject content= response.getJSONObject("content");
                            email.setText(content.getString("email"));
                            nombre.setText(content.getString("nombre"));
                            apellido.setText(content.getString("apellido"));
                            telefono.setText(content.getString("telefono"));
                            sexo.setText(content.getString("sexo"));
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
            Toast.makeText(getApplicationContext(),"Error: Usuario no existe",
                    Toast.LENGTH_LONG).show();
        }
        linearL_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
            View view = getLayoutInflater().inflate(R.layout.modal_email, null);

            final EditText em = view.findViewById(R.id.etEmail_modalEmail);
            Button guardar = view.findViewById(R.id.btnGuardar_modalEmail);
            builder.setView(view);
            final  AlertDialog  dialog = builder.create();
            dialog.show();
            // enfoca al editText y despliega el teclado
            em.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

            guardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (em.getText().length() > 6) {
                        String url = ConnectApi.url_local + ConnectApi.url_user + "email";

                        String jsonString =
                            "{" +
                                "idUsuario:" + id + "," +
                                "email:"  + em.getText() +
                            "}";
                        //System.out.println(jsonString);
                        JSONObject jsonObject = null;

                        try { jsonObject = new JSONObject(jsonString); } catch (Exception e){ e.printStackTrace(); }
                        //System.out.println(jsonObject);
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            JSONObject content = response.getJSONObject("content");

                                            if (content.getInt("codigo") == Mensaje.user_updated_email) {
                                                String mensaje = content.getString("descripcion");
                                                Toast.makeText(getApplicationContext(),mensaje,
                                                        Toast.LENGTH_LONG).show();

                                                //  Refresca la actividad //

                                                finish();
                                                overridePendingTransition(0, 0);
                                                startActivity(getIntent());
                                                overridePendingTransition(0, 0);

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
                        dialog.hide();
                        dialog.dismiss();
                    }
                }
            });

            }
        });

        linearL_telefon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                View view = getLayoutInflater().inflate(R.layout.modal_telefono, null);
                final EditText tel = view.findViewById(R.id.etTelefono_modalTelefono);
                Button guardar = view.findViewById(R.id.btnGuardar_modalTelefono);
                builder.setView(view);
                final  AlertDialog  dialog = builder.create();
                dialog.show();

                // enfoca al editText y despliega el teclado
                tel.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (tel.getText().length() == 9) {
                            String url = ConnectApi.url_local + ConnectApi.url_user + "telefono";

                            String jsonString =
                                    "{" +
                                            "idUsuario:" + id + "," +
                                            "telefono:"  + tel.getText() +
                                    "}";
                            JSONObject jsonObject = null;

                            try { jsonObject = new JSONObject(jsonString); } catch (Exception e){ e.printStackTrace(); }
                            System.out.println(jsonObject);
                            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                JSONObject content = response.getJSONObject("content");

                                                if (content.getInt("codigo") == Mensaje.user_updated_telef) {
                                                    String mensaje = content.getString("descripcion");
                                                    Toast.makeText(getApplicationContext(),mensaje,
                                                            Toast.LENGTH_LONG).show();

                                                    //  Refresca la actividad //
                                                    finish();
                                                    overridePendingTransition(0, 0);
                                                    startActivity(getIntent());
                                                    overridePendingTransition(0, 0);

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
                            dialog.hide();
                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        linearL_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                View view = getLayoutInflater().inflate(R.layout.modal_passwd, null);
                final EditText oldpass = view.findViewById(R.id.etOldPass_modalPasswd);
                final EditText newpass = view.findViewById(R.id.etNewPass_modalPasswd);
                final EditText newpass2 = view.findViewById(R.id.etNewPass2_modalPasswd);
                Button guardar = view.findViewById(R.id.btnGuardar_modalPasswd);
                builder.setView(view);
                final  AlertDialog  dialog = builder.create();
                dialog.show();

                // enfoca al editText y despliega el teclado
                oldpass.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);


                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String p1 = newpass.getText().toString().trim();
                        String p2 = newpass2.getText().toString().trim();
                        if (p1.equals(p2)) {

                            String url = ConnectApi.url_local + ConnectApi.url_user + "passwd";
                            String jsonString =
                                    "{" +
                                            "idUsuario:" + id + "," +
                                            "oldPass:"  + oldpass.getText().toString()+ "," +
                                            "newPass:"  + p2 +
                                    "}";
                            JSONObject jsonObject = null;

                            try { jsonObject = new JSONObject(jsonString); } catch (Exception e){ e.printStackTrace(); }
                            System.out.println(jsonObject);
                            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                JSONObject content = response.getJSONObject("content");
                                                String mensaje = content.getString("descripcion");
                                                if (content.getInt("codigo") != Mensaje.user_passw_no_valid) {
                                                    //  Refresca la actividad //
                                                    finish();
                                                    overridePendingTransition(0, 0);
                                                    startActivity(getIntent());
                                                    overridePendingTransition(0, 0);

                                                    dialog.hide();
                                                    dialog.dismiss();
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
                            Toast.makeText(getApplicationContext(),"Contraseñas no coinciden",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }


}
