package com.example.babadaryoush.atm_finder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Baba Daryoush on 02/07/2016.
 */

public class Search_fragment extends Fragment {
    public static String name2filterOn;
    public static Boolean fromSearchExecuted=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.search_layout, container, false);

        Button btn = (Button) rootView.findViewById(R.id.searchBtnValider);
        btn.setVisibility(View.VISIBLE);
        btn.setOnClickListener(searchBtnListener);
        return rootView;
    }
    View.OnClickListener searchBtnListener = new View.OnClickListener() {
        public void onClick(View v) {
            fromSearchExecuted = true;
            EditText txt = (EditText) getActivity().findViewById(R.id.searchTxtField);
            name2filterOn = txt.getText().toString();
            if(name2filterOn==null || name2filterOn=="" || name2filterOn==""){
                Toast.makeText(getContext(), "Champ texte vide", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);

        }
    };
}
