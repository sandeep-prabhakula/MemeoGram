package com.example.memeshare;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {
    private final List<SettingModel> list;

    public SettingsAdapter(List<SettingModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_layout, null, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SettingsAdapter.ViewHolder holder, int position) {
        SettingModel model = list.get(position);
        holder.settingName.setText(model.getSetting());
        holder.singleSetting.setOnClickListener(v -> {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseUser user = auth.getCurrentUser();
            if (position == 0) {
                v.getContext().startActivity(new Intent(v.getContext(), ResetPassword.class));
            }
            if (position == 1) {
                if (user != null) {
                    auth.signOut();
                }
                v.getContext().startActivity(new Intent(v.getContext(), Login.class));
            }
            if (position == 2) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setIcon(R.drawable.ic_baseline_error_24)
                        .setTitle("Delete Account")
                        .setMessage("Your data will be lost.")
                        .setNegativeButton("CANCEL", null)
                        .setPositiveButton("DELETE", (dialog, which) -> {
                            AuthCredential ac = EmailAuthProvider.getCredential(user.getEmail(), "rohitman@45");
                            user.reauthenticate(ac).addOnCompleteListener(task -> user.delete().addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    v.getContext().startActivity(new Intent(v.getContext(), Login.class));
                                    Toast.makeText(v.getContext(), "Account Deleted", Toast.LENGTH_SHORT).show();
                                }
                            }));
                        })
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView settingName;
        private final ConstraintLayout singleSetting;

        public ViewHolder(View itemView) {
            super(itemView);
            singleSetting = itemView.findViewById(R.id.singleSetting);
            settingName = itemView.findViewById(R.id.settingName);
        }
    }
}
