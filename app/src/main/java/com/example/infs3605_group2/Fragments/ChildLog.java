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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class ChildLog extends Fragment {
    private TableLayout tableLayout;
    private Context mContext;

    public ChildLog() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_child_log, container, false);
        tableLayout = view.findViewById(R.id.tableLayout);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Query transactionRef = database.getReference("/transactions/" +
                LoginActivity1.currentUser.getUsername()).orderByChild("time");

        //need to limit description chars
        transactionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Transaction transaction = snapshot.getValue(Transaction.class);
                    TableRow tbrow = new TableRow(mContext);
                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT,
                            1.0f);
                    TextView timestamp = new TextView(mContext);
                    timestamp.setText(transaction.getDate());
                    timestamp.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    timestamp.setGravity(Gravity.CENTER);
                    timestamp.setLayoutParams(layoutParams);
                    tbrow.addView(timestamp);

                    TextView description = new TextView(mContext);
                    description.setText(String.valueOf(transaction.getDescription()));
                    description.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    description.setGravity(Gravity.CENTER);
                    description.setLayoutParams(layoutParams);
                    tbrow.addView(description);

                    TextView event = new TextView(mContext);
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
