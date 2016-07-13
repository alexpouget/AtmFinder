package com.example.babadaryoush.atm_finder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map_fragment extends Fragment implements OnMapReadyCallback {

    public Map_fragment(){}

    private MapView mapView;
    private GoogleMap googleMap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.map_layout, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mf = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.maps);
        mf.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        //Faire une liste des banque la parcourir et en faire des marqueurs
        LatLng location = new LatLng(48.55, 3);
        map.addMarker(new MarkerOptions().position(location).title("Chez Michax la tchoin"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13));
    }
}