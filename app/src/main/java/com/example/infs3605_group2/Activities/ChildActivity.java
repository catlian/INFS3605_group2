package com.example.infs3605_group2.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.infs3605_group2.FragmentSwapper;
import com.example.infs3605_group2.Fragments.ChildLanding;
import com.example.infs3605_group2.Fragments.ChildLog;
import com.example.infs3605_group2.R;

public class ChildActivity extends AppCompatActivity {

    private FragmentSwapper fs = new FragmentSwapper();
    private Button btnLanding;
    private Button btnLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);
//        Bundle bundle = getIntent().getExtras();
//        String message = bundle.getString("userType");


        btnLanding = findViewById(R.id.btnLanding);
        btnLanding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChildLanding landingFrag = new ChildLanding();
                fs.swapFragmentBackstack(landingFrag, v);
            }
        });
        btnLog = findViewById(R.id.btnLog);
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChildLog log = new ChildLog();
                fs.swapFragmentBackstack(log, v);
            }
        });
    }
}
