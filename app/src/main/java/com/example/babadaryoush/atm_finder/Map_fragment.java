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

import java.util.ArrayList;
import java.util.List;

public class Map_fragment extends Fragment implements OnMapReadyCallback {

    public Map_fragment(){}

    private MapView mapView;
    private GoogleMap googleMap;
    //private ArrayList<Bank> bankList;
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
        ArrayList<Bank> banks = new ArrayList<Bank>();
        //Faire une liste des banque la parcourir et en faire des marqueurs
        Bank bnpNangis = new Bank ("BNP Nangis", 48.56, 3);
        Bank caNangis = new Bank("CA Nangis", 48.60, 3);
        banks.add(bnpNangis);
        banks.add(caNangis);
        for (Bank bank:banks) {
        LatLng location1 = new LatLng(bank.getLatitude(), bank.getLongitude());
        map.addMarker(new MarkerOptions().position(location1).title(bank.getName()));
    }
        LatLng location = new LatLng(48.55, 3);
        map.addMarker(new MarkerOptions().position(location).title("Chez Michax la tchoin"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13));
    }
}