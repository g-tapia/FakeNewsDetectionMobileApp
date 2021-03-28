package com.example.aifakenews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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
                        case R.id.News_nav:
                            selectedFragment = new NewsTabFragment();
                            Log.d(TAG, "onNavigationItemSelected: " + selectedFragment);
                            //Below code jumps straight to activity
//                            Intent j = new Intent(MainActivity.this, NewsActivity.class);
//                            startActivity(j);

                            break;
                        case R.id.Twitter_nav:
                            selectedFragment = new SocialFragment();
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
                Intent i = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(i);
                return true;
            case R.id.drawer_menu:
                Intent j = new Intent(MainActivity.this, NewsActivity.class);
                startActivity(j);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}