package com.example.projectsync.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectsync.R;
import com.example.projectsync.data.DataManager;
import com.example.projectsync.model.User;
import com.example.projectsync.ui.admin.AdminDashboardActivity;
import com.example.projectsync.ui.pmo.PMODashboardActivity;
import com.example.projectsync.ui.student.StudentDashboardActivity;
import com.example.projectsync.ui.supervisor.SupervisorDashboardActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String pass = etPassword.getText().toString().trim();

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            User user = DataManager.getInstance().authenticate(email, pass);

            if (user == null) {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this, "Login success: " + user.getRole(), Toast.LENGTH_LONG).show();


            switch (user.getRole()) {

                case "Admin":
                    startActivity(new Intent(this, AdminDashboardActivity.class));
                    break;

                case "PMO":
                    startActivity(new Intent(this, PMODashboardActivity.class));
                    break;

                case "Supervisor":
                    startActivity(new Intent(this, SupervisorDashboardActivity.class));
                    break;

                case "Student":
                    startActivity(new Intent(this, StudentDashboardActivity.class));
                    break;
            }

            finish();
        });
    }
}
