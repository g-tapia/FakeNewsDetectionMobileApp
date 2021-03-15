package com.example.aifakenews;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.parse.ParseObject;
import com.twitter.sdk.android.core.Twitter;

public class TwitterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Twitter.initialize(this);
        // Change back to twitter stuff
        setContentView(R.layout.activity_main);
        ParseObject firstObject = new ParseObject("FirstClass");
        firstObject.put("message","Hey ! First message from android. Parse is now connected");
        firstObject.saveInBackground(e -> {
            if (e != null){
                Log.e("MainActivity", e.getLocalizedMessage());
            }else{
                Log.d("MainActivity","Object saved.");
            }
        });
    }
}