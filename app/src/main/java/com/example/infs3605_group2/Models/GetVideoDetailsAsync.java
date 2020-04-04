package com.example.infs3605_group2.Models;

import android.os.AsyncTask;

import com.example.infs3605_group2.Models.YoutubeModel;
import com.example.infs3605_group2.R;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetVideoDetailsAsync extends AsyncTask<List<String>, Integer, List<YoutubeModel>> {
    private GetVideoDetailsDelegate delegate;

    public void setDelegate(GetVideoDetailsDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    protected List<YoutubeModel> doInBackground(List<String>...list) {
        List<YoutubeModel> youtubeModelList = new ArrayList<>();
        List<String> videoIDArray = list[0];
        YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(),
                new HttpRequestInitializer() {
                    @Override
                    public void initialize(com.google.api.client.http.HttpRequest request) throws IOException {

                    }
                }).setApplicationName("infs3605_group2").build();

        for (int i = 0; i < videoIDArray.size(); i++) {
            YoutubeModel YoutubeModel = new YoutubeModel();
            String videoId = videoIDArray.get(i);
            YouTube.Videos.List videoRequest = null;
            try {
                videoRequest = youtube.videos().list("snippet,statistics,contentDetails");
            } catch (IOException e) {
                e.printStackTrace();
            }
            videoRequest.setId(videoId);
            videoRequest.setKey(Constants.DEVELOPER_KEY);
            VideoListResponse listResponse = null;
            try {
                listResponse = videoRequest.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<Video> videoList = listResponse.getItems();
            Video targetVideo = videoList.iterator().next();

            YoutubeModel.setId(videoId);
            YoutubeModel.setTitle(targetVideo.getSnippet().getTitle());
            YoutubeModel.setChannel(targetVideo.getSnippet().getChannelTitle());
            long duration = Duration.parse(targetVideo.getContentDetails().getDuration()).getSeconds();
            long hours = duration / 3600;
            long minutes = (duration % 3600) / 60;
            long seconds = duration % 60;

            String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);

            YoutubeModel.setDuration(timeString);

            youtubeModelList.add(YoutubeModel);

        }

        return youtubeModelList;
    }

    @Override
    protected void onPostExecute(List<YoutubeModel> youtubeModelList){
        delegate.handleTaskResult(youtubeModelList);
    }
}
