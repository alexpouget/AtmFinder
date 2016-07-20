package com.example.babadaryoush.atm_finder;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Baba Daryoush on 02/07/2016.
 */

//fragment pour effectuer une recherche. On entre une localisation et la map s'actualise avec la position entrée et les ATM les + proches
public class BankList_fragment extends Fragment {
    public BankList_fragment(){}
    static boolean fromListViewClicked=false;

    ListView mListView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.banklist_layout, container, false);

        final ListView lv = (ListView) rootView.findViewById(R.id.banksListview);

            ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1, Bank.banks2String);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                fromListViewClicked = true;
                Object j = lv.getItemAtPosition(position);
                String[] myloc = j.toString().split(":");
                String[] latlng = myloc[1].split("/");
                Map_fragment.bankLat = new Double(latlng[0]);
                Map_fragment.bankLng = new Double(latlng[1]);


                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }


}
