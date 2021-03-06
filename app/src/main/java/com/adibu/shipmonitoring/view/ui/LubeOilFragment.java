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
public class LubeOilFragment extends Fragment {

    //Gauge List
    private List<GaugeModel> mLubeOilGaugeList;

    private GaugeAdapter mGaugeAdapter;

    private List<ImageView> mWarningViewList;

    private MainActivityViewModel mViewModel;


    public LubeOilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lube_oil, container, false);

        mViewModel = ViewModelProviders.of(getActivity()).get(MainActivityViewModel.class);

        mLubeOilGaugeList = new ArrayList<>();
        mLubeOilGaugeList.add(new GaugeModel("LO Temp Before ME", "°C", 0, 100, "lotempbeforeme", R.string.lubeoil));
        mLubeOilGaugeList.add(new GaugeModel("LO Temp After ME", "°C",0, 100, "lotempafterme", R.string.lubeoil));
        mLubeOilGaugeList.add(new GaugeModel("LO Transfer Pump", "Mpa",0, 1, "lotransferpump", R.string.lubeoil));
        mLubeOilGaugeList.add(new GaugeModel("LO Circl. Pump Press.", "Mpa",0, 2, "locirclpumppress", R.string.lubeoil));
        mLubeOilGaugeList.add(new GaugeModel("LO Sump Tank Lvl", "cm",0, 150, "losumptanklvl", R.string.lubeoil));

        mWarningViewList = new ArrayList<>();
        mWarningViewList.add((ImageView) view.findViewById(R.id.lo_b));
        mWarningViewList.add((ImageView) view.findViewById(R.id.lo_c));
        mWarningViewList.add((ImageView) view.findViewById(R.id.lo_d));
        mWarningViewList.add((ImageView) view.findViewById(R.id.lo_e));
        mWarningViewList.add((ImageView) view.findViewById(R.id.lo_f));

        RecyclerView recyclerView = view.findViewById(R.id.lube_oil_recycler_view);
        mGaugeAdapter = new GaugeAdapter(getActivity(), mLubeOilGaugeList);
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
                Log.d("Observe Data(LO Frag): ", keyValueModels.toString());
                for (int i=0;i<keyValueModels.size();i++) {
                    float value = Float.valueOf(keyValueModels.get(i).getValue());
                    switch (keyValueModels.get(i).getKey()) {
                        case "lotempbeforeme":
                            mLubeOilGaugeList.get(0).setCurrent(value);
                            mGaugeAdapter.notifyDataSetChanged();
                            if(value<35 || value>50) {
                                MethodHelper.imageBlinking(mWarningViewList.get(0));
                            }
                            else {
                                mWarningViewList.get(0).clearAnimation();
                            }
                            break;
                        case "lotempafterme":
                            mLubeOilGaugeList.get(1).setCurrent(value);
                            mGaugeAdapter.notifyDataSetChanged();
                            if(value<75 || value>90) {
                                MethodHelper.imageBlinking(mWarningViewList.get(1));
                            }
                            else {
                                mWarningViewList.get(1).clearAnimation();
                            }
                            break;
                        case "lotransferpump":
                            mLubeOilGaugeList.get(2).setCurrent(value);
                            mGaugeAdapter.notifyDataSetChanged();
                            if(value<0.5 || value>0.7) {
                                MethodHelper.imageBlinking(mWarningViewList.get(2));
                            }
                            else {
                                mWarningViewList.get(2).clearAnimation();
                            }
                            break;
                        case "locirclpumppress":
                            mLubeOilGaugeList.get(3).setCurrent(value);
                            mGaugeAdapter.notifyDataSetChanged();
                            if(value<1.4 || value>1.8) {
                                MethodHelper.imageBlinking(mWarningViewList.get(3));
                            }
                            else {
                                mWarningViewList.get(3).clearAnimation();
                            }
                            break;
                        case "losumptanklvl":
                            mLubeOilGaugeList.get(4).setCurrent(value);
                            mGaugeAdapter.notifyDataSetChanged();
                            if(value<30 || value>120) {
                                MethodHelper.imageBlinking(mWarningViewList.get(4));
                            }
                            else {
                                mWarningViewList.get(4).clearAnimation();
                            }
                            break;
                    }
                }
            }
        });
    }

}
