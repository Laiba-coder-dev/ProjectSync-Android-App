package com.example.projectsync.ui.supervisor;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectsync.R;
import com.example.projectsync.data.DataManager;
import com.example.projectsync.model.Announcement;
import com.example.projectsync.model.User;
import com.example.projectsync.ui.BaseDrawerActivity;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class SupervisorDashboardActivity extends BaseDrawerActivity {

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisor_dashboard);
        setTitle("Supervisor Dashboard");

        currentUser = DataManager.getInstance().getCurrentUser();

        if (currentUser == null) {
            Toast.makeText(this, "Session expired", Toast.LENGTH_SHORT).show();
            return;
        }

        // View Groups Button
        MaterialButton btnViewGroups = findViewById(R.id.btn_view_groups);
        btnViewGroups.setOnClickListener(v -> showGroupsInfo());

        // Add Announcement Button
        MaterialButton btnAddAnnouncement = findViewById(R.id.btn_add_announcement);
        btnAddAnnouncement.setOnClickListener(v -> showAddAnnouncementDialog());
        MaterialButton btnViewRequests = findViewById(R.id.btn_view_requests);
        btnViewRequests.setOnClickListener(v -> showStudentRequests());
    }
    private void showStudentRequests() {

        List<DataManager.SupervisorRequest> allRequests =
                DataManager.getInstance().getSupervisorRequests();

        List<DataManager.SupervisorRequest> myRequests = new ArrayList<>();

        for (DataManager.SupervisorRequest r : allRequests) {
            if (r.supervisorEmail.equalsIgnoreCase(currentUser.getEmail())
                    && "Pending".equalsIgnoreCase(r.status)) {
                myRequests.add(r);
            }
        }

        if (myRequests.isEmpty()) {
            Toast.makeText(this,
                    "No pending requests",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        String[] display = new String[myRequests.size()];
        for (int i = 0; i < myRequests.size(); i++) {
            display[i] = "Request from: " + myRequests.get(i).studentEmail;
        }

        new AlertDialog.Builder(this)
                .setTitle("Pending Requests")
                .setItems(display, (dialog, which) -> {

                    DataManager.SupervisorRequest selected =
                            myRequests.get(which);

                    new AlertDialog.Builder(this)
                            .setTitle("Review Request")
                            .setMessage("Approve request from "
                                    + selected.studentEmail + "?")
                            .setPositiveButton("Approve", (d, w) -> {
                                selected.status = "Approved";
                                Toast.makeText(this,
                                        "Request Approved",
                                        Toast.LENGTH_SHORT).show();
                            })
                            .setNegativeButton("Reject", (d, w) -> {
                                selected.status = "Rejected";
                                Toast.makeText(this,
                                        "Request Rejected",
                                        Toast.LENGTH_SHORT).show();
                            })
                            .show();

                })
                .show();
    }
    private void showGroupsInfo() {

        List<String> groups = new ArrayList<>();

        groups.add(
                "📌 Project: AI Based Attendance System\n" +
                        "Description: Face recognition based smart attendance.\n" +
                        "Students:\n" +
                        "• Ali (SE-001)\n" +
                        "• Ahmed (SE-002)"
        );

        groups.add(
                "📌 Project: Online FYP Portal\n" +
                        "Description: Web system for managing FYP workflow.\n" +
                        "Students:\n" +
                        "• Sara (SE-010)\n" +
                        "• Hina (SE-011)"
        );

        groups.add(
                "📌 Project: Android Health Tracker\n" +
                        "Description: Fitness tracking mobile application.\n" +
                        "Students:\n" +
                        "• Usman (SE-020)\n" +
                        "• Bilal (SE-021)"
        );

        String[] display = groups.toArray(new String[0]);

        new AlertDialog.Builder(this)
                .setTitle("My Assigned Groups")
                .setItems(display, null)
                .setPositiveButton("Close", null)
                .show();
    }

    private void showAddAnnouncementDialog() {

        View view = getLayoutInflater()
                .inflate(R.layout.dialog_add_announcement, null);

        EditText etTitle = view.findViewById(R.id.et_title);
        EditText etMessage = view.findViewById(R.id.et_message);

        new AlertDialog.Builder(this)
                .setTitle("New Announcement")
                .setView(view)
                .setPositiveButton("Post", (dialog, which) -> {

                    String title = etTitle.getText().toString().trim();
                    String message = etMessage.getText().toString().trim();

                    if (title.isEmpty() || message.isEmpty()) {
                        Toast.makeText(this,
                                "Fill all fields",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String role = currentUser.getRole();
                    String date = "2026-02-20";

                    DataManager.getInstance().addAnnouncement(
                            new Announcement(title, message, role, date)
                    );

                    Toast.makeText(this,
                            "Announcement posted",
                            Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}