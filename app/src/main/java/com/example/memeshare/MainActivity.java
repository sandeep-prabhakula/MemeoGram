package com.example.memeshare;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;
    FrameLayout frameLayout;
    @SuppressLint({"NotifyDataSetChanged", "NonConstantResourceId", "ResourceAsColor", "PrivateApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        frameLayout = findViewById(R.id.frameLayout);
        checkConnection();
        bottomNav = findViewById(R.id.bottomNav);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,MainFragment.newInstance());
        ft.commit();
        bottomNav.setSelectedItemId(R.id.menuHome);
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menuHome:
                    FragmentTransaction menuHome = getSupportFragmentManager().beginTransaction();
                    menuHome.replace(R.id.frameLayout,MainFragment.newInstance());
                    menuHome.commit();
                    return true;
                case R.id.menuExplore:
                    FragmentTransaction exploreFt = getSupportFragmentManager().beginTransaction();
                    exploreFt.replace(R.id.frameLayout,ExploreFragment.newInstance());
                    exploreFt.commit();
                    return true;
                case R.id.menuReels:
                    FragmentTransaction reelsFt = getSupportFragmentManager().beginTransaction();
                    reelsFt.replace(R.id.frameLayout,ReelsFragment.newInstance());
                    reelsFt.commit();
                    return true;
                case R.id.menuSettings:
                    FragmentTransaction settingFt = getSupportFragmentManager().beginTransaction();
                    settingFt.replace(R.id.frameLayout,SettingsFragment.newInstance());
                    settingFt.commit();
                    return true;
                case R.id.menuSaved:
                    FragmentTransaction savedFt = getSupportFragmentManager().beginTransaction();
                    savedFt.replace(R.id.frameLayout,SavedFragment.newInstance());
                    savedFt.commit();
                    return true;
            }
            return false;
        });
    }

    @SuppressLint("ResourceAsColor")
    private void checkConnection() {
        if(!isNetworkConnected()){
            Snackbar.make(frameLayout,"No internet connection",Snackbar.LENGTH_LONG).setAction("CONNECT", v -> {
                Intent intent = new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS);
                startActivity(intent);
            })
                    .setActionTextColor(R.color.white)
                    .show();
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkConnection();
        if(isNetworkConnected()){
            FragmentTransaction menuHome = getSupportFragmentManager().beginTransaction();
            menuHome.replace(R.id.frameLayout,MainFragment.newInstance());
            menuHome.commit();
        }
    }
}