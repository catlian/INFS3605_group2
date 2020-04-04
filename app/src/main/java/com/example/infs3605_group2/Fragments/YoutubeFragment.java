package com.example.infs3605_group2.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.infs3605_group2.Activities.ChildActivity;
import com.example.infs3605_group2.Models.Constants;
import com.example.infs3605_group2.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class YoutubeFragment extends Fragment {
    private static final String TAG = ChildActivity.class.getSimpleName();
    private String vidId;
    private YouTubePlayer youTubePlayer;

    public YoutubeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_youtube, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            vidId = bundle.getString("VIDEO_ID");
        }
        initializeYoutubePlayer();
        return view;
    }
    /**
     * initialize youtube player via Fragment and get instance of YoutubePlayer
     */
    private void initializeYoutubePlayer() {
        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_player_fragment, youTubePlayerFragment).commit();


        if (youTubePlayerFragment == null)
            return;
        youTubePlayerFragment.initialize(Constants.DEVELOPER_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                                boolean wasRestored) {
                if (!wasRestored) {
                    youTubePlayer = player;

                    //set the player style default
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    youTubePlayer.setFullscreen(false);

                    //cue the 1st video by default
                    youTubePlayer.cueVideo(vidId);
                }
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {

                //print or show error if initialization failed
                Log.e(TAG, "Youtube Player View initialization failed");
            }
        });
    }
}
