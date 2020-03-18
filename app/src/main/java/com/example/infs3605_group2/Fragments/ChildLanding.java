package com.example.infs3605_group2.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infs3605_group2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class ChildLanding extends Fragment {

    private TextView txtName;
    private TextView txtBalance;

    private DatabaseReference childBalanceRef;

    public ChildLanding() {
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
        return inflater.inflate(R.layout.fragment_child_landing, container, false);
    }
    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        txtName = view.findViewById(R.id.txtName);
        txtBalance = view.findViewById(R.id.txtBalance);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        childBalanceRef = database.getReference("/userInfo/username2/balance");
        //will need to code dynamic path with username val

        // Read from the database
        childBalanceRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double childBalance = dataSnapshot.getValue(Long.class);
                txtBalance.setText("$" + childBalance);
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
