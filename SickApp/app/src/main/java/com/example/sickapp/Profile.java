package com.example.sickapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {

    ArrayList<User> users = new ArrayList<>();
    User user;
    EditText profilenama, profiledob, profilealamat, profileno_tlp;
    RadioButton rb1, rb2;
    Button btnsubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        AppDatabase.initDatabase(getApplicationContext(), "DB");

        profilenama = findViewById(R.id.profilenama);
        profiledob = findViewById(R.id.profiledob);
        profilealamat = findViewById(R.id.profilealamat);
        profileno_tlp = findViewById(R.id.profileno_tlp);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        btnsubmit = findViewById(R.id.btnsubmit);

        profilenama.setText("");
        profiledob.setText("");
        profilealamat.setText("");
        profileno_tlp.setText("");

        user = getIntent().getParcelableExtra("user");

        if (user.nama != null){
            profilenama.setText(user.nama);
        }
        if (user.dob != null){
            profiledob.setText(user.dob);
        }
        if (user.alamat != null){
            profilealamat.setText(user.alamat);
        }
        if (user.no_tlp != null){
            profileno_tlp.setText(user.no_tlp);
        }
        if (user.gender != null && user.gender.equals("Wanita")){
            rb2.setChecked(true);
        }


        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!profilenama.getText().toString().equals("")){
                    user.nama = profilenama.getText().toString();
                }
                if (!profiledob.getText().toString().equals("")){
                    user.dob = profiledob.getText().toString();
                }
                if (!profilealamat.getText().toString().equals("")){
                    user.alamat = profilealamat.getText().toString();
                }
                if (!profileno_tlp.getText().toString().equals("")){
                    user.no_tlp = profileno_tlp.getText().toString();
                }

                if (rb1.isChecked()){
                    user.gender = "Pria";
                }
                else{
                    user.gender = "Wanita";
                }

                new UpdateTask().execute(user);
                Intent i = new Intent(Profile.this, User_page.class);
                i.putExtra("user", user);
                startActivity(i);
            }
        });
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