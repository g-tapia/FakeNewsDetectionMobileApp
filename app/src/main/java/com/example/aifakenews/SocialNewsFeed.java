package com.example.aifakenews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SocialNewsFeed extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ModelFeed> modelFeedArrayList = new ArrayList<>();
    AdapterFeed adapterFeed;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social_news_feed);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapterFeed = new AdapterFeed(this, modelFeedArrayList);
        recyclerView.setAdapter(adapterFeed);

        fab = findViewById(R.id.fab_btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SocialNewsFeed.this, "You clicked the button!", Toast.LENGTH_SHORT).show();
            }
        });

        populateRecyclerView();

    }

    public void populateRecyclerView() {
        ModelFeed modelFeed = new ModelFeed(1, 100, 2, R.drawable.ic_profile_pic, "Alexis Edwards", "2 hrs", "I hate Android Studio!!!");
        modelFeedArrayList.add(modelFeed);

        ModelFeed modelFeed2 = new ModelFeed(2, 50, 2, R.drawable.ic_profile_pic, "George Tapia", "3 hrs", "Check out this link: https://21stcenturywire.com/2016/11/12/henningsen-us-anti-trump-protests-similar-to-soros-color-revolutions-abroad/");
        modelFeedArrayList.add(modelFeed2);

        ModelFeed modelFeed3 = new ModelFeed(3, 75, 2, R.drawable.ic_profile_pic, "Franklin Lu", "4 hrs", "Practice posting status 3 and this is a really long one to see what happens when the status is really long");
        modelFeedArrayList.add(modelFeed3);

        ModelFeed modelFeed4 = new ModelFeed(4, 25, 2, R.drawable.ic_profile_pic, "Truong Pham", "10 hrs", "Practice posting status 4");
        modelFeedArrayList.add(modelFeed4);

        adapterFeed.notifyDataSetChanged();
    }
}