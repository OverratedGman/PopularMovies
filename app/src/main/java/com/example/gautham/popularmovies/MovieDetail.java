package com.example.gautham.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class MovieDetail extends ActionBarActivity {
    private Context mContext;
    public static final  String BASE_URL = "http://image.tmdb.org/t/p/";
    public static final String IMAGE_SIZE = "w500";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent intent = getIntent();
       String position = intent.getStringExtra("key");

      int pos = Integer.parseInt(position);
        MovieDetailSetter(pos);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return false;
    }
   public void MovieDetailSetter(int id){
        TextView titleView = (TextView)findViewById(R.id.movieTitleView);
        TextView releaseDateView = (TextView)findViewById(R.id.movieReleaseDateView);
        TextView userRatingView = (TextView)findViewById(R.id.movieUserRating);
        TextView descriptionView = (TextView)findViewById(R.id.movieDescriptionView);
       ImageView imageView = (ImageView)findViewById(R.id.imageView);

       MovieObject movieObject = MainActivityFragment.MovieObjectArray[id];
       titleView.setText(movieObject.getTitle());
       releaseDateView.setText(movieObject.getReleaseDate());
       userRatingView.setText(movieObject.getUserRating());
       descriptionView.setText(movieObject.getDescription());
       descriptionView.setMovementMethod(new ScrollingMovementMethod());
       String url = String.format("%s/%s/%s", BASE_URL, IMAGE_SIZE,movieObject.getBackDropPath());
       mContext = getApplicationContext();
       Picasso.with(mContext).load(url).into(imageView);

    }
}
