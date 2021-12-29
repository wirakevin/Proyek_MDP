package com.example.sickapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link User_home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class User_home extends Fragment {

    User user;
    CardView cardaid, cardinfo;
    TextView tvnamauser;
    RelativeLayout header;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public User_home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment User_home.
     */
    // TODO: Rename and change types and number of parameters
    public static User_home newInstance(String param1, String param2) {
        User_home fragment = new User_home();
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
        return inflater.inflate(R.layout.fragment_user_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cardaid = view.findViewById(R.id.cardaid);
        cardinfo = view.findViewById(R.id.cardinfo);
        tvnamauser = view.findViewById(R.id.tvnamauser);
        header = view.findViewById(R.id.rlheaderuser);

        Bundle b = getArguments();
        if(b!= null) {
            user = b.getParcelable("user");
            if (user.nama != null){
                tvnamauser.setText("Hi " + user.nama + "!");
            }
            else{
                tvnamauser.setText("Hi!");
            }
        }

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Profile.class);
                i.putExtra("user", user);
                startActivity(i);
            }
        });

        cardaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), FirstAid.class);
                i.putExtra("user", user);
                startActivity(i);
            }
        });

        cardinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Information_page.class);
                i.putExtra("user", user);
                startActivity(i);
            }
        });
    }

}