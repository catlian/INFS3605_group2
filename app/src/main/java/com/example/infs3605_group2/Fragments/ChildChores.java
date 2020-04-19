package com.example.infs3605_group2.Fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infs3605_group2.Activities.LoginActivity1;
import com.example.infs3605_group2.Models.Chore;
import com.example.infs3605_group2.Models.Transaction;
import com.example.infs3605_group2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChildChores extends Fragment {
    private RecyclerView recyclerLog;
    private RecyclerView recyclerNewChores;
    private RecyclerView recyclerPendingChores;
    private Context mContext;
    private FirebaseDatabase database;

    private ArrayList<Transaction> transactionArrayList = new ArrayList<>();
    private ArrayList<Chore> choreNewArrayList = new ArrayList<>();
    private ArrayList<Chore> chorePendingArrayList = new ArrayList<>();
    private ArrayList<Chore> allChores = new ArrayList<>();

    public ChildChores() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_child_chores, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        recyclerLog = view.findViewById(R.id.recyclerLog);
        recyclerNewChores = view.findViewById(R.id.recyclerNewChores);
        recyclerPendingChores = view.findViewById(R.id.recyclerPendingChores);

        database = FirebaseDatabase.getInstance();
        DatabaseReference notDoneRef = database.getReference("/chores/" + LoginActivity1.currentUser.getUsername());
        notDoneRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allChores.clear();
                choreNewArrayList.clear();
                chorePendingArrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chore chore = snapshot.getValue(Chore.class);
                    chore.setKey(snapshot.getKey());
                    allChores.add(chore);
                }

                for (Chore aChore : allChores) {
                    if (aChore.getIsDone().equals("true")) {
                        chorePendingArrayList.add(aChore);
                    } else {
                        choreNewArrayList.add(aChore);
                    }
                }
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerNewChores.setLayoutManager(layoutManager);
                ChildNewChoreAdapter childNewChoreAdapter = new ChildNewChoreAdapter(choreNewArrayList);
                recyclerNewChores.setAdapter(childNewChoreAdapter);

                LinearLayoutManager layoutManagerPending = new LinearLayoutManager(getContext());
                recyclerPendingChores.setLayoutManager(layoutManagerPending);
                ChildPendingChoreAdapter childPendingChoreAdapter = new ChildPendingChoreAdapter(chorePendingArrayList);
                recyclerPendingChores.setAdapter(childPendingChoreAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getActivity(), "sorry",
                        Toast.LENGTH_SHORT).show();
            }
        });

        Query transactionRef = database.getReference("/transactions/" +
                LoginActivity1.currentUser.getUsername()).orderByChild("time");

        //need to limit description chars
        transactionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                transactionArrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Transaction transaction = snapshot.getValue(Transaction.class);
                    transactionArrayList.add(transaction);
                }

                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerLog.setLayoutManager(layoutManager);
                ChildLogAdapter childLogAdapter = new ChildLogAdapter(transactionArrayList);
                recyclerLog.setAdapter(childLogAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getActivity(), "sorry",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
