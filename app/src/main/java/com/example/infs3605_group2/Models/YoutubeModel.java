package com.example.infs3605_group2.Models;

public class YoutubeModel {
    private String id;
    private String title;
    private String duration;
    private String channel;

    public YoutubeModel() {
    }

    public String getId() {
        return id;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
