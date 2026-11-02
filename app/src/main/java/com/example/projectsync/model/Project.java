package com.example.projectsync.model;

public class Project {
    private String title;
    private String description;
    private String status; // Pending, Ongoing, Completed
    private String documentName;
    private String supervisorFeedback;

    public Project(String title, String description) {
        this.title = title;
        this.description = description;
        this.status = "Pending";
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getSupervisorFeedback() {
        return supervisorFeedback;
    }

    public void setSupervisorFeedback(String feedback) {
        this.supervisorFeedback = feedback;
    }
}
