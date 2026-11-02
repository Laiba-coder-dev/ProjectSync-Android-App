package com.example.projectsync.ui.student;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectsync.R;
import com.example.projectsync.data.DataManager;
import com.example.projectsync.model.User;
import com.example.projectsync.ui.BaseDrawerActivity;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class StudentDashboardActivity extends BaseDrawerActivity {

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        setTitle("Student Dashboard");

        currentUser = DataManager.getInstance().getCurrentUser();

        if (currentUser == null) {
            Toast.makeText(this, "Session expired", Toast.LENGTH_SHORT).show();
            return;
        }

        // Submit Proposal
        MaterialButton btnSubmit =
                findViewById(R.id.btn_submit_proposal);

        btnSubmit.setOnClickListener(v ->
                showProjectIdeaDialog());

        // View Announcements
        MaterialButton btnAnnouncements =
                findViewById(R.id.btn_view_announcements);

        btnAnnouncements.setOnClickListener(v ->
                startActivity(new android.content.Intent(
                        this,
                        com.example.projectsync.ui.AnnouncementsActivity.class
                )));

        // Select Supervisor
        MaterialButton btnSupervisor =
                findViewById(R.id.btn_select_supervisor);

        btnSupervisor.setOnClickListener(v ->
                showSupervisorSelection());
    }

    private void showSupervisorSelection() {

        List<User> supervisors = new ArrayList<>();

        for (User u : DataManager.getInstance().getUsers()) {
            if ("Supervisor".equals(u.getRole())) {
                supervisors.add(u);
            }
        }

        if (supervisors.isEmpty()) {
            Toast.makeText(this,
                    "No supervisors available",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        String[] names = new String[supervisors.size()];

        for (int i = 0; i < supervisors.size(); i++) {
            names[i] = supervisors.get(i).getName();
        }

        new AlertDialog.Builder(this)
                .setTitle("Select Supervisor")
                .setItems(names, (d, w) -> {

                    DataManager.SupervisorRequest req =
                            new DataManager.SupervisorRequest(
                                    currentUser.getEmail(),
                                    supervisors.get(w).getEmail()
                            );

                    DataManager.getInstance().addRequest(req);

                    Toast.makeText(this,
                            "Request sent to " + names[w],
                            Toast.LENGTH_SHORT).show();
                })
                .show();
    }

    private void showProjectIdeaDialog() {

        View view = getLayoutInflater()
                .inflate(R.layout.dialog_project_idea, null);

        EditText etTitle =
                view.findViewById(R.id.et_project_title);
        EditText etDesc =
                view.findViewById(R.id.et_project_desc);

        new AlertDialog.Builder(this)
                .setTitle("Submit Proposal")
                .setView(view)
                .setPositiveButton("Submit", (d, w) -> {

                    String title =
                            etTitle.getText().toString().trim();
                    String desc =
                            etDesc.getText().toString().trim();

                    if (title.isEmpty()) {
                        Toast.makeText(this,
                                "Enter project title",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Toast.makeText(this,
                            "Proposal submitted: " + title,
                            Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}