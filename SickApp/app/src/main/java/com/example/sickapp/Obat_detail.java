package com.example.sickapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Obat_detail extends AppCompatActivity {

    Obat obat;
    User user;
    TextView tvdetailnama_obat, tvdetailrasa_obat, tvdetailumur_obat, tvdetailtakaran_obat, tvdetailpenyakit_obat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obat_detail);

        tvdetailnama_obat = findViewById(R.id.tvdetailnama_obat);
        tvdetailrasa_obat = findViewById(R.id.tvdetailrasa_obat);
        tvdetailumur_obat = findViewById(R.id.tvdetailumur_obat);
        tvdetailpenyakit_obat = findViewById(R.id.tvdetailpenyakit_obat);
        tvdetailtakaran_obat = findViewById(R.id.tvdetailtakaran_obat);

        obat = getIntent().getParcelableExtra("obat");
        user = getIntent().getParcelableExtra("user");
        String u = "";
        String[] umur = obat.untuk_umur.split(",");
        if (umur[0].equals("<")){
            u = "Dibawah " + umur[1];
        }
        else if (umur[0].equals(">")){
            u = "Diatas " + umur[1];
        }
        else if (umur[0].equals("-")){
            u = "Antara " + umur[1] + " dan " + umur[2];
        }
        else{
            u = umur[0];
        }

        tvdetailnama_obat.setText("Nama : " + obat.nama);
        if (obat.rasa.equals("")){
            tvdetailrasa_obat.setText("Rasa : -");
        }
        else{
            tvdetailrasa_obat.setText("Rasa : " + obat.rasa);
        }
        tvdetailumur_obat.setText("Untuk umur : " + u);
        tvdetailpenyakit_obat.setText("Untuk mengobati : " + obat.untuk_penyakit);
        tvdetailtakaran_obat.setText("Takaran pemakaian : " + obat.takaran);
    }
}