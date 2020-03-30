package com.example.infs3605_group2.Models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Chore implements Serializable {
    private double amount;
    private String description;
    private int icon;
    private String isDone;
    private String key;

    public Chore() {
    }

    public Chore(double amount, String description, int icon, String isDone, String key) {
        this.amount = amount;
        this.description = description;
        this.icon = icon;
        this.isDone = isDone;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getIsDone() {
        return isDone;
    }

    public void setIsDone(String isDone) {
        this.isDone = isDone;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("key", key);
        result.put("icon", icon);
        result.put("description", description);
        result.put("amount", amount);
        result.put("isDone", "true");

        return result;
    }
}
