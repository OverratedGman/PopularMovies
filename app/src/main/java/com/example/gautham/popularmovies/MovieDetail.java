package com.example.gautham.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.support.v7.widget.ShareActionProvider;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MovieDetail extends ActionBarActivity {
    private Context mContext;
    public static final  String BASE_URL = "http://image.tmdb.org/t/p/";
    public static final String IMAGE_SIZE = "w500";
    public final String LOG_TAG = "LOG_TAG";
    int pos;
    static String finalTrailerUrl="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_movie_detail);
        if(savedInstanceState ==null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_view_fragment, new MovieDetailFragment())
                    .commit();
        }
        Intent intent = getIntent();
        String position = intent.getStringExtra("key");
        pos = Integer.parseInt(position);
        MovieDetailSetter(pos);
        MovieObject movieObject = MainActivityFragment.MovieObjectArray[pos];
        Log.e("LOG-MovieID", movieObject.getID());
        new fetchTrailerTask().execute(movieObject.getID());
        final Button trailerButton = (Button) findViewById(R.id.trailerButton);
        trailerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalTrailerUrl));
                startActivity(browserIntent);
            }
        });
    }


    public class fetchTrailerTask extends AsyncTask<String, Void, String > {
        @Override
        protected String doInBackground(String... movieID) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String trailerURLResult;
            try {
                URL url = new URL("https://api.themoviedb.org/3/movie/" + movieID[0] + "/videos?api_key=ed2d4035999bef82e38aeab256677ccf");
                Log.v(LOG_TAG, "URL: " + url);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                trailerURLResult = buffer.toString();
                Log.v(LOG_TAG, "json:" + trailerURLResult);

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error with internet ", e);
                // If the code didn't successfully get the movie data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                    Log.d(LOG_TAG, "disconnected ");
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            JSONObject filmJsonSorter = null;
            final String OBJ_RESULTS = "results";
            final String OBJ_KEY_URL = "key";
            String trailer = "";
            try {
                filmJsonSorter = new JSONObject(trailerURLResult);
                JSONArray resultsArray = filmJsonSorter.getJSONArray(OBJ_RESULTS);
                JSONObject movieInfo = resultsArray.getJSONObject(0);
                trailer = movieInfo.getString(OBJ_KEY_URL);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            trailer = "https://www.youtube.com/watch?v=" + trailer;
            finalTrailerUrl =trailer;
            return trailer;
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

   public void MovieDetailSetter(int identification){
        TextView titleView = (TextView)findViewById(R.id.movieTitleView);
        TextView releaseDateView = (TextView)findViewById(R.id.movieReleaseDateView);
        TextView userRatingView = (TextView)findViewById(R.id.movieUserRating);
        TextView descriptionView = (TextView)findViewById(R.id.movieDescriptionView);
       ImageView imageView = (ImageView)findViewById(R.id.imageView);

       MovieObject movieObject = MainActivityFragment.MovieObjectArray[identification];
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
