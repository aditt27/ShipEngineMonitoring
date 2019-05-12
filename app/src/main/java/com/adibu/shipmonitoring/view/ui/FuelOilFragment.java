package com.adibu.shipmonitoring.view.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adibu.shipmonitoring.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FuelOilFragment extends Fragment {


    public FuelOilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fuel_oil, container, false);

        // Inflate the layout for this fragment
        return view;
    }
}
