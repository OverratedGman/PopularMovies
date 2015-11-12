package com.example.gautham.popularmovies;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.BaseAdapter;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        MainActivityFragment maf = (MainActivityFragment)getSupportFragmentManager().findFragmentById(R.id.movie_view_fragment);        //noinspection SimplifiableIfStatement
        if (id == R.id.popularMenuItem) {
            maf.prefTemp="popularity.desc";
        }
        if (id == R.id.ratingMenuItem) {
            maf.prefTemp="vote_average.desc";
        }
        maf.updateMovies();
        return super.onOptionsItemSelected(item);
    }
}
