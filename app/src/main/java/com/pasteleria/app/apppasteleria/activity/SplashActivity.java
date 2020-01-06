package com.pasteleria.app.apppasteleria.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.pasteleria.app.apppasteleria.R;

public class SplashActivity extends AppCompatActivity {

    private ImageView ivImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ivImagen = findViewById(R.id.ivImagen);
        Animation animation = AnimationUtils.loadAnimation(
                this, R.anim.transicion);

        ivImagen.startAnimation(animation);

        final Intent main = new Intent(
                SplashActivity.this,
                MainActivity.class);


        Thread timer = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(500);
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }finally {
                    startActivity(main);
                    finish();
                }
            }
        };
        timer.start();
    }

}
