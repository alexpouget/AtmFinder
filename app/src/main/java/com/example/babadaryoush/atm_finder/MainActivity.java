package com.example.babadaryoush.atm_finder;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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

        checkGeolocation();

        mTitle = mDrawerTitle = getTitle();
        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        setupToolbar();

        //si on veut + de menu dans le drawer augmenter le chiffre ci dessous
        Drawer_DataModel[] drawerItem = new Drawer_DataModel[4];

        drawerItem[0] = new Drawer_DataModel(/*R.drawable.connect,*/ "Accueil"); //en commentaire l'argument pour ajouter un icone
        drawerItem[1] = new Drawer_DataModel(/*R.drawable.fixtures,*/ "Mes ATM les plus proches"); //le décommenter dans Drawer_DataModel aussi
        drawerItem[2] = new Drawer_DataModel(/*R.drawable.fixtures,*/ "Rechercher"); //le décommenter dans Drawer_DataModel aussi
        drawerItem[3] = new Drawer_DataModel(/*R.drawable.fixtures,*/ "Paramètres"); //le décommenter dans Drawer_DataModel aussi
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        Drawer_ItemCustom adapter = new Drawer_ItemCustom(this, R.layout.drawer_listviewitem, drawerItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setupDrawerToggle();

        Map_fragment tah = null;

        tah = new Map_fragment();

        if (tah != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, tah).commit();
        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    //pour affciher l'icone de de déploiement du drawer (bouton sur lequel on clique pour déplier le drawer)
    void setupDrawerToggle(){
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.app_name, R.string.app_name);
        mDrawerToggle.syncState();
    }

    public void checkGeolocation(){
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);

        boolean enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!enabled) {
            new AlertDialog.Builder(this)
                    .setTitle("Geolocalisation désactivée")
                    .setMessage("Oups. La Géolocalisation n'est pas activée. Voulez-vous l'activer ?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //finish();
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

//onclick d'un des menus du drawer
    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

        private void selectItem(int position) {

            Fragment fragment = null;

            switch (position) {
                case 0:
                    fragment = new Map_fragment();
                    break;
                case 1:
                    fragment = new BankList_fragment();
                    break;
                case 2:
                    fragment = new Search_fragment();
                    break;
                case 3:
                    fragment = new Parametre_fragment();
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


