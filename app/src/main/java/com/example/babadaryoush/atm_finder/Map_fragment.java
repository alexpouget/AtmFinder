package com.example.babadaryoush.atm_finder;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.AlertDialog;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Map_fragment extends Fragment implements OnMapReadyCallback, LocationListener {

    private LocationManager locationManager;
    private String provider;
    private Double latitude=0.0;
    private Double longitude=0.0;
    private int i=0;
    public static LatLng myLocation;
    public static ArrayList<Bank> bankList;
    public static Double bankLat;
    public static Double bankLng;
    private Button btn;


    public Map_fragment() {
    }

    private MapView mapView;
    public static GoogleMap googleMap;

    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.map_layout, container, false);
            Location location = getMyLocation();
            if (location != null) {
                onLocationChanged(location);

            } else {
                System.out.println("location not available");
            }
        return rootView;
        }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;
        Intent starterIntent = getActivity().getIntent();
        getActivity().finish();
        startActivity(starterIntent);
    }


    Location getMyLocation(){
        Location bestLocation = null;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(PERMISSIONS, 1337);
            } else {
                Toast.makeText(getContext(), "permission error", Toast.LENGTH_SHORT).show();            }
        }
        else {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            List<String> providers = locationManager.getProviders(true);

            for (String provider : providers) {
                locationManager.requestLocationUpdates(provider, 1500, 0, this);
                Location l = locationManager.getLastKnownLocation(provider);

                if (l == null)
                    continue;

                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy())
                    bestLocation = l;
            }
        }
        return bestLocation;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.maps);
        googleMap = mapFragment.getMap();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        myLocation=new LatLng(latitude, longitude);
        bankList = new ArrayList<>();
        bankList = nearestBanks_List();
        btn = (Button) getActivity().findViewById(R.id.filterDeactivate);

        if(geolocEnabled()) {

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                return;
            //latitude=48.849265;
            //longitude=2.389843;
            LatLng location = new LatLng(latitude, longitude);

            if (i > 0) map.clear();
            map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.m)).position(location).title("Ma position"));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15.0f));
            i++;

            if(!BankList_fragment.fromListViewClicked && !Search_fragment.fromSearchExecuted) {
                /*Parcours de la list des ATM aux alentours et ajout du marqueur sur la carte*/
                for (Bank bank : bankList) {
                    LatLng location1 = new LatLng(bank.getLatitude(), bank.getLongitude());
                    map.addMarker(new MarkerOptions().position(location1).title(bank.getName() + " " +
                            bank.getAddress()).icon(BitmapDescriptorFactory.fromResource(R.drawable.euro)));
                }
            }
            if(BankList_fragment.fromListViewClicked){
                Double blatitude = bankLat;
                Double blongitude = bankLng;
                btn.setVisibility(View.VISIBLE);
                btn.setOnClickListener(deactivateFilterBtnListener);
                onMapFiltered(blatitude, blongitude, map);
            }

            if(Search_fragment.fromSearchExecuted){
                btn.setVisibility(View.VISIBLE);
                btn.setOnClickListener(deactivateFilterBtnListener);
                onMapFilteredByName(Search_fragment.name2filterOn, map);
            }

            Bank.getNearestBankList(bankList, location);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                       || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                   return;
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        List<String> providers = locationManager.getProviders(true);
    if(BankList_fragment.fromListViewClicked ||Search_fragment.fromSearchExecuted) {
        for (String provider : providers) {
            locationManager.requestLocationUpdates(provider, 15000000, 0, this);
        }
    }   if(!BankList_fragment.fromListViewClicked ||!Search_fragment.fromSearchExecuted) {
            {
                for (String provider : providers) {
                    locationManager.requestLocationUpdates(provider, 1500, 0, this);
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;
            locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);
    }

    boolean geolocEnabled(){
        LocationManager service = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        boolean enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        return enabled;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {


    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    //retourne les banques dans un rayon de 1km
    public ArrayList<Bank> nearestBanks_List() {
        ArrayList<Bank>lstBank = new ArrayList<>();

        InputStreamReader is = null;
        try {
            is = new InputStreamReader(getActivity().getApplicationContext().getAssets().open("F-ATM.csv"));
            BufferedReader reader = new BufferedReader(is);
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                    String[] lines = line.split(",");
                    Double lat = new Double(lines[1]);
                    Double lon = new Double(lines[0]);
                if(Bank.distance(latitude, longitude, lat, lon)<1000) {
                    Bank bank = new Bank(lat, lon, lines[2], lines[3]);
                    lstBank.add(bank);
                }
            }
        } catch (IOException e) {
        }
            return lstBank;
    }

    //dans le cas de la listview cliquée on focus sur l'ATM cliqué
    public static void onMapFiltered(Double lat, Double lng, GoogleMap map) {
        for (Bank bank : bankList) {
            LatLng location1 = new LatLng(bank.getLatitude(), bank.getLongitude());
            if(bank.getLatitude()==lat && bank.getLongitude()==lng) {
                map.addMarker(new MarkerOptions().position(location1).title(bank.getName() + " " +
                        bank.getAddress()).icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder)));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(location1, 15.0f));
            }
        }
    }

    //dans le cas de la recherche validée on affiche tous les ATM correspondants
    public void onMapFilteredByName(String name, GoogleMap map) {
    Boolean found = false;
        for (Bank bank : bankList) {
            LatLng location1 = new LatLng(bank.getLatitude(), bank.getLongitude());
            if(bank.getName().toLowerCase().contains(name.toLowerCase())) {
                found = true;
                map.addMarker(new MarkerOptions().position(location1).title(bank.getName() + " " +
                        bank.getAddress()).icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder)));
            }
        }
        if(!found){
            btn = (Button) getActivity().findViewById(R.id.filterDeactivate);
            Toast.makeText(getContext(), "Aucun ATM trouvé correspondant à cette banque", Toast.LENGTH_SHORT).show();
            Fragment fragment = new Search_fragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        }

    }

    //listener du bouton "annuler filtre"
    View.OnClickListener deactivateFilterBtnListener = new View.OnClickListener() {
        public void onClick(View v) {
            BankList_fragment.fromListViewClicked=false;
            Search_fragment.fromSearchExecuted=false;
            Fragment fragment = new Map_fragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            Toast.makeText(getContext(), "Chargement en cours.\nVeuillez patienter quelques secondes", Toast.LENGTH_LONG).show();
            v.setVisibility(View.GONE);
            /*if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                return;
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);*/
        }
    };
    }

