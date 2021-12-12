package com.example.sickapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Disease_detail extends AppCompatActivity {

    Disease disease;
    User user;
    TextView tvdetailnama_penyakit, tvdetailgejala_penyakit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_detail);

        tvdetailnama_penyakit = findViewById(R.id.tvdetailnama_penyakit);
        tvdetailgejala_penyakit = findViewById(R.id.tvdetailgejala_penyakit);

        disease = getIntent().getParcelableExtra("disease");
        user = getIntent().getParcelableExtra("user");

        tvdetailnama_penyakit.setText("Nama : " + disease.nama);
        tvdetailgejala_penyakit.setText("Gejala penyakit : " + disease.gejala);

    }
}