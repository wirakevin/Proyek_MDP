package com.example.sickapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Add_Disease extends AppCompatActivity {

    ArrayList<String> gejalapenyakit = new ArrayList<>();
    ArrayList<Disease> diseases = new ArrayList<>();
    ArrayList<Gejala> gejalas = new ArrayList<>();
    ArrayList<String> listmygejala = new ArrayList<>();
    TextView tvgejalapenyakit;
    EditText innamapenyakit, ingejalapenyakit;
    Button btnnewdisease, btnsearch;
    RecyclerView rvgejala;
    GejalaAdapter adapter;
    String search = "", mygejala = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_disease);
        AppDatabase.initDatabase(getApplicationContext(), "DB");
        new GetAllDisease().execute();
        new GetAllGejala().execute();

        innamapenyakit = findViewById(R.id.innamapenyakit);
        tvgejalapenyakit = findViewById(R.id.tvgejalapenyakit);
        ingejalapenyakit = findViewById(R.id.ingejalapenyakit);
        btnsearch = findViewById(R.id.btnsearch);
        rvgejala = findViewById(R.id.rvgejala);
        btnnewdisease = findViewById(R.id.btnnewdisease);

        tvgejalapenyakit.setText("Gejala : " + mygejala);

        rvgejala.setLayoutManager(new LinearLayoutManager(Add_Disease.this));
        adapter = new GejalaAdapter(gejalas, gejalapenyakit);
        rvgejala.setAdapter(adapter);

        btnnewdisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String innama = innamapenyakit.getText().toString();

                if (innama.equals("") || mygejala.equals("")){
                    Toast.makeText(Add_Disease.this, "Field kosong", Toast.LENGTH_SHORT).show();
                }
                else{
                    Disease disease = new Disease(innama, mygejala);
                    Gejala gejala = new Gejala(innama);
                    new InsertTask().execute(disease);
                    new InsertGejalaTask().execute(gejala);
                    Toast.makeText(Add_Disease.this, "Disease Added", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Add_Disease.this,Admin_page.class);
                    startActivity(i);
                }
            }
        });

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ingejalapenyakit.getText().toString().equals("")){
                    new GetAllGejala().execute();
                }
                else{
                    search = ingejalapenyakit.getText().toString();
                    new getGejalaByName().execute();
                }
            }
        });

        adapter.setOnItemClickCallback(new GejalaAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Gejala gejala) {
                int n = -1;
                for (int i = 0; i < listmygejala.size(); i++) {
                    if (listmygejala.get(i).equals(gejala.nama)){
                        n = i;
                    }
                }
                if (n >= 0){
                    listmygejala.remove(n);
                }
                else{
                    listmygejala.add(gejala.nama);
                }
                mygejala = "";
                for (String s:listmygejala) {
                    mygejala += s+",";
                }
                tvgejalapenyakit.setText("Gejala : " + mygejala);
            }
        });
    }

    private class InsertTask extends AsyncTask<Disease, Void, Void> {

        @Override
        protected Void doInBackground(Disease... listdisease) {
            AppDatabase.database.diseaseDAO().insert(listdisease[0]);
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
            diseases.clear();
            diseases.addAll(AppDatabase.database.diseaseDAO().getAllDisease());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private class GetAllDisease extends AsyncTask<Void, Void, List<Disease>>{

        @Override
        protected List<Disease> doInBackground(Void... voids) {
            List<Disease> diseases = AppDatabase.database.diseaseDAO().getAllDisease();
            return diseases;
        }

        @Override
        protected void onPostExecute(List<Disease> listdisease) {
            super.onPostExecute(listdisease);
            // Mau ngapain dari hasil doInBackground
            diseases.clear();
            diseases.addAll(listdisease);
        }
    }

    private class InsertGejalaTask extends AsyncTask<Gejala, Void, Void> {

        @Override
        protected Void doInBackground(Gejala... listgejala) {
            AppDatabase.database.gejalaDAO().insert(listgejala[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new GetAllGejalaTask().execute();
        }
    }

    private class GetAllGejalaTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            gejalas.clear();
            gejalas.addAll(AppDatabase.database.gejalaDAO().getAllGejala());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private class GetAllGejala extends AsyncTask<Void, Void, List<Gejala>> {

        @Override
        protected List<Gejala> doInBackground(Void... voids) {
            List<Gejala> gejalas = AppDatabase.database.gejalaDAO().getAllGejala();
            return gejalas;
        }

        @Override
        protected void onPostExecute(List<Gejala> listgejala) {
            super.onPostExecute(listgejala);
            // Mau ngapain dari hasil doInBackground
            gejalas.clear();
            gejalas.addAll(listgejala);
            adapter.notifyDataSetChanged();
        }
    }

    private class getGejalaByName extends AsyncTask<Void, Void, List<Gejala>> {

        @Override
        protected List<Gejala> doInBackground(Void... voids) {
            List<Gejala> gejalas = AppDatabase.database.gejalaDAO().getGejalaByName(search);
            return gejalas;
        }

        @Override
        protected void onPostExecute(List<Gejala> listgejala) {
            super.onPostExecute(listgejala);
            // Mau ngapain dari hasil doInBackground
            gejalas.clear();
            gejalas.addAll(listgejala);
            adapter.notifyDataSetChanged();
        }
    }
}