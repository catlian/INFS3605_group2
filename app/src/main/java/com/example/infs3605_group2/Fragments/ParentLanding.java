package com.example.infs3605_group2.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infs3605_group2.Activities.LoginActivity1;
import com.example.infs3605_group2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ParentLanding extends Fragment {
    private TextView parentName2;
    private TextView parentBalance2;
    private TextView childBalance2;
    private TextView childName2;

   /* private ImageView mImageView;
    private Uri imageUri;
    private static final int IMAGE_REQUEST = 2;
    private DatabaseReference pictureRef;
    private String pic1;
    */

    private DatabaseReference childBalanceRef;
    private DatabaseReference parentBalanceRef;


    public ParentLanding() {
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
        return inflater.inflate(R.layout.fragment_parent_landing, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        parentName2 = view.findViewById(R.id.parentName2);
        parentName2.setText(LoginActivity1.currentUser.getUsername()+":");
        parentBalance2 = view.findViewById(R.id.parentBalance2);
        childBalance2 = view.findViewById(R.id.childBalance2);
        childName2 = view.findViewById(R.id.childName2);
        childName2.setText(LoginActivity1.currentUser.getLinkedAccount()+":");


        /*
        mImageView = view.findViewById(R.id.imageView_child);
        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        pictureRef = database1.getReference().child("userInfo").child(LoginActivity1.currentUser.getUsername()).child("profileImage");
        pictureRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pic1 = dataSnapshot.getValue(String.class);
                Picasso.get().load(pic1).into(mImageView);
                mImageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getActivity(), "sorry",
                        Toast.LENGTH_SHORT).show();

            }
        });
        mImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity (intent);
            }

        });

         */
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        parentBalanceRef = database.getReference().child("userInfo").child(LoginActivity1.currentUser.getUsername()).child("balance");


        // Read from the database
        parentBalanceRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double childBalance = dataSnapshot.getValue(Long.class);
                parentBalance2.setText("$" + childBalance + "0");
            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getActivity(), "sorry",
                        Toast.LENGTH_SHORT).show();

            }
        });
        childBalanceRef = database.getReference().child("userInfo").child(LoginActivity1.currentUser.getLinkedAccount()).child("balance");
        childBalanceRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double childBalance = dataSnapshot.getValue(Long.class);
                childBalance2.setText("$" + childBalance + "0");
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


