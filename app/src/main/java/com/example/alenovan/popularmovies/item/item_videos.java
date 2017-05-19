package com.example.alenovan.popularmovies.item;

/**
 * Created by alenovan on 5/14/17.
 */

public class item_videos {
    private String title,url,image_link;
    private int id ;


    public item_videos(int id,String title, String url, String image_link) {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage_link(){
        return image_link;
    }

    public void setImage_link(String image_link){
        this.image_link = image_link;
    }
}
