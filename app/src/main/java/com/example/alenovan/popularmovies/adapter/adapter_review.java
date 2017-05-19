package com.example.alenovan.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.alenovan.popularmovies.R;
import com.example.alenovan.popularmovies.item.*;

import java.util.List;

/**
 * Created by alenovan on 5/14/17.
 */

public class adapter_review extends RecyclerView.Adapter<adapter_review.MyViewHolder> {

private Context mContext;
private List<item_review> albumList;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView title, count,url,image_text;
    public ImageView thumbnail, overflow;

    public MyViewHolder(View view) {
        super(view);
        title = (TextView) view.findViewById(R.id.title);
        count = (TextView) view.findViewById(R.id.count);
        url = (TextView) view.findViewById(R.id.url);
        image_text = (TextView) view.findViewById(R.id.image_text);
        thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        overflow = (ImageView) view.findViewById(R.id.overflow);
        thumbnail.setVisibility(View.GONE);
        overflow.setVisibility(View.GONE);
        url.setVisibility(View.GONE);
    }
}


    public adapter_review(Context mContext, List<item_review> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_review, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        item_review album = albumList.get(position);
        holder.title.setText("FROM"+"   :  " +album.getTitle());
        holder.count.setText(album.getVote_average());
        holder.image_text.setText(album.getImage_link().toString().substring(0,1));

        Glide.with(mContext).load(album.getImage_link()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new adapter_review.MyMenuItemClickListener());
        popup.show();
    }

/**
 * Click listener for popup menu items
 */
class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

    public MyMenuItemClickListener() {
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
                /*case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;*/
            case R.id.action_play_next:
                Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                return true;
            default:
        }
        return false;
    }
}

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}

