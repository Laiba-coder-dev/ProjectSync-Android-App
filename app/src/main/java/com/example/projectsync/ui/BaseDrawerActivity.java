package com.example.projectsync.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.projectsync.R;
import com.example.projectsync.data.DataManager;
import com.example.projectsync.model.User;
import com.example.projectsync.ui.auth.LoginActivity;
import com.google.android.material.navigation.NavigationView;

public abstract class BaseDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout drawerLayout;
    protected User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentUser = DataManager.getInstance().getCurrentUser();
    }

    @Override
    public void setContentView(int layoutResID) {

        // Inflate base drawer layout
        drawerLayout = (DrawerLayout) getLayoutInflater()
                .inflate(R.layout.activity_base_drawer, null);

        FrameLayout contentFrame =
                drawerLayout.findViewById(R.id.content_frame);

        // Inflate actual screen inside drawer
        getLayoutInflater().inflate(layoutResID, contentFrame, true);

        super.setContentView(drawerLayout);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setup Navigation View
        NavigationView navigationView =
                findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        // Setup header user info
        View headerView = navigationView.getHeaderView(0);

        TextView navUserName =
                headerView.findViewById(R.id.nav_user_name);
        TextView navUserRole =
                headerView.findViewById(R.id.nav_user_role);

        if (currentUser != null) {
            navUserName.setText(currentUser.getName());
            navUserRole.setText(currentUser.getRole());
        }

        // Drawer Toggle
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(
                        this,
                        drawerLayout,
                        toolbar,
                        R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
        }

        else if (id == R.id.nav_logout) {

            DataManager.getInstance().setCurrentUser(null);

            startActivity(new Intent(this, LoginActivity.class));
            finishAffinity();
        }

        else if (id == R.id.nav_dashboard) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout != null &&
                drawerLayout.isDrawerOpen(GravityCompat.START)) {

            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}