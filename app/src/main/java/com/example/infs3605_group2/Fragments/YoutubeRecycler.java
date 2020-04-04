package com.example.infs3605_group2.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.infs3605_group2.Models.Constants;
import com.example.infs3605_group2.Models.GetVideoDetailsAsync;
import com.example.infs3605_group2.Models.GetVideoDetailsDelegate;
import com.example.infs3605_group2.Models.YoutubeModel;
import com.example.infs3605_group2.R;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import com.google.firebase.crashlytics.internal.network.HttpRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class YoutubeRecycler extends Fragment implements GetVideoDetailsDelegate {
    private ArrayList<YoutubeModel> YoutubeModelArrayList = new ArrayList<>();


    public YoutubeRecycler() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<String> videoIDArray = Arrays.asList(getResources().getStringArray(R.array.video_id_array));
        GetVideoDetailsAsync getVideoDetailsAsync = new GetVideoDetailsAsync();
        getVideoDetailsAsync.setDelegate(this);
        getVideoDetailsAsync.execute(videoIDArray);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_youtube_recycler, container, false);

    }

    @Override
    public void handleTaskResult(List<YoutubeModel> youtubeList) {
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        YoutubeVideoAdapter adapter = new YoutubeVideoAdapter(getContext(), (ArrayList<YoutubeModel>) youtubeList);
        recyclerView.setAdapter(adapter);
    }
}
