package com.example.aifakenews;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        editText = view.findViewById(R.id.link);
        history = view.findViewById(R.id.history);
        button = view.findViewById(R.id.button);
        button.setOnClickListener(this);

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (history.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "History is empty", Toast.LENGTH_SHORT).show();
                }else{
                    String historytext = history.getText().toString();
                    final CharSequence[] hArray = historytext.split("\\r?\\n");
                    Log.d(TAG, "onClick: the length of the list is: " + hArray.length);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Make a selection:");
                    builder.setItems(hArray, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            editText.setText(hArray[which]);
                        }
                    });

                    builder.setNegativeButton("Nevermind", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ;
                        }
                    });

                    AlertDialog dialog = builder.create();

                    dialog.show();

                }
            }
        });

        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item=menu.findItem(R.id.drawer_menu);
        if(item!=null)
            item.setVisible(false);
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: The link is: " + editText.getText().toString());
        history.setText(editText.getText().toString() + "\n" + history.getText().toString() + "\n");

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare(); //////////////
                Uri dataUri = Uri.parse(editText.getText().toString());
                String urlToUse = dataUri.toString();

                Configuration config = new Configuration(getActivity().getCacheDir().getAbsolutePath());
                ContentExtractor extractor = new ContentExtractor(config);

                Article article = null;
                try {
                    article = extractor.extractContent(urlToUse, true);
                    Log.d(TAG, "run: " + article);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Invalid URL", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "run: " + article);
                }

                String details = "";
                try {
                    details = article.getCleanedArticleText();
                    Log.d(TAG, "run: " + details);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Invalid URL", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "run: " + details);
                }
            }
        }).start();

    }



}
