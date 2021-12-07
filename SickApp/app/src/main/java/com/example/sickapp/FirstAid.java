package com.example.sickapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FirstAid extends AppCompatActivity {

    ArrayList<Obat> obats = new ArrayList<>();
    User user;
    CheckBox cb1, cb2, cb3, cb4, cb5;
    EditText inumur, insuhu;
    Button btnsubmitdiagnosa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstaid);
        AppDatabase.initDatabase(getApplicationContext(), "DB");
        new GetAllObat().execute();

        cb1 = findViewById(R.id.cb1);
        cb2 = findViewById(R.id.cb2);
        cb3 = findViewById(R.id.cb3);
        cb4 = findViewById(R.id.cb4);
        cb5 = findViewById(R.id.cb5);
        inumur = findViewById(R.id.inumur);
        insuhu = findViewById(R.id.insuhu);
        btnsubmitdiagnosa = findViewById(R.id.btnsubmitdianosa);

        btnsubmitdiagnosa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (insuhu.getText().toString().equals("") || inumur.getText().toString().equals("")){
                    Toast.makeText(FirstAid.this, "Field kosong", Toast.LENGTH_SHORT).show();
                }
                else{
                    ArrayList<String> gejala = new ArrayList<>();
                    if (cb1.isChecked()){
                        gejala.add("Batuk");
                    }
                    if (cb2.isChecked()){
                        gejala.add("Berdahak");
                    }
                    if (cb3.isChecked()){
                        gejala.add("Pilek");
                    }
                    if (cb4.isChecked()){
                        gejala.add("Hidung tersumbat");
                    }
                    if (cb5.isChecked()){
                        gejala.add("Sakit kepala");
                    }

                    if (gejala.size() < 1){
                        Toast.makeText(FirstAid.this, "Gejala kosong", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String ingejala = "";
                        for (int i = 0; i < gejala.size(); i++) {
                            if (i == 0){
                                ingejala = gejala.get(i);
                            }
                            else{
                                ingejala += "," + gejala.get(i);
                            }
                        }


                    }
                }
            }
        });
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