package com.adibu.shipmonitoring.view.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adibu.shipmonitoring.R;
import com.adibu.shipmonitoring.model.KeyValueModel;
import com.adibu.shipmonitoring.model.WarningModel;
import com.adibu.shipmonitoring.viewmodel.MainActivityViewModel;

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
    private DatabaseReference mDataReference;

    private MediaPlayer mMediaPlayer;

    private static final String FIREBASE_TAG = "Firebase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance();
        mDataReference = mDatabase.getReference("data");

        mDrawerLayout = findViewById(R.id.main_drawer_layout);

        mWarningList = new ArrayList<>();

        mWarningMessage = findViewById(R.id.main_warning_rview);
        mWarningAdapter = new WarningAdapter(mWarningList);
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

        //Ambil data dari firebase
        mDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<KeyValueModel> data = new ArrayList<>();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Log.e(FIREBASE_TAG, "key:" + childSnapshot.getKey() + ", value:" + childSnapshot.getValue());
                    data.add(new KeyValueModel(childSnapshot.getKey(), childSnapshot.getValue().toString()));
                }
                mViewModel.getData().setValue(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(FIREBASE_TAG, "Cancelled: ", databaseError.toException());
            }
        });

        //Warning
        mViewModel.getData().observe(this, new Observer<List<KeyValueModel>>() {
            @Override
            public void onChanged(List<KeyValueModel> keyValueModels) {
                mWarningList.clear();
                for (int i=0;i<keyValueModels.size();i++) {
                    float value = Float.valueOf(keyValueModels.get(i).getValue());
                    String key = keyValueModels.get(i).getKey();
                    WarningModel warningModel = new WarningModel(key, value);
                    if(warningModel.getMessage()!=null) {
                        mWarningList.add(warningModel);
                    }
                }
                mViewModel.getWarningData().setValue(mWarningList);
            }
        });

        //Nyalakan suara jika ada warning
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
                setTitle(getString(R.string.app_name) + " - " + getString(R.string.mainengine));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_frame_layout, new MainEngineFragment()).commit();
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

    public class WarningAdapter extends RecyclerView.Adapter<WarningAdapter.MyViewHolder> {

        private List<WarningModel> mData ;

        public WarningAdapter(List<WarningModel> mData) {
            this.mData = mData;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view ;
            LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
            view = mInflater.inflate(R.layout.item_warning,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
            holder.categories.setText(mData.get(position).getCategories());
            holder.title.setText(mData.get(position).getTitle());
            holder.message.setText(mData.get(position).getMessage());
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this, DetailWarningActivity.class);
                    i.putExtra("category",mWarningList.get(holder.getAdapterPosition()).getCategories());
                    i.putExtra("title", mWarningList.get(holder.getAdapterPosition()).getTitle());
                    i.putExtra("message", mWarningList.get(holder.getAdapterPosition()).getMessage());
                    i.putExtra("recommendation", mWarningList.get(holder.getAdapterPosition()).getRecommendation());
                    startActivity(i);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView title;
            TextView categories;
            TextView message;
            RelativeLayout item;

            public MyViewHolder(View itemView) {
                super(itemView);
                categories = itemView.findViewById(R.id.item_warning_categories);
                title = itemView.findViewById(R.id.item_warning_title) ;
                message = itemView.findViewById(R.id.item_warning_message);
                item = itemView.findViewById(R.id.item_warning);
            }
        }
    }
}
