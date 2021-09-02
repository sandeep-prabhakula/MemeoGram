package com.example.memeshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Settings extends AppCompatActivity {
    RecyclerView settingRecyclerView;
    SettingsAdapter adapter;
    List<SettingModel>list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settingRecyclerView = findViewById(R.id.settingsRecyclerView);
        ImageView home = findViewById(R.id.home);
        ImageView explore = findViewById(R.id.explore);
        ImageView saved = findViewById(R.id.saved);
        ImageView reels = findViewById(R.id.reels);
        home.setOnClickListener(v-> startActivity(new Intent(Settings.this,MainActivity.class)));
        explore.setOnClickListener(v-> startActivity(new Intent(Settings.this,Explore.class)));
        saved.setOnClickListener(v->startActivity(new Intent(Settings.this,Saved.class)));
        reels.setOnClickListener(v ->startActivity(new Intent(Settings.this,ReelsActivity.class)));
        list = new ArrayList<>();
        list.add(new SettingModel("Change Password"));
        list.add(new SettingModel("Logout"));
        list.add(new SettingModel("Delete Account"));
        settingRecyclerView.addItemDecoration(new DividerItemDecoration(settingRecyclerView.getContext(),DividerItemDecoration.VERTICAL));
        settingRecyclerView.setHasFixedSize(true);
        settingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SettingsAdapter(list);
        settingRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}