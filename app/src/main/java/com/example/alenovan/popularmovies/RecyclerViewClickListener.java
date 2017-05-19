package com.example.alenovan.popularmovies;

/**
 * Created by alenovan on 5/13/17.
 */

import android.view.View;

/**
 * Created by alenovan on 4/28/17.
 */

public interface RecyclerViewClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}