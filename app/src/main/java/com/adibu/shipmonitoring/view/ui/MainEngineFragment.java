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
public class MainEngineFragment extends Fragment {

    //Gauge List
    private List<GaugeModel> mMainEngineGaugeList;

    private GaugeAdapter mGaugeAdapter;

    private List<ImageView> mWarningViewList;

    private MainActivityViewModel mViewModel;

    public MainEngineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_engine, container, false);

        mViewModel = ViewModelProviders.of(getActivity()).get(MainActivityViewModel.class);

        mMainEngineGaugeList = new ArrayList<>();
        mMainEngineGaugeList.add(new GaugeModel("Starting Air", "Mpa", 0, 10, "startingair", R.string.mainengine));
        mMainEngineGaugeList.add(new GaugeModel("Gas Temp Before Turbo", "°C", 0, 100, "gastempbefore", R.string.mainengine));
        mMainEngineGaugeList.add(new GaugeModel("Gas Temp After Turbo", "°C",0, 500, "gastempafter", R.string.mainengine));

        mWarningViewList = new ArrayList<>();
        mWarningViewList.add((ImageView) view.findViewById(R.id.me_a));
        mWarningViewList.add((ImageView) view.findViewById(R.id.me_b));
        mWarningViewList.add((ImageView) view.findViewById(R.id.me_c));

        RecyclerView recyclerView = view.findViewById(R.id.main_engine_recycler_view);
        mGaugeAdapter = new GaugeAdapter(getActivity(), mMainEngineGaugeList);
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
                Log.d("Observe Data(ME Frag): ", keyValueModels.toString());
                for (int i=0;i<keyValueModels.size();i++) {
                    float value = Float.valueOf(keyValueModels.get(i).getValue());
                    switch (keyValueModels.get(i).getKey()) {
                        case "startingair":
                            mMainEngineGaugeList.get(0).setCurrent(value);
                            mGaugeAdapter.notifyDataSetChanged();
                            if(value<2 || value>4) {
                                MethodHelper.imageBlinking(mWarningViewList.get(0));
                            }
                            else {
                                mWarningViewList.get(0).clearAnimation();
                            }
                            break;
                        case "gastempbefore":
                            mMainEngineGaugeList.get(1).setCurrent(value);
                            mGaugeAdapter.notifyDataSetChanged();
                            if(value<30 || value>45) {
                                MethodHelper.imageBlinking(mWarningViewList.get(1));
                            }
                            else {
                                mWarningViewList.get(1).clearAnimation();
                            }
                            break;
                        case "gastempafter":
                            mMainEngineGaugeList.get(2).setCurrent(value);
                            mGaugeAdapter.notifyDataSetChanged();
                            if(value<300 || value>370) {
                                MethodHelper.imageBlinking(mWarningViewList.get(2));
                            }
                            else {
                                mWarningViewList.get(2).clearAnimation();
                            }
                            break;
                    }
                }
            }
        });
    }
}
