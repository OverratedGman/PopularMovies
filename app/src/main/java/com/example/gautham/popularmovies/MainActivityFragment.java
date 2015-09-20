package com.example.gautham.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivityFragment extends Fragment {

    public String movieJsonStr = null;
    public static String[] backdropPathStrArray;
    public static MovieObject[] MovieObjectArray;
    final String LOG_TAG = MainActivityFragment.class.getSimpleName();
    public static int numberFilms =0;
    ImageAdapter imageAdapter;
    public static String prefTemp = "popularity.desc";
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
       imageAdapter = new ImageAdapter(getActivity(),MovieObjectArray);

        // Set custom adapter to gridview
        GridView gridView = (GridView) rootView.findViewById(R.id.gridView);
        gridView.setAdapter(imageAdapter);
       final Intent intent = new Intent(getActivity(), com.example.gautham.popularmovies.MovieDetail.class);

        AdapterView.OnItemClickListener Listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v(LOG_TAG,position+"/+/"+id);
                String pos =""+position;
                 intent.putExtra("key", pos);
                getActivity().startActivity(intent);
            }
        };
        gridView.setOnItemClickListener(Listener);

        return rootView;
    }

    public void updateMovies() {
        fetchMovieTask fetchInfo = new fetchMovieTask();
        fetchInfo.execute(prefTemp);

    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();

    }


    public class fetchMovieTask extends AsyncTask<String, Void, MovieObject[]> {

        public Object[] getMovieDataFromJson(String movieStr)
                throws JSONException {
            // These are the names of the JSON objects that need to be extracted.
            final String OBJ_BACKDROP_PATH = "backdrop_path";
            final String OBJ_TITLE = "original_title";
            final String OBJ_RESULTS = "results";
            final String OBJ_OVERVIEW = "overview";
            final String OBJ_RELEASE_DATE = "release_date";
            final String OBJ_USER_RATING = "vote_average";

            JSONObject filmJsonSorter = new JSONObject(movieStr);
            JSONArray resultsArray = filmJsonSorter.getJSONArray(OBJ_RESULTS);
            numberFilms =resultsArray.length();


                     backdropPathStrArray = new String[resultsArray.length()];
                     MovieObjectArray = new MovieObject[resultsArray.length()];


            for(int y=0; y<resultsArray.length();y++) {

                JSONObject movieInfo = resultsArray.getJSONObject(y);
                String bdp= movieInfo.getString(OBJ_BACKDROP_PATH);
                String mtitle = movieInfo.getString(OBJ_TITLE);
                String mdescription = movieInfo.getString(OBJ_OVERVIEW);
                String reldat = movieInfo.getString(OBJ_RELEASE_DATE);
                String ur = movieInfo.getString(OBJ_USER_RATING);
                MovieObject MObject = new MovieObject(bdp,mtitle,mdescription,reldat,ur);
                MovieObjectArray[y]=MObject;
            } //loop end

            return MovieObjectArray;

        }


        @Override
        protected MovieObject[] doInBackground(String... params) {
            if (params.length == 0) {

                return null;
            }
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {
                final String FORECAST_BASE_URL = "https://api.themoviedb.org/3/discover/movie?";
                final String SORT_MODE = "sort_by";
                final String KEY = "ed2d4035999bef82e38aeab256677ccf";
                final String API_KEY = "api_key";

                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(SORT_MODE, params[0])
                        .appendQueryParameter(API_KEY, KEY)
                        .build();

                URL url = new URL(builtUri.toString());
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
                movieJsonStr = buffer.toString();
                Log.v(LOG_TAG,"json:"+movieJsonStr);

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error with internet ", e);
                // If the code didn't successfully get the movie data, there's no point in attemping
                // to parse it.
                return MovieObjectArray;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                    Log.d(LOG_TAG,"disconnected ");
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                getMovieDataFromJson(movieJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return MovieObjectArray;
        }

       @Override
        protected void onPostExecute(MovieObject[] ope) {
            super.onPostExecute(ope);
            if(ope != null) {
                imageAdapter.clear();
                imageAdapter.addAll(ope);
                imageAdapter.notifyDataSetChanged();
            }
        }

    }
}
