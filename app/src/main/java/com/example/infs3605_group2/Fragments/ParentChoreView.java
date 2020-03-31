package com.example.infs3605_group2.Fragments;


import android.app.AlertDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infs3605_group2.Activities.LoginActivity1;
import com.example.infs3605_group2.Models.Chore;
import com.example.infs3605_group2.Models.Transaction;
import com.example.infs3605_group2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParentChoreView extends Fragment{
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private Fragment thisFragment = this;
    private ArrayList<Chore> choreData = new ArrayList<>();

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
        recyclerView = view.findViewById(R.id.rv_chores);
        floatingActionButton = view.findViewById(R.id.floatingActionButton);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference choresRef = database.getReference().child("chores").child(LoginActivity1.currentUser.getLinkedAccount());

        choresRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                choreData.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chore chore = snapshot.getValue(Chore.class);
                    chore.setKey(snapshot.getKey());
                    choreData.add(chore);
                }

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

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
            }
        });

        return view;
    }
    public void onViewCreated(final View view, Bundle savedInstanceState) {

    }
    private void swapFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_slot, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void createDialog(){
        LayoutInflater factory = LayoutInflater.from(getContext());
        final View createChoreDialogView = factory.inflate(R.layout.fragment_new_chore, null);
        final AlertDialog createChoreDialog = new AlertDialog.Builder(getContext()).create();
        final TextView txtDescription = createChoreDialogView.findViewById(R.id.txtDescription);
        final EditText txtAmount = createChoreDialogView.findViewById(R.id.txtAmount);
        final Spinner spinnerIcon = createChoreDialogView.findViewById(R.id.spinnerIcon);

        Integer[] ids = new Integer[]{1,2,3};

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>
                (getContext(),android.R.layout.simple_spinner_item, ids);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIcon.setAdapter(adapter);
        createChoreDialog.setView(createChoreDialogView);

        createChoreDialogView.findViewById(R.id.btnCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chore chore = new Chore();
                chore.setAmount(Double.valueOf(txtAmount.getText().toString()));
                chore.setDescription(txtDescription.getText().toString());
                chore.setIcon(Integer.valueOf(spinnerIcon.getSelectedItem().toString()));
                chore.setIsDone("false");

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference choreRef = database.getReference().child("chores").
                        child(LoginActivity1.currentUser.getLinkedAccount());
                choreRef.push().setValue(chore);

                createChoreDialog.dismiss();
            }
        });
        createChoreDialogView.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createChoreDialog.dismiss();
            }
        });

        createChoreDialog.show();
    }

}
