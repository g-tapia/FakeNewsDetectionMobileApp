//Created by Franklin Lu

package com.example.aifakenews;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class NewsFragment extends Fragment {

    private static final String TAG = "NewsFragment";

    public NewsFragment() {

    }

    static NewsFragment newInstance(NewsArticle article, int index, int max) {
        NewsFragment f = new NewsFragment();
        Bundle bdl = new Bundle(1);
        bdl.putSerializable("ARTICLE", article);
        bdl.putSerializable("INDEX", index);
        bdl.putSerializable("TOTAL_COUNT", max);
        f.setArguments(bdl);
        return f;
    }

    //Setup screen layout for article view
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragment_layout = inflater.inflate(R.layout.news_articles, container, false);


        //**TESTER CODE BELOW
//        setHasOptionsMenu(true);

        Bundle args = getArguments();

        if (args != null) {
            final NewsArticle currentArticle = (NewsArticle) args.getSerializable("ARTICLE");
            if (currentArticle == null) {
                return null;
            }

            int index = args.getInt("INDEX");
            int total = args.getInt("TOTAL_COUNT");

            TextView title = fragment_layout.findViewById(R.id.title);
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(currentArticle.getUrl()));
                    startActivity(intent);
                }
            });
            title.setText(currentArticle.getTitle());

            TextView date = fragment_layout.findViewById(R.id.date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Log.d(TAG, "onCreateView: ");
            String s = currentArticle.getDate();
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date d;
            try {
                d = sdf.parse(s);
                date.setText(d.toString());
            } catch (Exception e) {
                e.printStackTrace();
                date.setText(s);
            }
            //date.setText(d.toString());
            //date.setText(currentArticle.getDate());

            TextView author = fragment_layout.findViewById(R.id.author);
            author.setText(currentArticle.getAuthor());
            if (author.getText().toString().equals("null")) {
                author.setVisibility(View.GONE);
            }

            ImageView image = fragment_layout.findViewById(R.id.image);
            try {
                Picasso.get().load(currentArticle.getUrlToImage())
                        //.error(R.drawable.brokenimage)
                        //.placeholder(R.drawable.placeholder)
                        .fit()
                        .centerCrop()
                        .into(image);
            } catch (Exception e) {
                image.setVisibility(View.GONE);
            }

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(currentArticle.getUrl()));
                    startActivity(intent);
                }
            });

            TextView description = fragment_layout.findViewById(R.id.description);
            description.setText(currentArticle.getDescription());
            description.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(currentArticle.getUrl()));
                    startActivity(intent);
                }
            });
            TextView page = fragment_layout.findViewById(R.id.page);
            page.setText(index + " of " + total);
            Log.d(TAG, "onCreateView: " + currentArticle);
            return fragment_layout;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }


//    //Special menu to include drawer layout
//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.menu, menu);
//        //inflater.inflate(R.menu.news_menu, menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        if (item.getItemId() == android.R.id.home) {
//            if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
//                mDrawerLayout.closeDrawer(Gravity.LEFT);
//            }else{
//                mDrawerLayout.openDrawer(Gravity.LEFT);
//            }
//
//        }else if (item.getItemId()== R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}