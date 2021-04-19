package com.example.aifakenews;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelFeed implements Parcelable {

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

    protected ModelFeed(Parcel in) {
        id = in.readInt();
        likes = in.readInt();
        comments = in.readInt();
        propic = in.readInt();
        name = in.readString();
        time = in.readString();
        status = in.readString();
    }

    public static final Creator<ModelFeed> CREATOR = new Creator<ModelFeed>() {
        @Override
        public ModelFeed createFromParcel(Parcel in) {
            return new ModelFeed(in);
        }

        @Override
        public ModelFeed[] newArray(int size) {
            return new ModelFeed[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(likes);
        dest.writeInt(comments);
        dest.writeInt(propic);
        dest.writeString(name);
        dest.writeString(time);
        dest.writeString(status);
    }

}
