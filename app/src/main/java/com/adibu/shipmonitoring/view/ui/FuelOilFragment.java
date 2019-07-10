package com.adibu.shipmonitoring.view.ui;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.adibu.shipmonitoring.R;
import com.adibu.shipmonitoring.model.GaugeModel;
import com.adibu.shipmonitoring.model.KeyValueModel;
import com.adibu.shipmonitoring.view.adapter.GaugeAdapter;
import com.adibu.shipmonitoring.view.helper.MethodHelper;
import com.adibu.shipmonitoring.view.helper.RecyclerViewItemOffsetDecoration;
import com.adibu.shipmonitoring.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FuelOilFragment extends Fragment {

    //Gauge List
    private List<GaugeModel> mFuelOilGaugeList;

    private GaugeAdapter mGaugeAdapter;

    private List<ImageView> mWarningViewList;

    private MainActivityViewModel mViewModel;

    public FuelOilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fuel_oil, container, false);

        mViewModel = ViewModelProviders.of(getActivity()).get(MainActivityViewModel.class);

        mFuelOilGaugeList = new ArrayList<>();
        mFuelOilGaugeList.add(new GaugeModel("Supply Pump Press", "Mpa", 0, 1, "fosupplypumppress", R.string.fueloil));
        mFuelOilGaugeList.add(new GaugeModel("Circl. Pump Press.", "Mpa", 0, 2, "focirclpumppress", R.string.fueloil));
        mFuelOilGaugeList.add(new GaugeModel("Settling Tank Lvl", "cm",0, 200, "fosettlingtanklvl", R.string.fueloil));
        mFuelOilGaugeList.add(new GaugeModel("Service Tank Lvl", "cm",0, 200, "foservicetanklvl", R.string.fueloil));
        mFuelOilGaugeList.add(new GaugeModel("Service Tank Temp.", "°C",0, 100, "foservicetanktemp", R.string.fueloil));
        mFuelOilGaugeList.add(new GaugeModel("Settling Tank Temp.", "°C",0, 100, "fosettlingtanktemp", R.string.fueloil));
        mFuelOilGaugeList.add(new GaugeModel("Temp. Before ME", "°C",0, 200, "fotempbeforeme", R.string.fueloil));


        mWarningViewList = new ArrayList<>();
        mWarningViewList.add((ImageView) view.findViewById(R.id.fo_a1));
        mWarningViewList.add((ImageView) view.findViewById(R.id.fo_a2));
        mWarningViewList.add((ImageView) view.findViewById(R.id.fo_b1));
        mWarningViewList.add((ImageView) view.findViewById(R.id.fo_b2));
        mWarningViewList.add((ImageView) view.findViewById(R.id.fo_c1));
        mWarningViewList.add((ImageView) view.findViewById(R.id.fo_c2));
        mWarningViewList.add((ImageView) view.findViewById(R.id.fo_d1));
        mWarningViewList.add((ImageView) view.findViewById(R.id.fo_d2));
        mWarningViewList.add((ImageView) view.findViewById(R.id.fo_e1));
        mWarningViewList.add((ImageView) view.findViewById(R.id.fo_e2));
        mWarningViewList.add((ImageView) view.findViewById(R.id.fo_f1));
        mWarningViewList.add((ImageView) view.findViewById(R.id.fo_f2));
        mWarningViewList.add((ImageView) view.findViewById(R.id.fo_g));

        RecyclerView recyclerView = view.findViewById(R.id.fuel_oil_recycler_view);
        mGaugeAdapter = new GaugeAdapter(getActivity(), mFuelOilGaugeList);
        RecyclerViewItemOffsetDecoration itemDecoration = new RecyclerViewItemOffsetDecoration(getActivity(), R.dimen.item_offset);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(mGaugeAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.getData().observe(this, new Observer<List<KeyValueModel>>() {
            @Override
            public void onChanged(List<KeyValueModel> keyValueModels) {
                Log.d("Observe Data(FO Frag): ", keyValueModels.toString());
                for (int i=0;i<keyValueModels.size();i++) {
                    float value = Float.valueOf(keyValueModels.get(i).getValue());
                    switch (keyValueModels.get(i).getKey()) {
                        case "fosupplypumppress":
                            mFuelOilGaugeList.get(0).setCurrent(value);
                            mGaugeAdapter.notifyDataSetChanged();
                            if(value<0.25) {
                                MethodHelper.imageBlinking(mWarningViewList.get(0));
                                MethodHelper.imageBlinking(mWarningViewList.get(1));
                            }
                            else {
                                mWarningViewList.get(0).clearAnimation();
                                mWarningViewList.get(1).clearAnimation();
                            }
                            break;
                        case "focirclpumppress":
                            mFuelOilGaugeList.get(1).setCurrent(value);
                            mGaugeAdapter.notifyDataSetChanged();
                            if(value<1.4 || value>1.8) {
                                MethodHelper.imageBlinking(mWarningViewList.get(2));
                                MethodHelper.imageBlinking(mWarningViewList.get(3));
                            }
                            else {
                                mWarningViewList.get(2).clearAnimation();
                                mWarningViewList.get(3).clearAnimation();
                            }
                            break;
                        case "fosettlingtanklvl":
                            mFuelOilGaugeList.get(2).setCurrent(value);
                            mGaugeAdapter.notifyDataSetChanged();
                            if(value<40 || value>190) {
                                MethodHelper.imageBlinking(mWarningViewList.get(4));
                                MethodHelper.imageBlinking(mWarningViewList.get(5));
                            }
                            else {
                                mWarningViewList.get(4).clearAnimation();
                                mWarningViewList.get(5).clearAnimation();
                            }
                            break;
                        case "foservicetanklvl":
                            mFuelOilGaugeList.get(3).setCurrent(value);
                            mGaugeAdapter.notifyDataSetChanged();
                            if(value<40 || value>190) {
                                MethodHelper.imageBlinking(mWarningViewList.get(6));
                                MethodHelper.imageBlinking(mWarningViewList.get(7));
                            }
                            else {
                                mWarningViewList.get(6).clearAnimation();
                                mWarningViewList.get(7).clearAnimation();
                            }
                            break;
                        case "foservicetanktemp":
                            mFuelOilGaugeList.get(4).setCurrent(value);
                            mGaugeAdapter.notifyDataSetChanged();
                            if(value<70 || value>90) {
                                MethodHelper.imageBlinking(mWarningViewList.get(8));
                                MethodHelper.imageBlinking(mWarningViewList.get(9));
                            }
                            else {
                                mWarningViewList.get(8).clearAnimation();
                                mWarningViewList.get(9).clearAnimation();
                            }
                            break;
                        case "fosettlingtanktemp":
                            mFuelOilGaugeList.get(5).setCurrent(value);
                            mGaugeAdapter.notifyDataSetChanged();
                            if(value<50 || value>70) {
                                MethodHelper.imageBlinking(mWarningViewList.get(10));
                                MethodHelper.imageBlinking(mWarningViewList.get(11));
                            }
                            else {
                                mWarningViewList.get(10).clearAnimation();
                                mWarningViewList.get(11).clearAnimation();
                            }
                            break;
                        case "fotempbeforeme":
                            mFuelOilGaugeList.get(6).setCurrent(value);
                            mGaugeAdapter.notifyDataSetChanged();
                            if(value<130 || value>150) {
                                MethodHelper.imageBlinking(mWarningViewList.get(12));
                            }
                            else {
                                mWarningViewList.get(12).clearAnimation();
                            }
                            break;
                    }
                }
            }
        });
    }
}
