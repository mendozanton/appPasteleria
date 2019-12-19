package com.pasteleria.app.apppasteleria.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.pasteleria.app.apppasteleria.R;
import com.pasteleria.app.apppasteleria.fragments.ActividadFragment;
import com.pasteleria.app.apppasteleria.fragments.ComprasFragment;
import com.pasteleria.app.apppasteleria.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment active = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    active = new HomeFragment();
                    break;
                case R.id.navigation_dashboard:
                    active = new ActividadFragment();
                    break;
                case R.id.navigation_notifications:
                    active = new ComprasFragment();
                    break;
                    /*
                case R.id.navigation_account:
                    fm.beginTransaction().hide(active)
                            .show(ajustes).commit();
                    active = ajustes;
                    return true;
                    */
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content,
                    active).commit();
            return true;

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, new HomeFragment()).commit();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
