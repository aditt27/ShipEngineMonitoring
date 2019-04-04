package com.adibu.shipmonitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

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
