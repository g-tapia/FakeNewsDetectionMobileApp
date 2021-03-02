package com.example.aifakenews;

import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import me.angrybyte.goose.Article;
import me.angrybyte.goose.Configuration;
import me.angrybyte.goose.ContentExtractor;

public class LinkRunnable implements Runnable{
    private static final String TAG = "LinkRunnable";
    private MainActivity mainActivity;
    private final String URL;

    public LinkRunnable(MainActivity ma, String link){
       mainActivity = ma;
       URL = link;
    }

    public void run(){
        Uri dataUri = Uri.parse(URL);
        String urlToUse = dataUri.toString();

        Configuration config = new Configuration(mainActivity.getCacheDir().getAbsolutePath());
        ContentExtractor extractor = new ContentExtractor(config);

        Article article = extractor.extractContent(urlToUse, true);
        if (article == null) {
            Log.d(TAG, "Couldn't load the article, is your URL correct, is your Internet working?");
            return;
        }

        String details = article.getCleanedArticleText();
        Log.d(TAG, "run: " + details);
        if (details == null) {
            Log.w(TAG, "Couldn't load the article text, the page is messy. Trying with page description...");
            details = article.getMetaDescription();
        }


    }
}
