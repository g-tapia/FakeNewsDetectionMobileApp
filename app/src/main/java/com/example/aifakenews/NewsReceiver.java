//Created by Franklin Lu

package com.example.aifakenews;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class NewsReceiver extends BroadcastReceiver{

    private NewsActivity newsActivity;
    private static final String TAG = "NewsReceiver";
    private int count = 0;
    public NewsReceiver(NewsActivity newsActivity){
        this.newsActivity = newsActivity;
    }


    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (action == null){
            return;
        }

        switch (action){
            case NewsActivity.NEWS_FROM_SERVICE:
                if (intent.hasExtra(NewsActivity.ARTICLE_FROM_SERVICE)){
                    final NewsArticle article = (NewsArticle) intent.getSerializableExtra(NewsActivity.ARTICLE_FROM_SERVICE);
                    count++;
                    Log.d(TAG, "onReceive: " + count);
                    newsActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            newsActivity.updateArticle(article);
                        }
                    });
                    Log.d(TAG, "onReceive: " + article.getTitle());}
                break;

        }
    }
}
