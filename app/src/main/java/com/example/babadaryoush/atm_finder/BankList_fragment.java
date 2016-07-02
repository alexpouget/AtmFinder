package com.example.babadaryoush.atm_finder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Baba Daryoush on 02/07/2016.
 */

//fragment pour effectuer une recherche. On entre une localisation et la map s'actualise avec la position entrée et les ATM les + proches
public class BankList_fragment extends Fragment {
    public BankList_fragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.banklist_layout, container, false);

        return rootView;
    }
}
