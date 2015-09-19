package com.example.gautham.popularmovies;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MovieDetail extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
       String position = intent.getStringExtra("key");
        setContentView(R.layout.activity_movie_detail);
      int pos = Integer.parseInt(position);
        MovieDetailSetter(pos);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void MovieDetailSetter(int id){
        TextView titleView = (TextView)findViewById(R.id.movieTitleView);
        TextView releaseDateView = (TextView)findViewById(R.id.movieReleaseDateView);
        TextView userRatingView = (TextView)findViewById(R.id.movieUserRating);
        TextView descriptionView = (TextView)findViewById(R.id.movieDescriptionView);

        MainActivityFragment maf = new MainActivityFragment();
        MovieObject movieObject = maf.MovieObjectArray[id];
        titleView.setText(movieObject.getTitle());
        releaseDateView.setText(movieObject.getReleaseDate());
        userRatingView.setText(movieObject.getUserRating());
        descriptionView.setText(movieObject.getDescription());

    }
}
