package com.example.memeshare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReelsActivity extends AppCompatActivity {
    List<VideoItems>list;
    ViewPager2 pager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reels);
        Objects.requireNonNull(getSupportActionBar()).hide();
        pager = findViewById(R.id.pager);
        list = new ArrayList<>();
        loadVideos();
    }

    private void loadVideos() {
        RequestQueue rq = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://raw.githubusercontent.com/bikashthapa01/myvideos-android-app/master/data.json",
                null, response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("categories");
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        JSONArray videoArray = jsonObject.getJSONArray("videos");
                        for(int i=0;i< videoArray.length();i++){
                            JSONObject obj = videoArray.getJSONObject(i);
                            JSONArray videoURL = obj.getJSONArray("sources");
                            String url = videoURL.getString(0);
                            list.add(new VideoItems(url));
                        }
                        pager.setAdapter(new VideoAdapter(list));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Log.d("memeogram",error.toString()));rq.add(jsonObjectRequest);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
    }
}