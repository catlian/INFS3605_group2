package com.example.infs3605_group2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.infs3605_group2.Fragments.ParentChoreView;
import com.example.infs3605_group2.Fragments.ParentLanding;
import com.example.infs3605_group2.Fragments.ParentLog;
import com.example.infs3605_group2.Fragments.ParentTransactions;
import com.example.infs3605_group2.R;

public class ParentActivity extends AppCompatActivity {


    private Button button;
    private Button btnlog;
    private Button btnChores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//        Bundle bundle = getIntent().getExtras();
//        String message = bundle.getString("userType");
//        Toast.makeText(ParentActivity.this, message, Toast.LENGTH_SHORT).show();

        ParentLanding landingFrag = new ParentLanding();
        swapFragment(landingFrag);


        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParentLanding landingFrag = new ParentLanding();
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
                ParentTransactions parentTransactions = new ParentTransactions();
                swapFragment(parentTransactions);
            }
        });

    }
    private void swapFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_slot, fragment);
        fragmentTransaction.commit();
    }
}
