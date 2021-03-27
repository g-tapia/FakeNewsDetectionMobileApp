package com.example.aifakenews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NewsTabFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.activity_news, container, false);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item=menu.findItem(R.id.about);
        MenuItem item2=menu.findItem(R.id.help);
        if(item!=null)
            item.setVisible(false);
            item2.setVisible(false);
    }






}
