package com.example.aifakenews;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

//Firebase import
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SocialLoginActivity extends AppCompatActivity {

    EditText emailID, password;
    Button loginBtn, signUpBtn;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social_login);


        //Assign global variables
        mFirebaseAuth = FirebaseAuth.getInstance();
        emailID = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        loginBtn = findViewById(R.id.button2);
        signUpBtn = findViewById(R.id.button3);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    Toast.makeText(SocialLoginActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(SocialLoginActivity.this, SocialNewsFeed.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(SocialLoginActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        };

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("HELLO", "Register Button Clicked");
                String email = emailID.getText().toString();
                String pwd = password.getText().toString();

                if(email.isEmpty()) {
                    emailID.setError("Please provide an email address");
                    emailID.requestFocus();
                }
                else if (pwd.isEmpty()) {
                    emailID.setError("Please provide a password");
                    emailID.requestFocus();
                }
                else if (email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(SocialLoginActivity.this, "Fields are empty!", Toast.LENGTH_SHORT).show();
                }
                else if (!(email.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(SocialLoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(SocialLoginActivity.this, "Login Unsuccessful. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Intent j = new Intent(SocialLoginActivity.this, SocialNewsFeed.class);
                                startActivity(j);
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(SocialLoginActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailID.getText().toString();
                String pwd = password.getText().toString();

                if(email.isEmpty()) {
                    emailID.setError("Please provide an email address");
                    emailID.requestFocus();
                }
                else if (pwd.isEmpty()) {
                    emailID.setError("Please provide a password");
                    emailID.requestFocus();
                }
                else if (email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(SocialLoginActivity.this, "Fields are empty!", Toast.LENGTH_SHORT).show();
                }
                else if (!(email.isEmpty() && pwd.isEmpty())){
                    //Create username and password
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(SocialLoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SocialLoginActivity.this, "Sign Up unsuccessful, Please try again", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                startActivity(new Intent(SocialLoginActivity.this, SocialNewsFeed.class));
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(SocialLoginActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }

}
