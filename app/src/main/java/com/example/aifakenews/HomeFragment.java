package com.example.aifakenews;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import me.angrybyte.goose.Article;
import me.angrybyte.goose.Configuration;
import me.angrybyte.goose.ContentExtractor;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    EditText editText;
    TextView history;
    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_home, container, false);
        /*
        View fragment_layout = inflater.inflate(R.layout.fragment_home, container, false);

        editText = fragment_layout.findViewById(R.id.link);
        history = fragment_layout.findViewById(R.id.history);

        return super.onCreateView(inflater, container, savedInstanceState);

         */
    }

    /*
    public void onClick(View v){
        //editText.getText().toString();
        Log.d(TAG, "onClick: The link is: " + editText.getText().toString());
        history.setText(editText.getText().toString() + "\n" + history.getText().toString() + "\n");
        doRunnable(editText.getText().toString());
    }

    public void doRunnable(String url){

        LinkRunnable linkRunnable = new LinkRunnable(this, url);
        new Thread(linkRunnable).start();

        Uri dataUri = Uri.parse(url);
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
    */

}
