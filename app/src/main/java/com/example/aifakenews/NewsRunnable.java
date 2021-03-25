package com.example.aifakenews;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsRunnable implements Runnable{

    private NewsActivity newsActivity;
    private String category = "";
    private final String key = "ef9b47f477454be1bc1f57a233b4e2fa";
    private static final String TAG = "NewsRunnable";

    public NewsRunnable(NewsActivity na, String category){
        newsActivity = na;
        if (category.equals("all")){
            this.category = "";
        }else {
            this.category = category;
        }

    }

    @Override
    public void run() {

        String URL = "https://newsapi.org/v2/sources?language=en&country=us&category=" + category + "&apiKey=" + key;
        Log.d(TAG, "run: " + URL);
        Uri dataUri = Uri.parse(URL);
        String urlToUse = dataUri.toString();

        StringBuilder sb = new StringBuilder();
        try{
            java.net.URL url = new URL(urlToUse);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.addRequestProperty("User-Agent", "");
            conn.connect();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK){
                handleResults(null);
            }

            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader((new InputStreamReader(is)));

            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }


        }catch(Exception e){
            //handleResults(null);
            Log.d(TAG, "run: " + sb);
            Log.d(TAG, "run: ERROR THROWN");
            e.printStackTrace();
            return;
        }

        handleResults(sb.toString());

    }

    public void handleResults(String s){
        Log.d(TAG, "handleResults: ");
        final List<NewsSource> news = parseJSON(s);
        newsActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                newsActivity.updateNews(news);

            }
        });
    }

    public List<NewsSource> parseJSON(String s){
        Log.d(TAG, "parseJSON: ");
        List <NewsSource> news = new ArrayList<>();

        try{
            JSONObject jsonObj = new JSONObject(s);
            String sources = jsonObj.getString("sources");
            JSONArray jSources = new JSONArray(sources);
            Log.d(TAG, "parseJSON: ");
            for (int i = 0; i < jSources.length(); i++){

                JSONObject jObj = (JSONObject)jSources.get(i);
                String id = jObj.getString("id");
                String name = jObj.getString("name");
                String category = jObj.getString("category");
                Log.d(TAG, "parseJSON: " + id + " " + name + " " + category);
                NewsSource newsSource = new NewsSource(id, name, category);
                news.add(newsSource);
            }

        }catch (Exception e){
            Log.d(TAG, "parseJSON: ERROR THROWN");
        }

        return news;
    }

}
