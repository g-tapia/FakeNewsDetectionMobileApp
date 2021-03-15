package com.example.aifakenews;

import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import me.angrybyte.goose.Article;
import me.angrybyte.goose.Configuration;
import me.angrybyte.goose.ContentExtractor;

public class HomeFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "HomeFragment";
    EditText editText;
    TextView history;
    Button button;
    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        editText = view.findViewById(R.id.link);
        history = view.findViewById(R.id.history);
        button = view.findViewById(R.id.button);
        button.setOnClickListener(this);

        return view;
        //return inflater.inflate(R.layout.fragment_home, container, false);
        /*
        View fragment_layout = inflater.inflate(R.layout.fragment_home, container, false);

        editText = fragment_layout.findViewById(R.id.link);
        history = fragment_layout.findViewById(R.id.history);


        return super.onCreateView(inflater, container, savedInstanceState);
        */

    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: The link is: " + editText.getText().toString());
        history.setText(editText.getText().toString() + "\n" + history.getText().toString() + "\n");

        new Thread(new Runnable(){
            @Override
            public void run(){
                Looper.prepare(); //////////////
                Uri dataUri = Uri.parse(editText.getText().toString());
                String urlToUse = dataUri.toString();

                Configuration config = new Configuration(getActivity().getCacheDir().getAbsolutePath());
                ContentExtractor extractor = new ContentExtractor(config);

                Article article = null;
                try {
                    article = extractor.extractContent(urlToUse, true);
                    Log.d(TAG, "run: " + article);
                }catch (Exception e){
                    Toast.makeText(getActivity(), "Invalid URL", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "run: " + article);
                }

                String details = "";
                try {
                    details = article.getCleanedArticleText();
                    Log.d(TAG, "run: " + details);
                }catch (Exception e){
                    Toast.makeText(getActivity(), "Invalid URL", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "run: " + details);
                }
            }
        }).start();

    }




}
