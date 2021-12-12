package com.example.sickapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Information_page extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    ArrayList<Disease> diseases = new ArrayList<>();
    ArrayList<Obat> obats = new ArrayList<>();
    User user;
    BottomNavigationView bn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_page);
        AppDatabase.initDatabase(getApplicationContext(), "DB");

        bn = findViewById(R.id.bn);

        user = getIntent().getParcelableExtra("user");

        new GetAllDisease().execute();
        new GetAllObat().execute();

        bn.setOnNavigationItemSelectedListener(Information_page.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            Fragment f = new Information_disease();
            Bundle b = new Bundle();
            b.putParcelableArrayList("diseases",diseases);
            b.putParcelable("user",user);
            f.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.container,f).commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuDisease_admin){
            Fragment f = new Information_disease();
            Bundle b = new Bundle();
            b.putParcelableArrayList("diseases",diseases);
            b.putParcelable("user",user);
            f.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.container,f).commit();
            return true;
        }
        if (item.getItemId() == R.id.menuObat_admin){
            Fragment f = new Information_obat();
            Bundle b = new Bundle();
            b.putParcelableArrayList("diseases",diseases);
            b.putParcelableArrayList("obats",obats);
            b.putParcelable("user",user);
            f.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.container,f).commit();
            return true;
        }
//        return super.onOptionsItemSelected(item);
        return false;
    }

    /*private class getDiseaseByName extends AsyncTask<Void, Void, List<Disease>> {

        @Override
        protected List<Disease> doInBackground(Void... voids) {
            List<Disease> diseases = AppDatabase.database.diseaseDAO().getDiseaseByName(search);
            return diseases;
        }

        @Override
        protected void onPostExecute(List<Disease> listobat) {
            super.onPostExecute(listobat);
            // Mau ngapain dari hasil doInBackground
            diseases.clear();
            diseases.addAll(listobat);
            adapter.notifyDataSetChanged();
        }
    }*/

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

            bn.setSelectedItemId(R.id.menuDisease_admin);
            Fragment f = new Information_disease();
            Bundle b = new Bundle();
            b.putParcelableArrayList("diseases",diseases);
            b.putParcelable("user",user);
            f.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.container,f).commit();
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
        }
    }
}