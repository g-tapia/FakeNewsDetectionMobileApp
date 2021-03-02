package com.example.aifakenews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView history;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.link);
        history = findViewById(R.id.history);

    }

    public void onClick(View v){
        //editText.getText().toString();
        Log.d(TAG, "onClick: The link is: " + editText.getText().toString());
        history.setText(editText.getText().toString() + "\n" + history.getText().toString() + "\n");
        doRunnable(editText.getText().toString());
    }

    public void doRunnable(String url){
        LinkRunnable linkRunnable = new LinkRunnable(this, url);
        new Thread(linkRunnable).start();
    }



}