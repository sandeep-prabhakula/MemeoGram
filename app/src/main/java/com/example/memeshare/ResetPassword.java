package com.example.memeshare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {
    EditText rEmail;
    Button sendResetLink;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        rEmail = findViewById(R.id.registeredEmail);
        auth = FirebaseAuth.getInstance();
        sendResetLink = findViewById(R.id.reset);
        sendResetLink.setOnClickListener(v -> {
            String email = rEmail.getText().toString();
            if(email.isEmpty()){
                rEmail.setError("Email is required");
                rEmail.requestFocus();
                return ;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                rEmail.setError("Please Enter Valid Email");
                rEmail.requestFocus();
                return;
            }
            auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(ResetPassword.this, "Email sent to Your Email", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ResetPassword.this, "Try Again! something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}