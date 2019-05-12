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
import com.adibu.shipmonitoring.view.helper.ImageBlinking;
import com.adibu.shipmonitoring.view.helper.RecyclerViewItemOffsetDecoration;
import com.adibu.shipmonitoring.view.viewmodel.MainActivityViewModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CoolingFragment extends Fragment {

    //Gauge List
    private List<GaugeModel> mCoolingGaugeList;

    private GaugeAdapter mGaugeAdapter;

    private List<ImageView> mWarningViewList;

    private MainActivityViewModel mViewModel;

    public CoolingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cooling, container, false);

        mViewModel = ViewModelProviders.of(getActivity()).get(MainActivityViewModel.class);

        mCoolingGaugeList = new ArrayList<>();
        mCoolingGaugeList.add(new GaugeModel("FW Temp. After ME", "°C", 0, 100, "fwtempafterme", R.string.cooling));
        mCoolingGaugeList.add(new GaugeModel("FW Temp Before ME", "°C", 0, 100, "fwtempbeforeme", R.string.cooling));
        mCoolingGaugeList.add(new GaugeModel("FW Press. Before ME", "MPa",0, 100, "fwpressbeforeme", R.string.cooling));
        mCoolingGaugeList.add(new GaugeModel("SW Press. Before CU","MPa",0, 1, "swpressbeforecu", R.string.cooling));
        mCoolingGaugeList.add(new GaugeModel("Vol. Tank Exp.","%",0, 100, "voltankexp", R.string.cooling));

        mWarningViewList = new ArrayList<>();
        mWarningViewList.add((ImageView) view.findViewById(R.id.cooling_fwtempafterme_sign1));
        mWarningViewList.add((ImageView) view.findViewById(R.id.cooling_fwtempbeforeme_sign2));
        mWarningViewList.add((ImageView) view.findViewById(R.id.cooling_fwpressbeforeme_sign3));
        mWarningViewList.add((ImageView) view.findViewById(R.id.cooling_swpressbeforecu_sign4));
        mWarningViewList.add((ImageView) view.findViewById(R.id.cooling_voltankexp_sign5));

        RecyclerView recyclerView = view.findViewById(R.id.activity_recycler_view);
        mGaugeAdapter = new GaugeAdapter(getActivity(), mCoolingGaugeList);
        RecyclerViewItemOffsetDecoration itemDecoration = new RecyclerViewItemOffsetDecoration(getActivity(), R.dimen.item_offset);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(mGaugeAdapter);

        ImageView imageView = view.findViewById(R.id.image_view_diagram);
        imageView.setImageResource(R.drawable.cooling_model);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.getCoolingData().observe(this, new Observer<List<KeyValueModel>>() {
            @Override
            public void onChanged(List<KeyValueModel> keyValueModels) {
                Log.d("Observe Cooling: ", keyValueModels.toString());
                for (int i=0;i<keyValueModels.size();i++) {
                    float value = Float.valueOf(keyValueModels.get(i).getValue());
                    switch (keyValueModels.get(i).getKey()) {
                        case "fwtempafterme":
                            mCoolingGaugeList.get(0).setCurrent(value);
                            mGaugeAdapter.notifyDataSetChanged();
                            if(value<40) {
                                ImageBlinking.imageBlinking(mWarningViewList.get(0));
                            }
                            else if(value>90) {
                                ImageBlinking.imageBlinking(mWarningViewList.get(0));
                            } else {
                                mWarningViewList.get(0).clearAnimation();
                            }
                            break;
                        case "fwtempbeforeme":
                            mCoolingGaugeList.get(1).setCurrent(value);
                            mGaugeAdapter.notifyDataSetChanged();
                            if(value<30) {
                                ImageBlinking.imageBlinking(mWarningViewList.get(1));
                            }
                            else if(value>50) {
                                ImageBlinking.imageBlinking(mWarningViewList.get(1));
                            } else {
                                mWarningViewList.get(1).clearAnimation();
                            }
                            break;
                        case "fwpressbeforeme":
                            mCoolingGaugeList.get(2).setCurrent(value);
                            mGaugeAdapter.notifyDataSetChanged();
                            if(value<40) {
                                ImageBlinking.imageBlinking(mWarningViewList.get(2));
                            }
                            else if(value>50) {
                                ImageBlinking.imageBlinking(mWarningViewList.get(2));
                            } else {
                                mWarningViewList.get(2).clearAnimation();
                            }
                            break;
                        case "swpressbeforecu":
                            mCoolingGaugeList.get(3).setCurrent(value);
                            mGaugeAdapter.notifyDataSetChanged();
                            if(value<0.15) {
                                ImageBlinking.imageBlinking(mWarningViewList.get(3));
                            } else {
                                mWarningViewList.get(3).clearAnimation();
                            }
                            break;
                        case "voltankexp":
                            mCoolingGaugeList.get(4).setCurrent(value);
                            mGaugeAdapter.notifyDataSetChanged();
                            if(value<50) {
                                ImageBlinking.imageBlinking(mWarningViewList.get(4));
                            } else {
                                mWarningViewList.get(4).clearAnimation();
                            }
                            break;
                    }
                }
            }
        });
    }
}
