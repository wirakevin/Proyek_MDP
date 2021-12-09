package com.example.sickapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Add_obat extends AppCompatActivity {

    ArrayList<Obat> obats = new ArrayList<>();
    ArrayList<Disease> diseases = new ArrayList<>();
    ArrayList<String> listspn = new ArrayList<>();
    RadioButton rbumur, rbumur1, rbumur2, rbumur3;
    TextView tvantara;
    EditText innamaobat, inrasaobat, inobatuntukumur, inobatuntukumur2, intakaranobat;
    Spinner spnobatpenyakit;
    Button btnaddnewobat;
    ArrayAdapter<String> spnadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_obat);
        AppDatabase.initDatabase(getApplicationContext(), "DB");

        new GetAllDisease().execute();
        new GetAllObat().execute();

        innamaobat = findViewById(R.id.innamaobat);
        inrasaobat = findViewById(R.id.inrasaobat);
        rbumur = findViewById(R.id.rbumur);
        rbumur1 = findViewById(R.id.rbumur1);
        rbumur2 = findViewById(R.id.rbumur2);
        rbumur3 = findViewById(R.id.rbumur3);
        tvantara = findViewById(R.id.tvantara);
        inobatuntukumur = findViewById(R.id.inobatuntukumur);
        inobatuntukumur2 = findViewById(R.id.inobatuntukumur2);
        intakaranobat = findViewById(R.id.intakaranobat);
        spnobatpenyakit = findViewById(R.id.spnobatpenyakit);
        btnaddnewobat = findViewById(R.id.btnaddnewobat);

        tvantara.setVisibility(View.INVISIBLE);
        inobatuntukumur2.setVisibility(View.INVISIBLE);

        spnadapter =
                new ArrayAdapter<String>(Add_obat.this,  android.R.layout.simple_spinner_dropdown_item, listspn);
        spnadapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        spnobatpenyakit.setAdapter(spnadapter);

        rbumur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvantara.setVisibility(View.INVISIBLE);
                inobatuntukumur2.setVisibility(View.INVISIBLE);
            }
        });
        rbumur1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvantara.setVisibility(View.INVISIBLE);
                inobatuntukumur2.setVisibility(View.INVISIBLE);
            }
        });
        rbumur2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvantara.setVisibility(View.INVISIBLE);
                inobatuntukumur2.setVisibility(View.INVISIBLE);
            }
        });
        rbumur3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvantara.setVisibility(View.VISIBLE);
                inobatuntukumur2.setVisibility(View.VISIBLE);
            }
        });

        btnaddnewobat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = innamaobat.getText().toString();
                String rasa = inrasaobat.getText().toString();
                String umur = inobatuntukumur.getText().toString();
                String takaran = intakaranobat.getText().toString();
                String penyakit = spnobatpenyakit.getSelectedItem().toString();

                if (nama.equals("") || umur.equals("") || takaran.equals("") || penyakit.equals("...")){
                    Toast.makeText(Add_obat.this, "Field kosong", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (rbumur1.isChecked()){
                        umur = ">," + inobatuntukumur.getText().toString();
                    }
                    else if (rbumur2.isChecked()){
                        umur = "<," + inobatuntukumur.getText().toString();
                    }
                    else if (rbumur3.isChecked()){
                        umur = "-," + inobatuntukumur.getText().toString() + "," + inobatuntukumur2.getText().toString();
                    }

                    Obat obat = new Obat(nama, umur, rasa, takaran, penyakit);
                    new InsertTask().execute(obat);
                    Toast.makeText(Add_obat.this, "Obat added", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Add_obat.this, Admin_page.class);
                    startActivity(i);
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

    private class GetAllDisease extends AsyncTask<Void, Void, List<Disease>>{

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

            listspn.clear();
            listspn.add("...");
            for (Disease d:diseases) {
                listspn.add(d.nama);
            }

            spnadapter.notifyDataSetChanged();
        }
    }
}