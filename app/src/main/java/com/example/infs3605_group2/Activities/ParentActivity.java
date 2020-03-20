package com.example.infs3605_group2.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.infs3605_group2.FragmentSwapper;
import com.example.infs3605_group2.Fragments.ChildLanding;
import com.example.infs3605_group2.Fragments.ChildLog;
import com.example.infs3605_group2.Fragments.ParentLanding;
import com.example.infs3605_group2.Fragments.ParentLog;
import com.example.infs3605_group2.R;

public class ParentActivity extends AppCompatActivity {

    FragmentSwapper fs = new FragmentSwapper();
    private Button button;
    private Button btnlog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//        Bundle bundle = getIntent().getExtras();
//        String message = bundle.getString("userType");
//        Toast.makeText(ParentActivity.this, message, Toast.LENGTH_SHORT).show();


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

    }
}
