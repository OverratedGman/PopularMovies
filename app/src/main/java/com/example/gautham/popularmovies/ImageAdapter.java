package com.example.gautham.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.view.LayoutInflater;

public class ImageAdapter extends BaseAdapter
{

    private String[] posters;
    final String LOG_TAG = ImageAdapter.class.getSimpleName();
    private Context mContext;
    public static final  String BASE_URL = "http://image.tmdb.org/t/p/";
    public static final String IMAGE_SIZE = "w185";

    public ImageAdapter(Context c) {
        mContext = c;
    };
    public ImageAdapter(Context c, String[] posters){
        this(c);
        this.posters = posters;
        ?
    }

    @Override
    public int getCount() {
        return posters != null?posters.length:0;
    }

    @Override
    public String getItem(int position) {
        return posters[position];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public void addAll(String[] uris){
        posters = uris;
    }

    public static class ViewHolder
    {
        public ImageView imgView;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            imageView.setAdjustViewBounds(true);
            imageView.setPadding(1, 1, 1, 1);
        } else {
            imageView = (ImageView) convertView;
        }
        Picasso.with(mContext).load(String.format("%s/%s/%s", BASE_URL, IMAGE_SIZE, posters[position])).into(imageView);
        return imageView;
    }

}