package com.example.babadaryoush.atm_finder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Baba Daryoush on 02/07/2016.
 */

//fragment pour effectuer une recherche. On entre une localisation et la map s'actualise avec la position entrée et les ATM les + proches
public class BankList_fragment extends Fragment {
    public BankList_fragment(){}

    ListView mListView;
    String[] banks = new String[]{"BNP Champs Elysée", "Crédit Agricole", "Crédit Lyonnais", "Suce un chybre de qualité"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.banklist_layout, container, false);

        ListView lv = (ListView) rootView.findViewById(R.id.banksListview);

                ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1, Bank.banks2String);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
            }
        });
        return rootView;
    }
}
