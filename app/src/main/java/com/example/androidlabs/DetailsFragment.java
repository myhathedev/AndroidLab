package com.example.androidlabs;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DetailsFragment extends Fragment {
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_details, container, false);
        TextView fillName = view.findViewById(R.id.fill_me1);
        TextView fillHeight = view.findViewById(R.id.fill_me2);
        TextView fillMass = view.findViewById(R.id.fill_me3);
        System.out.println("---------"+getArguments().getString("name"));
        fillName.setText(getArguments().getString("name"));
        fillHeight.setText(getArguments().getString("height"));
        fillMass.setText(getArguments().getString("mass"));
        return view ;

    }
}