package com.example.infs3605_group2.Fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infs3605_group2.Activities.LoginActivity1;
import com.example.infs3605_group2.Models.Transaction;
import com.example.infs3605_group2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParentOtherTransactions extends Fragment {

    private TextView txtParentBalance;
    private TextView txtChildBalance;
    private TextView txtAmount;
    private TextView txtMessage;
    private Button btnSend;
    private Button btnRetrieve;
    private double amount;
    private double childBalance;
    private double parentBalance;
    private DatabaseReference childBalanceRef;
    private DatabaseReference parentBalanceRef;
    private DatabaseReference transactionRef;

    public ParentOtherTransactions() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.parent_other_transactions, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState){
        txtParentBalance = view.findViewById(R.id.txtParentBalance);
        txtChildBalance = view.findViewById(R.id.txtChildBalance);
        txtAmount = view.findViewById(R.id.editTextAmount);
        btnSend = view.findViewById(R.id.buttonSend);
        btnRetrieve = view.findViewById(R.id.buttonRetrieve);
        txtMessage = view.findViewById(R.id.editTextMessage);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        childBalanceRef = database.getReference().child("userInfo").child(LoginActivity1.currentUser.getLinkedAccount()).child("balance");
        parentBalanceRef = database.getReference().child("userInfo").child(LoginActivity1.currentUser.getUsername()).child("balance");
        transactionRef = database.getReference().child("transactions").child(LoginActivity1.currentUser.getLinkedAccount());
        //will need to code dynamic path with username val

        // Read from the database
        childBalanceRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                childBalance = dataSnapshot.getValue(Long.class);
                txtChildBalance.setText("$" + childBalance + "0");
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
                parentBalance = dataSnapshot.getValue(Long.class);
                txtParentBalance.setText("$" + parentBalance + "0");
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
                if (TextUtils.isEmpty(txtMessage.getText().toString())) {
                    Toast.makeText(getActivity(), "Missing Message", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(txtAmount.getText().toString())) {
                    Toast.makeText(getActivity(), "Missing Amount", Toast.LENGTH_SHORT).show();
                }
                else {
                    amount = Double.parseDouble(txtAmount.getText().toString()); //NEED TO VALIDTE INT ONLY
                    if (validateBalance(parentBalance)) {
                        childBalanceRef.setValue(childBalance + amount);
                        parentBalanceRef.setValue(parentBalance - amount);
                        Toast.makeText(getActivity(), "Money Sent",
                                Toast.LENGTH_SHORT).show();
                        pushTransaction("+");
                    } else {
                        Toast.makeText(getActivity(), "Not enough money in account",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                if (TextUtils.isEmpty(txtMessage.getText().toString())) {
                    Toast.makeText(getActivity(), "Missing Message", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(txtAmount.getText().toString())) {
                    Toast.makeText(getActivity(), "Missing Amount", Toast.LENGTH_SHORT).show();
                } else {
                    amount = Double.parseDouble(txtAmount.getText().toString());
                    double roundOff = Math.round(amount * 100.0) / 100.0;
                    System.out.println(roundOff);
                    System.out.format("%.4f", amount);
                    if (validateBalance(childBalance)) {
                        childBalanceRef.setValue(childBalance - roundOff);
                        parentBalanceRef.setValue(parentBalance + roundOff);
                        pushTransaction("-");
                        Toast.makeText(getActivity(), "Money Retrieved",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Not enough money in account",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean validateBalance(double balance){
        if(balance - amount >= 0){
            return true;
        }
        else{
            return false;
        }
    }
    private void pushTransaction(String symbol){
        Transaction transaction = new Transaction();
        transaction.setDescription(txtMessage.getText().toString());
        transaction.setEvent(symbol + "$" + amount);
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        transaction.setDate(dateTimeFormat.format(LocalDateTime.now()));
        transaction.setTime(-1 * new Date().getTime());
        transactionRef.push().setValue(transaction);
    }

    //code ref: https://stackoverflow.com/questions/4005728/hide-default-keyboard-on-click-in-android
    private void hideSoftKeyboard(){
        if(getActivity().getCurrentFocus()!=null && getActivity().getCurrentFocus() instanceof EditText){
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(txtAmount.getWindowToken(), 0);
        }
    }

}
