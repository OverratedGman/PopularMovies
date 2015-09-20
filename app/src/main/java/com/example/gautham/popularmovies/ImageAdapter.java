package com.example.gautham.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class ImageAdapter extends BaseAdapter
{

     MovieObject[] movieData;
    final String LOG_TAG = ImageAdapter.class.getSimpleName();
    private Context mContext;
    public static final  String BASE_URL = "http://image.tmdb.org/t/p/";
    public static final String IMAGE_SIZE = "w500";

    public ImageAdapter(Context c) {
        mContext = c;
    };
    public ImageAdapter(Context c, MovieObject[] movieDataObjectArray){
        this(c);
        this.movieData = movieDataObjectArray;
    }

    @Override
    public int getCount() {
        return movieData != null?movieData.length:0;
    }

    public void clear(){
        movieData = null;
    }

    @Override
    public Object getItem(int position) {
        return movieData[position];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public void addAll(MovieObject[] Add){
        movieData = Add;
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
        MovieObject m =movieData[position];
        Log.v(LOG_TAG,"BDP="+m.getBackDropPath());
        String url = String.format("%s/%s/%s", BASE_URL, IMAGE_SIZE,m.getBackDropPath());
        Picasso.with(mContext).load(url).into(imageView);
        Log.v(LOG_TAG,"final url:"+url);
        return imageView;
    }

}