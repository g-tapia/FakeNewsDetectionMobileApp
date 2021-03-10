package com.example.aifakenews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    /*
    EditText editText;
    TextView history;
     */
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        editText = findViewById(R.id.link);
        history = findViewById(R.id.history);


         */
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){
                        case R.id.Home_nav:
                            selectedFragment = new HomeFragment();
                            Log.d(TAG, "onNavigationItemSelected: " + selectedFragment);
                            break;
                        case R.id.Facebook_nav:
                            selectedFragment = new FacebookFragment();
                            Log.d(TAG, "onNavigationItemSelected: " + selectedFragment);
                            break;
                        case R.id.Twitter_nav:
                            selectedFragment = new TwitterFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                    return true;
                }
            };

    /*
    public void onClick(View v){
        try {
            Log.d(TAG, "onClick: The link is: " + editText.getText().toString());
            history.setText(editText.getText().toString() + "\n" + history.getText().toString() + "\n");
            doRunnable(editText.getText().toString());
        }catch (Exception e){
            Log.d(TAG, "onClick: " + editText);
        }
    }

    public void doRunnable(String url){
        LinkRunnable linkRunnable = new LinkRunnable(this, url);
        new Thread(linkRunnable).start();
    }

     */




}