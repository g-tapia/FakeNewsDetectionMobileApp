package com.example.aifakenews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.help:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

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