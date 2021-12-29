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
 * Use the {@link User_history#newInstance} factory method to
 * create an instance of this fragment.
 */
public class User_history extends Fragment {

    ArrayList<Disease> diseases = new ArrayList<>();
    User user;
    RecyclerView rvpenyakit;
    DiseaseAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public User_history() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment User_history.
     */
    // TODO: Rename and change types and number of parameters
    public static User_history newInstance(String param1, String param2) {
        User_history fragment = new User_history();
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
        return inflater.inflate(R.layout.fragment_user_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvpenyakit = view.findViewById(R.id.rvpenyakit);

        Bundle b = getArguments();
        if(b!= null) {
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

        if (user.history_penyakit != null){
            new GetAllDisease().execute();
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


            ArrayList<String> db = new ArrayList<>();
            db.clear();
            String[] tempdb = user.history_penyakit.split(",");
            for (String s:tempdb) {
                db.add(s);
            }

            for (Disease d:listdisease) {
                for (String myd:db) {
                    if (d.nama.equals(myd)){
                        diseases.add(d);
                    }
                }
            }

            adapter.notifyDataSetChanged();
        }
    }
}