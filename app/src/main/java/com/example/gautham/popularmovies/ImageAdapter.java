package com.example.gautham.popularmovies;

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

    private Activity activity;
    final String LOG_TAG = ImageAdapter.class.getSimpleName();

    public ImageAdapter() {
        super();
        MainActivity activity = new MainActivity();
        this.activity = activity;
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return MainActivityFragment.numberFilms;
    }

    @Override
    public String getItem(int position) {
        // TODO Auto-generated method stub
        return MainActivityFragment.finalBackdropPathStrArray[position-1];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public static class ViewHolder
    {
        public ImageView imgView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder view;
        LayoutInflater inflater = activity.getLayoutInflater();

        if(convertView==null)
        {
            view = new ViewHolder();
            convertView = inflater.inflate(R.layout.gridview_element, null);

            view.imgView = (ImageView) convertView.findViewById(R.id.imageView1);

            convertView.setTag(view);
        }
        else
        {
            view = (ViewHolder) convertView.getTag();
        }
        MainActivityFragment fragment = new MainActivityFragment();
        for(int i=0; i>fragment.numberFilms;i++) {
            Picasso.with(activity).load(getItem(i)).into(view.imgView);
        }
        Log.e(LOG_TAG,"just updated view");
        return convertView;
    }
}