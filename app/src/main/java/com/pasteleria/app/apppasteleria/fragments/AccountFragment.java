package com.pasteleria.app.apppasteleria.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.pasteleria.app.apppasteleria.activity.LoginActivity;
import com.pasteleria.app.apppasteleria.activity.MainActivity;
import com.pasteleria.app.apppasteleria.activity.SettingsActivity;
import com.pasteleria.app.apppasteleria.modelo.Imagen;
import com.pasteleria.app.apppasteleria.modelo.Producto;
import com.pasteleria.app.apppasteleria.modelo.Usuario;
import com.pasteleria.app.apppasteleria.services.ConnectApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 **/
public class AccountFragment extends Fragment {
    private TextView nom,ajustes;
    private boolean sesion;
    private LinearLayout layout;
    private SharedPreferences preferences;
    private RequestQueue mQueue;

    public AccountFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        nom = view.findViewById(R.id.tvHead_Faccout);
        ajustes = view.findViewById(R.id.tvAjustes_Faccount);
        mQueue = Volley.newRequestQueue(getContext());

        preferences = getActivity().getSharedPreferences(
                "users", getActivity().MODE_PRIVATE);

        if(preferences.contains("idUsuario")){
            int id = Integer.parseInt(preferences.getString("idUsuario",""));
            String url = ConnectApi.url_local + ConnectApi.url_user + id;

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject content= response.getJSONObject("content");
                                String nomApe = content.getString("nombre") + " " + content.getString("apellido");
                                nom.setText(nomApe);


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
            sesion = true;
        } else {
            nom.setText("INICIAR SESION");
            sesion = false;
        }
        layout = view.findViewById(R.id.linLayout_head_Faccout);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sesion) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    getContext().startActivity(intent);
                }
            }
        });

        ajustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sesion) {
                    Intent intent = new Intent(getContext(), SettingsActivity.class);
                    getContext().startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    getContext().startActivity(intent);
                }

            }
        });
        return view;
    }

}
