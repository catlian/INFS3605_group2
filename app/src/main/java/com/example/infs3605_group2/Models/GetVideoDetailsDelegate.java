package com.example.infs3605_group2.Models;

import com.example.infs3605_group2.Models.YoutubeModel;

import java.util.List;

public interface GetVideoDetailsDelegate {
    void handleTaskResult(List<YoutubeModel> youtubeList);
}
