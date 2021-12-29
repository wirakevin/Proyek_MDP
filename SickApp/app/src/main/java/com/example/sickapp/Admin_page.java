package com.example.sickapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Admin_page extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    ArrayList<Disease> diseases = new ArrayList<>();
    ArrayList<Obat> obats = new ArrayList<>();
    BottomNavigationView bnadmin;
    private UserSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        AppDatabase.initDatabase(getApplicationContext(), "DB");

        bnadmin = findViewById(R.id.bnadmin);

        new GetAllDisease().execute();
        new GetAllObat().execute();

        bnadmin.setOnNavigationItemSelectedListener(Admin_page.this);
        settings = (UserSettings) getApplication();

        SharedPreferences.Editor editor = getSharedPreferences(UserSettings.PREFERENCES, MODE_PRIVATE).edit();
        editor.putString(UserSettings.USERNAME, "admin");
        editor.putString(UserSettings.PASSWORD, "admin");
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuLogout) {
            settings.setUsername("");
            settings.setPassword("");

            SharedPreferences.Editor editor = getSharedPreferences(UserSettings.PREFERENCES, MODE_PRIVATE).edit();
            editor.putString(UserSettings.USERNAME, settings.getUsername());
            editor.putString(UserSettings.PASSWORD, settings.getPassword());
            editor.apply();

            Intent i = new Intent(Admin_page.this,MainActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            Fragment f = new Admin_disease();
            Bundle b = new Bundle();
            b.putParcelableArrayList("diseases",diseases);
            f.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.container,f).commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuDisease_admin){
            Fragment f = new Admin_disease();
            Bundle b = new Bundle();
            b.putParcelableArrayList("diseases",diseases);
            f.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.container,f).commit();
            return true;
        }
        if (item.getItemId() == R.id.menuObat_admin){
            Fragment f = new Admin_obat();
            Bundle b = new Bundle();
            b.putParcelableArrayList("diseases",diseases);
            b.putParcelableArrayList("obats",obats);
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

            bnadmin.setSelectedItemId(R.id.menuDisease_admin);
            Fragment f = new Admin_disease();
            Bundle b = new Bundle();
            b.putParcelableArrayList("diseases",diseases);
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