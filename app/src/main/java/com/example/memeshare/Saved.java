package com.example.memeshare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Saved extends AppCompatActivity {
    List<SavedMemes>list;
    RecyclerView savedRecycler;
    SavedAdapter adapter;
    ImageView home;
    ImageView explore;
    ImageView settings;
    ImageView reels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        savedRecycler = findViewById(R.id.savedRecycler);
        home = findViewById(R.id.home);
        explore = findViewById(R.id.explore);
        settings = findViewById(R.id.settings);
        reels = findViewById(R.id.reels);
        settings.setOnClickListener(v -> startActivity(new Intent(Saved.this,Settings.class)));
        home.setOnClickListener(v -> startActivity(new Intent(Saved.this,MainActivity.class)));
        explore.setOnClickListener(v -> startActivity(new Intent(Saved.this,Explore.class)));
        reels.setOnClickListener(v ->startActivity(new Intent(Saved.this,ReelsActivity.class)));
        MyDbHandler dbHandler = new MyDbHandler(this);
        list = dbHandler.getAllTodos();
        savedRecycler.addItemDecoration(new DividerItemDecoration(savedRecycler.getContext(),DividerItemDecoration.VERTICAL));
        savedRecycler.setHasFixedSize(true);
        savedRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SavedAdapter(list);
        savedRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Saved.this,MainActivity.class));
    }
}