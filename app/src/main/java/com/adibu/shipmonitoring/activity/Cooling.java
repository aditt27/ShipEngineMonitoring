package com.adibu.shipmonitoring.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.adibu.shipmonitoring.GaugeAdapter;
import com.adibu.shipmonitoring.RecyclerViewItemOffsetDecoration;
import com.adibu.shipmonitoring.R;
import com.adibu.shipmonitoring.model.GaugeModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Cooling extends AppCompatActivity {

    private List<GaugeModel> mGaugeList;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mCoolingReference;

    private static final String FIREBASE_TAG = "Cooling_Firebase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_gauge);
        Toolbar toolbar = findViewById(R.id.activity_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        final TextView title = findViewById(R.id.activity_title);
        title.setText(R.string.cooling);

        mGaugeList = new ArrayList<>();
        mGaugeList.add(new GaugeModel("Temp. Before ME", "°C", 0, 100, "tempbeforeme"));
        mGaugeList.add(new GaugeModel("Temp. After ME", "°C", 0, 100, "tempafterme"));
        mGaugeList.add(new GaugeModel("Press. Before ME", "MPa",0, 1, "pressbeforeme"));
        mGaugeList.add(new GaugeModel("Press. After ME","MPa",0, 1, "pressafterme"));

        RecyclerView recyclerView = findViewById(R.id.activity_recycler_view);
        recyclerView.setHasFixedSize(true);
        final GaugeAdapter gaugeAdapter = new GaugeAdapter(this,mGaugeList);
        RecyclerViewItemOffsetDecoration itemDecoration = new RecyclerViewItemOffsetDecoration(this, R.dimen.item_offset);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(gaugeAdapter);

        mDatabase = FirebaseDatabase.getInstance();
        mCoolingReference = mDatabase.getReference("cooling");
        mCoolingReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(FIREBASE_TAG, "Child Added: " + dataSnapshot.toString());

                for(int i=0;i<mGaugeList.size();i++) {
                    if(mGaugeList.get(i).getId().equals(dataSnapshot.getKey())) {
                        mGaugeList.get(i).setCurrent(Float.valueOf(dataSnapshot.getValue().toString()));
                        gaugeAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(FIREBASE_TAG, "Child Changed: " + dataSnapshot.toString());

                for(int i=0;i<mGaugeList.size();i++) {
                    if(mGaugeList.get(i).getId().equals(dataSnapshot.getKey())) {
                        mGaugeList.get(i).setCurrent(Float.valueOf(dataSnapshot.getValue().toString()));
                        gaugeAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.d(FIREBASE_TAG, "Child Removed: " + dataSnapshot.toString());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(FIREBASE_TAG, "Child Moved: " + dataSnapshot.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(FIREBASE_TAG, "Cancelled: ", databaseError.toException());
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
