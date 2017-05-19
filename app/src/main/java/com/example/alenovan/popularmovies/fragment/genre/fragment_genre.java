package com.example.alenovan.popularmovies.fragment.genre;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.alenovan.popularmovies.R;
import com.example.alenovan.popularmovies.RecyclerViewClickListener;
import com.example.alenovan.popularmovies.RecyclerViewTouchListener;
import com.example.alenovan.popularmovies.adapter.adapter_allmovies;
import com.example.alenovan.popularmovies.adapter.adapter_favorite;
import com.example.alenovan.popularmovies.adapter.adapter_genre;
import com.example.alenovan.popularmovies.config.config;
import com.example.alenovan.popularmovies.detail.detail_movie;
import com.example.alenovan.popularmovies.fragment.allmovies.fragment_all_movies;
import com.example.alenovan.popularmovies.fragment.favorite.favorite_fragment;
import com.example.alenovan.popularmovies.item.item_movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class fragment_genre extends Fragment {
    View myView;
    HashMap<String,String> Hash_file_maps ;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private adapter_genre adapter;

    private ProgressDialog dialog;
    private List<item_movies> data_list;


    public static fragment_genre newInstance() {
        fragment_genre fragment_genre = new fragment_genre();
        return fragment_genre;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_fragment_genre, container, false);
        recyclerView = (RecyclerView) myView.findViewById(R.id.recycler_view);
        SliderLayout mDemoSlider = (SliderLayout)myView.findViewById(R.id.slider_image);

        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");
        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);

        try {
            Glide.with(this).load(R.drawable.cover).into((ImageView) myView.findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }

       data_list = new ArrayList<>();
//        initCollapsingToolbar();
        load_data_from_server(0);
        adapter = new adapter_genre(getActivity(), data_list);
//
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2,dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(), recyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                String link = data_list.get(position).getVote_average();
                String title= data_list.get(position).getTitle();
                Bundle bundle=new Bundle();
                bundle.putString("link", link);
                bundle.putString("title", title);
                genre_activity genre_activity=new genre_activity();
                genre_activity.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,genre_activity).commit();

            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getActivity(), data_list.get(position).getVote_average() + " Rate This Film", Toast.LENGTH_SHORT).show();

            }
        }));


        return myView;
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout)myView.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("allll");
        AppBarLayout appBarLayout = (AppBarLayout)myView.findViewById(R.id.appbar);
        appBarLayout.setExpanded(false);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle("Favorite Movie");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }else{
                    collapsingToolbar.setTitle("Favorite Movie");
                    appBarLayout.getTotalScrollRange();
                }
            }
        });
    }



    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private void load_data_from_server(int id) {


            item_movies data1 = new item_movies(1,"Poupular Movies",config.UrlPopular,"http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
            item_movies data2= new item_movies(1,"Highhest Rate",config.UrlHighhestRate,"http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");
            item_movies data3 = new item_movies(1,"Up Comming",config.UrlUpcoming,"http://cdn3.nflximg.net/images/3093/2043093.jpg");
            item_movies data4 = new item_movies(1,"Now Playing",config.UrlNowPlaying,"http://cdn3.nflximg.net/images/3093/2043093.jpg");
            data_list.add(data1);
            data_list.add(data2);
            data_list.add(data3);
            data_list.add(data4);


    }
}
