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

        mTitleWindow.setText("(AI FakeNews)\n\nThis is an app that uses an AI model to detect if a link contains fake news\n\nHow to use application:\n\n");

        StringBuilder stringBuilder = new StringBuilder();

        String someMessage = " Having trouble using the app? No problem! we are here to help!\n\n " +
                "I'm sure you're aware already! the main purpose of our app is to fight fake news! that being said, we implemented a couple features to help with this. Below, we will go over our app and give you an overview on how to use it.\n\n"
                +"At the main menu, you have a search bar at the top, with three buttons below. Lets go over these:\n\n"
                +"(Search Bar)- the search bar takes the input of an article URL that you would like our system to determine if it comes from a credible source or not.\n\n"
                + "(Home Button)- last of all, the home button takes you back to the main page."
                +"\n\n©2021, Franklin Lu, Alexis Edwards, George Tapia, Truong Pham";

        stringBuilder.append(someMessage);

        mMessageWindow.setText(stringBuilder.toString());

    }
}