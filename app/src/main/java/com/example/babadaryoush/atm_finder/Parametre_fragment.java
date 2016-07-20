package com.example.babadaryoush.atm_finder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Baba Daryoush on 02/07/2016.
 */

public class Parametre_fragment extends Fragment {
    public static int range=0;
    private Button btn;
    private EditText numb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.parametre_layout, container, false);
        btn = (Button) rootView.findViewById(R.id.button);
        btn.setOnClickListener(rangeBtnListener);
        numb = (EditText) rootView.findViewById(R.id.txtRange);
        numb.setText(""+range, TextView.BufferType.EDITABLE);
        return rootView;
    }

    View.OnClickListener rangeBtnListener = new View.OnClickListener() {
        public void onClick(View v) {

            String j = numb.getText().toString();
            int f = new Integer(j);
            if(f<50 || f>5000){
                Toast.makeText(getContext(), "Périmètre minimum de 50m et maximum de 5000m", Toast.LENGTH_LONG).show();
            }
            else{
                range = f;
                Fragment fragment = new Map_fragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                Toast.makeText(getContext(), "Mise à jour avec périmètre de"+range+"m", Toast.LENGTH_LONG).show();

            }
        }
    };
}
