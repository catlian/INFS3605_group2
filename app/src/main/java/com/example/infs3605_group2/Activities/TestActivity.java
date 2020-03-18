package com.example.infs3605_group2.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.infs3605_group2.FragmentSwapper;
import com.example.infs3605_group2.Fragments.ChildLanding;
import com.example.infs3605_group2.Fragments.ChildLog;
import com.example.infs3605_group2.Fragments.ParentLanding;
import com.example.infs3605_group2.Fragments.ParentLog;
import com.example.infs3605_group2.R;

public class TestActivity extends AppCompatActivity {

    private FragmentSwapper fs = new FragmentSwapper();
    private Button button;
    private Button btnlog;
    private Button childLand;
    private Button childLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParentLanding landingFrag = new ParentLanding();
                fs.swapFragmentBackstack(landingFrag, v);
            }
        });
        btnlog = findViewById(R.id.button2);
        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParentLog log = new ParentLog();
                fs.swapFragmentBackstack(log, v);
            }
        });
        childLand = findViewById(R.id.childLand);
        childLand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChildLanding landingFrag = new ChildLanding();
                fs.swapFragmentBackstack(landingFrag, v);
            }
        });
        childLog = findViewById(R.id.childLog);
        childLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChildLog log = new ChildLog();
                fs.swapFragmentBackstack(log, v);
            }
        });
    }
}
