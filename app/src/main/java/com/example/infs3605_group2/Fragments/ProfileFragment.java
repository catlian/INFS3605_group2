package com.example.infs3605_group2.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.infs3605_group2.Activities.ChildActivity;
import com.example.infs3605_group2.Activities.LoginActivity1;
import com.example.infs3605_group2.Activities.ParentActivity;
import com.example.infs3605_group2.Activities.SavingsActivity;
import com.example.infs3605_group2.R;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileFragment extends Fragment {
    private ImageView koala;
    private ImageView bear;
    private ImageView chicken;
    private ImageView platypus;
    private ImageView kangaroo;

    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        koala = view.findViewById(R.id.imageView_koala);
        bear = view.findViewById(R.id.imageView_bear);
        kangaroo = view.findViewById(R.id.imageView_kangaroo);
        chicken = view.findViewById(R.id.imageView_chicken);
        platypus = view.findViewById(R.id.imageView_platypus);
        koala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("userInfo").child(LoginActivity1.currentUser.getUsername()).child("profileImage").setValue("koala");
                if (LoginActivity1.currentUser.getUserType().equals("parent")){
                    Intent intent = new Intent(getActivity(), ParentActivity.class);
                    startActivity (intent);

                }
                else{
                    Intent intent = new Intent(getActivity(), ChildActivity.class);
                    startActivity (intent);

                }
            }
        });
        bear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("userInfo").child(LoginActivity1.currentUser.getUsername()).child("profileImage").setValue("bear");
                if (LoginActivity1.currentUser.getUserType().equals("parent")){
                    Intent intent = new Intent(getActivity(), ParentActivity.class);
                    startActivity (intent);

                }
                else{
                    Intent intent = new Intent(getActivity(), ChildActivity.class);
                    startActivity (intent);

                }
            }

        });
        kangaroo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("userInfo").child(LoginActivity1.currentUser.getUsername()).child("profileImage").setValue("kangaroo");
                if (LoginActivity1.currentUser.getUserType().equals("parent")){
                    Intent intent = new Intent(getActivity(), ParentActivity.class);
                    startActivity (intent);

                }
                else{
                    Intent intent = new Intent(getActivity(), ChildActivity.class);
                    startActivity (intent);

                }
            }
        });
        chicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("userInfo").child(LoginActivity1.currentUser.getUsername()).child("profileImage").setValue("chicken");
                if (LoginActivity1.currentUser.getUserType().equals("parent")){
                    Intent intent = new Intent(getActivity(), ParentActivity.class);
                    startActivity (intent);

                }
                else{
                    Intent intent = new Intent(getActivity(), ChildActivity.class);
                    startActivity (intent);

                }
            }

        });
        platypus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("userInfo").child(LoginActivity1.currentUser.getUsername()).child("profileImage").setValue("platypus");
                if (LoginActivity1.currentUser.getUserType().equals("parent")){
                    Intent intent = new Intent(getActivity(), ParentActivity.class);
                    startActivity (intent);

                }
                else{
                    Intent intent = new Intent(getActivity(), ChildActivity.class);
                    startActivity (intent);

                }
            }
        });
    }
}
