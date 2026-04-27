package com.example.tasktracker;

import java.util.UUID;

public class Task {
    private String id;
    private String description;
    private boolean isComplete;

    public Task(String description) {
        this.id = UUID.randomUUID().toString();
        this.description = description;
        this.isComplete = false;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isComplete() { return isComplete; }
    public void setComplete(boolean complete) { isComplete = complete; }
}