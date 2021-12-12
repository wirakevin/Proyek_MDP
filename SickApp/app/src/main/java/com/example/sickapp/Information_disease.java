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
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Information_disease#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Information_disease extends Fragment {

    ArrayList<Disease> diseases = new ArrayList<>();
    User user;
    EditText insearchpenyakit;
    Button btnsearchpenyakit;
    RecyclerView rvpenyakit;
    DiseaseAdapter adapter;
    String search = "";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Information_disease() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Information_disease.
     */
    // TODO: Rename and change types and number of parameters
    public static Information_disease newInstance(String param1, String param2) {
        Information_disease fragment = new Information_disease();
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
        return inflater.inflate(R.layout.fragment_information_disease, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        insearchpenyakit = view.findViewById(R.id.insearchpenyakit);
        btnsearchpenyakit = view.findViewById(R.id.btnsearchpenyakit);
        rvpenyakit = view.findViewById(R.id.rvpenyakit);

        Bundle b = getArguments();
        if(b!= null) {
            diseases.addAll(b.<Disease>getParcelableArrayList("diseases"));
            user = b.<User>getParcelable("user");
        }

        rvpenyakit.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new DiseaseAdapter(diseases);
        rvpenyakit.setAdapter(adapter);

        adapter.setOnItemClickCallback(new DiseaseAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Disease disease) {
                Intent i = new Intent(getActivity(), Disease_detail.class);
                i.putExtra("disease", disease);
                i.putExtra("user", user);
                startActivity(i);
            }
        });

        btnsearchpenyakit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search = insearchpenyakit.getText().toString();
                if (search.equals("")){
                    new GetAllDisease().execute();
                }
                else{
                    new getDiseaseByName().execute();
                }
            }
        });
    }

    private class getDiseaseByName extends AsyncTask<Void, Void, List<Disease>> {

        @Override
        protected List<Disease> doInBackground(Void... voids) {
            List<Disease> diseases = AppDatabase.database.diseaseDAO().getDiseaseByName(search);
            return diseases;
        }

        @Override
        protected void onPostExecute(List<Disease> listdisease) {
            super.onPostExecute(listdisease);
            // Mau ngapain dari hasil doInBackground
            diseases.clear();
            diseases.addAll(listdisease);
            adapter.notifyDataSetChanged();
        }
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
            adapter.notifyDataSetChanged();
        }
    }
}