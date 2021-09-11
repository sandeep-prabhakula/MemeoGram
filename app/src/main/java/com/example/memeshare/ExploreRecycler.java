package com.example.memeshare;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ExploreRecycler extends AppCompatActivity {
    private final String meme_url = "https://meme-api.herokuapp.com/gimme/50";
    RecyclerView exploreRecycler;
    MemeAdapter adapter;
    List<MemeModel> list;
    NestedScrollView nested;
    ProgressBar pb4;
    int page = 0;
    int limit = 10;
    BottomNavigationView bottomNav;
    @SuppressLint({"NonConstantResourceId", "NotifyDataSetChanged"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_recycler);
        pb4 = findViewById(R.id.progressBar4);
        bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.menuExplore);
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menuHome:
                    startActivity(new Intent(ExploreRecycler.this, MainActivity.class));
                    return true;
                case R.id.menuReels:
                    startActivity(new Intent(ExploreRecycler.this, ReelsActivity.class));
                    return true;
                case R.id.menuSettings:
                    startActivity(new Intent(ExploreRecycler.this,Settings.class));
                    return true;
                case R.id.menuSaved:
                    startActivity(new Intent(ExploreRecycler.this,Saved.class));
                    return true;
            }
            return false;
        });
        nested = findViewById(R.id.nested);
        Bundle bundle = getIntent().getExtras();
        String imageURL = bundle.getString("imageURL");
        String description = bundle.getString("description");
        list = new ArrayList<>();
        list.add(new MemeModel(imageURL,description));
        loadMemes(page,limit);
        nested.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                page++;
                loadMemes(page,limit);
            }
        });
        exploreRecycler.addItemDecoration(new DividerItemDecoration(exploreRecycler.getContext(),DividerItemDecoration.VERTICAL));
        exploreRecycler.setHasFixedSize(true);
        exploreRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MemeAdapter(list);
        adapter.notifyDataSetChanged();
    }

    private void loadMemes(int page,int limit) {
        if (page > limit) {
            Toast.makeText(this, "You're all caught Up", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestQueue rq = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, meme_url, null, response -> {
            try {
                pb4.setVisibility(View.GONE);
                JSONArray jsonArray = response.getJSONArray("memes");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject obj = jsonArray.getJSONObject(i);
                    list.add(new MemeModel(obj.getString("url"),obj.getString("title")));
                }
                exploreRecycler.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {
            pb4.setVisibility(View.GONE);
            if(error instanceof NetworkError){
                new AlertDialog.Builder(this)
                        .setIcon(R.drawable.ic_baseline_error_24)
                        .setTitle("No Internet Connection")
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialog, which) -> onBackPressed())
                        .setMessage("please check your internet connection or make your wifi toggle switch on.")
                        .show();
            }
        });
        rq.add(jsonObjectRequest);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,Explore.class));
    }
}