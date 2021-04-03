package com.example.aifakenews;

public class ModelFeed {

    int id, likes, comments, propic;
    String name, time, status;

    public ModelFeed(int id, int likes, int comments, int propic, String name, String time, String status) {
        this.id = id;
        this.likes = likes;
        this.comments = comments;
        this.propic = propic;
        this.name = name;
        this.time = time;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getPropic() {
        return propic;
    }

    public void setPropic(int propic) {
        this.propic = propic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
