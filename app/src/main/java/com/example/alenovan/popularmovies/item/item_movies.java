package com.example.alenovan.popularmovies.item;

import android.graphics.Bitmap;

/**
 * Created by alenovan on 5/12/17.
 */

public class item_movies {
    private String title,vote_average,image_link;
    private int id ;


    public item_movies(int id,String title, String vote_average, String image_link) {
        this.id = id;
        this.title = title;
        this.vote_average = vote_average;
        this.image_link = image_link;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getImage_link(){
        return image_link;
    }

    public void setImage_link(String image_link){
        this.image_link = image_link;
    }
}