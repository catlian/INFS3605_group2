package com.example.infs3605_group2.Fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    private TextView txtParentBalance;
    private TextView txtChildBalance;
    private TextView txtAmount;
    private Button btnSend;
    private Button btnRetrieve;
    private double amount;
    private double childBalance;
    private double parentBalance;
    private DatabaseReference childBalanceRef;
    private DatabaseReference parentBalanceRef;

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
        txtParentBalance = view.findViewById(R.id.txtParentBalance);
        txtChildBalance = view.findViewById(R.id.txtChildBalance);
        txtAmount = view.findViewById(R.id.editTextAmount);
        btnSend = view.findViewById(R.id.buttonSend);
        btnRetrieve = view.findViewById(R.id.buttonRetrieve);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        childBalanceRef = database.getReference("/userInfo/username2/balance");
        parentBalanceRef = database.getReference("/userInfo/username1/balance");
        Toast.makeText(getActivity(), "in",
                Toast.LENGTH_SHORT).show();

        //will need to code dynamic path with username val

        // Read from the database
        childBalanceRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                childBalance = dataSnapshot.getValue(Long.class);
                txtChildBalance.setText("$" + childBalance);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getActivity(), "sorry",
                        Toast.LENGTH_SHORT).show();

            }
        });

        parentBalanceRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                parentBalance = dataSnapshot.getValue(Long.class);
                txtParentBalance.setText("$" + parentBalance);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getActivity(), "sorry",
                        Toast.LENGTH_SHORT).show();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                amount = Double.parseDouble(txtAmount.getText().toString());
                if(validateBalance(parentBalance)){
                    childBalanceRef.setValue(childBalance + amount);
                    parentBalanceRef.setValue(parentBalance - amount);
                }
                else{
                    Toast.makeText(getActivity(), "mum u need more money",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                amount = Double.parseDouble(txtAmount.getText().toString());
                if(validateBalance(childBalance)){
                    childBalanceRef.setValue(childBalance - amount);
                    parentBalanceRef.setValue(parentBalance + amount);
                }
                else{
                    Toast.makeText(getActivity(), "mum ur kid doesnt even have that much??",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean validateBalance(double balance){
        if(balance - amount >= 0){
            return true;
        }
        else{
            return false;
        }
    }
    //code ref: https://stackoverflow.com/questions/4005728/hide-default-keyboard-on-click-in-android
    private void hideSoftKeyboard(){
        if(getActivity().getCurrentFocus()!=null && getActivity().getCurrentFocus() instanceof EditText){
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(txtAmount.getWindowToken(), 0);
        }
    }

}
