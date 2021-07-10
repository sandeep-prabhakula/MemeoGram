package com.example.memeshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

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