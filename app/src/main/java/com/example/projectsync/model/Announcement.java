package com.example.projectsync.model;

public class Announcement {

    private String title;
    private String message;
    private String postedBy;
    private String date;

    public Announcement(String title, String message, String postedBy, String date) {
        this.title = title;
        this.message = message;
        this.postedBy = postedBy;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {   // 🔥 THIS WAS MISSING
        return message;
    }

    public String getDate() {
        return date;
    }
}
