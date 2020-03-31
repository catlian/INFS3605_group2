package com.example.infs3605_group2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.infs3605_group2.Fragments.ChildChores;
import com.example.infs3605_group2.Fragments.ChildLanding;
import com.example.infs3605_group2.Fragments.ChildLog;
import com.example.infs3605_group2.Fragments.ParentSavings;
import com.example.infs3605_group2.R;

public class ChildActivity extends AppCompatActivity {

    private Button btnLanding;
    private Button btnLog;
    private Button btnChore;
    private Button btnSavings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);
//        Bundle bundle = getIntent().getExtras();
//        String message = bundle.getString("userType");

        ChildLanding landingFrag = new ChildLanding();
        swapFragment(landingFrag);

        btnLanding = findViewById(R.id.btnLanding);
        btnLanding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChildLanding landingFrag = new ChildLanding();
                swapFragment(landingFrag);
            }
        });
        btnLog = findViewById(R.id.btnLog);
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChildLog log = new ChildLog();
                swapFragment(log);
            }
        });
        btnChore = findViewById(R.id.btnChores);
        btnChore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChildChores chores = new ChildChores();
                swapFragment(chores);
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
