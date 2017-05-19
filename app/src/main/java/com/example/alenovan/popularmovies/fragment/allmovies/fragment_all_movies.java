package com.example.alenovan.popularmovies.fragment.allmovies;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bumptech.glide.Glide;
import com.example.alenovan.popularmovies.R;

import com.example.alenovan.popularmovies.RecyclerViewClickListener;
import com.example.alenovan.popularmovies.RecyclerViewTouchListener;
import com.example.alenovan.popularmovies.item.*;
import com.example.alenovan.popularmovies.config.config;
import com.example.alenovan.popularmovies.detail.detail_movie;
import com.example.alenovan.popularmovies.adapter.adapter_allmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import android.support.v4.app.Fragment;
import android.widget.Toast;


public class fragment_all_movies extends Fragment {
    View myView;

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private adapter_allmovies adapter;

    private ProgressDialog dialog;
    private List<item_movies> data_list;



    public static fragment_all_movies newInstance() {
        fragment_all_movies fragment_all_movies = new fragment_all_movies();
        return fragment_all_movies;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_fragment_all_movies, container, false);
        recyclerView = (RecyclerView) myView.findViewById(R.id.recycler_view);
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.show();


        data_list = new ArrayList<>();
        load_data_from_server(0);
        initCollapsingToolbar();
        adapter = new adapter_allmovies(getActivity(), data_list);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2,dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(), recyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                int id = data_list.get(position).getId();
                String backdrop = data_list.get(position).getImage_link();
                Intent detail_movies = new Intent(getActivity(), detail_movie.class);
                detail_movies.putExtra("id", id);
                detail_movies.putExtra("backdrop", backdrop);
                startActivity(detail_movies);
               // Toast.makeText(getActivity(), data_list.get(position).getImage_link() + " is long pressed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getActivity(), data_list.get(position).getVote_average() + " Rate This Film", Toast.LENGTH_SHORT).show();

            }
        }));

        try {
            Glide.with(this).load(R.drawable.cover).into((ImageView)myView.findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }


        return myView;
    }



    private void load_data_from_server(int id) {

        AsyncTask<Integer,Void,Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(config.UrlPopular)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String jsonData = response.body().string();
                    JSONObject Jobject = new JSONObject(jsonData);
                    JSONArray Jarray = Jobject.getJSONArray("results");

                    for (int i=0; i<Jarray.length(); i++){
                        JSONObject object     = Jarray.getJSONObject(i);
//                        item_movies a = new item_movies(1,object.getString("name"), "7.0", "Aaa");
//                        data_list.add(a);

                        item_movies data = new item_movies(object.getInt("id"),object.getString("original_title"),object.getString("vote_average") +" Rate","https://image.tmdb.org/t/p/original"+object.getString("poster_path"));
                        data_list.add(data);
                        hideDialog();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End Of Content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid){
                adapter.notifyDataSetChanged();
            }

        };
        task.execute(id);
//

    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout)myView.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout)myView.findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
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

    public void hideDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}