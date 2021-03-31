package com.example.aifakenews;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SocialFragment extends Fragment {


    private static final String TAG = "SocialFragment";
    EditText emailID, password;
    Button loginBtn, signUpBtn;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.social_login, container, false);
        View view = inflater.inflate(R.layout.social_login, container, false);


        //Assign global variables
        mFirebaseAuth = FirebaseAuth.getInstance();
        emailID = view.findViewById(R.id.editTextTextEmailAddress);
        password = view.findViewById(R.id.editTextTextPassword);
        loginBtn = view.findViewById(R.id.button2);
        signUpBtn = view.findViewById(R.id.button3);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    Toast.makeText(getActivity(), "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(), SocialNewsFeed.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getActivity(), "Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        };

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("HELLO", "Register Button Clicked");
                String email = emailID.getText().toString();
                String pwd = password.getText().toString();

                if (email.isEmpty()) {
                    emailID.setError("Please provide an email address");
                    emailID.requestFocus();
                } else if (pwd.isEmpty()) {
                    emailID.setError("Please provide a password");
                    emailID.requestFocus();
                } else if (email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(getActivity(), "Fields are empty!", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && pwd.isEmpty())) {

//                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            Log.d(TAG, "signInWithEmailAndPassword:onComplete:" + task.isSuccessful());
//                            if (!task.isSuccessful()) {
//                                // Notify user of failure
//                            }
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.d("HELLO", e.getLocalizedMessage());
//                            //notifyUser(e.getLocalizedMessage());
//                        }
//                    });

                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                            if (!task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Login Unsuccessful. Please try again.", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent j = new Intent(getActivity(), SocialNewsFeed.class);
                                startActivity(j);
                            }
                        }
                    });


                } else {
                    Toast.makeText(getActivity(), "Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailID.getText().toString();
                String pwd = password.getText().toString();

                if (email.isEmpty()) {
                    emailID.setError("Please provide an email address");
                    emailID.requestFocus();
                } else if (pwd.isEmpty()) {
                    password.setError("Please provide a password");
                    password.requestFocus();
                } else if (email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(getActivity(), "Fields are empty!", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && pwd.isEmpty())) {
                    //Create username and password
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
//                            if (!task.isSuccessful()) {
//                                // Notify user of failure
//                            }
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.d("HELLO", e.getLocalizedMessage());
//                            //notifyUser(e.getLocalizedMessage());
//                        }
//                    });

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Sign Up unsuccessful, Please try again", Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(getActivity(), SocialNewsFeed.class));
                            }
                        }
                    });

                } else {
                    Toast.makeText(getActivity(), "Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }




    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item=menu.findItem(R.id.drawer_menu);
        item.setVisible(false);
    }

}
