package com.example.alenovan.popularmovies;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.example.alenovan.popularmovies.fragment.allmovies.fragment_all_movies;
import com.example.alenovan.popularmovies.fragment.favorite.favorite_fragment;
import com.example.alenovan.popularmovies.fragment.genre.fragment_genre;

public class dashboard_2 extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_all_movies:
                    fragment_all_movies();
                    return true;
                case R.id.navigation_favorite:
                    favorite_fragment();
                    return true;
                case R.id.navigation_genre:
                    genre_fragment();
                    return true;
            }

            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_2);



        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fragment_all_movies();
    }

    public void fragment_all_movies(){
        fragment_all_movies crimeFragment=new fragment_all_movies();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,crimeFragment).commit();
    }


    public void favorite_fragment(){
        favorite_fragment favorite_fragment=new favorite_fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,favorite_fragment).commit();
    }

    public void genre_fragment(){
        fragment_genre fragment_genre=new fragment_genre();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment_genre).commit();
    }



}
