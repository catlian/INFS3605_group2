package com.example.infs3605_group2.Fragments;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infs3605_group2.Activities.LoginActivity1;
import com.example.infs3605_group2.Activities.ProfileActivity;
import com.example.infs3605_group2.Activities.SavingsActivity;
import com.example.infs3605_group2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import static android.app.Activity.RESULT_OK;

public class ChildLanding extends Fragment {

    private TextView txtName;
    private TextView txtBalance;
    private ImageView mImageView;
    private Uri imageUri;
    private static final int IMAGE_REQUEST = 2;
    private DatabaseReference pictureRef;
    private String pic1;

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
        txtName.setText(LoginActivity1.currentUser.getUsername());
        txtBalance = view.findViewById(R.id.txtBalance);
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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        childBalanceRef = database.getReference().child("userInfo").child(LoginActivity1.currentUser.getUsername()).child("balance");


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
