package com.example.memeshare;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.memeshare.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FrameLayout frameLayout;

    @SuppressLint({"NonConstantResourceId", "ResourceAsColor", "PrivateApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        frameLayout = findViewById(R.id.frameLayout);
        checkConnection();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, MainFragment.newInstance())
                .addToBackStack(null)
                .commit();
        binding.bottomNav.setSelectedItemId(R.id.menuHome);
        binding.bottomNav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menuHome:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout, MainFragment.newInstance())
                            .addToBackStack(null)
                            .commit();
                    return true;
                case R.id.menuExplore:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout, ExploreFragment.newInstance())
                            .addToBackStack(null)
                            .commit();
                    return true;
                case R.id.menuReels:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout, ReelsFragment.newInstance())
                            .addToBackStack(null)
                            .commit();
                    return true;
                case R.id.menuSettings:
                    new CustomBottomSheetDialog().show(getSupportFragmentManager(), "Dialog");
                    return true;
                case R.id.menuSaved:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout, SavedFragment.newInstance())
                            .addToBackStack(null)
                            .commit();
                    return true;
            }
            return false;
        });
    }

    @SuppressLint("ResourceAsColor")
    private void checkConnection() {
        if (!isNetworkConnected()) {
            Snackbar.make(frameLayout, "No internet connection", Snackbar.LENGTH_LONG).setAction("CONNECT", v -> {
                Intent intent = new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS);
                startActivity(intent);
            })
                    .setActionTextColor(Color.rgb(255, 255, 255))
                    .show();
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkConnection();
        if (isNetworkConnected()) {
            FragmentTransaction menuHome = getSupportFragmentManager().beginTransaction();
            menuHome.replace(R.id.frameLayout, MainFragment.newInstance());
            menuHome.commit();
        }
        binding.bottomNav.setSelectedItemId(R.id.menuHome);
    }
}