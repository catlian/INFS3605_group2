package com.example.infs3605_group2.Fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infs3605_group2.Activities.LoginActivity1;
import com.example.infs3605_group2.Models.Transaction;
import com.example.infs3605_group2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParentLog extends Fragment {

    private TableLayout tableLayout;


    public ParentLog() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_parent_log, container, false);
        tableLayout = view.findViewById(R.id.tableLayout);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference transactionRef = database.getReference().child("transactions").child(LoginActivity1.currentUser.getLinkedAccount());

        //need to limit description chars
        transactionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Context context = getContext();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Transaction transaction = snapshot.getValue(Transaction.class);
                    TableRow tbrow = new TableRow(context);
                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT,
                            1.0f);
                    TextView timestamp = new TextView(context);
                    timestamp.setText(transaction.getTimestamp());
                    timestamp.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    timestamp.setGravity(Gravity.CENTER);
                    timestamp.setLayoutParams(layoutParams);
                    tbrow.addView(timestamp);

                    TextView description = new TextView(context);
                    description.setText(String.valueOf(transaction.getDescription()));
                    description.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    description.setGravity(Gravity.CENTER);
                    description.setLayoutParams(layoutParams);
                    tbrow.addView(description);

                    TextView event = new TextView(context);
                    event.setText(String.valueOf(transaction.getEvent()));
                    event.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    event.setGravity(Gravity.CENTER);
                    event.setLayoutParams(layoutParams);
                    tbrow.addView(event);

                    tableLayout.addView(tbrow);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getActivity(), "sorry",
                        Toast.LENGTH_SHORT).show();
            }
        });



        return view;
    }

}
