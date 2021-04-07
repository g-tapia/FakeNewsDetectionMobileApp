package com.example.aifakenews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        TextView mTitleWindow = (TextView) findViewById(R.id.titleWindow);
        TextView mMessageWindow = (TextView) findViewById(R.id.messageWindow);

        mTitleWindow.setText("(About Page)\n\nThis is an app that uses an AI model to detect if a link contains fake news\n\nHow to use application:\n\n");

        StringBuilder stringBuilder = new StringBuilder();

        String someMessage = "Having trouble using the app? No problem! We are here to help!\n\n" +
                "I'm sure you're aware already; the main purpose of our app is to fight fake news! With that being said, we implemented a couple features to help with this. Below, we will go over our app and give you an overview on how to use it.\n\n"
                +"On the home page, you have certain functionalities. Lets go over these:\n\n"
                +"(Search Bar) - the search bar takes the input of an article URL that you would like our system to determine if it comes from a credible source or not.\n\n"
                +"(Detect) - this button will classify your article URL as either fake or real news.\n\n"
                +"(Search History) - clicking on your search history will allow you to select a link you've recently entered\n\n"
                +"(Home Button) - last of all, the home button redirects you back to the main page.";

        stringBuilder.append(someMessage);

        mMessageWindow.setText(stringBuilder.toString());

    }
}