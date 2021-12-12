package com.example.sickapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Information_obat#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Information_obat extends Fragment {

    ArrayList<Disease> diseases = new ArrayList<>();
    ArrayList<Obat> obats = new ArrayList<>();
    ArrayList<String> listspn = new ArrayList<>();
    User user;
    EditText insearchobat;
    Spinner spnpenyakit;
    Button btnsearchobat;
    RecyclerView rvobat;
    ObatAdapter adapter;
    String search = "";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Information_obat() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Information_obat.
     */
    // TODO: Rename and change types and number of parameters
    public static Information_obat newInstance(String param1, String param2) {
        Information_obat fragment = new Information_obat();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_information_obat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        insearchobat = view.findViewById(R.id.insearchobat);
        spnpenyakit = view.findViewById(R.id.spnpenyakit);
        btnsearchobat = view.findViewById(R.id.btnsearchobat);
        rvobat = view.findViewById(R.id.rvobat);

        Bundle b = getArguments();
        if(b!= null) {
            diseases.addAll(b.<Disease>getParcelableArrayList("diseases"));
            obats.addAll(b.<Obat>getParcelableArrayList("obats"));
            user = b.<User>getParcelable("user");
        }

        rvobat.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ObatAdapter(obats);
        rvobat.setAdapter(adapter);

        listspn.add("...");
        for (Disease d:diseases) {
            listspn.add(d.nama);
        }

        ArrayAdapter<String> spnadapter =
                new ArrayAdapter<String>(getContext(),  android.R.layout.simple_spinner_dropdown_item, listspn);
        spnadapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        spnpenyakit.setAdapter(spnadapter);

        adapter.setOnItemClickCallback(new ObatAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Obat obat) {
                Intent i = new Intent(getActivity(), Obat_detail.class);
                i.putExtra("obat", obat);
                i.putExtra("user", user);
                startActivity(i);
            }
        });

        btnsearchobat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search = insearchobat.getText().toString();
                if (search.equals("")){
                    new GetAllObat().execute();
                }
                else{
                    new getObatByName().execute();
                }
            }
        });
    }

    private class getObatByName extends AsyncTask<Void, Void, List<Obat>> {

        @Override
        protected List<Obat> doInBackground(Void... voids) {
            List<Obat> obats = AppDatabase.database.obatDAO().getObatByName(search);
            return obats;
        }

        @Override
        protected void onPostExecute(List<Obat> listobat) {
            super.onPostExecute(listobat);
            // Mau ngapain dari hasil doInBackground
            obats.clear();
            obats.addAll(listobat);
            adapter.notifyDataSetChanged();
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
            adapter.notifyDataSetChanged();
        }
    }
}