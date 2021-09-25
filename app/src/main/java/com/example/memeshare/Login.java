package com.example.memeshare;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

public class Login extends AppCompatActivity {
    private static final int RC_SIGN_IN = 200;
    public static  GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    public GoogleSignInOptions gso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_login);
        TextInputEditText mail = findViewById(R.id.text_input_username);
        TextInputEditText pass = findViewById(R.id.text_input_password);
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
        SignInButton gSignIn = findViewById(R.id.gSignIn);
        mAuth = FirebaseAuth.getInstance();
        createRequest();
        if(mAuth.getCurrentUser()!=null)startActivity(new Intent(this,MainActivity.class));
        gSignIn.setOnClickListener(v-> signIn());
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
                        FirebaseUser user = mAuth.getInstance().getCurrentUser();
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

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void createRequest() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("744363372067-cedv3uq212krm85ohnphc6dfooheakc3.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("messagingApp", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("messagingApp", "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d("messagingApp", "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        Intent i = new Intent(Login.this,MainActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.d("messagingApp", "signInWithCredential:failure", task.getException());
                        Toast.makeText(Login.this, Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleSignInClient.signOut();
    }
}