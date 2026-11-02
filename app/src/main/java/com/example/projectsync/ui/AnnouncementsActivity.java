package com.example.projectsync.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectsync.R;
import com.example.projectsync.adapter.AnnouncementAdapter;
import com.example.projectsync.data.DataManager;

public class AnnouncementsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AnnouncementAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);

        recyclerView = findViewById(R.id.recyclerAnnouncements);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AnnouncementAdapter(
                DataManager.getInstance().getAnnouncements()
        );

        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

}
