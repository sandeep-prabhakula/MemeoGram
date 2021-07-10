package com.example.memeshare;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Explore extends AppCompatActivity {
    GridView exploreMemes;
    ArrayList<ExploreModel> list;
    ExploreAdapter adapter;
    private final String meme_url = "https://meme-api.herokuapp.com/gimme/1050";
    ImageView home;
    ImageView saved;
    ImageView settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        exploreMemes = findViewById(R.id.exploreMemes);
        home = findViewById(R.id.home);
        saved = findViewById(R.id.saved);
        settings = findViewById(R.id.settings);
        list = new ArrayList<>();
        settings.setOnClickListener(v -> startActivity(new Intent(Explore.this,Settings.class)));
        home.setOnClickListener(v -> startActivity(new Intent(Explore.this,MainActivity.class)));
        saved.setOnClickListener(v -> startActivity(new Intent(Explore.this,Saved.class)));
        loadExploreMemes();
        adapter = new ExploreAdapter(this,list);
        adapter.notifyDataSetChanged();
    }

    private void loadExploreMemes() {
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();
        RequestQueue rq = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, meme_url, null, response -> {
            try {
                pd.dismiss();
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
            pd.dismiss();
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
        super.onBackPressed();
    }
}