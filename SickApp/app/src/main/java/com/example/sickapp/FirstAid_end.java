package com.example.sickapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FirstAid_end extends AppCompatActivity {

    ArrayList<User> users = new ArrayList<>();
    ArrayList<Obat> obats = new ArrayList<>();
    Disease mydisease;
    User user;
    Button btnbacktohome;
    RecyclerView rvobat;
    ObatAdapter adapter;
    TextView tvhasil;
    int umur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_aid_end);
        AppDatabase.initDatabase(getApplicationContext(), "DB");

        user = getIntent().getParcelableExtra("user");
        umur = Integer.parseInt(getIntent().getStringExtra("umur"));
        mydisease = getIntent().getParcelableExtra("mydisease");

        tvhasil = findViewById(R.id.tvhasil);
        rvobat = findViewById(R.id.rvobat);
        btnbacktohome = findViewById(R.id.btnbacktohome);

        if (mydisease.nama.equals("")){
            tvhasil.setText("Berdasarkan gejala yang dipilih, penyakit tidak dapat ditemukan");
        }
        else{
            tvhasil.setText("Berdasarkan gejala yang dipilih, anda kemungkinan terkena " + mydisease.nama);

            if (user != null){
                user.history_penyakit += ","+mydisease.nama;
                new UpdateTask().execute(user);
            }
        }

        rvobat.setLayoutManager(new LinearLayoutManager(FirstAid_end.this));
        adapter = new ObatAdapter(obats);
        rvobat.setAdapter(adapter);

        adapter.setOnItemClickCallback(new ObatAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Obat obat) {
                Intent i = new Intent(FirstAid_end.this, Obat_detail.class);
                i.putExtra("obat", obat);
                i.putExtra("user", user);
                startActivity(i);
            }
        });

        new GetAllObat().execute();

        btnbacktohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user == null){
                    Intent i = new Intent(FirstAid_end.this,Guest_home.class);
                    startActivity(i);
                }
                else{
                    Intent i = new Intent(FirstAid_end.this,User_page.class);
                    i.putExtra("user", user);
                    startActivity(i);
                }
            }
        });
    }

    private class GetAllObat extends AsyncTask<Void, Void, List<Obat>> {

        @Override
        protected List<Obat> doInBackground(Void... voids) {
            List<Obat> obats = AppDatabase.database.obatDAO().getAllObat();
            return obats;
        }

        @Override
        protected void onPostExecute(List<Obat> listobat) {
            super.onPostExecute(listobat);
            // Mau ngapain dari hasil doInBackground
            obats.clear();
            for (Obat o:listobat) {
                if (o.untuk_penyakit.equals(mydisease.nama)){
                    int u1 = -1, u2 = -1;
                    String[] obat_umur = o.untuk_umur.split(",");
                    if (obat_umur[0].equals("<")){
                        u1 = Integer.parseInt(obat_umur[1]);
                        if (umur < u1){
                            obats.add(o);
                        }
                    }
                    else if (obat_umur[0].equals(">")){
                        u1 = Integer.parseInt(obat_umur[1]);
                        if (umur > u1){
                            obats.add(o);
                        }
                    }
                    else if (obat_umur[0].equals("-")){
                        u1 = Integer.parseInt(obat_umur[1]);
                        u2 = Integer.parseInt(obat_umur[2]);
                        if (umur >= u1 && umur <= u2){
                            obats.add(o);
                        }
                    }
                    else{
                        u1 = Integer.parseInt(obat_umur[0]);
                        if (umur == u1){
                            obats.add(o);
                        }
                    }
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    private class UpdateTask extends AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... listuser) {
            AppDatabase.database.userDAO().update(listuser[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new GetAllTask().execute();
        }
    }

    private class GetAllTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            users.clear();
            users.addAll(AppDatabase.database.userDAO().getAllUser());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}