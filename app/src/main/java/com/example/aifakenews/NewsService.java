//Created by Franklin Lu

package com.example.aifakenews;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsService extends Service{

    private static final String TAG = "NewsService";
    private String URL = "";
    private final String pk = "ef9b47f477454be1bc1f57a233b4e2fa";
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.hasExtra("ID")){
            URL = "https://newsapi.org/v2/top-headlines?sources=" + ((NewsSource) intent.getSerializableExtra("ID")).getId() +  "&language=en&apiKey=" + pk;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
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
                        return;
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
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }


    public void handleResults(String s){
        List<NewsArticle> articles = parseJSON(s);

    }

    public List<NewsArticle> parseJSON(String s){
        List <NewsArticle> articles = new ArrayList<>();
        try{
            JSONObject jsonObject = new JSONObject(s);
            String sources = jsonObject.getString("articles");

            final JSONArray jsonArticles = new JSONArray(sources);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    NewsActivity.getArticles(jsonArticles.length());
                }
            }).start();

            for (int i = 0; i < jsonArticles.length() ; i++) {
                JSONObject jsonArticle = (JSONObject) jsonArticles.get(i);
                String author = "";
                try{
                    author = jsonArticle.getString("author");
                }catch (Exception e){
                    e.printStackTrace();
                }

                String title = "";
                try{
                    title = jsonArticle.getString("title");
                }catch (Exception e){
                    e.printStackTrace();
                }


                String desc = "";
                try{
                    desc = jsonArticle.getString("description");
                }catch (Exception e){
                    e.printStackTrace();
                }

                String url = "";
                try{
                    url = jsonArticle.getString("url");
                }catch (Exception e){
                    e.printStackTrace();
                }
                String urlToImage = "";
                try{
                    urlToImage = jsonArticle.getString("urlToImage");
                }catch (Exception e){
                    e.printStackTrace();
                }
                String publishedAt = "";
                try{
                    publishedAt = jsonArticle.getString("publishedAt");
                }catch (Exception e){
                    e.printStackTrace();
                }

                NewsArticle art = new NewsArticle(author, title, desc, url, urlToImage, publishedAt);
                Log.d(TAG, "parseJSON: " + author + title + desc + url + urlToImage + publishedAt);
                articles.add(art);
                sendArticle(art);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return articles;
    }

    private void sendArticle(NewsArticle newsToSend){
        Intent intent = new Intent();
        intent.setAction(NewsActivity.NEWS_FROM_SERVICE);
        intent.putExtra(NewsActivity.ARTICLE_FROM_SERVICE, newsToSend);
        sendBroadcast(intent);
        Log.d(TAG, "sendArticle: article sent! Title = " + newsToSend.getTitle());
    }



}
