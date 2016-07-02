package com.example.babadaryoush.atm_finder;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    Toolbar toolbar;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /***************DRAWER****************/
        mTitle = mDrawerTitle = getTitle();
        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        setupToolbar();

        Drawer_DataModel[] drawerItem = new Drawer_DataModel[2];

        drawerItem[0] = new Drawer_DataModel(/*R.drawable.connect,*/ "Mes ATM les plus proches"); //en commentaire l'argument pour ajouter un icone
        drawerItem[1] = new Drawer_DataModel(/*R.drawable.fixtures,*/ "Paramètres"); //le décommenter dans Drawer_DataModel aussi
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
/*      ***A décommenter et créer la classe DawerItemCustomAdapter****/
        Drawer_ItemCustom adapter = new Drawer_ItemCustom(this, R.layout.drawer_listviewitem, drawerItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //setupDrawerToggle();

        Map_fragment tah = null;

        tah = new Map_fragment();

        if (tah != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, tah).commit();
        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
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

    void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

        private void selectItem(int position) {

            Fragment fragment = null;

            switch (position) {
                case 0:
                    //fragment = new ConnectFragment();
                    break;
                case 1:
                    //fragment = new FixturesFragment();
                    break;
                case 2:
                    //fragment = new TableFragment();
                    break;

                default:
                    break;
            }

            if (fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

                mDrawerList.setItemChecked(position, true);
                mDrawerList.setSelection(position);
                setTitle(mNavigationDrawerItemTitles[position]);
                mDrawerLayout.closeDrawer(mDrawerList);

            } else {
                Log.e("MainActivity", "Error in creating fragment");
            }
        }
    }
}


