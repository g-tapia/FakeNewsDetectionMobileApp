//Created by Franklin Lu

package com.example.aifakenews;

import java.io.Serializable;

public class NewsArticle implements Serializable {

    //Global variables
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String date;

    //Constructor
    public NewsArticle(String author, String title, String desc, String url, String image, String date){
        this.author = author;
        this.title = title;
        this.description = desc;
        this.url = url;
        this.urlToImage = image;
        this.date = date;
    }

    //Helper methods

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }
}
