package com.pasteleria.app.apppasteleria.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pasteleria.app.apppasteleria.R;
import com.pasteleria.app.apppasteleria.services.ConnectApi;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    private Button btnIngresar,btnRegistrar;
    private EditText etUsuario, etPassword;
    private RequestQueue mQueue;
    private String url = ConnectApi.url_local + ConnectApi.url_accses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsuario =  findViewById(R.id.etUsuario);
        etPassword =  findViewById(R.id.etPassword);
        btnIngresar = findViewById(R.id.btnLogin);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        mQueue = Volley.newRequestQueue(this);

        SharedPreferences preferences = getSharedPreferences(
                "users", MODE_PRIVATE);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, String> params = new HashMap();
                params.put("email", etUsuario.getText().toString());
                params.put("password", etPassword.getText().toString());

                JSONObject jsonObject = new JSONObject(params);


                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject reponseContent = response.getJSONObject("content");
                                    int codigoResponse = reponseContent.getInt("codigo");

                                    if (codigoResponse == 2) {
                                        int id = reponseContent.getJSONObject("data").getInt("idUsuario");
                                        SharedPreferences.Editor editor =
                                                getSharedPreferences("users", MODE_PRIVATE).edit();
                                        editor.putString("idUsuario", String.valueOf(id));
                                        editor.apply();
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    }

                                    if (codigoResponse == 1) {
                                        Toast.makeText(getApplicationContext(),"Contraseña incorrecta",
                                                Toast.LENGTH_LONG).show();
                                    }
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
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}
