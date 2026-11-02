package com.example.projectsync.model;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String id;
    private String name;
    private List<User> members;
    private User supervisor;
    private Project project;

    public Group(String id, String name) {
        this.id = id;
        this.name = name;
        this.members = new ArrayList<>();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }


}
