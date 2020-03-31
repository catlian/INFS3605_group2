package com.example.infs3605_group2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.infs3605_group2.Fragments.ChildLog;
import com.example.infs3605_group2.Fragments.ParentOtherTransactions;
import com.example.infs3605_group2.Fragments.ParentLog;
import com.example.infs3605_group2.Fragments.ParentSavings;
import com.example.infs3605_group2.Fragments.ParentTransactionMain;
import com.example.infs3605_group2.R;

public class ParentActivity extends AppCompatActivity {


    private Button button;
    private Button btnlog;
    private Button btnChores;
    private Button btnSavings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//        Bundle bundle = getIntent().getExtras();
//        String message = bundle.getString("userType");
//        Toast.makeText(ParentActivity.this, message, Toast.LENGTH_SHORT).show();

        ParentOtherTransactions landingFrag = new ParentOtherTransactions();
        swapFragment(landingFrag);


        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParentOtherTransactions landingFrag = new ParentOtherTransactions();
                swapFragment(landingFrag);
            }
        });
        btnlog = findViewById(R.id.button2);
        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParentLog log = new ParentLog();
                swapFragment(log);
            }
        });
        btnlog = findViewById(R.id.btnChores);
        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParentTransactionMain parentTransactionMain = new ParentTransactionMain();
                swapFragment(parentTransactionMain);
            }
        });

        btnSavings = findViewById(R.id.btnSavingsGoal);
        btnSavings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParentSavings savings = new ParentSavings();
                swapFragment(savings);
            }
        });

    }
    private void swapFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_slot, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
