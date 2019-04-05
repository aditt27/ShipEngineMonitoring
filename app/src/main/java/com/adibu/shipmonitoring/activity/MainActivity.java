package com.adibu.shipmonitoring.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.adibu.shipmonitoring.MainGridAdapter;
import com.adibu.shipmonitoring.R;
import com.adibu.shipmonitoring.model.GridModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main_Activity";

    FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_gridview);

        GridView gridView = findViewById(R.id.grid_view);
        MainGridAdapter mainGridAdapter = new MainGridAdapter(this, 0);
        gridView.setAdapter(mainGridAdapter);

        mainGridAdapter.add(new GridModel(R.string.cooling, "Normal"));
        mainGridAdapter.add(new GridModel(R.string.fueloil, "Normal"));
        mainGridAdapter.add(new GridModel(R.string.gasexhaust, "Normal"));
        mainGridAdapter.add(new GridModel(R.string.lubeoil, "Normal"));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch ((int)id) {
                    case 0:
                        Intent i = new Intent(MainActivity.this, Cooling.class);
                        startActivity(i);
                        break;
                    case 1:
                        Intent j = new Intent(MainActivity.this, FuelOil.class);
                        startActivity(j);
                        break;
                    case 2:
                        Intent k = new Intent(MainActivity.this, GasExhaust.class);
                        startActivity(k);
                        break;
                    case 3:
                        Intent l = new Intent(MainActivity.this, LubeOil.class);
                        startActivity(l);
                        break;
                }
            }
        });
    }


}
