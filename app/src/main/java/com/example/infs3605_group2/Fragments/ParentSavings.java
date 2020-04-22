package com.example.infs3605_group2.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    private DatabaseReference nameRef;
    private DatabaseReference goalRef;
    private DatabaseReference pictureRef;
    private DatabaseReference balanceRef;
    private String name1;
    private String goal1;
    private String pic1;
    private double number;
    private double number2;
    private TextView progressMsg;
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
        progressMsg = view.findViewById(R.id.textView_YourProgress);
        final ProgressBar simpleProgressBar=(ProgressBar)view.findViewById(R.id.simpleProgressBar); // initiate the progress bar
        simpleProgressBar.setBackgroundColor(Color.GRAY); // black background color for the progress bar
        if (LoginActivity1.currentUser.getUserType().equals("parent")) {
            progressMsg.setText(LoginActivity1.currentUser.getLinkedAccount() + "'s progress");
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SavingsActivity.class);
                startActivity (intent);
            }
        });

        if (LoginActivity1.currentUser.getSavingsGoal().equals("0")){
            name.setVisibility(View.INVISIBLE);
            goal.setVisibility(View.INVISIBLE);
            edit.setText("CREATE");
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        nameRef = database.getReference().child("userInfo").child(LoginActivity1.currentUser.getUsername()).child("savingsName");
        nameRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name1 = dataSnapshot.getValue(String.class);
                name.setText(name1);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getActivity(), "sorry",
                        Toast.LENGTH_SHORT).show();

            }
        });
        goalRef = database.getReference().child("userInfo").child(LoginActivity1.currentUser.getUsername()).child("savingsGoal");
        goalRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                goal1 = dataSnapshot.getValue(String.class);
                goal.setText("$" + goal1);
                number = Integer.parseInt(goal1);
                if (number != 0) {
                    double fraction = ((number2/number) * 100);
                    if (fraction >= 100){
                        fraction = 100;
                    }
                    simpleProgressBar.setProgress((int)(fraction));
                    if (LoginActivity1.currentUser.getUserType().equals("parent")) {
                        progressMsg.setText(LoginActivity1.currentUser.getLinkedAccount() + "'s progress: " + Integer.toString((int)fraction) + "%");
                    }
                    else {
                        progressMsg.setText("Your Progress: " + Integer.toString((int)fraction) + "%");
                    }
                }

            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getActivity(), "sorry",
                        Toast.LENGTH_SHORT).show();

            }
        });
        balanceRef = database.getReference().child("userInfo").child(LoginActivity1.currentUser.getUsername()).child("balance");
        if (LoginActivity1.currentUser.getUserType().equals("parent")) {
            balanceRef = database.getReference().child("userInfo").child(LoginActivity1.currentUser.getLinkedAccount()).child("balance");
        }
        balanceRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                number2 = (double)(dataSnapshot.getValue(Integer.class));
                /*if (number2 > number){
                    number = number2;
                }*/
                if (number != 0) {
                    double fraction = ((number2/number) * 100);
                    if (fraction >= 100){
                        fraction = 100;
                    }
                    simpleProgressBar.setProgress((int) fraction);
                       // progressMsg.append(": " + Integer.toString((int)fraction) + "%");
                    if (LoginActivity1.currentUser.getUserType().equals("parent")) {
                        progressMsg.setText(LoginActivity1.currentUser.getLinkedAccount() + "'s progress: " + Integer.toString((int)fraction) + "%");
                    }
                    else {
                        progressMsg.setText("Your Progress: " + Integer.toString((int)fraction) + "%");
                    }

                }
                if (number2 == number){
                    if (LoginActivity1.currentUser.getUserType().equals("parent")) {
                        Toast.makeText(getActivity(), "Congratulations! " + LoginActivity1.currentUser.getLinkedAccount() + " has met their savings goal :)",
                                Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getActivity(), "Congratulations! You've met your savings goal :)",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        pictureRef = database.getReference().child("userInfo").child(LoginActivity1.currentUser.getUsername()).child("savingsGoalPic");
        pictureRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pic1 = dataSnapshot.getValue(String.class);
                Picasso.get().load(pic1).into(savePic);
                savePic.setVisibility(View.VISIBLE);
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
