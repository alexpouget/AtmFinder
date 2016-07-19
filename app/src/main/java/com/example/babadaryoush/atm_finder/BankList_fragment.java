package com.example.babadaryoush.atm_finder;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Baba Daryoush on 02/07/2016.
 */

//fragment pour effectuer une recherche. On entre une localisation et la map s'actualise avec la position entrée et les ATM les + proches
public class BankList_fragment extends Fragment {
    public BankList_fragment(){}
    static boolean fromListViewClicked;

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
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

                Object j = lv.getItemAtPosition(position);
                Toast.makeText(getActivity(),""+j,
                        Toast.LENGTH_LONG).show();
            }
        });
        return rootView;
    }
}
