package com.example.infs3605_group2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.infs3605_group2.Fragments.ChildChores;
import com.example.infs3605_group2.Fragments.ChildLanding;
import com.example.infs3605_group2.Fragments.ChildLog;
import com.example.infs3605_group2.Fragments.ParentSavings;
import com.example.infs3605_group2.Fragments.YoutubeFragment;
import com.example.infs3605_group2.Fragments.YoutubeRecycler;
import com.example.infs3605_group2.Fragments.YoutubeVideoAdapter;
import com.example.infs3605_group2.R;

public class ChildActivity extends AppCompatActivity  {

    private Button btnLanding, btnLog, btnChore, btnSavings, btnVideos;
    private static ChildActivity mInstance;


    public static void onFragmentInteraction(String string) {
        Bundle bundle = new Bundle();
        bundle.putString("VIDEO_ID", string);
        YoutubeFragment youtubeFragment = new YoutubeFragment();
        youtubeFragment.setArguments(bundle);

        swapFragment(youtubeFragment);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);
//        Bundle bundle = getIntent().getExtras();
//        String message = bundle.getString("userType");
        mInstance = this;
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
        btnVideos = findViewById(R.id.btnVideos);
        btnVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoutubeRecycler videos = new YoutubeRecycler();
                swapFragment(videos);
            }
        });
    }
    private static void swapFragment(Fragment fragment) {
        FragmentManager fragmentManager = mInstance.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_slot, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /*@Override
    p{
        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

        youTubePlayerFragment.initialize("DEVELOPER_KEY", new OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {

                if (!wasRestored) {
                    YPlayer = player;
                    YPlayer.setFullscreen(true);
                    YPlayer.loadVideo("2zNSgSzhBfM");
                    YPlayer.play();
                }

            }

            @Override
            public void onInitializationFailure(Provider arg0, YouTubeInitializationResult arg1) {
                // TODO Auto-generated method stub

            }
        });
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_fragment, youTubePlayerFragment).commit();
    }*/
}
