package com.example.alenovan.popularmovies.detail;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.alenovan.popularmovies.R;
import com.example.alenovan.popularmovies.RecyclerViewClickListener;
import com.example.alenovan.popularmovies.RecyclerViewTouchListener;
import com.example.alenovan.popularmovies.adapter.*;
import com.example.alenovan.popularmovies.config.config;
import com.example.alenovan.popularmovies.controller.Controller_detail;
import com.example.alenovan.popularmovies.fragment.allmovies.fragment_all_movies;
import com.example.alenovan.popularmovies.item.*;
import com.example.alenovan.popularmovies.controller.DataHelper;
import com.roughike.bottombar.BottomBarBadge;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import android.database.sqlite.SQLiteDatabase;
//import okhttp3.Response;

public class detail_movie extends AppCompatActivity {
    SliderLayout slider_image;
    TextView judul_movies,deskripsi,company,image_text,text_review,addfavorite,text_thriller;
    RatingBar ratingBar;
    public boolean thriller = true;
    public boolean addfavorite_bol = true;
    public boolean cek_review = true;
    LinearLayout  linier_review;
    private RequestQueue requestQueue;
    private StringRequest request;
    HashMap<String,String> Hash_file_maps ;

    DataHelper dbHelper;
    private RecyclerView recyclerView,recycler_review;
    //private GridLayoutManager gridLayoutManager;
    private adapter_videos adapter;
    private adapter_review adapter_review;

    private ProgressDialog dialog;
    private List<item_videos> data_list;
    private List<item_review> data_list_review;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        Hash_file_maps = new HashMap<String, String>();

        dbHelper = new DataHelper(this);
        judul_movies =(TextView)findViewById(R.id.judul_movies);
        deskripsi =(TextView)findViewById(R.id.deskripsi);
        ratingBar =(RatingBar) findViewById(R.id.ratingBar);
        company =(TextView) findViewById(R.id.company);
        image_text =(TextView) findViewById(R.id.image_text);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recycler_review = (RecyclerView)findViewById(R.id.recycler_review);
        linier_review =(LinearLayout)findViewById(R.id.linier_review);
        text_review =(TextView) findViewById(R.id.text_review);
        addfavorite =(TextView) findViewById(R.id.addfavorite);
        text_thriller=(TextView) findViewById(R.id.text_thriller);

        final int  id = getIntent().getIntExtra("id",1);
        final String  backdrop =getIntent().getStringExtra("backdrop");
        //Toast.makeText(getApplication(), backdrop + " is long pressed!", Toast.LENGTH_SHORT).show();
        text_review.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_up_black_24dp, 0);
        text_thriller.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_down_black_24dp, 0);

        review(id);
        recycler_review.setVisibility(View.GONE);


        linier_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cek_review) {
                    text_review.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_down_black_24dp, 0);
                    recycler_review.setVisibility(View.VISIBLE);
                    cek_review =true;
                }else if(cek_review) {
                    text_review.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_up_black_24dp, 0);
                    recycler_review.setVisibility(View.GONE);
                    cek_review = false;


                }
            }



        });

        text_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cek_review) {
                    text_review.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_down_black_24dp, 0);
                    recycler_review.setVisibility(View.VISIBLE);
                    cek_review =true;
                }else if(cek_review) {
                    text_review.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_up_black_24dp, 0);
                    recycler_review.setVisibility(View.GONE);
                    cek_review = false;


                }
            }
        });

//        addfavorite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                tambah_favorite(id,backdrop);
//            }
//        });


        text_thriller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!thriller) {
                    text_thriller.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_down_black_24dp, 0);
                    recyclerView.setVisibility(View.VISIBLE);
                    thriller =true;
                }else if(thriller) {
                    text_thriller.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_up_black_24dp, 0);
                    recyclerView.setVisibility(View.GONE);
                    thriller = false;


                }
            }
        });



