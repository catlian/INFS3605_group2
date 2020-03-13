package com.example.infs3605_group2.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.infs3605_group2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParentLanding extends Fragment {

    private TextView txtBalance;
    private TextView txtAmount;
    private Button btnSend;
    private Button btnRetrieve;
    private double amount;

    public ParentLanding() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_parent_landing, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState){
        txtBalance = view.findViewById(R.id.txtBalance);
        txtAmount = view.findViewById(R.id.editTextAmount);
        btnSend = view.findViewById(R.id.buttonSend);
        btnRetrieve = view.findViewById(R.id.buttonRetrieve);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference childBalanceRef = database.getReference("/userInfo/username2/balance");
        //will need to code dynamic path with username val

        // Read from the database
        childBalanceRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                txtBalance.setText("$" + dataSnapshot.getValue(Long.class).toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("error");

            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount = Double.parseDouble(txtAmount.getText().toString());
            }
        });
    }

}
