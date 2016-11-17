package com.example.nabil.moviestest;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Nabil on 4/11/2016.
 */
public class MoviesActivity extends Activity {
    ListView moviesListView;
    ArrayList <Movie> moviesList;
    MoviesAdapter moviesAdapter;
    char choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_activity);
        Intent intent = getIntent();
        char choice = intent.getCharExtra("choice", '1');


        moviesListView = (ListView) findViewById(R.id.moviesListView);
        moviesList = new ArrayList<Movie>();
        runProgram(choice);
        moviesAdapter = new MoviesAdapter(getApplicationContext(), R.layout.movies_layout, moviesList);
        moviesListView.setAdapter(moviesAdapter);
        moviesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MoviesActivity.this, MovieDetailsActivity.class);
                intent.putExtra( "Movie", moviesList.get( i ) );
                MoviesActivity.this.startActivity( intent );
            }
        });
     }


    //method that returns the string for the API call
    private String getAPIString(char choice){

        String url = null;
        if (choice == 1){
            url = Constants.MESSAGE_NOW_PLAYING + Constants.MESSAGE_API_KEY1;
        }
        else {
            url =  Constants.MESSAGE_COMING_SOON + Constants.MESSAGE_API_KEY2;
        }

        this.choice = choice;
        return url;
    }

    private void runProgram(char choice) {
        String url = getAPIString(choice);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        new getMovies().execute(response);
                        moviesAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error", error.getStackTrace().toString() );
                    }
                });

        ApplicationController.getInstance(this).addToRequestQueue(jsObjRequest);
    }

    private class getMovies extends AsyncTask<JSONObject, Void, Void> {
        @Override
        protected Void doInBackground(JSONObject... response) {
            JSONArray jsonArray = null;
            JSONObject jsonObject = response[0];
            String imageBaseURL = "http://image.tmdb.org/t/p/w500";
            String trailerBaseURL = "http://api.themoviedb.org/3/movie/";

            try {
                jsonArray = jsonObject.getJSONArray(Constants.MESSAGE_RESULTS);
                    for (int i = 0; i < jsonArray.length(); i++){
                    final Movie movie = new Movie();
                    JSONObject o = jsonArray.getJSONObject(i);
                    movie.setName(o.getString(Constants.MESSAGE_TITLE));
                    movie.setReleaseDate(o.getString(Constants.MESSAGE_RELEASE_DATE));
                    movie.setOverview(o.getString(Constants.MESSAGE_OVERVIEW));
                    movie.setBelongsTo(MoviesActivity.this.choice);
                    float tempVal = Float.parseFloat(o.getString(Constants.MESSAGE_VOTE_AVERAGE));
                    movie.setVotes((tempVal*5)/10);
                    movie.setImageURL(imageBaseURL + o.getString(Constants.MESSAGE_POSTER_PATH)
                            + "?&api_key=" + Constants.MESSAGE_API_KEY1);

                    JsonObjectRequest jsObjRequest = new JsonObjectRequest
                            (Request.Method.GET,  trailerBaseURL + o.getString(Constants.MESSAGE_ID)
                                    + "/videos?api_key=" + Constants.MESSAGE_API_KEY2
                                    , null, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        movie.setTrailerURL(response.getJSONArray(Constants.MESSAGE_RESULTS)
                                                .getJSONObject(0)
                                                .getString(Constants.MESSAGE_KEY));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    Log.d("Error", error.getStackTrace().toString());
                                }
                            });

                    // Access the RequestQueue through your singleton class.
                    ApplicationController.getInstance(MoviesActivity.this).addToRequestQueue(jsObjRequest);
                    moviesList.add(movie);
                }
            } catch (JSONException e) {
                Log.d("Error", e.getStackTrace().toString());
            }
            return null;
        }
    }
}
