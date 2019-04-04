package com.adibu.shipmonitoring;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Cooling extends AppCompatActivity {

    List<GaugeModel> gaugeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_gauge);
        Toolbar toolbar = findViewById(R.id.activity_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView title = findViewById(R.id.activity_title);
        title.setText(R.string.cooling);

        gaugeList = new ArrayList<>();
        gaugeList.add(new GaugeModel("Temp. Before ME", "°C", 0, 100));
        gaugeList.add(new GaugeModel("Temp. After ME", "°C", 0, 100));
        gaugeList.add(new GaugeModel("Press. Before ME", "MPa",0, 1));
        gaugeList.add(new GaugeModel("Press. After ME","MPa",0, 1));

        RecyclerView myrv = findViewById(R.id.activity_recycler_view);
        GaugeAdapter gaugeAdapter = new GaugeAdapter(this,gaugeList);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset);
        myrv.setLayoutManager(new GridLayoutManager(this, 2));
        myrv.addItemDecoration(itemDecoration);
        myrv.setAdapter(gaugeAdapter);

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
