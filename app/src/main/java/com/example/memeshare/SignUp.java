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

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignUp extends AppCompatActivity {
    private EditText UserName,pass,cpass,Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Objects.requireNonNull(getSupportActionBar()).hide();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        UserName = findViewById(R.id.userName);
        Email = findViewById(R.id.userId);
        pass = findViewById(R.id.password);
        cpass = findViewById(R.id.confirmPassword);
        ProgressBar loadingSignUp = findViewById(R.id.LoadingSignUp);
        loadingSignUp.setVisibility(View.GONE);
        Button signUp = findViewById(R.id.signUp);
        TextView intentToLogin = findViewById(R.id.intentToLogin);
        intentToLogin.setOnClickListener(v -> {
            startActivity(new Intent(SignUp.this,Login.class));
            finish();
        });
        signUp.setOnClickListener(v -> {
            loadingSignUp.setVisibility(View.VISIBLE);
            String User = UserName.getText().toString();
            String email = Email.getText().toString();
            String password = pass.getText().toString();
            String cPass = cpass.getText().toString();
            if(TextUtils.isEmpty(User)||TextUtils.isEmpty(email)||TextUtils.isEmpty(password)){
                loadingSignUp.setVisibility(View.GONE);
                Toast.makeText(SignUp.this, "Credentials Empty", Toast.LENGTH_SHORT).show();
            }
            else if(!cPass.matches(password)){
                loadingSignUp.setVisibility(View.GONE);
                Toast.makeText(SignUp.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
            }
            else if(password.length()<8){
                loadingSignUp.setVisibility(View.GONE);
                Toast.makeText(SignUp.this, "Password is too small", Toast.LENGTH_SHORT).show();
            }
            else{
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignUp.this, task -> {
                    if(task.isSuccessful()){
                        loadingSignUp.setVisibility(View.GONE);
                        Toast.makeText(SignUp.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUp.this,Login.class));
                        finish();
                    }
                    else{
                        loadingSignUp.setVisibility(View.GONE);
                        Toast.makeText(SignUp.this, "Registration Failed", Toast.LENGTH_SHORT).show();
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