package com.example.babadaryoush.atm_finder;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.example.babadaryoush.atm_finder.R.id.search_button;

/**
 * Created by Baba Daryoush on 27/06/2016.
 */

public class MainActivity extends AppCompatActivity {


    public MainActivity() {
    }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Map_fragment tah = null;

        tah = new Map_fragment();

        if (tah != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, tah).commit();
        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }

/*        Button button = (Button) findViewById(R.id.sex);
        button.setOnClickListener(new View.OnClickListener() {
            Map_fragment tah = null;
            @Override
            public void onClick(View v) {
                 tah = new Map_fragment();

                if (tah != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, tah).commit();
                } else {
                    Log.e("MainActivity", "Error in creating fragment");
                }
            }
        });
*/
    }
}
