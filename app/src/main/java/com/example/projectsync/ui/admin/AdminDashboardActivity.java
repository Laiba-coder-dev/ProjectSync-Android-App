package com.example.projectsync.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.projectsync.R;
import com.example.projectsync.data.DataManager;
import com.example.projectsync.model.User;
import com.example.projectsync.ui.BaseDrawerActivity;

public class AdminDashboardActivity extends BaseDrawerActivity {

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin_dashboard);

        setTitle("Admin Dashboard");

        currentUser = DataManager.getInstance().getCurrentUser();

        if (currentUser == null) {
            Toast.makeText(this, "User not found!", Toast.LENGTH_SHORT).show();
            return;
        }

        findViewById(R.id.btn_manage_users)
                .setOnClickListener(v ->
                        startActivity(new Intent(this, UserManagementActivity.class))
                );

        findViewById(R.id.btn_view_stats)
                .setOnClickListener(v ->
                        Toast.makeText(this,
                                "Total Users: " + DataManager.getInstance().getUsers().size(),
                                Toast.LENGTH_SHORT).show()
                );
    }
}