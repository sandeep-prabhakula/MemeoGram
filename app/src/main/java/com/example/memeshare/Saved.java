package com.example.memeshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.List;

public class Saved extends AppCompatActivity {
    List<SavedMemes>list;
    RecyclerView savedRecycler;
    SavedAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        savedRecycler = findViewById(R.id.savedRecycler);
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
        startActivity(new Intent(this,MainActivity.class));
    }
}