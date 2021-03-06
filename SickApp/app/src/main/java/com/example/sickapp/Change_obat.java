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

public class Change_obat extends AppCompatActivity {

    ArrayList<Obat> obats = new ArrayList<>();
    ArrayList<Disease> diseases = new ArrayList<>();
    ArrayList<String> listspn = new ArrayList<>();
    Obat obat;
    RadioButton rbumur, rbumur1, rbumur2, rbumur3;
    TextView tvantara;
    EditText innamaobat, inrasaobat, inobatuntukumur, inobatuntukumur2, intakaranobat;
    Spinner spnobatpenyakit;
    Button btnupdateobat, btndeleteobat;
    ArrayAdapter<String> spnadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_obat);
        AppDatabase.initDatabase(getApplicationContext(), "DB");

        new GetAllDisease().execute();
        new GetAllObat().execute();

        obat = getIntent().getParcelableExtra("obat");

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
        btnupdateobat = findViewById(R.id.btnupdateobat);
        btndeleteobat = findViewById(R.id.btndeleteobat);

        tvantara.setVisibility(View.INVISIBLE);
        inobatuntukumur2.setVisibility(View.INVISIBLE);

        spnadapter =
                new ArrayAdapter<String>(Change_obat.this,  android.R.layout.simple_spinner_dropdown_item, listspn);
        spnadapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        spnobatpenyakit.setAdapter(spnadapter);

        String[] umur = obat.untuk_umur.split(",");

        innamaobat.setText(obat.nama);
        inrasaobat.setText(obat.rasa);
        if (umur[0].equals("<")){
            inobatuntukumur.setText(umur[1]);
            rbumur1.setChecked(true);
        }
        else if (umur[0].equals(">")){
            inobatuntukumur.setText(umur[1]);
            rbumur2.setChecked(true);
        }
        else if (umur[0].equals("-")){
            inobatuntukumur.setText(umur[1]);
            inobatuntukumur2.setText(umur[2]);
            rbumur3.setChecked(true);
            tvantara.setVisibility(View.VISIBLE);
            inobatuntukumur2.setVisibility(View.VISIBLE);
        }
        else{
            inobatuntukumur.setText(umur[0]);
            rbumur.setChecked(true);
        }
        intakaranobat.setText(obat.takaran);

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

        btnupdateobat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekinput("update");
            }
        });

        btndeleteobat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekinput("delete");
            }
        });
    }

    public void cekinput(String task){
        if (task.equals("update")){
            String nama = innamaobat.getText().toString();
            String rasa = inrasaobat.getText().toString();
            String umur = inobatuntukumur.getText().toString();
            String takaran = intakaranobat.getText().toString();
            String penyakit = spnobatpenyakit.getSelectedItem().toString();

            if (nama.equals("") || umur.equals("") || takaran.equals("") || penyakit.equals("...")){
                Toast.makeText(Change_obat.this, "Field kosong", Toast.LENGTH_SHORT).show();
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

                obat.nama = nama;
                obat.rasa = rasa;
                obat.untuk_umur = umur;
                obat.takaran = takaran;
                obat.untuk_penyakit = penyakit;

                new UpdateTask().execute(obat);
                Toast.makeText(Change_obat.this, "Obat Updated", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Change_obat.this,Admin_page.class);
                startActivity(i);
            }
        }
        else{
            new DeleteTask().execute(obat);
            Toast.makeText(Change_obat.this, "Obat Deleted", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Change_obat.this,Admin_page.class);
            startActivity(i);
        }
    }

    private class UpdateTask extends AsyncTask<Obat, Void, Void> {

        @Override
        protected Void doInBackground(Obat... listobat) {
            AppDatabase.database.obatDAO().update(listobat[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new GetAllTask().execute();
        }
    }

    private class DeleteTask extends AsyncTask<Obat, Void, Void> {

        @Override
        protected Void doInBackground(Obat... listobat) {
            AppDatabase.database.obatDAO().delete(listobat[0]);
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