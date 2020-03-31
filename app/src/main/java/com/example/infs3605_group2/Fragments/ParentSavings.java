package com.example.infs3605_group2.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.infs3605_group2.Activities.LoginActivity1;
import com.example.infs3605_group2.Activities.SavingsActivity;
import com.example.infs3605_group2.Activities.SavingsActivityBase;
import com.example.infs3605_group2.Models.User;
import com.example.infs3605_group2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;


public class ParentSavings extends Fragment {

    private Context mContext;
    private TextView name;
    private TextView goal;
    private Button edit;

    private ImageView savePic;
    private String username;
    public static User currentUser;

    public ParentSavings() {
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
        final View view = inflater.inflate(R.layout.fragment_parent_savings, container, false);
        return view;
    }
    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {

        edit = view.findViewById(R.id.btnCreate);
        name = view.findViewById(R.id.textView_name);
        goal = view.findViewById(R.id.textView_amount);
        savePic = view.findViewById(R.id.imageView_SavingsPic);
        savePic.setVisibility(View.INVISIBLE);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SavingsActivity.class);
                startActivity (intent);
            }
        });
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("userInfo").child(LoginActivity1.currentUser.getUsername());

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (LoginActivity1.currentUser.getSavingsGoal() == "0"){
                    name.setVisibility(View.INVISIBLE);
                    goal.setVisibility(View.INVISIBLE);
                    edit.setText("CREATE");
                }
                Picasso.get().load(LoginActivity1.currentUser.getSavingsGoalPic()).into(savePic);
                name.setText(LoginActivity1.currentUser.getSavingsName());
                goal.setText(LoginActivity1.currentUser.getSavingsGoal());
                savePic.setVisibility(View.VISIBLE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
