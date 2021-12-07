package com.example.sickapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login extends Fragment {

    ArrayList<User> users = new ArrayList<>();
    EditText inusername, inpassword;
    Button btnlogin, btnlogin2;

    User now;

    OnFragmentInteractionListener onFragmentInteractionListener;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Login() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login.
     */
    // TODO: Rename and change types and number of parameters
    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
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
        if (context instanceof Login.OnFragmentInteractionListener){
            onFragmentInteractionListener = (Login.OnFragmentInteractionListener)context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inusername = view.findViewById(R.id.inusername);
        inpassword = view.findViewById(R.id.inpassword);
        btnlogin = view.findViewById(R.id.btnlogin);
        btnlogin2 = view.findViewById(R.id.btnlogin2);

        Bundle b = getArguments();
        if(b!= null) {
            users.addAll(b.<User>getParcelableArrayList("users"));
        }

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inusername.getText().toString().equals("") || inpassword.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Field kosong", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (inusername.getText().toString().equals("admin") && inpassword.getText().toString().equals("admin")){
                        Intent i = new Intent(getActivity(),Admin_page.class);
                        startActivity(i);
                    }
                    else{
                        int n = -1;
                        for (int i = 0; i < users.size(); i++) {
                            if (inusername.getText().toString().equals(users.get(i).username)){
                                n = i;
                            }
                        }

                        if (n < 0){
                            Toast.makeText(getActivity(), "Username tidak ada", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            User user = users.get(n);
                            if (inpassword.getText().toString().equals(user.password)){
                                onFragmentInteractionListener.login(user);
                                inusername.setText("");
                                inpassword.setText("");
                            }
                            else{
                                Toast.makeText(getActivity(), "Password salah", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });

        btnlogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Guest_home.class);
                startActivity(i);
            }
        });
    }

    public interface OnFragmentInteractionListener{
        void login(User user);
    }
}