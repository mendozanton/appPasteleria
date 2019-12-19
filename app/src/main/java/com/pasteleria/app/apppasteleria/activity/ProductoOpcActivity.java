package com.pasteleria.app.apppasteleria.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.pasteleria.app.apppasteleria.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ProductoOpcActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView textView1,textView2,textView3;
    private ImageButton imageButton1,imageButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_opc);

        imageView = findViewById(R.id.ivProd_opc);
        textView1 = findViewById(R.id.tvProdDesc_opc);
        textView2 = findViewById(R.id.tvProdPrec_opc);
        textView3 = findViewById(R.id.tvCantidad);

        imageButton1 = findViewById(R.id.imgbtMinus);
        imageButton2 = findViewById(R.id.imgbtPlus);

        Intent intent = getIntent();

        Double precio = intent.getExtras().getDouble("precioProd");
        String desc = intent.getExtras().getString("descripProd");
        String srcImg = intent.getExtras().getString("imgProd");

        Picasso.with(this)
                .load(srcImg)
                .fit()
                .centerCrop()
                .into(imageView);
        DecimalFormat formato = new DecimalFormat("#,###.00");

        textView2.setText("PEN S/. " + formato.format(precio));
        textView1.setText(desc);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cant =  Integer.parseInt(textView3.getText().toString());
                if (cant > 1) cant = cant - 1;
                textView3.setText(String.valueOf(cant));
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cant =  Integer.parseInt(textView3.getText().toString());
                cant = cant + 1;
                textView3.setText(String.valueOf(cant));
            }
        });

    }
}
