package com.example.infs3605_group2.Fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
    private TableLayout tblNotDone;
    private TableLayout tblPending;
    private Context mContext;
    private FirebaseDatabase database;

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
        tblNotDone = view.findViewById(R.id.tblNotDone);
        tblPending = view.findViewById(R.id.tblPending);
        database = FirebaseDatabase.getInstance();
        DatabaseReference notDoneRef = database.getReference("/chores/" + LoginActivity1.currentUser.getUsername());
        //need to limit description chars

        notDoneRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        tblNotDone.removeAllViews();
                        tblPending.removeAllViews();
                        ArrayList<Chore> allChores = new ArrayList<>();
                        ArrayList<Chore> notDoneChores = new ArrayList<>();
                        ArrayList<Chore> pendingChores = new ArrayList<>();
                        allChores.clear();
                        notDoneChores.clear();
                        pendingChores.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Chore chore = snapshot.getValue(Chore.class);
                            chore.setKey(snapshot.getKey());
                            allChores.add(chore);
                        }

                        for (Chore aChore : allChores) {
                            if (aChore.getIsDone().equals("true")) {
                                pendingChores.add(aChore);
                            } else {
                                notDoneChores.add(aChore);
                            }
                        }
                        addNotDoneChores(notDoneChores);
                        addPending(pendingChores);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Toast.makeText(getActivity(), "sorry",
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }
    private void addNotDoneChores(ArrayList<Chore> chores){
        for(final Chore chore : chores){
            TableRow tbrow = new TableRow(mContext);
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT,
                    1.0f);
            TextView icon = new TextView(mContext);
            icon.setText(String.valueOf(chore.getIcon()));
            icon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            icon.setGravity(Gravity.CENTER);
            icon.setLayoutParams(layoutParams);
            tbrow.addView(icon);

            TextView description = new TextView(mContext);
            description.setText((chore.getDescription()));
            description.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            description.setGravity(Gravity.CENTER);
            description.setWidth(200);
            tbrow.addView(description);

            TextView amount = new TextView(mContext);
            amount.setText(String.valueOf(chore.getAmount()));
            amount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            amount.setGravity(Gravity.CENTER);
            amount.setLayoutParams(layoutParams);
            tbrow.addView(amount);

            Button btn = new Button(mContext);
            btn.setText("Done");
            tbrow.addView(btn);


            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference notDoneRef = database.getReference("/chores/" +
                            LoginActivity1.currentUser.getUsername() + "/" + chore.getKey());
                    notDoneRef.setValue(chore.toMap());
                }
            });

            tblNotDone.addView(tbrow);
        }
    }
    private void addPending(ArrayList<Chore> chores){
        for (Chore chore : chores){
            TableRow tbrow = new TableRow(mContext);
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT,
                    1.0f);
            TextView icon = new TextView(mContext);
            icon.setText(String.valueOf(chore.getIcon()));
            icon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            icon.setGravity(Gravity.CENTER);
            icon.setLayoutParams(layoutParams);
            tbrow.addView(icon);

            TextView description = new TextView(mContext);
            description.setText((chore.getDescription()));
            description.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            description.setGravity(Gravity.CENTER);
            description.setWidth(200);
            tbrow.addView(description);

            TextView amount = new TextView(mContext);
            amount.setText(String.valueOf(chore.getAmount()));
            amount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            amount.setGravity(Gravity.CENTER);
            tbrow.addView(amount);

            tblPending.addView(tbrow);
        }
    }
}
