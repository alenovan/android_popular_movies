package com.example.alenovan.popularmovies.config;

import android.graphics.Bitmap;

/**
 * Created by alenovan on 5/12/17.
 */

public class config {
    private String detail;
    public static int[] id;
    public static String[] original_title;
    public static String[] vote_average;
    public static String[] backdrop_path;
    public static Bitmap[] bitmaps;
    public static final String TAG_IMAGE_URL = "url";
    public static final String TAG_IMAGE_NAME = "name";
    public static final String TAG_JSON_ARRAY="result";

    public static final String APIkeyMoves=""; // Api In hire

    public static final String UrlPopular = "http://api.themoviedb.org/3/movie/popular?api_key="+config.APIkeyMoves;
    public static final String UrlHighhestRate ="https://api.themoviedb.org/3/movie/top_rated?api_key="+config.APIkeyMoves+"&language=en-US&page=1";
    public static final String UrlUpcoming="https://api.themoviedb.org/3/movie/upcoming?api_key="+config.APIkeyMoves+"&language=en-US&page=1";
    public static final String UrlNowPlaying="https://api.themoviedb.org/3/movie/now_playing?api_key="+config.APIkeyMoves+"&language=en-US&page=1";
    public static final String UrlFavorit="";
    public static final String UrlThriler="https://api.themoviedb.org/3/movie/{movie_id}/videos?api_key="+config.APIkeyMoves+"&language=en-US";
    public static final String UrlReview="https://api.themoviedb.org/3/movie/{movie_id}/reviews?api_key="+config.APIkeyMoves+"&language=en-US&page=1";

    public config(int i){
        id = new int[i];
        original_title = new String[i];
        vote_average = new String[i];
        backdrop_path = new String[i];
        bitmaps = new Bitmap[i];
    }


    // Detail Movies
    public static final String UrlDetail_first ="https://api.themoviedb.org/3/movie/";
    public static final String UrlDetail_last ="?api_key="+config.APIkeyMoves+"&language=en-US";

    // Image Slider
    public static final String UrlImage_Slider ="https://image.tmdb.org/t/p/original";

    // Video Thumbnail
    public static final String UrlVideo_thumbnail ="http://img.youtube.com/vi/";

    // video detail_movies
    public static final String UrlVideo_first ="https://api.themoviedb.org/3/movie/";
    public static final String UrlVideo_last ="/videos?api_key="+config.APIkeyMoves+"&language=en-US";

    //Review Detail Movies
    public static final String UrlReview_first ="https://api.themoviedb.org/3/movie/";
    public static final String UrlReview_last ="/reviews?api_key="+config.APIkeyMoves+"&language=en-US";

    // Youtube Link
    public static final String UrlYoutube ="http://www.youtube.com/watch?v=";



}
