package com.example.memeshare;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Saved extends AppCompatActivity {
    RecyclerView savedRecycler;
    public static SavedAdapter adapter;
    BottomNavigationView bottomNav;
    public static SavedViewModel noteViewModel;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        savedRecycler = findViewById(R.id.savedRecycler);
        bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.menuSaved);
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menuExplore:
                    startActivity(new Intent(Saved.this, Explore.class));
                    return true;
                case R.id.menuReels:
                    startActivity(new Intent(Saved.this, ReelsActivity.class));
                    return true;
                case R.id.menuSettings:
                    startActivity(new Intent(Saved.this,Settings.class));
                    return true;
                case R.id.menuHome:
                    startActivity(new Intent(Saved.this,MainActivity.class));
                    return true;
            }
            return false;
        });
        savedRecycler.addItemDecoration(new DividerItemDecoration(savedRecycler.getContext(),DividerItemDecoration.VERTICAL));
        savedRecycler.setHasFixedSize(true);
        savedRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SavedAdapter();
        savedRecycler.setAdapter(adapter);
        noteViewModel = new ViewModelProvider(this).get(SavedViewModel.class);
        noteViewModel.getAllNotes().observe(this, adapter::setNotes);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(savedRecycler);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Saved.this,MainActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.clear_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.clearAll) {
            noteViewModel.deleteAllNote();
            Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}