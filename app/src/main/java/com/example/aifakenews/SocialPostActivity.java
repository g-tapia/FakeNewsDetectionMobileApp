package com.example.aifakenews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class SocialPostActivity extends AppCompatActivity {

    EditText name, status;
    Button post;
    ArrayList<ModelFeed> modelFeedArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_post);

        name = findViewById(R.id.nameText);
        status = findViewById(R.id.statusText);
        post = findViewById(R.id.postButton);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null){
            modelFeedArrayList = intent.getParcelableArrayListExtra("POSTS_IN");
        }

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name.getText().toString() != "" && status.getText().toString() != ""){
                    Intent intent = new Intent(SocialPostActivity.this, SocialNewsFeed.class);
//                    intent.putExtra("NAME", name.getText().toString());
//                    intent.putExtra("STATUS", status.getText().toString());

                    ModelFeed modelFeed5 = new ModelFeed(5, 0, 0, R.drawable.ic_profile_pic, name.getText().toString(), "Just Now", status.getText().toString());
                    modelFeedArrayList.add(modelFeed5);

                    intent.putExtra("POSTS_OUT", modelFeedArrayList);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(SocialPostActivity.this, "Error: All fields must be filled in", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}