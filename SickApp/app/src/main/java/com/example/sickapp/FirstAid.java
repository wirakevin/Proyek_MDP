package com.example.sickapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FirstAid extends AppCompatActivity {

    ArrayList<String> gejalapenyakit = new ArrayList<>();
    ArrayList<Disease> diseases = new ArrayList<>();
    ArrayList<Gejala> gejalas = new ArrayList<>();
    ArrayList<String> listmygejala = new ArrayList<>();
    User user;
    TextView tvgejalapenyakit;
    EditText inumur, ingejalapenyakit;
    Button btnsubmitdiagnosa, btnsearch;
    RecyclerView rvgejala;
    GejalaAdapter adapter;
    String search = "", mygejala = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstaid);
        AppDatabase.initDatabase(getApplicationContext(), "DB");
        new GetAllDisease().execute();
        new GetAllGejala().execute();

        user = getIntent().getParcelableExtra("user");

        /*if (!user.dob.equals("")){
            String[] dob = user.dob.split("/");

            int tanggal = Integer.parseInt(dob[0]);
            int bulan = Integer.parseInt(dob[1]);
            int tahun = Integer.parseInt(dob[2]);

            Toast.makeText(FirstAid.this, tanggal + "/" + bulan + "/" + tahun, Toast.LENGTH_SHORT).show();
        }*/

        inumur = findViewById(R.id.inumur);
        tvgejalapenyakit = findViewById(R.id.tvgejalapenyakit2);
        ingejalapenyakit = findViewById(R.id.ingejalapenyakit);
        btnsearch = findViewById(R.id.btnsearch);
        rvgejala = findViewById(R.id.rvgejala);
        btnsubmitdiagnosa = findViewById(R.id.btnsubmitdianosa);

        tvgejalapenyakit.setText("Gejala : " + mygejala);

        rvgejala.setLayoutManager(new LinearLayoutManager(FirstAid.this));
        adapter = new GejalaAdapter(gejalas, gejalapenyakit);
        rvgejala.setAdapter(adapter);

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ingejalapenyakit.getText().toString().equals("")){
                    new GetAllGejala().execute();
                }
                else{
                    search = ingejalapenyakit.getText().toString();
                    new getGejalaByName().execute();
                }
            }
        });

        adapter.setOnItemClickCallback(new GejalaAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Gejala gejala) {
                int n = -1;
                for (int i = 0; i < listmygejala.size(); i++) {
                    if (listmygejala.get(i).equals(gejala.nama)){
                        n = i;
                    }
                }
                if (n >= 0){
                    listmygejala.remove(n);
                }
                else{
                    listmygejala.add(gejala.nama);
                }
                mygejala = "";
                for (String s:listmygejala) {
                    mygejala += s+",";
                }
                tvgejalapenyakit.setText("Gejala : " + mygejala);
            }
        });

        btnsubmitdiagnosa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mygejala.equals("") || inumur.getText().toString().equals("")){
                    Toast.makeText(FirstAid.this, "Field kosong", Toast.LENGTH_SHORT).show();
                }
                else{
                    //Memasukkan gejala  inputan user ke arraylist daatabase(db)
                    ArrayList<String> db = new ArrayList<>();
                    db.clear();
                    String[] tempdb = mygejala.split(",");
                    for (String s:tempdb) {
                        db.add(s);
                    }

                    ArrayList<Disease> nextkb = new ArrayList<>();
                    ArrayList<Disease> kb = new ArrayList<>();
                    Disease mydisease = new Disease("", "");
                    int notfound = 0;

                    //memasukkan semua penyakit yang ada ke dalam nextkb
                    nextkb.clear();
                    new GetAllDisease();
                    nextkb.addAll(diseases);

                    while (nextkb.size() > 1){
                        //Refresh Knowledge Base yang belum dieksekusi
                        kb.clear();
                        kb.addAll(nextkb);
                        nextkb.clear();
                        notfound++;

                        for (Disease nowkb:kb) {
                            //Mengambil gejala penyakit atau knowledge sekarang
                            ArrayList<String> nowkbgejala = new ArrayList<>();
                            nowkbgejala.clear();
                            String[] tempgejala = nowkb.gejala.split(",");
                            for (String s:tempgejala) {
                                nowkbgejala.add(s);
                            }

                            //Jika jumlah gejala inputan user lebih banyak atau sama dengan jumlah gejala penyakit
                            //Maka akan lanjut ke step selanjutnya
                            if (nowkbgejala.size() <= db.size()){
                                int nada = 0;
                                for (String g1: nowkbgejala) {
                                    for (String g2:db) {
                                        if (g1.trim().toUpperCase().equals(g2.trim().toUpperCase())){
                                            nada++;
                                        }
                                    }
                                }

                                //Jika gejala inputan user ada/sama dengan semua gejala penyakit
                                //Maka penyakit akan ditambahkan ke database(db)
                                if (nada >= nowkbgejala.size()){
                                    boolean adadidb = false;
                                    for (String my:db) {
                                        if (my.trim().toUpperCase().equals(nowkb.nama.trim().toUpperCase())){
                                            adadidb = true;
                                        }
                                    }

                                    //pengecekan jika penyakit sudah ada dalam database(db)
                                    if (!adadidb){
                                        db.add(nowkb.nama);
                                        mydisease = nowkb;
                                        notfound = 0;
                                    }
                                }
                                else{
                                    nextkb.add(nowkb);
                                }
                            }
                            else{
                                nextkb.add(nowkb);
                            }
                        }
                        //Jika setelah 3x loop tidak ada data baru yang dimasukkan kedalam database(db)
                        //Loop akan diakhiri dan mengambil hasil terakhir
                        if (notfound > 3){
                            nextkb.clear();
                        }
                    }

                    Intent i = new Intent(FirstAid.this,FirstAid_end.class);
                    i.putExtra("user", user);
                    i.putExtra("umur", inumur.getText().toString());
                    i.putExtra("mydisease", mydisease);
                    startActivity(i);
                }
            }
        });
    }

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
        }
    }

    private class GetAllGejala extends AsyncTask<Void, Void, List<Gejala>> {

        @Override
        protected List<Gejala> doInBackground(Void... voids) {
            List<Gejala> gejalas = AppDatabase.database.gejalaDAO().getAllGejala();
            return gejalas;
        }

        @Override
        protected void onPostExecute(List<Gejala> listgejala) {
            super.onPostExecute(listgejala);
            // Mau ngapain dari hasil doInBackground
            gejalas.clear();
            gejalas.addAll(listgejala);
            adapter.notifyDataSetChanged();
        }
    }

    private class getGejalaByName extends AsyncTask<Void, Void, List<Gejala>> {

        @Override
        protected List<Gejala> doInBackground(Void... voids) {
            List<Gejala> gejalas = AppDatabase.database.gejalaDAO().getGejalaByName(search);
            return gejalas;
        }

        @Override
        protected void onPostExecute(List<Gejala> listgejala) {
            super.onPostExecute(listgejala);
            // Mau ngapain dari hasil doInBackground
            gejalas.clear();
            gejalas.addAll(listgejala);
            adapter.notifyDataSetChanged();
        }
    }
}