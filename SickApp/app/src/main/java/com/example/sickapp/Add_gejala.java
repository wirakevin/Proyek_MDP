package com.example.sickapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Add_gejala extends AppCompatActivity {

    ArrayList<Gejala> gejalas = new ArrayList<>();
    EditText innamagejala, incarigejala;
    Button btnaddnewgejala, btncarigejala;
    ListView listgejala;
    String search = "";
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gejala);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AppDatabase.initDatabase(getApplicationContext(), "DB");
        new GetAllGejala().execute();

        innamagejala = findViewById(R.id.innamagejala);
        incarigejala = findViewById(R.id.incarigejala);
        btnaddnewgejala = findViewById(R.id.btnaddnewgejala);
        btncarigejala = findViewById(R.id.btncarigejala);
        listgejala = findViewById(R.id.listgejala);

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, gejalas);
        listgejala.setAdapter(arrayAdapter);

        btnaddnewgejala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (innamagejala.getText().toString().equals("")){
                    Toast.makeText(Add_gejala.this, "Field kosong", Toast.LENGTH_SHORT).show();
                }
                else{
                    boolean sama = false;
                    for (Gejala g:gejalas) {
                        if (innamagejala.getText().toString().toUpperCase().equals(g.nama.toUpperCase())){
                            sama = true;
                        }
                    }
                    if (!sama){
                        Gejala gejala = new Gejala(innamagejala.getText().toString());
                        new InsertTask().execute(gejala);
                        new GetAllGejala().execute();

                        Toast.makeText(Add_gejala.this, "Gejala added", Toast.LENGTH_SHORT).show();
                        innamagejala.setText("");
                    }
                    else{
                        Toast.makeText(Add_gejala.this, "Gejala sudah ada", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btncarigejala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (incarigejala.getText().toString().equals("")){
                    new GetAllGejala().execute();
                }
                else{
                    search = incarigejala.getText().toString();
                    new getGejalaByName().execute();
                }
            }
        });

        listgejala.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(Add_gejala.this, gejalas.get(i).nama, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class InsertTask extends AsyncTask<Gejala, Void, Void> {

        @Override
        protected Void doInBackground(Gejala... listgejala) {
            AppDatabase.database.gejalaDAO().insert(listgejala[0]);
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
            gejalas.clear();
            gejalas.addAll(AppDatabase.database.gejalaDAO().getAllGejala());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
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
            arrayAdapter.notifyDataSetChanged();
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
            arrayAdapter.notifyDataSetChanged();
        }
    }
}