//  Thriler
        data_list = new ArrayList<>();
        get_video(id);
        adapter = new adapter_videos(this.getApplication(), data_list);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplication(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

//   Review




// Rating And Slider

        ratingBar.setFocusable(false);
        slider_image = (SliderLayout)findViewById(R.id.slider_image);
//        dialog = new ProgressDialog(getApplication());
//        dialog.setMessage("Loading...");
//        dialog.show();


        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getApplication(), recyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                String key = data_list.get(position).getUrl();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(config.UrlYoutube+key)));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        slider(id);

        createDatabase();

        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        if (getApplication()!=null) {
                            runOnUiThread(new Runnable(){
                                @Override
                                public void run(){
                                    openDatabase(id);
                                }
                            });
                        }
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();

    }

    public void slider(int id){
        request = new StringRequest(Method.GET, config.UrlDetail_first+id+config.UrlDetail_last, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //JSONArray Jarray = jsonObject.getJSONArray("production_companies");
                    Hash_file_maps.put(jsonObject.getString("original_title") + " " + "Image 1", config.UrlImage_Slider + jsonObject.getString("backdrop_path"));
                    Hash_file_maps.put(jsonObject.getString("original_title") + " " + "Image 2", config.UrlImage_Slider + jsonObject.getString("poster_path"));

//                    for (int i = 0; i < Jarray.length(); i++)
//                    {
//                        JSONObject object     = Jarray.getJSONObject(i);
//                        company.setText(object.getString("release_date"));
//                        }
                    company.setText(jsonObject.getString("release_date"));


                    for(String name : Hash_file_maps.keySet()){

                        TextSliderView textSliderView = new TextSliderView(detail_movie.this);
                        textSliderView
                                .description(name)
                                .image(Hash_file_maps.get(name))
                                .setScaleType(BaseSliderView.ScaleType.Fit);
                        textSliderView.bundle(new Bundle());
                        textSliderView.getBundle()
                                .putString("extra",name);
                        slider_image.addSlider(textSliderView);
                    }
                    slider_image.setPresetTransformer(SliderLayout.Transformer.Accordion);
                    slider_image.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                    //slider_image.setCustomAnimation(new DescriptionAnimation());
                    slider_image.setDuration(10000);
                    judul_movies.setText(jsonObject.getString("original_title"));
                    deskripsi.setText(jsonObject.getString("overview"));
                    ratingBar.setRating(jsonObject.getInt("vote_average")/2);

                } catch (JSONException ex) {
                    ex.printStackTrace();
                    Toast.makeText(getApplication(),"Eror", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<String, String>();
                hashMap.put("Content-Type", "application/json; charset=utf-8");;
                return hashMap;
            }
        };
        Controller_detail.getInstance(getApplication()).addToRequestQueue(request);

    }


    @Override
    protected void onStop() {

        slider_image.stopAutoCycle();

        super.onStop();
    }



    private void get_video(final int id) {

        AsyncTask<Integer,Void,Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(config.UrlVideo_first+id+config.UrlVideo_last)
                        .build();
                //
                try {
                    okhttp3.Response response = client.newCall(request).execute();
                    String jsonData = response.body().string();
                    JSONObject Jobject = new JSONObject(jsonData);
                    JSONArray Jarray = Jobject.getJSONArray("results");

                    for (int i=0; i<4; i++){
                        JSONObject object     = Jarray.getJSONObject(i);
                        item_videos a = new item_videos(Jobject.getInt("id"),object.getString("name"), object.getString("key"),config.UrlVideo_thumbnail+object.getString("key")+"/0.jpg");

                        data_list.add(a);
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

    private void get_review(final int id) {

        AsyncTask<Integer,Void,Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(config.UrlReview_first+id+config.UrlReview_last)
                        .build();
                //
                try {
                    okhttp3.Response response = client.newCall(request).execute();
                    String jsonData = response.body().string();
                    JSONObject Jobject = new JSONObject(jsonData);
                    JSONArray Jarray = Jobject.getJSONArray("results");

                    for (int i=0; i<Jarray.length(); i++){
                        JSONObject object     = Jarray.getJSONObject(i);
                        item_review a = new item_review(Jobject.getInt("id"),object.getString("author"), object.getString("content"), object.getString("author"));
                        data_list_review.add(a);
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
                adapter_review.notifyDataSetChanged();
            }

        };
        task.execute(id);
//

    }
    public void hideDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
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

    public int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public  void review(final int id){
        data_list_review = new ArrayList<>();
        get_review(id);
        adapter_review= new adapter_review(getApplication(), data_list_review);
        RecyclerView.LayoutManager review_manager = new GridLayoutManager(getApplication(), 1);
        recycler_review.setLayoutManager(review_manager);
        recycler_review.setItemAnimator(new DefaultItemAnimator());
        recycler_review.setAdapter(adapter_review);
    }

    public void tambah_favorite(final int id , final String backdrop){
        String judul = judul_movies.getText().toString().trim();
        //String backdrop = backdrop.getText().toString().trim();
        float vote = ratingBar.getRating();
        String query = "INSERT INTO favorite (id_movies,title,vote,backdrop) VALUES('"+id+"', '"+judul+"', '"+vote+"', '"+backdrop+"');";
        db.execSQL(query);
//        db.execSQL("DELETE FROM " + "favorite");
//        db.execSQL("VACUUM");
//        Toast.makeText(getApplicationContext(),"Saved Successfully", Toast.LENGTH_LONG).show();
    }


    public void delete_favorite(final int id , final String backdrop){
        String judul = judul_movies.getText().toString().trim();
        //String backdrop = backdrop.getText().toString().trim();
        float vote = ratingBar.getRating();
        String query = "Delete from favorite where id_movies="+id;
        db.execSQL(query);
//        Toast.makeText(getApplicationContext(),"Delete Successfully", Toast.LENGTH_LONG).show();
    }

    protected void createDatabase(){
        db=openOrCreateDatabase("favorite_db", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS favorite(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, id_movies VARCHAR,title VARCHAR,vote VARCHAR,backdrop VARCHAR);");
    }

    protected void openDatabase(final int id) {
        try {
            SQLiteDatabase db=openOrCreateDatabase("favorite_db",Context.MODE_APPEND,null);
            Cursor cr=db.rawQuery("SELECT id_movies FROM favorite where id_movies="+"'"+id+"'"+" Group by id_movies",null);
            if(cr!=null){
                if(cr.moveToFirst()){
                    do{

                        //Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                        addfavorite.setText("Your favorite Movies");
                        addfavorite.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final int  id = getIntent().getIntExtra("id",1);
                                final String  backdrop =getIntent().getStringExtra("backdrop");
                                delete_favorite(id,backdrop);
                            }
                        });
                        addfavorite_bol =true;
                    }while (cr.moveToNext());
                }else{
                        addfavorite.setText("Add favorite Movies");
                        addfavorite_bol =false;
                        addfavorite.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final int  id = getIntent().getIntExtra("id",1);
                                final String  backdrop =getIntent().getStringExtra("backdrop");
                                tambah_favorite(id,backdrop);
                            }
                        });

                }
            }
            cr.close();
            db.close();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();

        }
    }
}
