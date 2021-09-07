package com.example.memeshare;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_login);
        EditText mail = findViewById(R.id.loginId);
        EditText pass = findViewById(R.id.loginPassword);
        TextView forgot = findViewById(R.id.forgotPassword);
        forgot.setOnClickListener(v -> startActivity(new Intent(Login.this,ResetPassword.class)));
        Button login = findViewById(R.id.login);
        ProgressBar loading = findViewById(R.id.loading);
        loading.setVisibility(View.GONE);
        TextView intentToSignUp = findViewById(R.id.intentToSignUp);
        intentToSignUp.setOnClickListener(v -> {
            startActivity(new Intent(Login.this,SignUp.class));
            finish();
        });
        FirebaseAuth auth = FirebaseAuth.getInstance();
        login.setOnClickListener(v -> {
            loading.setVisibility(View.VISIBLE);
            if(TextUtils.isEmpty(mail.getText().toString())||TextUtils.isEmpty(pass.getText().toString())){
                Toast.makeText(Login.this, "Empty Credentials", Toast.LENGTH_SHORT).show();
            }
            else {
                auth.signInWithEmailAndPassword(mail.getText().toString(), pass.getText().toString()).addOnCompleteListener(Login.this, task -> {
                    if (task.isSuccessful()) {
                        loading.setVisibility(View.VISIBLE);
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        assert user != null;
                        if (user.isEmailVerified()) {
                            Intent i = new Intent(Login.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            loading.setVisibility(View.GONE);
                            user.sendEmailVerification();
                            Toast.makeText(Login.this, "Verification Email Sent", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i =  new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}