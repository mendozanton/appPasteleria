package com.pasteleria.app.apppasteleria;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private Button btnIngresar;
    private EditText etUsuario, etPassword;
    private ProgressBar progbar;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsuario =  findViewById(R.id.etUsuario);
        etPassword =  findViewById(R.id.etPassword);
        progbar = findViewById(R.id.progbar);
        btnIngresar = findViewById(R.id.btnLogin);

        mQueue = Volley.newRequestQueue(this);
        final String uriLogin = "https://pastelservices.azurewebsites.net/api/login";

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //btnIngresar.setVisibility(View.GONE);
                progbar.setVisibility(View.VISIBLE);

                Map<String, String> params = new HashMap();
                params.put("email", etUsuario.getText().toString());
                params.put("password", etPassword.getText().toString());

                JSONObject jsonObject = new JSONObject(params);


                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, uriLogin, jsonObject,
                        new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject reponseContent = response.getJSONObject("content");

                            int codigoResponse = reponseContent.getInt("codigo");


                            if (codigoResponse == 2) {
                                progbar.setVisibility(View.GONE);
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            }

                            if (codigoResponse == 1) {
                                progbar.setVisibility(View.GONE);
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
    }
}
