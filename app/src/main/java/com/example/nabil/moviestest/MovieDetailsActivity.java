package com.example.nabil.moviestest;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by nabil on 8/31/16.
 */
public class MovieDetailsActivity extends Activity {

    ImageView movieImage;
    TextView movieDescription;
    Button trailerButton;
    Button shareButton;
    Button showTimesButton;
    RatingBar movieRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_layout);

        Movie movie = (Movie) getIntent().getSerializableExtra("Movie");

        movieImage = (ImageView) findViewById(R.id.movieImage);
        movieDescription = (TextView) findViewById(R.id.movieDescription);
        trailerButton = (Button) findViewById(R.id.trailerButton);
        shareButton = (Button) findViewById(R.id.shareButton);
        showTimesButton = (Button) findViewById(R.id.showTimesButton);
        movieRate = (RatingBar) findViewById(R.id.movieRate);

        updateView(movie);
        addActionListeners(movie);
    }

    private void addActionListeners(final Movie movie ) {

        trailerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(Constants.MESSAGE_YOUTUBE_URL + movie.getTrailerURL()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, movie.toString());
                intent.setType("text/plain");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        showTimesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String searchQuery = movie.getName()
                        + " movie showtimes";
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, searchQuery);
                startActivity(intent);
            }
        });
    }

    private void updateView(Movie movie) {

        Picasso.with(this).load(movie.getImageURL()).into(movieImage);

        movieDescription.setText(movie.getOverview());
        movieRate.setRating(movie.getVotes());
        if (movie.getBelongsTo() == 0){
            ViewGroup layout = (ViewGroup) showTimesButton.getParent();
            layout.removeView(showTimesButton);
        }
    }
}


