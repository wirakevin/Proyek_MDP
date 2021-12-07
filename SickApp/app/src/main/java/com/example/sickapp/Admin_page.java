package com.example.sickapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Admin_page extends AppCompatActivity {

    ArrayList<Obat> obats = new ArrayList<>();
    EditText insearch;
    Button btnsearch, btnadd;
    RecyclerView rv;
    ObatAdapter adapter;
    String search = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        AppDatabase.initDatabase(getApplicationContext(), "DB");

        insearch = findViewById(R.id.insearch);
        btnsearch = findViewById(R.id.btnsearch);
        btnadd = findViewById(R.id.btnaddobat);
        rv = findViewById(R.id.rv);

        rv.setLayoutManager(new LinearLayoutManager(Admin_page.this));
        adapter = new ObatAdapter(obats);
        rv.setAdapter(adapter);

        new GetAllObat().execute();

        adapter.setOnItemClickCallback(new ObatAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Obat obat) {
                Intent i = new Intent(Admin_page.this,Change_obat.class);
                i.putExtra("obat", obat);
                startActivity(i);
            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Admin_page.this,Add_obatkimia.class);
                startActivity(i);
            }
        });

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search = insearch.getText().toString();
                if (search.equals("")){
                    new GetAllObat().execute();
                }
                else{
                    new getObatByName().execute();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuLogout) {
            Intent i = new Intent(Admin_page.this,MainActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    private class getObatByName extends AsyncTask<Void, Void, List<Obat>> {

        @Override
        protected List<Obat> doInBackground(Void... voids) {
            List<Obat> obats = AppDatabase.database.obatDAO().getObatByName(search);
            return obats;
        }

        @Override
        protected void onPostExecute(List<Obat> listobat) {
            super.onPostExecute(listobat);
            // Mau ngapain dari hasil doInBackground
            obats.clear();
            obats.addAll(listobat);
            adapter.notifyDataSetChanged();
        }
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
            obats.addAll(listobat);
            adapter.notifyDataSetChanged();
        }
    }
}