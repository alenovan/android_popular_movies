package com.example.alenovan.popularmovies.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import com.example.alenovan.popularmovies.*;

import com.example.alenovan.popularmovies.R;

public class splash_main extends Activity {

    ImageView imagen;
    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash_main);
        imagen=(ImageView)findViewById(R.id.imageView2);
        imagen.setImageDrawable(getResources().getDrawable(R.drawable.album1));
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(splash_main.this,dashboard_2.class);
                startActivity(mainIntent);
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
