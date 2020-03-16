package com.example.infs3605_group2.Models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Transaction implements Serializable {
    private String timestamp;
    private String event;
    private String description;

    public Transaction() {
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
