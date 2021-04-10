package com.example.aifakenews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SocialPostActivity extends AppCompatActivity {

    EditText name, status;
    Button post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_post);

        name = findViewById(R.id.nameText);
        status = findViewById(R.id.statusText);
        post = findViewById(R.id.postButton);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name.getText().toString() != "" && status.getText().toString() != ""){
                    Intent intent = new Intent(SocialPostActivity.this, SocialNewsFeed.class);
                    intent.putExtra("NAME", name.getText().toString());
                    intent.putExtra("STATUS", status.getText().toString());
                    startActivity(intent);
                }
                else{
                    Toast.makeText(SocialPostActivity.this, "Error: All fields must be filled in", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}