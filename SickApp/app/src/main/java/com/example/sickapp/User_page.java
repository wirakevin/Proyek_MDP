package com.example.sickapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class User_page extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bnuser;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        user = getIntent().getParcelableExtra("user");

        bnuser = findViewById(R.id.bnuser);
        bnuser.setOnNavigationItemSelectedListener(User_page.this);

        bnuser.setSelectedItemId(R.id.menuHome_user);
        Fragment f = new User_home();
        Bundle b = new Bundle();
        b.putParcelable("user",user);
        f.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,f).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuLogout) {
            Intent i = new Intent(User_page.this,MainActivity.class);
            startActivity(i);
        }
        else if (item.getItemId() == R.id.menuProfile){
            Intent i = new Intent(User_page.this, Profile.class);
            i.putExtra("user", user);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            Fragment f = new User_home();
            Bundle b = new Bundle();
            b.putParcelable("user",user);
            f.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.container,f).commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuHome_user){
            Fragment f = new User_home();
            Bundle b = new Bundle();
            b.putParcelable("user",user);
            f.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.container,f).commit();
            return true;
        }
        if (item.getItemId() == R.id.menuHistory_user){
            Fragment f = new User_history();
            Bundle b = new Bundle();
            b.putParcelable("user",user);
            f.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.container,f).commit();
            return true;
        }
//        return super.onOptionsItemSelected(item);
        return false;
    }
}