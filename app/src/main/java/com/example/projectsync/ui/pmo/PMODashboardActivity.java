package com.example.projectsync.ui.pmo;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectsync.R;
import com.example.projectsync.data.DataManager;
import com.example.projectsync.model.Announcement;
import com.example.projectsync.model.User;
import com.example.projectsync.ui.BaseDrawerActivity;

public class PMODashboardActivity extends BaseDrawerActivity {

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pmo_dashboard);

        setTitle("PMO Dashboard");

        currentUser = DataManager.getInstance().getCurrentUser();

        if (currentUser == null) {
            Toast.makeText(this, "User not found!", Toast.LENGTH_SHORT).show();
            return;
        }

        findViewById(R.id.btn_view_all_projects)
                .setOnClickListener(v -> showAllProjects());

        findViewById(R.id.btn_monitor_progress)
                .setOnClickListener(v -> showProgressDialog());

        findViewById(R.id.btn_add_announcement)
                .setOnClickListener(v -> showAddAnnouncementDialog());
        findViewById(R.id.btn_predict_performance)
                .setOnClickListener(v ->
                        startActivity(new Intent(this,
                                PerformancePredictionActivity.class)));
    }
    private void showAllProjects() {

        String projects =
                "📌 AI Based Attendance System\n" +
                        "Supervisor: Dr. Ahmed\n" +
                        "Students: Ali (SE-001), Ahmed (SE-002)\n\n" +

                        "📌 Online FYP Portal\n" +
                        "Supervisor: Dr. Sana\n" +
                        "Students: Sara (SE-010), Hina (SE-011)\n\n" +

                        "📌 Health Tracking App\n" +
                        "Supervisor: Dr. Usman\n" +
                        "Students: Bilal (SE-020), Usman (SE-021)\n\n" +

                        "📌 Smart Parking System\n" +
                        "Supervisor: Dr. Ali\n" +
                        "Students: Hamza (SE-030), Ayesha (SE-031)";

        new AlertDialog.Builder(this)
                .setTitle("All Active Projects")
                .setMessage(projects)
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
    private void showProgressDialog() {

        String progress =
                "📊 AI Attendance System\nProgress: 70%\n███████░░░\n\n" +

                        "📊 Online FYP Portal\nProgress: 50%\n█████░░░░░\n\n" +

                        "📊 Health Tracking App\nProgress: 85%\n████████░░\n\n" +

                        "📊 Smart Parking System\nProgress: 40%\n████░░░░░░";

        new AlertDialog.Builder(this)
                .setTitle("Project Progress Overview")
                .setMessage(progress)
                .setPositiveButton("Close", null)
                .show();
    }
}