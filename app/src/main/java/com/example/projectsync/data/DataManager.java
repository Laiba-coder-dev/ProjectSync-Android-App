package com.example.projectsync.data;

import com.example.projectsync.model.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {

    private static DataManager instance;

    private List<User> users;
    private List<Announcement> announcements;
    private List<SupervisorRequest> supervisorRequests;

    private User currentUser;

    private DataManager() {
        users = new ArrayList<>();
        announcements = new ArrayList<>();
        supervisorRequests = new ArrayList<>();

        loadDefaultUsers();
        loadDummyData();
    }

    public static synchronized DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    private void loadDefaultUsers() {
        users.add(new User("Admin", "admin@example.com", "admin123", "Admin"));
        users.add(new User("PMO Head", "pmo@example.com", "pmo123", "PMO"));
        users.add(new User("Mam Azka", "azka@example.com", "supervisor123", "Supervisor"));
        users.add(new User("Laiba Mudassir", "laiba@example.com", "student123", "Student"));
        users.add(new User("Mam Ayesha", "ayesha@uni.com", "1234", "Supervisor"));
        users.add(new User("Sir Khurram", "khurram@uni.com", "1234", "Supervisor"));
    }

    private void loadDummyData() {
        announcements.add(new Announcement(
                "FYP Setup",
                "Please register your groups by Friday.",
                "PMO",
                "2026-02-17"
        ));

        announcements.add(new Announcement(
                "Proposal Submission",
                "Deadline is March 1st.",
                "Supervisor",
                "2026-02-16"
        ));
    }

    // ================= AUTH =================

    public User authenticate(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)
                    && user.getPassword().equals(password)) {

                currentUser = user;   // 🔥 SET CURRENT USER
                return user;
            }
        }
        return null;
    }
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void logout() {
        currentUser = null;
    }

    // ================= USER MANAGEMENT =================

    public void addUser(User user) {
        users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }

    public void deleteUser(User user) {
        users.remove(user);
    }

    // ================= ANNOUNCEMENTS =================

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public void addAnnouncement(Announcement a) {
        announcements.add(a);
    }

    // ================= SUPERVISOR REQUESTS =================

    public List<SupervisorRequest> getSupervisorRequests() {
        return supervisorRequests;
    }

    public void addRequest(SupervisorRequest r) {
        supervisorRequests.add(r);
    }

    // ================= INNER REQUEST MODEL =================

    public static class SupervisorRequest {
        public String studentEmail;
        public String supervisorEmail;
        public String status;

        public SupervisorRequest(String studentEmail, String supervisorEmail) {
            this.studentEmail = studentEmail;
            this.supervisorEmail = supervisorEmail;
            this.status = "Pending";
        }
    }
}
