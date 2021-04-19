//Created by Franklin Lu

package com.example.aifakenews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


//api key = ef9b47f477454be1bc1f57a233b4e2fa
public class NewsActivity extends AppCompatActivity {


    private Toolbar toolbar;


    //Global variables for later use
    static final String NEWS_FROM_SERVICE = "NEWS_FROM_SERVICE";
    static final String ARTICLE_FROM_SERVICE = "ARTICLE_FROM_SERVICE";
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] items;
    private List<NewsSource> newsSources = new ArrayList<>();
    private List<NewsArticle> newsArticles = new ArrayList<>();
    private SharedPreferences sharedPrefs;

    private Menu menu;
    // **Changed from "MainActivity" to "NewsActivity"
    private static final String TAG = "NewsActivity";

    private List<Fragment> fragments;
    private MyPageAdapter pageAdapter;
    private ViewPager pager;
    private NewsReceiver newsReceiver;
    private static int articleCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        //Establish layout elements

        //Find Drawer view
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout); // <== Important!

        mDrawerList = findViewById(R.id.left_drawer); // <== Important!
        mDrawerToggle = new ActionBarDrawerToggle(   // <== Important!
                this,
                mDrawerLayout,
                R.string.drawer_open,
                R.string.drawer_close
        );

        fragments = new ArrayList<>();
        pageAdapter = new MyPageAdapter(getSupportFragmentManager());
        pager = findViewById(R.id.viewPager);
        pager.setAdapter(pageAdapter);
        startDownload();

        IntentFilter intentFilter = new IntentFilter(NEWS_FROM_SERVICE);
        newsReceiver = new NewsReceiver(this);
        registerReceiver(newsReceiver, intentFilter);
    }

    public void startDownload(){
        NewsRunnable newsRunnable = new NewsRunnable(this, "");
        new Thread(newsRunnable).start();
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // Important!
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        CharSequence category = item.getTitle();
        Log.d(TAG, "onOptionsItemSelected: " + category);
        String str = category.toString();
        /*
        SharedPreferences.Editor prefsEditor = sharedPrefs.edit();
        prefsEditor.putString("MY_PREFS", str);
        prefsEditor.apply();
        */
        NewsRunnable newsRunnable = new NewsRunnable(this, str);
        new Thread(newsRunnable).start();

        return super.onOptionsItemSelected(item);
    }

    private void selectItem(int position) {
        newsArticles.clear();
        for (int i = 0; i < pageAdapter.getCount(); i++){
            pageAdapter.notifyChangeInPosition(i);
        }
        Log.d(TAG, "selectItem: " + position);
        fragments.clear();

        setTitle(items[position]);
        Intent intent = new Intent(this, NewsService.class);
        intent.putExtra("ID", newsSources.get(position));
        startService(intent);
        Log.d(TAG, "selectItem: error check");
        mDrawerLayout.closeDrawer(mDrawerList);


    }

    public void updateNews(List <NewsSource> n){
        newsSources.clear();
        newsSources.addAll(n);
        Log.d(TAG, "updateNews: download done");

        Log.d(TAG, "onCreate: " + newsSources.size());

        items = new String[newsSources.size()];
        for (int i = 0; i < items.length; i++)
            items[i] = newsSources.get(i).getName();

        mDrawerList.setAdapter(new ArrayAdapter<>(this,   // <== Important!
                R.layout.drawer_list_item, items));

        mDrawerList.setOnItemClickListener(   // <== Important!

                new ListView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectItem(position);
                    }
                }
        );

        if (getSupportActionBar() != null) {  // <== Important!
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        if (menu.size() <= 1) {
            List<String> categories = new ArrayList<>();
            for (int i = 0; i < newsSources.size(); i++) {
                categories.add(newsSources.get(i).getCategory());
            }

            List<String> list = new ArrayList<String>(new LinkedHashSet<String>(categories));
            for (int i = 0; i < list.size(); i++) {
                menu.add(list.get(i));
            }

            //String colorList [] = {"Color.YELLOW", "Color.RED", "Color.BLUE", "Color.GREEN", "Color.GRAY", "Color.MAGENTA", "Color.CYAN", "Color.LTGRAY"};
            int colorList [] = {Color.rgb(255,215,0), -65536, -16776961, -16711936, -7829268, -65281, -16711681, -3355444};
            //Color.rgb(255,215,0);

            for (int i = 1; i <= list.size(); i++){
                String itemTitle = (String)menu.getItem(i).getTitle();

                SpannableString s = new SpannableString(itemTitle);
                s.setSpan(new ForegroundColorSpan(colorList[i - 1]), 0, s.length(), 0);
                menu.getItem(i).setTitle(s);

            }
        }
    }

    public void updateArticle(NewsArticle article){

        fragments.add(NewsFragment.newInstance(article, fragments.size() + 1, articleCount));

        pageAdapter.notifyDataSetChanged();
        pager.setCurrentItem(0);
    }

    public static void getArticles(int x){
        articleCount = x;
    }

    private class MyPageAdapter extends FragmentPagerAdapter {
        private long baseId = 0;

        MyPageAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public long getItemId(int position) {
            // give an ID different from position when position has been changed
            return baseId + position;
        }

        void notifyChangeInPosition(int n) {
            // shift the ID returned by getItemId outside the range of all previous fragments
            baseId += getCount() + n;
        }

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState(); // <== IMPORTANT
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig); // <== IMPORTANT
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;

        getMenuInflater().inflate(R.menu.categories, menu);
        return true;
    }

    private void stopService() {
        Log.d(TAG, "stopService: called");
        // **Changed from MainActivity.this to News.Activity.this
        Intent intent = new Intent(NewsActivity.this, NewsService.class);
        stopService(intent);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(newsReceiver);
        stopService();

        super.onDestroy();
    }
}

