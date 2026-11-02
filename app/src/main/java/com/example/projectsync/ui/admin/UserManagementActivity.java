package com.example.projectsync.ui.admin;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projectsync.R;
import com.example.projectsync.adapter.UserAdapter;
import com.example.projectsync.data.DataManager;
import com.example.projectsync.model.User;
import com.example.projectsync.ui.BaseDrawerActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class UserManagementActivity extends BaseDrawerActivity {

    private RecyclerView rvUsers;
    private UserAdapter adapter;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);
        setTitle("User Management");

        userList = DataManager.getInstance().getUsers();
        rvUsers = findViewById(R.id.rv_users);
        rvUsers.setLayoutManager(new LinearLayoutManager(this));

        adapter = new UserAdapter(userList, user -> showUserActions(user));
        rvUsers.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab_add_user);
        fab.setOnClickListener(v -> showAddUserDialog());
    }

    private void showUserActions(User user) {
        new AlertDialog.Builder(this)
                .setTitle(user.getName())
                .setMessage("Role: " + user.getRole())
                .setPositiveButton("Toggle Status", (d, w) -> {
                    user.setActive(!user.isActive());
                    Toast.makeText(this, "Status updated", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("Remove", (d, w) -> {
                    DataManager.getInstance().deleteUser(user);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(this, "User removed", Toast.LENGTH_SHORT).show();
                })
                .setNeutralButton("Reset Password", (d, w) -> {
                    user.setPassword("password123");
                    Toast.makeText(this, "Password reset to password123", Toast.LENGTH_SHORT).show();
                })
                .show();
    }

    private void showAddUserDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_user, null);
        EditText etName = view.findViewById(R.id.et_name);
        EditText etEmail = view.findViewById(R.id.et_email);
        EditText etPass = view.findViewById(R.id.et_password);
        Spinner spinnerRole = view.findViewById(R.id.spinner_role);

        String[] roles = { "Student", "Supervisor", "PMO", "Admin" };
        spinnerRole.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, roles));

        new AlertDialog.Builder(this)
                .setTitle("Register New User")
                .setView(view)
                .setPositiveButton("Register", (d, w) -> {
                    String name = etName.getText().toString();
                    String email = etEmail.getText().toString();
                    String pass = etPass.getText().toString();
                    String role = spinnerRole.getSelectedItem().toString();

                    if (!name.isEmpty() && !email.isEmpty() && !pass.isEmpty()) {
                        DataManager.getInstance().addUser(new User(name, email, pass, role));
                        adapter.notifyDataSetChanged();
                        Toast.makeText(this, "User registered", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
