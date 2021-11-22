package com.example.memeshare;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CustomBottomSheetDialog extends BottomSheetDialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_dialog_layout,container,false);
        TextView resetPassword = v.findViewById(R.id.resetPassword);
        TextView logout = v.findViewById(R.id.logout);
        TextView deleteAccount = v.findViewById(R.id.deleteAccount);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        resetPassword.setOnClickListener(v1 -> {
            startActivity(new Intent(getActivity(),ResetPassword.class));
        });
        logout.setOnClickListener(v12 -> {
            if (user != null) {
                auth.signOut();
            }
            startActivity(new Intent(getActivity(),Login.class));
        });
        deleteAccount.setOnClickListener(v13 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setIcon(R.drawable.ic_baseline_error_24)
                    .setTitle("Delete Account")
                    .setMessage("Your data will be lost.")
                    .setNegativeButton("CANCEL", null)
                    .setPositiveButton("DELETE", (dialog, which) -> {
                        AuthCredential ac = EmailAuthProvider.getCredential(user.getEmail(), "rohitman@45");
                        user.reauthenticate(ac).addOnCompleteListener(task -> user.delete().addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                startActivity(new Intent(getActivity(), Login.class));
                                Toast.makeText(getActivity(), "Account Deleted", Toast.LENGTH_SHORT).show();
                            }
                        }));
                    })
                    .show();
        });
        return v;
    }
}
