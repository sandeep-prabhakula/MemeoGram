package com.example.memeshare;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private final String meme_url = "https://meme-api.herokuapp.com/gimme/50";
    RecyclerView memes;
    MemeAdapter adapter;
    List<MemeModel> list;
    NestedScrollView nest;
    int page = 0;
    int limit = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView saved = findViewById(R.id.saved);
        Objects.requireNonNull(getSupportActionBar()).hide();
        saved.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,Saved.class)));
        nest = findViewById(R.id.nested);
        memes = findViewById(R.id.memes);
        list = new ArrayList<>();
        loadMeme(page,limit);
        nest.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                page++;
                loadMeme(page,limit);
            }
        });
        memes.addItemDecoration(new DividerItemDecoration(memes.getContext(),DividerItemDecoration.VERTICAL));
        memes.setHasFixedSize(true);
        memes.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MemeAdapter(list);
        adapter.notifyDataSetChanged();
    }

    private void loadMeme(int page,int limit) {
        if (page > limit) {
            Toast.makeText(this, "That's all the data..", Toast.LENGTH_SHORT).show();
            return;
        }
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
                    list.add(new MemeModel(obj.getString("url"),obj.getString("title")));
                }
                memes.setAdapter(adapter);
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
}