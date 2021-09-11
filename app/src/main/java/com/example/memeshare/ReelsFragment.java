package com.example.memeshare;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReelsFragment extends Fragment {
    List<VideoItems> list;
    ViewPager2 pager;
    public ReelsFragment() {

    }

    public static ReelsFragment newInstance() {
        ReelsFragment fragment = new ReelsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reels, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        pager = view.findViewById(R.id.pager);
        list = new ArrayList<>();
        loadVideos();
    }
    private void loadVideos() {
        RequestQueue rq = Volley.newRequestQueue(getActivity());
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
}