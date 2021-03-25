// Created by Franklin Lu

package com.example.aifakenews;

import java.io.Serializable;

public class NewsSource implements Serializable{

    //Global variables
    private String id;
    private String name;
    private String category;


    //Constructor
    public NewsSource(String id, String name, String category){
        this.id = id;
        this.name = name;
        this.category = category;
    }


    //Helper methods
    public String getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public String getCategory() {
        return category;
    }

}
