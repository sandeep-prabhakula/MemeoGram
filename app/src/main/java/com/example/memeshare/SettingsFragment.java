package com.example.memeshare;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {
    RecyclerView settings;
    SettingsAdapter adapter;
    List<SettingModel> list;
    public SettingsFragment() {
    }
    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        list = new ArrayList<>();
        settings = view.findViewById(R.id.settings);
        settings.addItemDecoration(new DividerItemDecoration(settings.getContext(),DividerItemDecoration.VERTICAL));
        settings.setLayoutManager(new LinearLayoutManager(getActivity()));
        settings.setHasFixedSize(true);
        list.add(new SettingModel("ChangePassword"));
        list.add(new SettingModel("Logout"));
        list.add(new SettingModel("Delete account"));
        adapter = new SettingsAdapter(list);
        settings.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}