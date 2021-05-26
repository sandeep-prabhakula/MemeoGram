package com.example.memeshare;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {
    private ImageView meme;
    private Button next;
    private String meme_url = "https://meme-api.herokuapp.com/gimme";
    private String current = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        meme = findViewById(R.id.meme);
        Button share = findViewById(R.id.share);
        next = findViewById(R.id.next);
        loadMeme();
        next.setOnClickListener(v -> loadMeme());
        share.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_TEXT,"checkout the meme"+"\n"+current);
            startActivity(Intent.createChooser(i,"choose an app"));
        });
    }

    private void loadMeme() {

        RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, meme_url, null, response -> {
            try {
                current = response.getString("url");
                Glide.with(getApplicationContext()).load(Uri.parse(current)).into(meme);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.d("Error",error.toString()));

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}