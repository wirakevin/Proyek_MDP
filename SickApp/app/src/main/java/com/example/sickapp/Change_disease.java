package com.example.sickapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Change_disease extends AppCompatActivity {

    ArrayList<String> gejalapenyakit = new ArrayList<>();
    ArrayList<Disease> diseases = new ArrayList<>();
    ArrayList<Gejala> gejalas = new ArrayList<>();
    ArrayList<String> listmygejala = new ArrayList<>();
    TextView tvgejalapenyakit;
    EditText innamapenyakit, ingejalapenyakit;
    Button btnupdate, btndelete, btnsearch;
    RecyclerView rvgejala;
    GejalaAdapter adapter;
    String search = "", mygejala = "";
    Disease disease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_disease);
        AppDatabase.initDatabase(getApplicationContext(), "DB");
        new GetAllDisease().execute();
        new GetAllGejala().execute();

        disease = getIntent().getParcelableExtra("disease");

        innamapenyakit = findViewById(R.id.innamapenyakit);
        tvgejalapenyakit = findViewById(R.id.tvgejalapenyakit);
        ingejalapenyakit = findViewById(R.id.ingejalapenyakit);
        btnsearch = findViewById(R.id.btnsearch);
        rvgejala = findViewById(R.id.rvgejala);
        btnupdate = findViewById(R.id.btnupdate);
        btndelete = findViewById(R.id.btndelete);

        listmygejala.clear();
        String[] g = disease.gejala.split(",");
        for (String s:g) {
            listmygejala.add(s);
        }
        gejalapenyakit = listmygejala;
        mygejala = "";
        for (String s:listmygejala) {
            mygejala += s+",";
        }

        rvgejala.setLayoutManager(new LinearLayoutManager(Change_disease.this));
        adapter = new GejalaAdapter(gejalas, gejalapenyakit);
        rvgejala.setAdapter(adapter);

        tvgejalapenyakit.setText("Gejala : " + mygejala);
        innamapenyakit.setText(disease.nama);

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

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekinput("update");
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekinput("delete");
            }
        });
    }

    public void cekinput(String task){
        if (task.equals("update")){
            if (innamapenyakit.equals("") || mygejala.equals("")){
                Toast.makeText(Change_disease.this, "Field kosong", Toast.LENGTH_SHORT).show();
            }
            else{
                disease.nama = innamapenyakit.getText().toString();
                disease.gejala = mygejala;

                new UpdateTask().execute(disease);
                Toast.makeText(Change_disease.this, "Disease Updated", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Change_disease.this,Admin_page.class);
                startActivity(i);
            }
        }
        else{
            new DeleteTask().execute(disease);
            Toast.makeText(Change_disease.this, "Disease Deleted", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Change_disease.this,Admin_page.class);
            startActivity(i);
        }
    }

    private class UpdateTask extends AsyncTask<Disease, Void, Void> {

        @Override
        protected Void doInBackground(Disease... listdisease) {
            AppDatabase.database.diseaseDAO().update(listdisease[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new GetAllTask().execute();
        }
    }

    private class DeleteTask extends AsyncTask<Disease, Void, Void> {

        @Override
        protected Void doInBackground(Disease... listdisease) {
            AppDatabase.database.diseaseDAO().delete(listdisease[0]);
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

    private class GetAllDisease extends AsyncTask<Void, Void, List<Disease>> {

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