package com.example.alenovan.popularmovies.item;

/**
 * Created by alenovan on 5/14/17.
 */

public class item_review {
    private String title,url,image_link;
    private int id ;


    public item_review(int id,String title, String url, String image_link) {
        this.id = id;
        this.title = title;
        this.url = url;
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
        return url;
    }

    public void setVote_average(String vote_average) {
        this.url = vote_average;
    }

    public String getImage_link(){
        return image_link;
    }

    public void setImage_link(String image_link){
        this.image_link = image_link;
    }
}
