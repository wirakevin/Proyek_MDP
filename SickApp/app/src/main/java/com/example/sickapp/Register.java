package com.example.sickapp;

import android.content.Context;
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
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Register#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Register extends Fragment {

    ArrayList<User> users = new ArrayList<>();
    EditText inusername, inpassword;
    Button btnregister;

    User now;

    OnFragmentInteractionListener onFragmentInteractionListener;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Register() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Register.
     */
    // TODO: Rename and change types and number of parameters
    public static Register newInstance(String param1, String param2) {
        Register fragment = new Register();
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
    public void onAttach( Context context) {
        super.onAttach(context);
        if (context instanceof Register.OnFragmentInteractionListener){
            onFragmentInteractionListener = (Register.OnFragmentInteractionListener)context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inusername = view.findViewById(R.id.inusername);
        inpassword = view.findViewById(R.id.inpassword);
        btnregister = view.findViewById(R.id.btnregister);

        Bundle b = getArguments();
        if(b!= null) {
            users.addAll(b.<User>getParcelableArrayList("users"));
        }

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inusername.getText().toString().equals("") || inpassword.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Field kosong", Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean ada = false;
                    for (User user:users) {
                        if (user.username.equals(inusername.getText().toString())){
                            ada = true;
                        }
                    }
                    if (ada){
                        Toast.makeText(getActivity(), "Username telah terpakai", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        User user = new User(inusername.getText().toString(), inpassword.getText().toString());
                        onFragmentInteractionListener.insertuser(user);
                        inusername.setText("");
                        inpassword.setText("");
                    }
                }
            }
        });
    }

    public interface OnFragmentInteractionListener{
        void insertuser(User user);
    }
}