package com.example.alenovan.popularmovies.fragment.favorite;



import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
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

import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.alenovan.popularmovies.R;
import com.example.alenovan.popularmovies.RecyclerViewClickListener;
import com.example.alenovan.popularmovies.RecyclerViewTouchListener;
import com.example.alenovan.popularmovies.adapter.adapter_favorite;
import com.example.alenovan.popularmovies.detail.detail_movie;
import com.example.alenovan.popularmovies.fragment.allmovies.fragment_all_movies;
import com.example.alenovan.popularmovies.item.item_movies;

import java.util.ArrayList;
import java.util.List;

public class favorite_fragment extends Fragment {
    View myView;
    TextView coba;
    private SQLiteDatabase db;
    private static final String SELECT_SQL = "SELECT * FROM favorite";
    private Cursor c;

    private adapter_favorite adapter;

    private List<item_movies> data_list;


    private RecyclerView recyclerView;



    public static favorite_fragment newInstance() {
        favorite_fragment favorite_fragment = new favorite_fragment();
        return favorite_fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_favorite_fragment, container, false);
        recyclerView = (RecyclerView) myView.findViewById(R.id.recycler_view);
        initCollapsingToolbar();

        data_list = new ArrayList<>();
        adapter = new adapter_favorite(getActivity(), data_list);
        openDatabase();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2,dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

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

    protected void openDatabase() {
        try {
            SQLiteDatabase db=getActivity().openOrCreateDatabase("favorite_db",Context.MODE_APPEND,null);
            Cursor cr=db.rawQuery("SELECT * FROM favorite GROUP BY id_movies",null);
            if(cr!=null){
                if(cr.moveToFirst()){
                    do{

                        int id_movies=cr.getInt(cr.getColumnIndex("id_movies"));
                        String title=cr.getString(cr.getColumnIndex("title"));
                        String vote=cr.getString(cr.getColumnIndex("vote"));
                        String backdrop=cr.getString(cr.getColumnIndex("backdrop"));
                        item_movies data = new item_movies(id_movies,title,vote,backdrop);
                        data_list.add(data);
                        recyclerView.setAdapter(adapter);


                    }while (cr.moveToNext());
                }else{
                    Toast.makeText(getActivity(), "No Data to show", Toast.LENGTH_LONG).show();

                }
            }
            cr.close();
            db.close();
        }catch (Exception e){
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();

        }
    }


    //}

    protected void showRecords() {
//        String id = c.getString(0);
//        String name = c.getString(1);
//        String add = c.getString(2);
        Toast.makeText(getActivity(),  " is long pressed!", Toast.LENGTH_SHORT).show();
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



}