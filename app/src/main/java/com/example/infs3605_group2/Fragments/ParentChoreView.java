package com.example.infs3605_group2.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.infs3605_group2.Activities.LoginActivity1;
import com.example.infs3605_group2.Models.Chore;
import com.example.infs3605_group2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParentChoreView extends Fragment{
    private RecyclerView recyclerView;
    private Fragment thisFragment = this;
    private ArrayList<Chore> choreData = new ArrayList<>();
//    private ParentChoreAdapter parentChoreAdapter = new ParentChoreAdapter();

    // Required empty public constructor

    public ParentChoreView() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_parent_chores, container, false);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference choresRef = database.getReference().child("chores").child(LoginActivity1.currentUser.getLinkedAccount());

        choresRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chore chore = snapshot.getValue(Chore.class);
                    choreData.add(chore);
                }
//                GenericTypeIndicator<ArrayList<Chore>> t = new GenericTypeIndicator<ArrayList<Chore>>() {};
//                choreData = dataSnapshot.getValue(t);
                recyclerView = getView().findViewById(R.id.rv_chores);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                ParentChoreAdapter parentChoreAdapter = new ParentChoreAdapter(choreData);
                recyclerView.setAdapter(parentChoreAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getActivity(), "sorry",
                        Toast.LENGTH_SHORT).show();
            }
        });
        System.out.println(choreData);

        return view;
    }


}
