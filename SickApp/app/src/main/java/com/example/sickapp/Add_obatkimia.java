package com.example.sickapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Add_obatkimia extends AppCompatActivity {

    ArrayList<Obat> obats = new ArrayList<>();
    EditText innamaobat, inrasaobat, inumurobat, intakaranobat, inhargaobat;
    CheckBox cb1, cb2, cb3, cb4, cb5;
    Button btnaddobat_kimia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_obatkimia);
        AppDatabase.initDatabase(getApplicationContext(), "DB");
        new GetAllObat().execute();

        innamaobat = findViewById(R.id.innamaobat);
        inrasaobat = findViewById(R.id.inrasaobat);
        inumurobat = findViewById(R.id.inumurobat);
        intakaranobat = findViewById(R.id.intakaranobat);
        inhargaobat = findViewById(R.id.inhargaobat);
        cb1 = findViewById(R.id.cb1);
        cb2 = findViewById(R.id.cb2);
        cb3 = findViewById(R.id.cb3);
        cb4 = findViewById(R.id.cb4);
        cb5 = findViewById(R.id.cb5);
        btnaddobat_kimia = findViewById(R.id.btnaddobat_kimia);

        btnaddobat_kimia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = innamaobat.getText().toString();
                String rasa = inrasaobat.getText().toString();
                String untuk_umur = inumurobat.getText().toString();
                String takaran = intakaranobat.getText().toString();
                String harga = inhargaobat.getText().toString();

                if (nama.equals("") || untuk_umur.equals("") || takaran.equals("")){
                    Toast.makeText(Add_obatkimia.this, "Field kosong", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Add_obatkimia.this, "Gejala kosong", Toast.LENGTH_SHORT).show();
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
                        Obat obat = new Obat(nama, untuk_umur, takaran, ingejala);
                        if (!rasa.equals("")){
                            obat.rasa = rasa;
                        }
                        if (!harga.equals("")){
                            obat.harga = harga;
                        }
                        new InsertTask().execute(obat);
                        Toast.makeText(Add_obatkimia.this, "Obat Added", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Add_obatkimia.this,Admin_page.class);
                        startActivity(i);
                    }
                }
            }
        });
    }

    private class InsertTask extends AsyncTask<Obat, Void, Void> {

        @Override
        protected Void doInBackground(Obat... listobat) {
            AppDatabase.database.obatDAO().insert(listobat[0]);
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
            obats.clear();
            obats.addAll(AppDatabase.database.obatDAO().getAllObat());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private class GetAllObat extends AsyncTask<Void, Void, List<Obat>>{

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