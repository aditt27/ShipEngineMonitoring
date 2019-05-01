package com.adibu.shipmonitoring.view.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.adibu.shipmonitoring.view.adapter.GaugeAdapter;
import com.adibu.shipmonitoring.view.helper.RecyclerViewItemOffsetDecoration;
import com.adibu.shipmonitoring.R;
import com.adibu.shipmonitoring.model.GaugeModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Gauge List
    private List<GaugeModel> mCoolingGaugeList;
    private List<GaugeModel> mFuelOilGaugeList;
    private List<GaugeModel> mGasExhaustGaugeList;
    private List<GaugeModel> mLubeOilGaugeList;

    private FirebaseDatabase mDatabase;

    //Database Gauge Source
    private DatabaseReference mCoolingReference;
    private DatabaseReference mFuelOilReference;
    private DatabaseReference mGasExhaustReference;
    private DatabaseReference mLubeOilReference;

    private static final String FIREBASE_COOLING_TAG = "Firebase_Cooling";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_gauge);

        mDatabase = FirebaseDatabase.getInstance();
        mCoolingReference = mDatabase.getReference("cooling");
        mFuelOilReference = mDatabase.getReference("fueloil");
        mGasExhaustReference = mDatabase.getReference("gasexhaust");
        mLubeOilReference = mDatabase.getReference("lubeoil");

        mCoolingGaugeList = new ArrayList<>();
        mCoolingGaugeList.add(new GaugeModel("Temp. Before ME", "°C", 0, 100, "tempbeforeme", R.string.cooling));
        mCoolingGaugeList.add(new GaugeModel("Temp. After ME", "°C", 0, 100, "tempafterme", R.string.cooling));
        mCoolingGaugeList.add(new GaugeModel("Press. Before ME", "MPa",0, 1, "pressbeforeme", R.string.cooling));
        mCoolingGaugeList.add(new GaugeModel("Press. After ME","MPa",0, 1, "pressafterme", R.string.cooling));

        RecyclerView recyclerView = findViewById(R.id.activity_recycler_view);
        final GaugeAdapter gaugeAdapter = new GaugeAdapter(this, mCoolingGaugeList);
        RecyclerViewItemOffsetDecoration itemDecoration = new RecyclerViewItemOffsetDecoration(this, R.dimen.item_offset);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(gaugeAdapter);

        mCoolingReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(FIREBASE_COOLING_TAG, "Child Added: " + dataSnapshot.toString());

                for(int i = 0; i< mCoolingGaugeList.size(); i++) {
                    if(mCoolingGaugeList.get(i).getId().equals(dataSnapshot.getKey())) {
                        mCoolingGaugeList.get(i).setCurrent(Float.valueOf(dataSnapshot.getValue().toString()));
                        gaugeAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(FIREBASE_COOLING_TAG, "Child Changed: " + dataSnapshot.toString());

                for(int i = 0; i< mCoolingGaugeList.size(); i++) {
                    if(mCoolingGaugeList.get(i).getId().equals(dataSnapshot.getKey())) {
                        mCoolingGaugeList.get(i).setCurrent(Float.valueOf(dataSnapshot.getValue().toString()));
                        gaugeAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.d(FIREBASE_COOLING_TAG, "Child Removed: " + dataSnapshot.toString());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(FIREBASE_COOLING_TAG, "Child Moved: " + dataSnapshot.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(FIREBASE_COOLING_TAG, "Cancelled: ", databaseError.toException());
            }
        });

    }
}
