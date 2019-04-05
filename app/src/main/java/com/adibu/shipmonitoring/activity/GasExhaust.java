package com.adibu.shipmonitoring.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.adibu.shipmonitoring.R;
import com.adibu.shipmonitoring.model.GaugeModel;

import java.util.List;

public class GasExhaust extends AppCompatActivity {

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
        title.setText(R.string.gasexhaust);
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
