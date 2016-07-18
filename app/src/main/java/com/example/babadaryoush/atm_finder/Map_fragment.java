package com.example.babadaryoush.atm_finder;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class Map_fragment extends Fragment implements OnMapReadyCallback, LocationListener {

    private LocationManager locationManager;
    private String provider;
    private Double latitude=0.0;
    private Double longitude=0.0;

    public Map_fragment() {
    }

    private MapView mapView;
    private GoogleMap googleMap;

    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.map_layout, container, false);
       /* System.out.println("location-> " + ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)+", other-> "+ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_CALENDAR));*/

       /*LocationManager service = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);*/

        /*if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(PERMISSIONS, 1337);
            } else {
                Toast.makeText(getContext(), "permission error", Toast.LENGTH_SHORT).show();            }
        }
        else {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            provider = locationManager.getBestProvider(criteria, true);
            Location location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                System.out.println("Provider " + provider + " has been selected.");
                onLocationChanged(location);
            } else {
                System.out.println("location not available");
            }*/
            Location location = getMyLocation();
            if (location != null) {
                onLocationChanged(location);

            } else {
                System.out.println("location not available");
            }
        //}
            return rootView;
        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;
        //getActivity().recreate();
        Intent starterIntent = getActivity().getIntent();
        getActivity().finish();
        startActivity(starterIntent);
        //relancer l'activity
         //map.setMyLocationEnabled(true);
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
                locationManager.requestLocationUpdates(provider, 0, 0, this);
                Location l = locationManager.getLastKnownLocation(provider);

                if (l == null)
                    continue;

                // Found best last known location: %s", l);
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
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        ArrayList<Bank> banks = new ArrayList<Bank>();
        //Faire une liste des banque la parcourir et en faire des marqueurs
        /*Bank cic1 = new Bank("CIC", 48.853009, 2.370746);
        Bank lcl1 = new Bank("LCL", 48.852027, 2.37407);
        Bank lcl2 = new Bank("LCL", 48.8515021, 2.374523599999975);
        Bank bred1 = new Bank("Banque Populaire", 48.8509561, 2.378124200000002);
        Bank sg1 = new Bank("Société Générale", 48.85068529999999, 2.3777853999999934);
        Bank bnp1 = new Bank("BNP", 48.8503036, 2.3810077000000547);
        Bank ca1 = new Bank("Crédit Agricole", 48.84695, 2.387495);
        Bank bred2 = new Bank("Banque Populaire", 48.8471462, 2.3868104000000585);
        Bank bnp2 = new Bank("BNP", 48.84656769999999, 2.38799240000003);
        banks.add(cic1);
        banks.add(lcl1);
        banks.add(lcl2);
        banks.add(bred1);
        banks.add(sg1);
        banks.add(bnp1);
        banks.add(ca1);
        banks.add(bred2);
        banks.add(bnp2);
        for (Bank bank : banks) {
            LatLng location1 = new LatLng(bank.getLatitude(), bank.getLongitude());
            map.addMarker(new MarkerOptions().position(location1).title(bank.getName()).icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        }*/
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;
        System.out.println("mapasync: latitude->"+latitude+", longitude->"+longitude);
            LatLng location = new LatLng(latitude, longitude);

            map.addMarker(new MarkerOptions().position(location).title("Chez Michax la tchoin"));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 13.0f));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                       || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                   return;
        //locationManager.requestLocationUpdates(provider, 400, 1, this);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        List<String> providers = locationManager.getProviders(true);

        for (String provider : providers) {
            locationManager.requestLocationUpdates(provider, 0, 0, this);
        }/*
        Location location = getMyLocation();
        if (location != null) {
            onLocationChanged(location);

        } else {
            System.out.println("location not available");
        }*/
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

}