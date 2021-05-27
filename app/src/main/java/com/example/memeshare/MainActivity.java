package com.example.memeshare;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {
    private ImageView meme;
    private Button next;
    private String meme_url = "https://meme-api.herokuapp.com/gimme";
    private String current = "";
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        meme = findViewById(R.id.meme);
        Button share = findViewById(R.id.share);
        next = findViewById(R.id.next);
        pb = findViewById(R.id.progressBar);

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
        pb.setVisibility(View.VISIBLE);
        MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, meme_url, null, response -> {
            try {
                current = response.getString("url");
                Glide.with(getApplicationContext()).load(Uri.parse(current)).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        pb.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        pb.setVisibility(View.GONE);
                        return false;
                    }
                }).into(meme);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Log.d("Error",error.toString());
            if(error instanceof NetworkError){
                        new AlertDialog.Builder(this)
                        .setIcon(R.drawable.ic_baseline_error_24)
                        .setTitle("No Internet Connection")
                        .setMessage("please check your internet connection or make your wifi toggle switch on.")
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialog, which) -> onBackPressed())
                        .show();
            }
        });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}