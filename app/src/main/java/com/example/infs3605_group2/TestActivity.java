package com.example.infs3605_group2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.infs3605_group2.Fragments.ParentLanding;

public class TestActivity extends AppCompatActivity {

    private FragmentSwapper fs = new FragmentSwapper();
    private Button button;

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
    }
}
