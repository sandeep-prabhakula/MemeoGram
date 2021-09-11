package com.example.memeshare;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Settings extends AppCompatActivity {
    RecyclerView settingRecyclerView;
    SettingsAdapter adapter;
    List<SettingModel>list;
    BottomNavigationView bottomNav;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settingRecyclerView = findViewById(R.id.settingsRecyclerView);
        bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.menuSettings);
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menuExplore:
                    startActivity(new Intent(Settings.this, Explore.class));
                    return true;
                case R.id.menuReels:
                    startActivity(new Intent(Settings.this, ReelsActivity.class));
                    return true;
                case R.id.menuSaved:
                    startActivity(new Intent(Settings.this,Saved.class));
                    return true;
                case R.id.menuHome:
                    startActivity(new Intent(Settings.this,MainActivity.class));
                    return true;
            }
            return false;
        });
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
        startActivity(new Intent(this,MainActivity.class));
    }
}