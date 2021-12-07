package com.example.sickapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Register.OnFragmentInteractionListener,  Login.OnFragmentInteractionListener, BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bnhome;
    ArrayList<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppDatabase.initDatabase(getApplicationContext(), "DB");

        bnhome = findViewById(R.id.bnhome);

        new GetAllUser().execute();

        bnhome.setOnNavigationItemSelectedListener(MainActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            Fragment f = new Login();
            Bundle b = new Bundle();
            b.putParcelableArrayList("users",users);
            f.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.container,f).commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.option_login){
            Fragment f = new Login();
            Bundle b = new Bundle();
            b.putParcelableArrayList("users",users);
            f.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.container,f).commit();
            return true;
        }
        if (item.getItemId() == R.id.option_register){
            Fragment f = new Register();
            Bundle b = new Bundle();
            b.putParcelableArrayList("users",users);
            f.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.container,f).commit();
            return true;
        }
//        return super.onOptionsItemSelected(item);
        return false;
    }

    @Override
    public void insertuser(User user) {
        Toast.makeText(this, "User added", Toast.LENGTH_SHORT).show();
        new InsertTask().execute(user);
    }

    @Override
    public void login(User user) {
        Intent i = new Intent(MainActivity.this,User_page.class);
        i.putExtra("user", user);
        startActivity(i);
    }

    private class InsertTask extends AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... listuser) {
            AppDatabase.database.userDAO().insert(listuser[0]);
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

    private class GetAllUser extends AsyncTask<Void, Void, List<User>>{

        @Override
        protected List<User> doInBackground(Void... voids) {
            List<User> users = AppDatabase.database.userDAO().getAllUser();
            return users;
        }

        @Override
        protected void onPostExecute(List<User> listuser) {
            super.onPostExecute(listuser);
            // Mau ngapain dari hasil doInBackground
            users.clear();
            users.addAll(listuser);

            bnhome.setSelectedItemId(R.id.option_login);
            Fragment f = new Login();
            Bundle b = new Bundle();
            b.putParcelableArrayList("users",users);
            f.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.container,f).commit();
        }
    }
}