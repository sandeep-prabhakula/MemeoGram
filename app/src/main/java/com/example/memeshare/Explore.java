package com.example.memeshare;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

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

public class Explore extends AppCompatActivity {
    RecyclerView exploreMemes;
    List<ExploreModel> list;
    ExploreAdapter adapter;
    private final String meme_url = "https://meme-api.herokuapp.com/gimme/1050";
    ProgressBar pb5;
    BottomNavigationView bottomNav;

    @SuppressLint({"NotifyDataSetChanged", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        exploreMemes = findViewById(R.id.exploreMemes);
        bottomNav = findViewById(R.id.bottomNav);
        pb5 = findViewById(R.id.progressBar5);
        list = new ArrayList<>();
        bottomNav.setSelectedItemId(R.id.menuExplore);
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menuHome:
                    startActivity(new Intent(Explore.this, MainActivity.class));
                    return true;
                case R.id.menuReels:
                    startActivity(new Intent(Explore.this, ReelsActivity.class));
                    return true;
                case R.id.menuSettings:
                    startActivity(new Intent(Explore.this,Settings.class));
                    return true;
                case R.id.menuSaved:
                    startActivity(new Intent(Explore.this,Saved.class));
                    return true;
            }
            return false;
        });
        exploreMemes.setLayoutManager(new StaggeredGridLayoutManager(3,LinearLayoutManager.VERTICAL));
        exploreMemes.setHasFixedSize(true);
        loadExploreMemes();
        adapter = new ExploreAdapter(list);
        adapter.notifyDataSetChanged();
    }

    private void loadExploreMemes() {
        RequestQueue rq = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, meme_url, null, response -> {
            try {
                pb5.setVisibility(View.GONE);
                JSONArray jsonArray = response.getJSONArray("memes");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject obj = jsonArray.getJSONObject(i);
                    list.add(new ExploreModel(obj.getString("url"),obj.getString("title")));
                }
                exploreMemes.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {
            pb5.setVisibility(View.GONE);
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
        startActivity(new Intent(Explore.this,MainActivity.class));
    }
}