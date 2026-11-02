package com.example.projectsync.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectsync.R;
import com.example.projectsync.data.DataManager;
import com.example.projectsync.ui.auth.LoginActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("My Profile");

        Button btnLogout = findViewById(R.id.btn_logout);

        btnLogout.setOnClickListener(v -> {

            // Clear logged-in user
            DataManager.getInstance().setCurrentUser(null);

            // Go to Login screen
            startActivity(new Intent(this, LoginActivity.class));

            // Clear back stack
            finishAffinity();
        });
    }
}