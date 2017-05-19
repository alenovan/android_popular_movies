package com.example.alenovan.popularmovies;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.alenovan.popularmovies.fragment.allmovies.fragment_all_movies;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarBadge;
import com.roughike.bottombar.BottomBarFragment;
import com.roughike.bottombar.OnTabSelectedListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class dashboard  extends AppCompatActivity {
    private BottomBar bottomBar;
    private RequestQueue requestQueue;
    private static final String URL_NOTIF = "http://alenovanwdk.hol.es/count_notif.php";
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#99000000")));
        if (android.os.Build.VERSION.SDK_INT >= 21){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#99000000"));
        }

        bottomBar = BottomBar.attach(this, savedInstanceState);
        bottomBar.useOnlyStatusBarTopOffset();
        bottomBar.setFragmentItems(getSupportFragmentManager(), R.id.fragmentContainer,
                new BottomBarFragment(fragment_all_movies.newInstance(), R.drawable.ic_local_dining_white_24dp, "List Akun"),
                new BottomBarFragment(fragment_all_movies.newInstance(), R.drawable.ic_update_white_24dp, "Control"),
                new BottomBarFragment(fragment_all_movies.newInstance(), R.drawable.ic_favorite_white_24dp, "List History"),
                new BottomBarFragment(fragment_all_movies.newInstance(), R.drawable.ic_location_on_white_24dp, "Tentang")
        );


        // Setting colors for different tabs when there's more than three of them.
        bottomBar.mapColorForTab(0, "#00796B");
        bottomBar.mapColorForTab(1, "#ea902a");
        bottomBar.mapColorForTab(2, "#7B1FA2");
        bottomBar.mapColorForTab(3, "#FF5252");



        bottomBar.setOnItemSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onItemSelected(int position) {

                switch (position) {
                    case 0:
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#99000000")));
                        if (android.os.Build.VERSION.SDK_INT >= 21){
                            Window window = getWindow();
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            window.setStatusBarColor(Color.parseColor("#99000000"));
                        }
                        break;
                    case 1:

                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#99000000")));
                        if (android.os.Build.VERSION.SDK_INT >= 21){
                            Window window = getWindow();
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            window.setStatusBarColor(Color.parseColor("#99000000"));
                        }
                        break;
                    case 2:
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#99000000")));
                        if (android.os.Build.VERSION.SDK_INT >= 21){
                            Window window = getWindow();
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            window.setStatusBarColor(Color.parseColor("#99000000"));
                        }
                        break;
                    case 3:
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#99000000")));
                        if (android.os.Build.VERSION.SDK_INT >= 21){
                            Window window = getWindow();
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            window.setStatusBarColor(Color.parseColor("#990000000"));
                        }
                        break;
                }
            }

        });

        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3A1212")));

    }








}
