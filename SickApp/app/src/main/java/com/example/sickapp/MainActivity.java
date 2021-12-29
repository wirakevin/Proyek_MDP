package com.example.sickapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
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

    private UserSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppDatabase.initDatabase(getApplicationContext(), "DB");

        new GetAllUser().execute();
        settings = (UserSettings) getApplication();

        bnhome = findViewById(R.id.bnhome);
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
        login(user);
    }

    @Override
    public void login(User user) {
        settings.setUsername(user.username);
        settings.setPassword(user.password);

        SharedPreferences.Editor editor = getSharedPreferences(UserSettings.PREFERENCES, MODE_PRIVATE).edit();
        editor.putString(UserSettings.USERNAME, settings.getUsername());
        editor.putString(UserSettings.PASSWORD, settings.getPassword());
        editor.apply();

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

            loadSharedPreferences();

            bnhome.setSelectedItemId(R.id.option_login);
            Fragment f = new Login();
            Bundle b = new Bundle();
            b.putParcelableArrayList("users",users);
            f.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.container,f).commit();
        }
    }

    private void loadSharedPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences(UserSettings.PREFERENCES, MODE_PRIVATE);
        String username = sharedPreferences.getString(UserSettings.USERNAME, "");
        settings.setUsername(username);
        String password = sharedPreferences.getString(UserSettings.PASSWORD, "");
        settings.setPassword(password);

        if (!settings.getUsername().equals("") || !settings.getPassword().equals("")){
            if (settings.getUsername().equals("admin") && settings.getPassword().equals("admin")){
                Intent i = new Intent(MainActivity.this,Admin_page.class);
                startActivity(i);
            }
            else{
                int u = -1;
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).username.equals(settings.getUsername()) && users.get(i).password.equals(settings.getPassword())){
                        u = i;
                    }
                }
                if (u >= 0){
                    Intent i = new Intent(MainActivity.this,User_page.class);
                    i.putExtra("user", users.get(u));
                    startActivity(i);
                }
            }
        }
    }
}