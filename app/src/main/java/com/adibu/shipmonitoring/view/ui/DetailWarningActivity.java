package com.adibu.shipmonitoring.view.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.adibu.shipmonitoring.R;

public class DetailWarningActivity extends AppCompatActivity {

    Intent mIntent;
    TextView category;
    TextView title;
    TextView message;
    TextView rekomendasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_warning);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mIntent = getIntent();

        category = findViewById(R.id.detail_warning_kategori);
        title = findViewById(R.id.detail_warning_judul);
        message = findViewById(R.id.detail_warning_pesan);
        rekomendasi = findViewById(R.id.detail_warning_rekomendasi);

        category.setText(mIntent.getStringExtra("category"));
        title.setText(mIntent.getStringExtra("title"));
        message.setText(mIntent.getStringExtra("message"));
        rekomendasi.setText(mIntent.getStringExtra("recommendation"));
    }
}
