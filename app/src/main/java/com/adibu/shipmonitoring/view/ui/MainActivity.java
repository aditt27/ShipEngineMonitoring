package com.adibu.shipmonitoring.view.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.adibu.shipmonitoring.R;
import com.adibu.shipmonitoring.model.KeyValueModel;
import com.adibu.shipmonitoring.model.WarningModel;
import com.adibu.shipmonitoring.view.adapter.WarningAdapter;
import com.adibu.shipmonitoring.view.viewmodel.MainActivityViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    private RecyclerView mWarningMessage;
    private WarningAdapter mWarningAdapter;
    private List<WarningModel> mWarningList;

    private MainActivityViewModel mViewModel;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mCoolingReference;

    private MediaPlayer mMediaPlayer;

    private static final String FIREBASE_COOLING_TAG = "Firebase_Cooling";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance();
        mCoolingReference = mDatabase.getReference("cooling");

        mDrawerLayout = findViewById(R.id.main_drawer_layout);

        mWarningList = new ArrayList<>();

        mWarningMessage = findViewById(R.id.main_warning_rview);
        mWarningAdapter = new WarningAdapter(this, mWarningList);
        mWarningMessage.setLayoutManager(new LinearLayoutManager(this));
        mWarningMessage.setAdapter(mWarningAdapter);

        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.main_frame_layout) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                setTitle(savedInstanceState.getString("title"));
                return;
            }
            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_frame_layout, new CoolingFragment()).commit();

            // Set title according to the fragment
            setTitle(getString(R.string.app_name) + " - " + getString(R.string.cooling));
        }

        mCoolingReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<KeyValueModel> coolingData = new ArrayList<>();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    coolingData.add(new KeyValueModel(childSnapshot.getKey(), childSnapshot.getValue().toString()));
                }
                mViewModel.getCoolingData().setValue(coolingData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(FIREBASE_COOLING_TAG, "Cancelled: ", databaseError.toException());
            }
        });

        mViewModel.getCoolingData().observe(this, new Observer<List<KeyValueModel>>() {
            @Override
            public void onChanged(List<KeyValueModel> keyValueModels) {
                Log.d("Observe Main Activity: ", keyValueModels.toString());
                mWarningList.clear();
                for (int i=0;i<keyValueModels.size();i++) {
                    float value = Float.valueOf(keyValueModels.get(i).getValue());
                    switch (keyValueModels.get(i).getKey()) {
                        case "fwtempafterme":
                            if (value < 40) {
                                mWarningList.add(new WarningModel("Cooling", "FW Temp. After ME", "Abnormal. Suhu<40°C"));
                            } else if (value > 90) {
                                mWarningList.add(new WarningModel("Cooling", "FW Temp. After ME", "Abnormal. Suhu>90°C"));
                            }
                            break;
                        case "fwtempbeforeme":
                            if (value < 30) {
                                mWarningList.add(new WarningModel("Cooling", "FW Temp. Before ME", "Abnormal. Suhu<30°C"));
                            } else if (value > 50) {
                                mWarningList.add(new WarningModel("Cooling", "FW Temp. Before ME", "Abnormal. Suhu>40°C"));
                            }
                            break;
                        case "fwpressbeforeme":
                            if (value < 40) {
                                mWarningList.add(new WarningModel("Cooling", "FW Press Before ME", "Abnormal. Tekanan<40Mpa"));
                            } else if (value > 50) {
                                mWarningList.add(new WarningModel("Cooling", "FW Press Before ME", "Abnormal. Tekanan>50Mpa"));
                            }
                            break;
                        case "swpressbeforecu":
                            if (value < 0.15) {
                                mWarningList.add(new WarningModel("Cooling", "SW Press Before CU", "Abnormal. Tekanan<0.15Mpa"));
                            }
                            break;
                        case "voltankexp":
                            if (value < 50) {
                                mWarningList.add(new WarningModel("Cooling", "Vol. Tank Exp.", "Abnormal. Volume<50%"));
                            }
                            break;
                    }
                }
                mViewModel.getWarningData().setValue(mWarningList);
            }
        });

        mViewModel.getWarningData().observe(this, new Observer<List<WarningModel>>() {
            @Override
            public void onChanged(List<WarningModel> warningModels) {
                Log.d("Observe Warning Msg", warningModels.toString());
                if(warningModels.size()>0) {
                    playWarningSound();
                } else {
                    stopWarningSound();
                }
                mWarningList = warningModels;
                mWarningAdapter.notifyDataSetChanged();
            }
        });
    }

    private void playWarningSound() {
        if(mMediaPlayer==null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.warning);
            mMediaPlayer.setLooping(true);
            mMediaPlayer.start();
        }
    }

    private void stopWarningSound() {
        if(mMediaPlayer!=null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("title", getTitle().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_cooling:
                setTitle(getString(R.string.app_name) + " - " + getString(R.string.cooling));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_frame_layout, new CoolingFragment()).commit();
                return true;
            case R.id.menu_fuel_oil:
                setTitle(getString(R.string.app_name) + " - " + getString(R.string.fueloil));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_frame_layout, new FuelOilFragment()).commit();
                return true;
            case R.id.menu_gas_exhaust:
                setTitle(getString(R.string.app_name) + " - " + getString(R.string.gasexhaust));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_frame_layout, new GasExhaustFragment()).commit();
                return true;
            case R.id.menu_lube_oil:
                setTitle(getString(R.string.app_name) + " - " + getString(R.string.lubeoil));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_frame_layout, new LubeOilFragment()).commit();
                return true;
            case R.id.warning_message:
                if(!mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                    return true;
                }
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        stopWarningSound();
        super.onStop();
    }

    @Override
    protected void onStart() {
        if(mWarningList.size()>0)  {
            playWarningSound();
        }
        super.onStart();
    }
}
