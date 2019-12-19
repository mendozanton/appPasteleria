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

import com.pasteleria.app.apppasteleria.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductoInfoActivity extends AppCompatActivity {
    private ImageView imagen;
    private TextView precio,descripcion;
    private Context context = this;
    private String img,desc;
    private Double prec;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navi_cesta:
                    llamarProductoOpcActivity(0);
                    break;
                case R.id.navi_comp:
                    llamarProductoOpcActivity(1);
                    break;
            }
            return true;

        }
    };

    private void llamarProductoOpcActivity(int op) {
        Intent intent = new Intent(context, ProductoOpcActivity.class);
        intent.putExtra("imgProd",img);
        intent.putExtra("precioProd",prec);
        intent.putExtra("descripProd",desc);
        intent.putExtra("op",op);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_info);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_compra);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        imagen = findViewById(R.id.ivProdDetalle);
        precio = findViewById(R.id.tvProDeta_precio);
        descripcion = findViewById(R.id.tvProDeta_descrip);

        Intent intent = getIntent();

        ArrayList<String> imgs = intent.getExtras().getStringArrayList("imagesProd");

        for (int i = 0; i < imgs.size(); i++){

        }
        img = imgs.get(0);
        prec = intent.getExtras().getDouble("precioProd");
        desc = intent.getExtras().getString("descripProd");

        Picasso.with(context)
                .load(img)
                .fit()
                .centerCrop()
                .into(imagen);

        DecimalFormat formato = new DecimalFormat("#,###.00");

        precio.setText("PEN S/. " + formato.format(prec));
        descripcion.setText(desc);
    }
}
